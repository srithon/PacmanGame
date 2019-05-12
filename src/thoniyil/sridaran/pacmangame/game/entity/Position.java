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
}
