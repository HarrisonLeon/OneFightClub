package game.videogrames.onefightclub.screens;

import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import game.videogrames.onefightclub.utils.Constants;

public class AchievementsScreen extends OFCScreen{
	private Stage stage;
	private String lockImage = "images/locked.png";
	private String beltImage = "images/belt.png";
	private Vector<Boolean> achievements;
	private Vector<Integer[]> points;
	
	public AchievementsScreen(Game game) {
		super(game);
		points = new Vector<Integer[]>();
	}

	@Override
    public void show() {
		stage = new Stage();
		
		Integer[] pointone = {-25, 0, 250, 600};
		points.add(pointone);
		Integer[] pointtwo = {-275, 0, 600, 600};
		points.add(pointtwo);
		Integer[] pointthree = {-525, 0, 900, 600};
		points.add(pointthree);
		Integer[] pointfour = {-775, 0, 1200, 600};
		points.add(pointfour);
		Integer[] pointfive = {-25, -200, 250, 600};
		points.add(pointfive);
		Integer[] pointsix = {-275, -200, 600, 600};
		points.add(pointsix);
		Integer[] pointseven = {-525, -200, 900, 600};
		points.add(pointseven);
		Integer[] pointeight = {-775, -200, 1200, 600};
		points.add(pointeight);
		Integer[] pointnine = {-25, -400, 250, 600};
		points.add(pointnine);
		Integer[] pointten = {-275, -400, 600, 600};
		points.add(pointten);
		Integer[] pointeleven = {-525, -400, 900, 600};
		points.add(pointeleven);
		Integer[] pointtwelve = {-775, -400, 1200, 600};
		points.add(pointtwelve);
		
		achievements = Constants.ui.achievements();
		for(int i = 0; i < achievements.size(); i++) {
			if(achievements.elementAt(i)) {
				Image unlock = new Image(new TextureRegion(new Texture(Gdx.files.internal(beltImage)), points.elementAt(i)[0], points.elementAt(i)[1], points.elementAt(i)[2], points.elementAt(i)[3]));
				stage.addActor(unlock);
			}
			else {
				Image lock = new Image(new TextureRegion(new Texture(Gdx.files.internal(lockImage)), points.elementAt(i)[0], points.elementAt(i)[1], points.elementAt(i)[2], points.elementAt(i)[3]));
				stage.addActor(lock);
			}
		}
	}

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(30);
		stage.draw();
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
