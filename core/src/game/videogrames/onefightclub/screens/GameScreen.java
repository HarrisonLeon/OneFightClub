package game.videogrames.onefightclub.screens;

import static game.videogrames.onefightclub.utils.Constants.APP_HEIGHT;
import static game.videogrames.onefightclub.utils.Constants.APP_WIDTH;
import static game.videogrames.onefightclub.utils.Constants.PPM;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.actors.Enemy;
import game.videogrames.onefightclub.actors.Player;
import game.videogrames.onefightclub.actors.PowerUp;
import game.videogrames.onefightclub.actors.Weapon;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.OFCContactListener;

public class GameScreen extends OFCScreen {
    private World world;
    private OFCContactListener cl;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera b2dCamera;

    private OrthographicCamera mainCam;
    private SpriteBatch sb;

    private BodyDef bdef;
    private Body body;

    private Body playerBody;
    private Player player;
    private Vector<Enemy> enemies;
    private Vector<PowerUp> powerups;
    private Vector<Weapon> weapons;

    private Timer enemy_timer;
    private int currentEnemies = 0;
    
    private Timer ambience_timer;
    private Sound ambience_fizzle;
    private Sound ambience_beep1;
    private Sound ambience_beep2;
    private Sound ambience_robot;
    private Sound ambience_scifi;
    private Sound theme1;

    public GameScreen(Game game) {
    	super(game);
    	
    	theme1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Theme_1.wav"));
    	theme1.loop(0.3f);
    	
    	ambience_fizzle = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_fizzle.wav"));
    	ambience_beep1 = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_beep1.wav"));
    	ambience_beep2 = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_beep2.wav"));
    	ambience_robot = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_robot.wav"));
    	ambience_scifi = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_scifi.wav"));
    }

