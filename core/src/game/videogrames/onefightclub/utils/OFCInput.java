package game.videogrames.onefightclub.utils;

public class OFCInput
{
	public static boolean[]	currKeys;
	public static boolean[]	prevKeys;

	public static final int	NUM_KEYS	= 4;
	public static final int	BUTTON_0	= 0;	// left
	public static final int	BUTTON_1	= 1;	// right
	public static final int	BUTTON_2	= 2;	// jump
	public static final int	BUTTON_3	= 3;	// attack

	static
	{
		currKeys = new boolean[NUM_KEYS];
		prevKeys = new boolean[NUM_KEYS];
	}

	public static void update()
	{
		for (int i = 0; i < NUM_KEYS; i++)
		{
			prevKeys[i] = currKeys[i];
		}
	}

	public static void setKey(int i, boolean b)
	{
		currKeys[i] = b;
	}

	public static boolean isDown(int i)
	{
		return currKeys[i];
	}

	public static boolean isPressed(int i)
	{
		return currKeys[i] && !prevKeys[i];
	}
}
