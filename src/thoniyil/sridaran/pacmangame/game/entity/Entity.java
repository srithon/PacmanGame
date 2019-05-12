package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;

public abstract class Entity
{
	private Position pos;
	
	public abstract Image getIcon();
	
	public Entity(int x, int y)
	{
		pos = new Position(x, y);
	}
	
	public Position getPosition()
	{
		return pos;
	}
}
