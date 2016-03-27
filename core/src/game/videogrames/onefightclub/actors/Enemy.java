package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class Enemy extends MovingSprite {
    public static final String PLAYER_FILEPATH = "images/bunny.png";

    public Enemy(Body body) {
	super(body);

	Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
	Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 32, 32)[0]);

	setAnimation(1 / 12.0f, sprites);
    }
}