    @Override
    public void show() {
		world = new World(new Vector2(0.0f, Constants.GRAVITY), true);
		cl = new OFCContactListener();
		world.setContactListener(cl);
		debugRenderer = new Box2DDebugRenderer();
	
		sb = ((OneFightClub) game).getSpriteBatch();
		mainCam = ((OneFightClub) game).getMainCam();
	
		Gdx.input.setInputProcessor(new InputAdapter() {
		    @Override
		    public boolean keyDown(int keycode) {
			switch (keycode) {
			case Keys.A: // move left
			    player.setMovingLeft(true);
			    break;
			case Keys.D: // move right
			    player.setMovingRight(true);
			    break;
			case Keys.W: // jump
			    if (cl.isPlayerGrounded()) {
			    	player.jump();
			    }
		
			b2dCamera = new OrthographicCamera();
			b2dCamera.setToOrtho(false, APP_WIDTH / PPM, APP_HEIGHT / PPM);
		
			enemies = new Vector<Enemy>();
			powerups = new Vector<PowerUp>();
			weapons = new Vector<Weapon>();
		
			createPlayer();
			/*for (int i = 0; i < 5; i++) {
			    createEnemy();
			}*/
			/*createPowerUp();
			createWeapon();*/
			
			//set up timer
			enemy_timer = new Timer();
			Task task = new Task() {
				public void run() {
					createEnemy();
					currentEnemies += 1;
				}
			};
			enemy_timer.scheduleTask(task, 3, 3);
			
			ambience_timer = new Timer();
			Task task2 = new Task() {
				public void run() {
					playAmbience();
				}
			};
			Random random = new Random();
	        int randomNumber = random.nextInt(35-15) + 15;
			ambience_timer.scheduleTask(task2, randomNumber);
		
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
	
			    break;
			case Keys.SPACE: // attack
			    player.attack();
			    break;
			}
			return true;
		    }
	
		    @Override
		    public boolean keyUp(int keycode) {
			switch (keycode) {
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
	
		enemies = new Vector<Enemy>();
		powerups = new Vector<PowerUp>();
		weapons = new Vector<Weapon>();
	
		createPlayer();
		/*
		 * for (int i = 0; i < 5; i++) { createEnemy(); }
		 */
		/*
		 * createPowerUp(); createWeapon();
		 */
	
		// set up timer
		enemy_timer = new Timer();
		Task task = new Task() {
		    public void run() {
			createEnemy();
			currentEnemies += 1;
		    }
		};
		enemy_timer.scheduleTask(task, 3, 3);
	
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


    public void createPlayer() {
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
		fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_ENEMY;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("player.foot");
	
		player = new Player(playerBody);
		playerBody.setUserData(player);
    }

    public void createEnemy() {
		// create player
		Random rand = new Random();
		int randval = rand.nextInt(3);
		BodyDef bdef2 = new BodyDef();
		bdef2.position.set(Constants.spawns[randval]);
		bdef2.type = BodyType.DynamicBody;
		Body enemyBody = world.createBody(bdef2);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40.0f / PPM, 32.0f / PPM);
		FixtureDef fdef2 = new FixtureDef();
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_ENEMY;
		fdef2.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_PLAYER;
		fdef2.friction = 2.0f;
		enemyBody.createFixture(fdef2).setUserData("enemy");
	
		// create foot sensor
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(0.0f, -32.0f / PPM), 0);
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_PLAYER;
		fdef2.filter.maskBits = Constants.BIT_GROUND;
		fdef2.isSensor = true;
		enemyBody.createFixture(fdef2).setUserData("enemy.foot");
		Enemy enemy = new Enemy(enemyBody);
		enemyBody.setUserData(enemy);
		enemies.add(enemy);
    }

    public void createPowerUp() {
		BodyDef bdef2 = new BodyDef();
		bdef2.position.set(200.0f / PPM, 200.0f / PPM);
		bdef2.type = BodyType.DynamicBody;
		Body powerupBody = world.createBody(bdef2);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40.0f / PPM, 32.0f / PPM);
		FixtureDef fdef2 = new FixtureDef();
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_PLAYER;
		fdef2.filter.maskBits = Constants.BIT_GROUND;
		fdef2.friction = 2.0f;
		powerupBody.createFixture(fdef2).setUserData("powerup");
	
		// create foot sensor
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(0.0f, -32.0f / PPM), 0);
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_PLAYER;
		fdef2.filter.maskBits = Constants.BIT_GROUND;
		fdef2.isSensor = true;
		powerupBody.createFixture(fdef2).setUserData("powerup.foot");
		PowerUp powerup = new PowerUp(powerupBody);
		powerupBody.setUserData(powerup);
		powerups.add(powerup);
    }

    public void createWeapon() {
		BodyDef bdef2 = new BodyDef();
		bdef2.position.set(500.0f / PPM, 200.0f / PPM);
		bdef2.type = BodyType.DynamicBody;
		Body weaponBody = world.createBody(bdef2);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(40.0f / PPM, 32.0f / PPM);
		FixtureDef fdef2 = new FixtureDef();
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_PLAYER;
		fdef2.filter.maskBits = Constants.BIT_GROUND;
		fdef2.friction = 2.0f;
		weaponBody.createFixture(fdef2).setUserData("weapon");
	
		// create foot sensor
		shape.setAsBox(2.0f / PPM, 2.0f / PPM, new Vector2(0.0f, -32.0f / PPM), 0);
		fdef2.shape = shape;
		fdef2.filter.categoryBits = Constants.BIT_PLAYER;
		fdef2.filter.maskBits = Constants.BIT_GROUND;
		fdef2.isSensor = true;
		weaponBody.createFixture(fdef2).setUserData("weapon.foot");
		Weapon weapon = new Weapon(weaponBody);
		weaponBody.setUserData(weapon);
		weapons.add(weapon);
    }

    @Override
    public void render(float delta) {
		// processInput();
		Vector<Enemy> toBeRemoved = new Vector<Enemy>();
		if (currentEnemies >= Constants.NUM_ENEMIES) {
		    enemy_timer.stop();
		}
	
		b2dCamera.update();
		mainCam.update();
	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		sb.setProjectionMatrix(mainCam.combined);
	
		player.updateMotion();
		player.setGrounded(cl.isPlayerGrounded());
		player.render(sb);
	
		for (Enemy e : enemies) {
		    if (e.isDead()) {
			toBeRemoved.add(e);
		    } else {
			e.render(sb);
		    }
		}
	
		for (PowerUp p : powerups) {
		    p.render(sb);
		}
	
		for (Weapon w : weapons) {
		    w.render(sb);
		}
	
		for (Enemy e : toBeRemoved) {
		    e.killEnemy();
		    enemies.remove(e);
		}
		toBeRemoved.clear();
	
		debugRenderer.render(world, b2dCamera.combined);
	
		world.step(1 / 60f, 6, 2);
    }

    @Override
    public void hide() {
    	Gdx.app.debug("One Fight Club", "dispose game screen");
    }
    
    private void playAmbience() {
    	Random random = new Random();
        int randomNumber = random.nextInt(5);
    	
        if (randomNumber == 0) {
        	ambience_beep1.play(0.1f);
        }
        else if (randomNumber == 1) {
        	ambience_beep2.play(0.6f);
        }
        else if (randomNumber == 2) {
        	ambience_fizzle.play(0.3f);
        }
        else if (randomNumber == 3) {
        	ambience_robot.play(0.2f);
        }
        else if (randomNumber == 4) {
        	ambience_scifi.play(0.1f);
        }
    	
    	ambience_timer = new Timer();
		Task task2 = new Task() {
			public void run() {
				playAmbience();
			}
		};
		Random random2 = new Random();
        int randomNumber2 = random2.nextInt(25-10) + 10;
		ambience_timer.scheduleTask(task2, randomNumber2);
    }
}