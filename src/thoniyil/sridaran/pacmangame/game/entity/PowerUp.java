package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;

import thoniyil.sridaran.pacmangame.game.active.Effect;

public abstract class PowerUp extends Entity
{
	protected PowerUp(int x, int y)
	{
		super(x, y);
	}

	private Effect effect;
	
	public abstract Image getImage();
	
	public void update()
	{
		
	}
}
