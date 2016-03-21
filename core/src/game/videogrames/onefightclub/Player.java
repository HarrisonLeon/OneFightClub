package game.videogrames.onefightclub;

public class Player
{
	boolean	movingLeft;
	boolean	movingRight;

	public void updateMotion()
	{
		if (movingLeft)
		{
			// TODO: move player left
		}
		if (movingRight)
		{
			// TODO: move player right
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
		// TODO: make player jump
	}

	public void attack()
	{
		// TODO: make player attack
	}

}
