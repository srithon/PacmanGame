package thoniyil.sridaran.pacmangame.game.entity;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Position implements Comparable<Position>
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
	
	public boolean equals(Position o)
	{
		//System.out.println(o + " " + this);
		return x == o.x && y == o.y;
	}
	
	public boolean equals(int x, int y)
	{
		return this.x == x && this.y == y;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public int compareTo(Position o)
	{
		int a = y * Board.WIDTH + x;
		int b = o.y * Board.WIDTH + o.x;
		return a - b;
	}
	
	public Position copy()
	{
		return new Position(x, y);
	}
}
