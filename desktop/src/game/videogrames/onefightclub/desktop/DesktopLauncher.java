package game.videogrames.onefightclub.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.utils.Constants;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "OneFightClub";
		config.useGL30 = false;
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;

		new LwjglApplication(new OneFightClub(), config);
	}
}