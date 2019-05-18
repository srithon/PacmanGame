package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;

import thoniyil.sridaran.pacmangame.game.active.Effect;

public abstract class PowerUp extends Entity implements Consumable, StaticEntity
{
	private Effect effect;
	
	protected PowerUp(int x, int y)
	{
		super(x, y);
	}
	
	public Effect getEffect()
	{
		return effect;
	}
	
	public abstract Image getImage();
}
