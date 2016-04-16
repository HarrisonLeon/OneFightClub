package game.videogrames.onefightclub.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	public static final String TITLE = "2-0: One Fight Club";
	public static final int APP_WIDTH = 960;
	public static final int APP_HEIGHT = 640;

	// pixels per meter
	public static final float PPM = 100.0f;

	public static final float GRAVITY = -15.0f;
	public static final float JUMP_VELOCITY = 6.5f;
	public static final float RUN_VELOCITY = 4.0f;

	public static final short BIT_GROUND = 2;
	public static final short BIT_EDGE = 4;
	public static final short BIT_PLAYER = 6;
	public static final short BIT_ENEMY = 8;
	public static final short BIT_WEAPON = 10;

	public static final Vector2[] spawns = new Vector2[] { new Vector2((Constants.APP_WIDTH / PPM) / 2, (((float)Constants.APP_HEIGHT) * (7.0f/8.0f)) / PPM),
			new Vector2((Constants.APP_WIDTH / PPM) / 2, (((float)Constants.APP_HEIGHT) * (3.0f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (1.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (4.0f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (7.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (4.0f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (1.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (2.0f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (7.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (1.5f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (5.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (6.0f/8.0f)) / PPM),
			new Vector2(((((float)Constants.APP_WIDTH) * (3.0f/8.0f)) / PPM), (((float)Constants.APP_HEIGHT) * (6.0f/8.0f)) / PPM)};

	public static final int NUM_ENEMIES = 10;

	public static UserInfo ui;
}
