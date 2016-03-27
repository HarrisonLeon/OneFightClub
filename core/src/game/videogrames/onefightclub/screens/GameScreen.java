package game.videogrames.onefightclub.screens;

import static game.videogrames.onefightclub.utils.Constants.APP_HEIGHT;
import static game.videogrames.onefightclub.utils.Constants.APP_WIDTH;
import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.actors.Player;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.OFCContactListener;

public class GameScreen extends OFCScreen
{
	private World				world;
	private OFCContactListener	cl;
	private Box2DDebugRenderer	debugRenderer;
	private OrthographicCamera	b2dCamera;

	private OrthographicCamera	mainCam;
	private SpriteBatch			sb;

	private BodyDef				bdef;
	private Body				body;

	private Body				playerBody;
	private Player				player;

	public GameScreen(Game game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		world = new World(new Vector2(0.0f, Constants.GRAVITY), true);
		cl = new OFCContactListener();
		world.setContactListener(cl);
		debugRenderer = new Box2DDebugRenderer();

		sb = ((OneFightClub) game).getSpriteBatch();
		mainCam = ((OneFightClub) game).getMainCam();

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode)
			{
				switch (keycode)
				{
				case Keys.A: // move left
					player.setMovingLeft(true);
					break;
				case Keys.D: // move right
					player.setMovingRight(true);
					break;
				case Keys.W: // jump
					if (cl.isPlayerGrounded())
					{
						player.jump();
					}
					break;
				case Keys.SPACE: // attack
					player.attack();
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode)
			{
				switch (keycode)
				{
				case Keys.A: // stopped moving left
					player.setMovingLeft(false);
					break;
				case Keys.D: // stopped moving right
					player.setMovingRight(false);
					break;
				}
				return true;
			}
		});

		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, APP_WIDTH / PPM, APP_HEIGHT / PPM);

		createPlayer();

		// create platform
		bdef = new BodyDef();
		bdef.position.set(0.0f / PPM, 0.0f / PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(APP_WIDTH / PPM, 5.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_GROUND;
		fdef.filter.maskBits = Constants.BIT_PLAYER;
		body.createFixture(fdef).setUserData("ground");

		shape.dispose();
	}

	public void createPlayer()
	{
		// create player
		BodyDef bdef = new BodyDef();
		bdef.position.set(160.0f / PPM, 200.0f / PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40.0f / PPM, 32.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND;
		fdef.friction = 2.0f;
		playerBody.createFixture(fdef).setUserData("player");

		// create foot sensor
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(0.0f, -32.0f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("player.foot");

		player = new Player(playerBody);
		playerBody.setUserData(player);
	}

	@Override
	public void render(float delta)
	{
		// processInput();

		b2dCamera.update();
		mainCam.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.setProjectionMatrix(mainCam.combined);
		player.updateMotion();
		player.render(sb);

		debugRenderer.render(world, b2dCamera.combined);

		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void hide()
	{
		Gdx.app.debug("One Fight Club", "dispose game screen");
	}
}