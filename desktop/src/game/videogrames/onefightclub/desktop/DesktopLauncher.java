package game.videogrames.onefightclub.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.videogrames.onefightclub.OneFightClub;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "OneFightClub";
		config.useGL30 = false;
		config.width = 960;
		config.height = 640;

		new LwjglApplication(new OneFightClub(), config);
	}
}