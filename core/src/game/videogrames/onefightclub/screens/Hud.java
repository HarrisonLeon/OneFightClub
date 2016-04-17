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
	private Integer score = 0;
	
	Label healthLabel;
	Label scoreLabel;;
	
	public Hud(SpriteBatch sb) {
		viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		healthLabel = new Label("Lives: " + String.format("%01d",  health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		healthLabel.setFontScale(2, 2);
		scoreLabel = new Label("Score: " + String.format("%01d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(healthLabel);
		table.padTop(15);
		table.padLeft(-Constants.APP_WIDTH*.85f);
	
		stage.addActor(table);
	}
	
	public void setHealth(int health) {
		this.health = health;
		healthLabel.setText("Lives: " + String.format("%01d",  health));
	}
	
	public void addScore() {
		this.score += 1;
	}
	
}
