package thoniyil.sridaran.pacmangame.mapcreator;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public enum TileState
{
	WALL, EMPTY, PACMAN, GHOST, DEADEND;
	
	/*
	 * Have integer KEYS for each color
	 * Have HashMap for looking up rgb
	 */

	private static final int EMPTY_COLOR = Integer.MIN_VALUE;
	private static final int WALL_COLOR = Integer.MAX_VALUE;
	private static final int PACMAN_COLOR = Integer.MAX_VALUE / 5 * 3;
	private static final int GHOST_COLOR = Integer.MAX_VALUE / 5 * 2;
	private static final int DEADEND_COLOR = Integer.MAX_VALUE / 5;
	
	private static final HashMap<Integer, TileState> COLOR_TABLE;
	private static final EnumMap<TileState, Integer> REVERSED_COLOR_TABLE;
	
	private static final int[] COLOR_VALUES;
	
	static
	{
		COLOR_TABLE = new HashMap<>();
		
		COLOR_TABLE.put(EMPTY_COLOR, EMPTY);
		COLOR_TABLE.put(WALL_COLOR, WALL);
		COLOR_TABLE.put(PACMAN_COLOR, PACMAN);
		COLOR_TABLE.put(GHOST_COLOR, GHOST);
		COLOR_TABLE.put(DEADEND_COLOR, DEADEND);
		
		REVERSED_COLOR_TABLE = new EnumMap<>(TileState.class);
		
		for (int i : COLOR_TABLE.keySet())
		{
			REVERSED_COLOR_TABLE.put(COLOR_TABLE.get(i), i);
		}
		
		COLOR_VALUES = new int[] { EMPTY_COLOR, DEADEND_COLOR, GHOST_COLOR, PACMAN_COLOR, WALL_COLOR };
		Arrays.sort(COLOR_VALUES);
	}
	
	public Image getImage()
	{
		switch(this)
		{
			case WALL:
				return Wall.getStaticImage();
			case DEADEND:
			case EMPTY:
				return Coin.getStaticImage();
		}
		
		System.err.println(this.toString() + " called getImage() on!!!!!!! TileState");
		
		return null;
	}
	
	public int stateToRGB()
	{
		return REVERSED_COLOR_TABLE.get(this);
	}
	
	public static TileState colorToState(Color c)
	{
		return rgbToState(getRGB(c));
	}
	
	private static TileState rgbToState(int rgb)
	{
		// given rgb, return tilestate
		
		int diff = Integer.MAX_VALUE;
		int pointer = 0;
		
		while (diff > 100_000)
		{
			diff = Math.abs(rgb - COLOR_VALUES[++pointer]);
		}
		
		return COLOR_TABLE.get(COLOR_VALUES[pointer]);
	}
	
	private static int getRGB(Color c)
	{
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		
		String rgb = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
		
		return Integer.parseInt(rgb, 16);
	}
}
