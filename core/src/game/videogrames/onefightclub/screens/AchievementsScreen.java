package game.videogrames.onefightclub.screens;

import com.badlogic.gdx.Game;

public class AchievementsScreen extends OFCScreen{
	private Stage stage;
	
	public AchievementsScreen(Game game) {
		super(game);
	}

	@Override
    public void show() {
		stage = new Stage();
	}

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    	stage.dispose();
    }
}
