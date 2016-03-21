package game.videogrames.onefightclub.screens;

import static game.videogrames.onefightclub.utils.Constants.APP_HEIGHT;
import static game.videogrames.onefightclub.utils.Constants.APP_WIDTH;
import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen extends OFCScreen
{
	private World				world;
	private Box2DDebugRenderer	debugRenderer;

	private OrthographicCamera	b2dCamera;

	public GameScreen(Game game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		world = new World(new Vector2(0.0f, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();

		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, APP_WIDTH / PPM, APP_HEIGHT / PPM);

		// create platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(0.0f / PPM, 0.0f / PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(APP_WIDTH / PPM, 5.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("ground");

		// create falling box (represents approx size of player)
		bdef.position.set(160.0f / PPM, 200.0f / PPM);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);

		shape.setAsBox(20.0f / PPM, 16.0f / PPM);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("box");
	}

	@Override
	public void render(float delta)
	{
		b2dCamera.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, b2dCamera.combined);

		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void hide()
	{
		Gdx.app.debug("One Fight Club", "dispose game screen");
	}

}