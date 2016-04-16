package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class PowerUp extends StillSprite {

    public static final String PLAYER_FILEPATH = "images/weapon_melee.png";

    	
    private boolean isDead = false;
    
    public PowerUp(Body body) {
		super(body);
	
		Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 26, 60)[0]);
	
		setAnimation(1 / 12.0f, sprites);
    }
    
    public boolean getIsDead() {
    	return isDead;
    }
    
    public void setIsDead(boolean b) {
    	isDead = b;
    }
    
    public void killPowerUp() {
		this.getBody().getWorld().destroyBody(this.getBody());
	}
}
