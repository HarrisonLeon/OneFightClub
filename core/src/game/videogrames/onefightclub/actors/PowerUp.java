package game.videogrames.onefightclub.actors;

import static game.videogrames.onefightclub.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class PowerUp extends StillSprite {

	public static final String PLAYER_FILEPATH = "images/powerup.png";

	private boolean isDead = false;

	public PowerUp(Body body) {
		super(body);

		Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 40, 40)[0]);
		setAnimation(1 / 12.0f, sprites);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(20.0f / PPM, 20.0f / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_ITEM;
		fdef.filter.maskBits = Constants.BIT_GROUND;
		body.createFixture(fdef).setUserData("powerup");
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
