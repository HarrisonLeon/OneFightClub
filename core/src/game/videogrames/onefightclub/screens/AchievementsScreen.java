package game.videogrames.onefightclub.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AchievementsScreen extends OFCScreen{
	private Stage stage;
	private Image lock;
	
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
