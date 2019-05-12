package thoniyil.sridaran.pacmangame.game.entity;

import thoniyil.sridaran.pacmangame.game.active.Direction;

public abstract class MovableEntity extends Entity
{
	private Direction dir;
	
	protected MovableEntity(int x, int y)
	{
		super(x, y);
		dir = Direction.randomDirection();
	}
	
	public void move()
	{ //TODO
		if (Math.random() < 0.25)
		{
			//turn
		}
	}
}
