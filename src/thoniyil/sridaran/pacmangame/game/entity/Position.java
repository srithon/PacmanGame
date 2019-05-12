package thoniyil.sridaran.pacmangame.game.entity;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Position
{
	private int x, y;
	
	public Position()
	{
		this(0, 0);
	}
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//getters
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	//move
	/*public boolean left()
	{
		if (x == 0)
			return false;
		x--;
		return true;
	}
	
	public boolean right()
	{
		if (x == Board.WIDTH)
			return false;
		x++;
		return true;
	}
	
	public boolean up()
	{
		if (y == Board.HEIGHT)
			return false;
		y++;
		return true;
	}
	
	public boolean down()
	{
		if (y == 0)
			return false;
		y--;
		return true;
	}*/
	
	//move with wall detection
	
	/*
	 * else if (!Board.isEmpty(x - 1, y))
			return false;
	 */
	
	public boolean move(Direction d)
	{
		switch (d)
		{
			case UP:
				if (!upSafe())
					return false;
				break;
			case DOWN:
				if (!downSafe())
					return false;
				break;
			case LEFT:
				if (!leftSafe())
					return false;
				break;
			case RIGHT:
				if (!rightSafe())
					return false;
				break;
			default:
				System.out.println("ERROR IN MOVE BOOLEAN IN GHOST: INVALID DIRETCION!");
				return false;
		}
		
		return true;
	}
	
	public boolean leftSafe()
	{
		if (!Board.isEmpty(x - 1, y))
			return false;
		//return left();
		x--;
		return true;
	}
	
	public boolean rightSafe()
	{
		if (!Board.isEmpty(x + 1, y))
			return false;
		//return right();
		x++;
		return true;
	}
	
	public boolean upSafe()
	{
		if (!Board.isEmpty(x, y + 1))
			return false;
		//return up();
		y++;
		return true;
	}
	
	public boolean downSafe()
	{
		if (!Board.isEmpty(x, y - 1))
			return false;
		//return down();
		y--;
		return true;
	}
}
