package game.videogrames.onefightclub;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.videogrames.onefightclub.screens.MainMenu;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.OFCInputProcessor;

public class OneFightClub extends Game
{
	private SpriteBatch			sb;
	private OrthographicCamera	mainCam;
	private OrthographicCamera	hudCam;

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void dispose()
	{
		sb.dispose();
	}

	public SpriteBatch getSpriteBatch()
	{
		return sb;
	}

	public OrthographicCamera getMainCam()
	{
		return mainCam;
	}

	public OrthographicCamera getHudCam()
	{
		return hudCam;
	}

	@Override
	public void create()
	{
		Gdx.input.setInputProcessor(new OFCInputProcessor());

		sb = new SpriteBatch();
		mainCam = new OrthographicCamera();
		mainCam.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		setScreen(new MainMenu(this));
	}
}
