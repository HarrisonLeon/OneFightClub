package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class Melee extends Weapon {

	public static final String MELEE_FILEPATH = "images/weapon_melee.png";

	private Array<TextureRegion> meleeAnimation;
	private Player player;

	public Melee(Body body, Player player) {
		super(body);
		this.player = player;

		// create idle animation
		Texture idleTexture = new Texture(Gdx.files.internal(MELEE_FILEPATH));
		meleeAnimation = new Array<TextureRegion>(TextureRegion.split(idleTexture, 26, 60)[0]);

		setAnimation(1 / 12.0f, meleeAnimation);
	}

	// Overridden to account for sword offset from player body
	public Vector2 getPosition() {
		boolean direction = player.facingRight;
		Vector2 shiftedPos = new Vector2((direction ? 29.0f : -29.0f) / Constants.PPM, 0.0f);
		shiftedPos = shiftedPos.add(body.getPosition());
		return (shiftedPos);
	}

}