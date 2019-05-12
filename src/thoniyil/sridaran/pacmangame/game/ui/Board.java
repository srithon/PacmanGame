package thoniyil.sridaran.pacmangame.game.ui;

import java.awt.Image;

import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class Board
{
	public static final int WIDTH;
	public static final int HEIGHT;
	
	private static Image[][] squares;
	
	static
	{
		WIDTH = 28; //52
		HEIGHT = 36; //13
	}
	
	public static boolean isEmpty(Position pos)
	{
		return isEmpty(pos.getX(), pos.getY());
	}
	
	public static boolean isEmpty(int x, int y)
	{
		try
		{
			return squares[y][x] != Wall.getStaticImage();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
