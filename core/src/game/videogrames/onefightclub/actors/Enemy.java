package game.videogrames.onefightclub.actors;

import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class Enemy extends MovingSprite {
	public static final String PLAYER_FILEPATH = "images/bunny.png";

	private Sound sound_death;

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean isGrounded = false;
	private boolean isDead = false;
	private int spawnpoint;

	public Enemy(Body body) {
		super(body);

		Texture enemyTexture = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> enemyAnimation = new Array<TextureRegion>(TextureRegion.split(enemyTexture, 32, 32)[0]);

		setAnimation(1 / 12.0f, enemyAnimation);
		movingRight = true;
		setFacingRight(true);

		// main bounding box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(16.0f / PPM, 16.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_PLAYER | Constants.BIT_WEAPON;
		fdef.friction = 0.0f;
		body.createFixture(fdef).setUserData("enemy");

		// foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(-13.5f / PPM, -15.0f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_EDGE | Constants.BIT_PLAYER;
		fdef.friction = 2.0f;
		body.createFixture(fdef).setUserData("enemy.foot");

		// foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(13.5f / PPM, -15.0f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_EDGE | Constants.BIT_PLAYER;
		fdef.friction = 2.0f;
		body.createFixture(fdef).setUserData("enemy.foot");

		sound_death = Gdx.audio.newSound(Gdx.files.internal("sounds/Enemy_Death.wav"));
	}

	public void render(SpriteBatch sb) {
		super.render(sb);

	}

	public void setSpawn(int num) {
		spawnpoint = num;
	}

	public void updateMotion() {
		if (movingLeft) {
			body.setLinearVelocity(-Constants.RUN_VELOCITY / 2, body.getLinearVelocity().y);
		}
		if (movingRight) {
			body.setLinearVelocity(Constants.RUN_VELOCITY / 2, body.getLinearVelocity().y);
		}
	}

	public void jump() {
		body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY);
	}

	public void moveLeft() {
		if (movingRight) {
			movingRight = false;
		}
		this.movingLeft = true;
		setFacingRight(false);
	}

	public void moveRight() {
		if (movingLeft) {
			movingLeft = false;
		}
		this.movingRight = true;
		setFacingRight(true);
	}

	public void setGrounded(boolean b) {
		this.isGrounded = b;
	}

	public void killEnemy() {
		sound_death.play(0.045f);
		this.getBody().getWorld().destroyBody(this.getBody());
	}

	public boolean isDead() {
		return isDead;
	}

	public void setIsDead(boolean b) {
		isDead = b;
	}

	public int getSpawn() {
		return spawnpoint;
	}
}
