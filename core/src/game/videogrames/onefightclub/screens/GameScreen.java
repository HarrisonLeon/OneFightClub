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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	private static Hud hud;

	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;

	private BodyDef bdef;
	private Body body;

	private Body playerBody;
	private Player player;

	private Vector<Enemy> enemies;
	private Vector<PowerUp> powerups;
	private Vector<Weapon> weapons;

	private Vector<Boolean> enemySpawnAvailable;
	private Vector<Boolean> powerupSpawnAvailable;

	private Timer enemy_timer;
	private boolean enemiesResume = false;
	private int currentEnemies = 0;
	private static int enemiesKilled = 0;

	private Timer ambience_timer;
	private Sound ambience_fizzle;
	private Sound ambience_beep1;
	private Sound ambience_beep2;
	private Sound ambience_robot;
	private Sound ambience_scifi;
	private Sound theme1;
	private Sound fanfare;
	private Sound gameover;

	private boolean fanfareIsPlaying = false;
	private static boolean gameOver = false;

	public GameScreen(Game game) {
		super(game);

		fanfareIsPlaying = false;
		gameOver = false;

		theme1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Theme_1.wav"));
		theme1.loop(0.3f);

		fanfare = Gdx.audio.newSound(Gdx.files.internal("sounds/Fanfare.wav"));

		gameover = Gdx.audio.newSound(Gdx.files.internal("sounds/GameOver.wav"));

		ambience_fizzle = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_fizzle.wav"));
		ambience_beep1 = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_beep1.wav"));
		ambience_beep2 = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_beep2.wav"));
		ambience_robot = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_robot.wav"));
		ambience_scifi = Gdx.audio.newSound(Gdx.files.internal("sounds/ambience_scifi.wav"));
	}

	@Override
	public void show() {

		enemySpawnAvailable = new Vector<Boolean>();
		for (int i = 0; i < Constants.enemySpawns.length; i++) {
			enemySpawnAvailable.add(true);
		}

		powerupSpawnAvailable = new Vector<Boolean>();
		for (int i = 0; i < Constants.powerupSpawns.length; i++) {
			powerupSpawnAvailable.add(true);
		}

		world = new World(new Vector2(0.0f, Constants.GRAVITY), true);
		cl = new OFCContactListener();
		world.setContactListener(cl);
		debugRenderer = new Box2DDebugRenderer();

		sb = ((OneFightClub) game).getSpriteBatch();
		mainCam = ((OneFightClub) game).getMainCam();
		hud = new Hud(sb);
		hud.resetScore();

		// set up input for the game
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.A: // move left
					player.setMovingLeft(true);
					player.stopAttack();
					break;
				case Keys.D: // move right
					player.setMovingRight(true);
					player.stopAttack();
					break;
				case Keys.W: // jump
					if (cl.isPlayerGrounded()) {
						player.jump();
					}
					player.stopAttack();
					break;
				case Keys.SPACE: // attack
					player.startAttack();
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
				case Keys.SPACE: // stop attacking
					player.stopAttack();
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

		spawnPowerup();

		// set up timer
		enemy_timer = new Timer();
		Task task = new Task() {
			public void run() {
				spawnEnemy();
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
		int randomNumber = random.nextInt(35 - 15) + 15;
		ambience_timer.scheduleTask(task2, randomNumber);

		Task task3 = new Task() {
			public void run() {
				spawnPowerup();
			}
		};
		enemy_timer.scheduleTask(task3, 10, 10);

		createPlatforms();
	}

	public void createPlatforms() {
		tileMap = new TmxMapLoader().load("maps/onefightclubmap.tmx");
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

		TiledMapTileLayer layer;
		layer = (TiledMapTileLayer) tileMap.getLayers().get("floor");
		drawTiles(layer, Constants.BIT_GROUND, "floor");

		layer = (TiledMapTileLayer) tileMap.getLayers().get("edge");
		drawTiles(layer, Constants.BIT_EDGE, "edge");
	}

	public void drawTiles(TiledMapTileLayer layer, short bits, String data) {
		float tileSize = layer.getTileWidth();

		for (int r = 0; r < layer.getHeight(); r++) {
			for (int c = 0; c < layer.getWidth(); c++) {
				Cell cell = layer.getCell(c, r);

				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;

				// create body based on cell
				BodyDef bdef = new BodyDef();
				bdef.type = BodyType.StaticBody;
				bdef.position.set((c + 0.5f) * tileSize / PPM, (r + 0.5f) * tileSize / PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[4];
				v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM); // bottom left
				v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM); // top left
				v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM); // top right
				v[3] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM); // bottom right
				cs.createChain(v);
				FixtureDef fd = new FixtureDef();
				fd.friction = 2.0f;
				fd.shape = cs;
				fd.filter.categoryBits = bits;
				fd.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_ENEMY;
				world.createBody(bdef).createFixture(fd).setUserData(data);
				cs.dispose();
			}
		}
	}

	public void createPlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(Constants.playerSpawn); // spawn location
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);

		player = new Player(playerBody, hud);
		playerBody.setUserData(player);
		player.setGameScreen(this);
	}

	public void spawnEnemy() {
		Random rand = new Random();
		int randLoc = rand.nextInt(Constants.enemySpawns.length);
		if (enemySpawnAvailable.elementAt(randLoc)) {
			createEnemy(randLoc);
		} else {
			int index = findNextSpawn(randLoc);
			if (!(index == -1)) {
				createEnemy(index);
			}
		}
	}

	public int findNextSpawn(int val) {
		int numSpawns = enemySpawnAvailable.size();
		int i = val;
		for (int j = 0; j < numSpawns; j++) {
			if (enemySpawnAvailable.elementAt(i)) {
				return i;
			}
			i++;
			if (i == numSpawns) {
				i = 0;
			}
		}
		return -1;
	}

	public void createEnemy(int spawnLoc) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(Constants.enemySpawns[spawnLoc]);
		bdef.type = BodyType.DynamicBody;
		Body enemyBody = world.createBody(bdef);

		Enemy enemy = new Enemy(enemyBody);
		enemyBody.setUserData(enemy);
		enemies.add(enemy);
		currentEnemies += 1;
		enemy.setSpawn(spawnLoc);
		enemySpawnAvailable.setElementAt(false, spawnLoc);
	}

	public void spawnPowerup() {
		Random rand = new Random();
		int randLoc = rand.nextInt(Constants.powerupSpawns.length);

		if (powerupSpawnAvailable.elementAt(randLoc)) {
			createPowerup(randLoc);
			powerupSpawnAvailable.setElementAt(false, randLoc);
		}
	}

	public void freePowerupSpawn(int i) {
		powerupSpawnAvailable.set(i, true);
	}

	public void createPowerup(int spawnLoc) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(Constants.powerupSpawns[spawnLoc]);
		bdef.type = BodyType.DynamicBody;
		Body powerupBody = world.createBody(bdef);

		PowerUp powerup = new PowerUp(powerupBody);
		powerupBody.setUserData(powerup);
		powerups.add(powerup);
	}

	@Override
	public void render(float delta) {
		// processInput();
		Vector<Enemy> toBeRemoved = new Vector<Enemy>();
		if (currentEnemies >= Constants.NUM_ENEMIES) {
			enemy_timer.stop();
			enemiesResume = true;
		} else if (enemiesResume) {
			enemy_timer.start();
			enemiesResume = false;
		}

		Vector<PowerUp> powerupRemoval = new Vector<PowerUp>();

		b2dCamera.update();
		mainCam.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmRenderer.setView(mainCam);
		tmRenderer.render();

		sb.setProjectionMatrix(mainCam.combined);

		if (!player.getIsDead()) {
			if (player.getIsRespawning()) {
				player.getBody().setTransform(Constants.playerSpawn, 0.0f);
				player.setGrounded(true);
			} else {
				player.updateMotion();
				player.setGrounded(cl.isPlayerGrounded());
				player.render(sb);
			}
		} else { // Player is dead
			gameOver = true;
			theme1.stop();
			gameover.play();
			System.out.println(Constants.ui.numKills());
			System.out.println(Constants.ui.numDeaths());
			System.out.println(Constants.ui.numJumps());
			System.out.println(Constants.ui.killStreak());
			Constants.ui.updateStats();
			game.setScreen(new AchievementsScreen(game));
			this.dispose();
			System.out.println("im dead");
		}

		for (Enemy e : enemies) {
			if (e.isDead()) {
				toBeRemoved.add(e);
			} else {
				e.updateMotion();
				e.render(sb);
			}
		}

		for (PowerUp p : powerups) {
			if (p.getIsDead()) {
				powerupRemoval.add(p);
			} else {
				p.render(sb);
			}
		}

		for (Weapon w : weapons) {
			w.render(sb);
		}

		for (Enemy e : toBeRemoved) {
			int spawn = e.getSpawn();
			enemySpawnAvailable.setElementAt(true, spawn);
			e.killEnemy();
			enemies.remove(e);
			currentEnemies -= 1;
		}
		toBeRemoved.clear();

		for (PowerUp p : powerupRemoval) {
			p.killPowerUp();
			powerups.remove(p);
		}
		powerupRemoval.clear();

		debugRenderer.render(world, b2dCamera.combined);

		world.step(1 / 60f, 6, 2);
		sb.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

		if (enemiesKilled >= Constants.LEVEL_1_GOAL) { // LEVEL ENDS WHEN THIS CONDITION IS MET
			GameOver();
		}
	}

	@Override
	public void hide() {
		Gdx.app.debug("One Fight Club", "dispose game screen");

		theme1.stop();
		fanfare.stop();
		ambience_fizzle.stop();
		ambience_beep1.stop();
		ambience_beep2.stop();
		ambience_robot.stop();
		ambience_scifi.stop();

		player.stop();
	}

	private void playAmbience() {
		if (gameOver) {
			return;
		}
		Random random = new Random();
		int randomNumber = random.nextInt(5);

		if (randomNumber == 0) {
			ambience_beep1.play(0.1f);
		} else if (randomNumber == 1) {
			ambience_beep2.play(0.6f);
		} else if (randomNumber == 2) {
			ambience_fizzle.play(0.3f);
		} else if (randomNumber == 3) {
			ambience_robot.play(0.2f);
		} else if (randomNumber == 4) {
			ambience_scifi.play(0.1f);
		}

		ambience_timer = new Timer();
		Task task2 = new Task() {
			public void run() {
				playAmbience();
			}
		};
		Random random2 = new Random();
		int randomNumber2 = random2.nextInt(25 - 10) + 10;
		ambience_timer.scheduleTask(task2, randomNumber2);
	}

	public static void addScore() {
		enemiesKilled++;
		hud.addScore();
	}

	private void GameOver() {
		gameOver = true;

		theme1.stop();
		if (!fanfareIsPlaying) { // Harrison change this once u stop updating
			fanfare.play();
			fanfareIsPlaying = true;
			gameOver = true;
		}
		System.out.println(Constants.ui.numKills());
		System.out.println(Constants.ui.numDeaths());
		System.out.println(Constants.ui.numJumps());
		System.out.println(Constants.ui.killStreak());
		Constants.ui.updateStats();
		Task task = new Task() {
			public void run() {
				game.getScreen().dispose();
				game.setScreen(new AchievementsScreen(game));
			}
		};
		enemy_timer.scheduleTask(task, 5);
	}

	public static boolean isOver() {
		return gameOver;
	}
}