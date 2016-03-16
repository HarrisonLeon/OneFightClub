package game.videogrames.onefightclub;

import com.badlogic.gdx.Game;

import game.videogrames.onefightclub.screens.MainMenu;

public class OneFightClub extends Game
{
	@Override
	public void create()
	{
		setScreen(new MainMenu(this));
	}
}
