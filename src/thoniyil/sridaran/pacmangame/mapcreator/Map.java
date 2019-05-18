package thoniyil.sridaran.pacmangame.mapcreator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class Map
{
	private boolean[][] map;
	
	public Map()
	{
		this(null);
	}
	
	public Map(boolean[][] map)
	{
		this.map = map;
	}
	
	public boolean[][] getMap()
	{
		return map;
	}
	
	public void setMap(boolean[][] map)
	{
		this.map = map;
	}
	
	public void toggle(int x, int y)
	{
		map[y][x] = !map[y][x];
	}
	
	public BufferedImage renderMap(int tileSize)
	{
		BufferedImage im = new BufferedImage(tileSize * map[0].length, tileSize * map.length, BufferedImage.TYPE_BYTE_GRAY);
		byte[] pixels = ((DataBufferByte) (im.getRaster().getDataBuffer())).getData();
		
		int pixelIndex = 0;
		
		for (int r = 0; r < map.length; r++)
		{
			for (int i = 0; i < tileSize; i++)
				for (int c = 0; c < map[0].length; c++)
				{
					byte pixelValue = (map[r][c]) ? (byte) 255 : 0;
					int finalPixel = pixelIndex + tileSize;
					while (pixelIndex < finalPixel)
					{
						pixels[pixelIndex] = pixelValue;
						pixelIndex++;
					}
				}
		}
		
		return im;
	}
}
