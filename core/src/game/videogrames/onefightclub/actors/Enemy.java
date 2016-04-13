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
    private boolean isDead = false;
    
    public Enemy(Body body) {
		super(body);
	
		Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 32, 32)[0]);
	
		setAnimation(1 / 12.0f, sprites);
		movingRight = true;
    }
    
    public void render(SpriteBatch sb) {
    	super.render(sb);
    	
    	
    }
    
    public void updateMotion() {
		if (movingLeft) {
		    body.setLinearVelocity(-Constants.RUN_VELOCITY/2, body.getLinearVelocity().y);
		}
		if (movingRight) {
		    body.setLinearVelocity(Constants.RUN_VELOCITY/2, body.getLinearVelocity().y);
		}
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
    
    public boolean getMovingRight() {
    	return movingRight;
    }
    
    public boolean getMovingLeft() {
    	return movingLeft;
    }
    
    public void setGrounded(boolean b) {
    	this.isGrounded = b;
    }
    
    public void killEnemy() {
    	this.getBody().getWorld().destroyBody(this.getBody());
    }
    
    public boolean isDead() {
    	return isDead;
    }
    
    public void setIsDead(boolean b) {
    	isDead = b;
    }
}
