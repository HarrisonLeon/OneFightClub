package game.videogrames.onefightclub.actors;

import static game.videogrames.onefightclub.utils.Constants.PPM;

import java.util.Random;

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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import game.videogrames.onefightclub.screens.GameScreen;
import game.videogrames.onefightclub.screens.Hud;
import game.videogrames.onefightclub.utils.Constants;

public class Player extends MovingSprite {
	private Array<TextureRegion> idleAnimation;
	private Array<TextureRegion> walkAnimation;

	private Sound sound_jump;
	private Sound sound_walk;
	private Sound sound_powerup;

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean isGrounded = false;
	private boolean isDead = false;
	private boolean gameover = false;

	private Array<Float> modifiers;

	private Timer timer;

	private Weapon weapon;
	private int health = 6;

	private Hud hud;

	private boolean isRespawning = false;

	private GameScreen gs;

	public Player(Body body, Hud hud) {
		super(body);

		modifiers = new Array<Float>();
		for (int i = 0; i < 2; i++) {
			modifiers.add(1.0f);
		}

		timer = new Timer();

		this.hud = hud;

		// create idle animation
		Texture idleTexture = new Texture(
				Gdx.files.internal(Constants.IdleCharacterSprites[Constants.ui.character() - 1]));
		idleAnimation = new Array<TextureRegion>(TextureRegion.split(idleTexture, 36, 60)[0]);

		// create walk animation
		Texture walkTexture = new Texture(
				Gdx.files.internal(Constants.WalkingCharacterSprites[Constants.ui.character() - 1]));
		walkAnimation = new Array<TextureRegion>(TextureRegion.split(walkTexture, 36, 60)[0]);

		setAnimation(1 / 12.0f, idleAnimation);

		sound_jump = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Jump.wav"));
		sound_powerup = Gdx.audio.newSound(Gdx.files.internal("sounds/Powerup.wav"));
		sound_walk = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Walk.wav"));
		sound_walk.loop(0.1f);
		sound_walk.pause();

		// main bounding box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(18.0f / PPM, 30.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_ENEMY;
		fdef.friction = 0.0f;
		body.createFixture(fdef).setUserData("player");

		// left foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(-14.0f / PPM, -29.5f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_ENEMY;
		fdef.friction = 4.0f;
		body.createFixture(fdef).setUserData("player.foot");

		// right foot fixture
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(14.0f / PPM, -29.5f / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_ENEMY;
		fdef.friction = 4.0f;
		body.createFixture(fdef).setUserData("player.foot");

		weapon = new Melee(body, this);
	}

	public void updateMotion() {
		if (movingLeft) {
			body.setLinearVelocity(-Constants.RUN_VELOCITY * modifiers.get(0), body.getLinearVelocity().y);
			this.setAnimation(1 / 12.0f, walkAnimation);
			this.setFacingRight(false);
		} else if (movingRight) {
			body.setLinearVelocity(Constants.RUN_VELOCITY * modifiers.get(0), body.getLinearVelocity().y);
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

	public void setGameScreen(GameScreen gsc) {
		gs = gsc;
	}

	public boolean getIsRespawning() {
		return isRespawning;
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

		if (!GameScreen.isOver()) {
			if ((movingRight || movingLeft) && isGrounded) {
				sound_walk.resume();
			} else {
				sound_walk.pause();
			}
		} else {
			sound_walk.pause();
		}
	}

	public void setGrounded(boolean b) {
		this.isGrounded = b;
	}

	public void jump() {
		sound_jump.play(0.08f);

		body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY * modifiers.get(1));
		Constants.ui.setnumJumps(Constants.ui.numJumps() + 1);
	}

	public void setIsDead(boolean b) {
		isDead = b;
	}

	public boolean getIsDead() {
		return isDead;
	}

	public void takeDamage(int damage) {
		health += -damage;
		hud.setHealth(health);
		isRespawning = true;
		if (health == 0) {
			isDead = true;
		} else {
			Task task = new Task() {
				public void run() {
					isRespawning = false;
				}
			};
			timer.scheduleTask(task, 2);
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void GetPowerUp(PowerUp pu) {
		sound_powerup.play(0.7f);

		Random rand = new Random();
		final int i = rand.nextInt(2);
		modifiers.set(i, modifiers.get(i) * 1.5f);
		Task task = new Task() {
			public void run() {
				endPowerUp(i);
			}
		};
		timer.scheduleTask(task, 5);
		gs.freePowerupSpawn(pu.getSpawn());
	}

	public void endPowerUp(int num) {
		modifiers.set(num, modifiers.get(num) / 1.5f);
	}

	public void addScore() {
		Hud.addScore();
	}

	public boolean getGameOver() {
		return gameover;
	}

	public void setGameOver(boolean b) {
		gameover = b;
	}

	public void stop() {
		sound_jump.stop();
		sound_walk.stop();
		sound_powerup.stop();
	}
}
