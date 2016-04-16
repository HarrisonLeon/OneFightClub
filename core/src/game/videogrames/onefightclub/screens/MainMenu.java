package game.videogrames.onefightclub.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenu extends OFCScreen {
    Image title;
    TextButton play;
    
    TextureAtlas atlas;
    Skin skin;
    TextureAtlas buttonAtlas;
    TextButton achievements;
    BitmapFont pixels;
    Stage stage;
    
    SpriteBatch batch;
    float time = 0;

    public MainMenu(Game game) {
    	super(game);
    }

    @Override
    public void show() {
    	stage = new Stage();
    	Gdx.input.setInputProcessor(stage);
    	TextureRegion upRegion = new TextureRegion();
    	TextureRegion downRegion = new TextureRegion();

		pixels = new BitmapFont();
    	//pixels = new BitmapFont(Gdx.files.internal("font.fnt"));
    	pixels.setColor(Color.WHITE);
		
    	skin = new Skin();
    	TextButtonStyle style = new TextButtonStyle();
    	style.font = pixels;
    	achievements = new TextButton("Achievements", style); 
    	achievements.setX(400);
    	achievements.setY(100);
    	achievements.setWidth(100);
    	achievements.setHeight(50);
    	
    	play = new TextButton("Play", style);
    	play.setX(400);
    	play.setY(150);
    	play.setWidth(100);
    	play.setHeight(50);
    	
    	title = new Image(new TextureRegion(new Texture(Gdx.files.internal("images/temptitle.png")), -75, 0, 850, 500));
		//achievements = new Image(new TextureRegion(new Texture(Gdx.files.internal("images/achievements.png")), 0, 0, 600, 600));
		
		
		//stage.addActor(achievements);
		//stage.addActor(play);
		stage.addActor(title);
		stage.addActor(play);
		stage.addActor(achievements);
		//batch = new SpriteBatch();
		//batch.getProjectionMatrix().setToOrtho2D(0, 0, 960, 640);
		
    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(30);
		stage.draw();
		
		achievements.addListener(new ClickListener() {
		    public void clicked(InputEvent event, float x, float y){
		        System.out.println("pressed");
		        game.setScreen(new AchievementsScreen(game));
		    }
		}); 
		
		play.addListener(new ClickListener() {
		    public void clicked(InputEvent event, float x, float y) {
		        System.out.println("pressed title");
		        game.setScreen(new GameScreen(game));
		    }
		});
	
		time += delta;
		/*if (time > 0) {
		    if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
			game.setScreen(new GameScreen(game));
		    }
		}*/
    }

    @Override
    public void hide() {
		Gdx.app.debug("One Fight Club", "dispose main menu");
		stage.dispose();
		//batch.dispose();
		//title.getTexture().dispose();
    }

}
