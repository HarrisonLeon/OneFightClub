package game.videogrames.onefightclub.actors;

import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
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
	public static final String PLAYER_FILEPATH = "images/enemy_spider.png";

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean isDead = false;
	private int spawnpoint;

	public Enemy(Body body) {
		super(body);

		Texture enemyTexture = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> enemyAnimation = new Array<TextureRegion>(TextureRegion.split(enemyTexture, 62, 46)[0]);

		setAnimation(1 / 4.0f, enemyAnimation);
		movingRight = true;
		setFacingRight(true);

		// main bounding box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(31.0f / PPM, 23.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_PLAYER | Constants.BIT_WEAPON;
		fdef.friction = 0.0f;
		body.createFixture(fdef).setUserData("enemy");

		// left foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(-24.0f / PPM, -23.0f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_EDGE | Constants.BIT_PLAYER;
		fdef.friction = 2.0f;
		body.createFixture(fdef).setUserData("enemy.foot");

		// right foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(24.0f / PPM, -23.0f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ENEMY;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_EDGE | Constants.BIT_PLAYER;
		fdef.friction = 2.0f;
		body.createFixture(fdef).setUserData("enemy.foot");
	}

	public void render(SpriteBatch sb) {
		super.render(sb);

	}

	public void setSpawn(int num) {
		spawnpoint = num;
	}

	public void updateMotion() {
		if (movingLeft) {
			body.setLinearVelocity(-Constants.ENEMY_VELOCITY / 2, body.getLinearVelocity().y);
		}
		if (movingRight) {
			body.setLinearVelocity(Constants.ENEMY_VELOCITY / 2, body.getLinearVelocity().y);
		}
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

	public void killEnemy() {
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
