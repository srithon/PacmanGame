package thoniyil.sridaran.pacmangame.game.entity;

import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.ui.Board;

// describes upper-left corner of the image
public class Position// implements Comparable<Position>
{
	private float x, y;
	
	public Position()
	{
		this(0, 0);
	}
	
	public Position(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	//getters
	public float getX()
	{
		return x;
	}
	
	public float getY()
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
		// int cast y or no?
		if (!Board.isEmpty((int) (x - GameController.MOVE_FACTOR), (int) y))
			return false;
		//return left();
		x -= GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean rightSafe()
	{
		if (!Board.isEmpty((int) (x + Board.TILE_SIZE + GameController.MOVE_FACTOR), (int) y))
			return false;
		//return right();
		x += GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean upSafe()
	{
		if (!Board.isEmpty((int) x, (int) (y - GameController.MOVE_FACTOR)))
			return false;
		//return up();
		y += GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean downSafe()
	{
		if (!Board.isEmpty((int) x, (int) (y + Board.TILE_SIZE + GameController.MOVE_FACTOR)))
			return false;
		//return down();
		y -= GameController.MOVE_FACTOR;
		return true;
	}
	
	/*
	public boolean equals(Position o)
	{
		//System.out.println(o + " " + this);
		return x == o.x && y == o.y;
	}
	
	public boolean equals(int x, int y)
	{
		return this.x == x && this.y == y;
	}
	*/
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	/*
	public int compareTo(Position o)
	{
		int a = y * Board.WIDTH + x;
		int b = o.y * Board.WIDTH + o.x;
		return a - b;
	}
	*/
	
	public Position copy()
	{
		return new Position(x, y);
	}
}
