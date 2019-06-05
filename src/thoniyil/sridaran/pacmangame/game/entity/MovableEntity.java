package thoniyil.sridaran.pacmangame.game.entity;

public abstract class MovableEntity extends Entity
{
	private Position lastPosition;
	
	protected MovableEntity(Position p)
	{
		super(p);
	}
	
	protected MovableEntity(int x, int y)
	{
		super(x, y);
	}
	
	protected MovableEntity(float x, float y)
	{
		super(x, y);
	}
	
	public abstract void move();
	
	public Position getLastPosition()
	{
		return lastPosition;
	}
	
	public void setLastPosition(Position lastPosition)
	{
		this.lastPosition = lastPosition.copy();
	}
}
