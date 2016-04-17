package game.videogrames.onefightclub.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.videogrames.onefightclub.actors.Player;
import game.videogrames.onefightclub.utils.Constants;


public class Hud {
	public Stage stage;
	private Viewport viewport;
	
	private Integer health = 6;
	private static Integer score = 0;
	
	Label healthLabel;
	static Label scoreLabel;
	
	public Hud(SpriteBatch sb) {
		viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		healthLabel = new Label("Lives: " + String.format("%01d",  health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		healthLabel.setFontScale(2, 2);
		
		scoreLabel = new Label("Score: " + String.format("%01d", score) + "/" + Constants.LEVEL_1_GOAL, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel.setFontScale(2, 2);
		
		table.add(healthLabel);
		table.row();
		table.add(scoreLabel);
		table.padTop(15);
		table.padLeft(-Constants.APP_WIDTH*.8f);
	
		stage.addActor(table);
	}
	
	public void setHealth(int health) {
		this.health = health;
		healthLabel.setText("Lives: " + String.format("%01d",  health));
	}
	
	public static void addScore() {
		score += 1;
		scoreLabel.setText("Score: " + String.format("%01d", score) + "/" + Constants.LEVEL_1_GOAL);
	}
	
}
