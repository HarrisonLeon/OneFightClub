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
	
	private Integer health;
	private float timeCount;
	private Integer score;
	
	Label healthLabel;
	Label scoreLabel;
	Label timeLabel;
	Label marioLabel;
	
	public Hud(SpriteBatch sb) {
		health = 6;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		healthLabel = new Label(String.format("%03d",  health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(healthLabel).expandX().padTop(10);
	
		stage.addActor(table);
	}
	
	public void setHealth(int health) {
		this.health = health;
		healthLabel.setText(String.format("%03d",  health));
	}
	
}
