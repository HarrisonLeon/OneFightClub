package game.videogrames.onefightclub.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class OFCScreen implements Screen
{
	Game game;

	public OFCScreen(final Game game)
	{
		this.game = game;
	}

	@Override
	public void show()
	{}

	@Override
	public void render(float delta)
	{}

	@Override
	public void resize(int width, int height)
	{}

	@Override
	public void pause()
	{}

	@Override
	public void resume()
	{}

	@Override
	public void hide()
	{}

	@Override
	public void dispose()
	{}

}
