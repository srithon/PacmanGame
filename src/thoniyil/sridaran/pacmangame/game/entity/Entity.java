package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;

public abstract class Entity
{
	private Position pos;
	
	public abstract Image getImage();
	
	public Entity(Position p)
	{
		this(p.getX(), p.getY());
	}
	
	public Entity(int x, int y)
	{
		pos = new Position(x, y);
	}
	
	public Position getPosition()
	{
		return pos;
	}
	
	public void setPosition(Position newPosition)
	{
		pos = newPosition.copy();
	}
}
