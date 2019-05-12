package thoniyil.sridaran.pacmangame.game.active;

public class Effect
{
	private Modifier modifier;
	private int magnitude;
	
	public Effect(Modifier modifier)
	{
		this.modifier = modifier;
		this.magnitude = Modifier.getDefaultMagnitude(modifier);
	}
	
	public int getMagnitude()
	{
		return magnitude;
	}
	
	public Modifier getModifier()
	{
		return modifier;
	}
}
