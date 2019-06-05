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
				{
					System.out.println("Up blocked");
					return false;
				}
				break;
			case DOWN:
				if (!downSafe())
				{
					System.out.println("Down blocked");
					return false;
				}
				break;
			case LEFT:
				if (!leftSafe())
				{
					System.out.println("Left blocked");
					return false;
				}
				break;
			case RIGHT:
				if (!rightSafe())
				{
					System.out.println("Right blocked");
					return false;
				}
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
		System.out.println("Current Tile: (" + ((int) (x - GameController.MOVE_FACTOR)) + " " + (int) y + ")");
		if (!Board.isEmpty((int) (x - GameController.MOVE_FACTOR), (int) y))
			return false;
		if (!Board.isEmpty((int) (x - GameController.MOVE_FACTOR), (int) (y + 1)) )
		x -= GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean rightSafe()
	{
		if (!Board.isEmpty((int) (x + 1 + GameController.MOVE_FACTOR), (int) y))
			return false;
		if (!Board.isEmpty((int) (x + 1 + GameController.MOVE_FACTOR), (int) (y + 1)))
			return false;
		x += GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean upSafe()
	{
		if (!Board.isEmpty((int) x, (int) (y - GameController.MOVE_FACTOR)))
			return false;
		if (!Board.isEmpty((int) (x + 1), (int) (y - GameController.MOVE_FACTOR)))
			return false;
		y -= GameController.MOVE_FACTOR;
		return true;
	}
	
	public boolean downSafe()
	{
		if (!Board.isEmpty((int) x, (int) (y + 1 + GameController.MOVE_FACTOR)))
			return false;
		if (!Board.isEmpty((int) (x + 1), (int) (y + 1 + GameController.MOVE_FACTOR)))
			return false;
		y += GameController.MOVE_FACTOR;
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
