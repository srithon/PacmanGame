package thoniyil.sridaran.pacmangame.game.active;

public enum Modifier
{
	CLOCK_SPEED,
	POWER_PELLET;
	
	public static final int CLOCK_SPEED_MAG_D = 60;
	public static final int POWER_PELLET_MAG_D = 10;

	public static Modifier randomModifier()
	{
		return Modifier.values()[((int) Math.random() * Modifier.values().length)];
	}
	
	public static int getDefaultMagnitude(Modifier m)
	{
		switch (m)
		{
			case CLOCK_SPEED: return CLOCK_SPEED_MAG_D;
			case POWER_PELLET: return POWER_PELLET_MAG_D;
		}
		
		System.out.println("ERROR IN GET DEFAULT MAGNITUDE; INVALID MODIFIER: " + m);
		return Integer.MIN_VALUE;
	}
}