package thoniyil.sridaran.pacmangame.game.entity;

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
	public boolean left()
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
	}
	
	//move with wall detection
	
	/*
	 * else if (!Board.isEmpty(x - 1, y))
			return false;
	 */
	
	public boolean leftSafe()
	{
		if (!Board.isEmpty(x - 1, y))
			return false;
		return left();
	}
	
	public boolean rightSafe()
	{
		if (!Board.isEmpty(x + 1, y))
			return false;
		return right();
	}
	
	public boolean upSafe()
	{
		if (!Board.isEmpty(x, y + 1))
			return false;
		return up();
	}
	
	public boolean downSafe()
	{
		if (!Board.isEmpty(x, y - 1))
			return false;
		return down();
	}
}
