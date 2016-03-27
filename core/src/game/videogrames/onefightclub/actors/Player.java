package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import game.videogrames.onefightclub.utils.Constants;

public class Player extends OFCSprite
{
	public static final String	PLAYER_FILEPATH	= "images/soldierwalk.png";
	
	private Sound sound_jump;
	
	
	boolean						movingLeft		= false;
	boolean						movingRight		= false;

	public Player(Body body)
	{
		super(body);

		Texture t = new Texture(Gdx.files.internal(PLAYER_FILEPATH));
		Array<TextureRegion> sprites = new Array<TextureRegion>(TextureRegion.split(t, 80, 64)[0]);

		setAnimation(1 / 12.0f, sprites);
		
		sound_jump = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Jump.wav"));
	}

	public void updateMotion()
	{
		if (movingLeft)
		{
			body.setLinearVelocity(-Constants.RUN_VELOCITY, body.getLinearVelocity().y);
		}
		if (movingRight)
		{
			body.setLinearVelocity(Constants.RUN_VELOCITY, body.getLinearVelocity().y);
		}
	}

	public void setMovingLeft(boolean b)
	{
		if (movingRight && b)
		{
			movingRight = false;
		}
		this.movingLeft = b;
	}

	public void setMovingRight(boolean b)
	{
		if (movingLeft && b)
		{
			movingLeft = false;
		}
		this.movingRight = b;
	}

	public void jump()
	{
		sound_jump.play(0.3f);
		body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY);
	}

	public void attack()
	{
		// TODO: make player attack
	}

}
