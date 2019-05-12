package thoniyil.sridaran.pacmangame.game.ui;

import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;

public class Board
{
	public static final int WIDTH;
	public static final int HEIGHT;
	
	private static boolean[][] map;
	
	private static Coin[] coins;
	private static Ghost[] ghosts;
	private static Pacman pacman;
	private static PowerUp[] powerUps;
	
	static
	{
		WIDTH = 28; //52
		HEIGHT = 36; //13
	}
	
	public Board(boolean[][] map)
	{
		this.map = map;
	}
	
	public static boolean isEmpty(Position pos)
	{
		return isEmpty(pos.getX(), pos.getY());
	}
	
	public static boolean isEmpty(int x, int y)
	{
		try
		{
			return !(map[y][x]);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
