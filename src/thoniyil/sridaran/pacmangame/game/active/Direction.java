package thoniyil.sridaran.pacmangame.game.active;

public enum Direction
{
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public static Direction randomValidTurningDirection(Direction dir)
	{
		Direction[] subSet = null;
		
		if (dir == UP || dir == DOWN)
			subSet = new Direction[] {LEFT, RIGHT};
		else
			subSet = new Direction[] {UP, DOWN};
		
		return subSet[((int) Math.random() * subSet.length)];
	}
	
	public static Direction randomDirection()
	{
		return Direction.values()[(int) (Math.random() * 4)];
	}
	
	public static Direction getOppositeDirection(Direction dir)
	{
		if (dir == UP)
			return DOWN;
		else if (dir == DOWN)
			return UP;
		else if (dir == LEFT)
			return RIGHT;
		else
			return LEFT;
	}
}
