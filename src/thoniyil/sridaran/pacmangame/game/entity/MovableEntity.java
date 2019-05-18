package thoniyil.sridaran.pacmangame.game.entity;

public abstract class MovableEntity extends Entity
{
	private Position lastPosition;
	
	protected MovableEntity(int x, int y)
	{
		super(x, y);
	}
	
	public Position getLastPosition()
	{
		return lastPosition;
	}
	
	public void setLastPosition(Position lastPosition)
	{
		this.lastPosition = lastPosition.copy();
	}
}
