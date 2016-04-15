package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public abstract class OFCSprite {
	protected Body body;

	protected Animation animation;
	protected float width;
	protected float height;

	protected boolean facingRight;

	protected float stateTime;

	public OFCSprite(Body body) {
		this.body = body;
		this.stateTime = 0.0f;
		this.facingRight = true;
	}

	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean getFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean b) {
		this.facingRight = b;
	}

	public void setAnimation(float frameDuration, Array<TextureRegion> keyFrames) {
		animation = new Animation(frameDuration, keyFrames);
		width = keyFrames.get(0).getRegionWidth();
		height = keyFrames.get(0).getRegionHeight();
	}

	public void render(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currFrame = animation.getKeyFrame(stateTime, true);

		// draw the current frame
		sb.begin();

		// flip frames according to direction sprite is facing
		if (!facingRight && !currFrame.isFlipX()) {
			currFrame.flip(true, false);
		} else if (facingRight && currFrame.isFlipX()) {
			currFrame.flip(true, false);
		}

		sb.draw(currFrame, getPosition().x * Constants.PPM - width / 2, getPosition().y * Constants.PPM - height / 2);
		sb.end();
	}

}
