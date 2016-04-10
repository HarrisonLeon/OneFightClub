package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class Enemy extends MovingSprite {
    public static final String PLAYER_FILEPATH = "images/bunny.png";

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean isGrounded = false;
    
    public Enemy(Body body) {
		super(body);
	
		Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 32, 32)[0]);
	
		setAnimation(1 / 12.0f, sprites);
    }
    
    public void render(SpriteBatch sb) {
    	super.render(sb);
    	
    	
    }
    
    public void jump() {
    	body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY);
    }
    
    public void setMovingLeft(boolean b) {
		if (movingRight && b) {
		    movingRight = false;
		}
		this.movingLeft = b;
    }

    public void setMovingRight(boolean b) {
		if (movingLeft && b) {
		    movingLeft = false;
		}
		this.movingRight = b;
    }
    
    public void setGrounded(boolean b) {
    	this.isGrounded = b;
    }
}
