package game.videogrames.onefightclub.screens;

import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import game.videogrames.onefightclub.utils.Constants;

public class AchievementsScreen extends OFCScreen{
	private Stage stage;
	private String lockImage = "images/locked.png";
	private String beltImage = "images/belt.png";
	private Vector<Boolean> achievements;
	private Vector<Integer[]> points;
	private Vector<String> titles;
	private BitmapFont pixels;
	private Vector<Integer> pointTitle;
	
	public AchievementsScreen(Game game) {
		super(game);
		points = new Vector<Integer[]>();
		titles = new Vector<String>();
		pointTitle = new Vector<Integer>();
	}

	@Override
    public void show() {
		stage = new Stage();
		
		pixels = new BitmapFont();
		TextButtonStyle style = new TextButtonStyle();
    	style.font = pixels;
    	
		
		titles.add("First Death");
		titles.add("One Hundred Kills");
		titles.add("Ten Kills");
		titles.add("First Kill");
		
		titles.add("Ten Jumps");
		titles.add("First Jump");
		titles.add("One Hundred Deaths");
		titles.add("Ten Deaths");
		
		titles.add("Twenty Killstreak");
		titles.add("Ten Killstreak");
		titles.add("Five Killstreak");
		titles.add("One Hundred Jumps");
		
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
		
		pointTitle.add(50);
		pointTitle.add(1000);
		pointTitle.add(1500);

		
		
		
		achievements = Constants.ui.achievements();
		for(int i = 0; i < achievements.size(); i++) {
			if(achievements.elementAt(i)) {
				int element = 0;
				if(i % 4 == 0) {
					element = 3;
				}
				if(i % 4 == 1) {
					element = 1;
				}
				
				if(i % 4 == 2) {
					element = -1;
				}
				if(i % 4 == 3) {
					element = -3;
				}
				
				TextButton title = new TextButton(titles.elementAt(i + element), style);
				title.setX(points.elementAt(i + element)[0] + 100);
				title.setY(points.elementAt(i + element)[1] + 575);
				title.setWidth(pointTitle.elementAt(2));
				title.setHeight(15);
				stage.addActor(title);
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
