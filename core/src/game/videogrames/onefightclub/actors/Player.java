package game.videogrames.onefightclub.actors;

import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class Player extends MovingSprite {
	public static final String IDLE_FILEPATH = "images/soldier_idle.png";
	public static final String WALK_FILEPATH = "images/soldier_walk.png";
	public static final String ATTACK_FILEPATH = "images/soldier_attack.png";

	private Array<TextureRegion> idleAnimation;
	private Array<TextureRegion> walkAnimation;
	// private Array<TextureRegion> attackAnimation;

	private Sound sound_jump;
	private Sound sound_walk;

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean isGrounded = false;
	private boolean isDead = false;
	private boolean isAttacking = false;

	private Weapon weapon;
	private int health = 6;

	public Player(Body body) {
		super(body);

		// create idle animation
		Texture idleTexture = new Texture(Gdx.files.internal(IDLE_FILEPATH));
		idleAnimation = new Array<TextureRegion>(TextureRegion.split(idleTexture, 36, 60)[0]);

		// create walk animation
		Texture walkTexture = new Texture(Gdx.files.internal(WALK_FILEPATH));
		walkAnimation = new Array<TextureRegion>(TextureRegion.split(walkTexture, 36, 60)[0]);

		// create attack animation
		// Texture attackTexture = new Texture(Gdx.files.internal(ATTACK_FILEPATH));
		// attackAnimation = new Array<TextureRegion>(TextureRegion.split(attackTexture, 60, 60)[0]);

		setAnimation(1 / 12.0f, idleAnimation);

		sound_jump = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Jump.wav"));

		sound_walk = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Walk.wav"));
		sound_walk.loop(0.1f);
		sound_walk.pause();

		weapon = new Melee(body, this);
	}

	public void updateMotion() {
		if (movingLeft) {
			body.setLinearVelocity(-Constants.RUN_VELOCITY, body.getLinearVelocity().y);
			this.setAnimation(1 / 12.0f, walkAnimation);
			this.setFacingRight(false);
		} else if (movingRight) {
			body.setLinearVelocity(Constants.RUN_VELOCITY, body.getLinearVelocity().y);
			this.setAnimation(1 / 12.0f, walkAnimation);
			this.setFacingRight(true);
		} else {
			this.setAnimation(1 / 12.0f, idleAnimation);
		}
	}

	public void setMovingLeft(boolean b) {
		if (movingRight && b) {
			movingRight = false;
		}
		this.movingLeft = b;
	}

	public void setMovingRight(boolean b) {
		if (movingLeft && b) {
			movingLeft = false;
		}
		this.movingRight = b;
	}

	public void startAttack() {
		// perform short range attack
		if (weapon instanceof Melee) {
			PolygonShape shape = new PolygonShape();
			boolean direction = getFacingRight();
			shape.setAsBox(13.0f / PPM, 30.0f / PPM, new Vector2(direction ? 32.0f / PPM : -32.0f / PPM, 0.0f / PPM),
					0);
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.filter.categoryBits = Constants.BIT_WEAPON;
			fdef.filter.maskBits = Constants.BIT_ENEMY;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData(weapon);
			weapon.setActive(true);
		}

	}

	public void stopAttack() {
		Array<Fixture> fixtures = body.getFixtureList();
		for (Fixture fix : fixtures) {
			if (fix.getUserData().equals(weapon)) {
				body.destroyFixture(fix);
			}
		}
		weapon.setActive(false);
	}

	public void render(SpriteBatch sb) {
		super.render(sb);
		if (weapon.isActive()) {
			weapon.render(sb);
		}

		if ((movingRight || movingLeft) && isGrounded) {
			sound_walk.resume();
		} else {
			sound_walk.pause();
		}
	}

	public void setGrounded(boolean b) {
		this.isGrounded = b;
	}

	public void jump() {
		sound_jump.play(0.08f);
		body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY);
	}

	public void setIsDead(boolean b) {
		isDead = b;
	}

	public boolean getIsDead() {
		return isDead;
	}

	public void takeDamage(int damage) {
		health += -damage;
		if (health == 0) {
			isDead = true;
			// this.getBody().getWorld().destroyBody(this.getBody());
		}
	}
}
