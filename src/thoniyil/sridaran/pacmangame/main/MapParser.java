package thoniyil.sridaran.pacmangame.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class MapParser
{
	public static boolean[][] getRandomMap()
	{
		String[] files = new File("maps/").list(new FilenameFilter() {
			public boolean accept(File directory, String nameOfFile)
			{
				return nameOfFile.endsWith("jpg");
			}
		});
		
		System.out.println(Arrays.toString(files));
		
		String mapFileName = "maps/" + files[(int) (Math.random() * files.length)];
		
		Image mapImage = null;
		
		mapImage = new Image("file:" + mapFileName); //ERROR
		
		return parseImage(mapImage);
	}
	
	public static boolean[][] parseImage(Image mapImage)
	{
		PixelReader reader = mapImage.getPixelReader();
		
		int height = (int) mapImage.getHeight() / 10;
		int width = (int) mapImage.getWidth() / 10;
		
		System.out.println("Height: " + height);
		System.out.println("Width: " + width);
		
		boolean[][] map = new boolean[height][width];
		
		for (int r = 0; r < height; r++)
		{
			for (int c = 0; c < width; c++)
				map[r][c].setState(closestColor(getRGB(reader.getColor(c * 10 + 5, r * 10 + 5))));
			
			System.out.println();
		}
		
		return map;
	}
	
	public static int getRGB(Color c)
	{
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		
		String rgb = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
		
		int rgbVal = Integer.parseInt(rgb, 16);
		
		return rgbVal;
	}
	
	public static int closestColor(int rgb)
	{
		
		
		return 0;
	}
	
	@Deprecated
	public static boolean[][] getMap()
	{
		File map = new File("maps/map1.txt");
		
		boolean[][] mapBool = new boolean[19][17];
		
		try (BufferedReader reader = new BufferedReader(new FileReader(map)))
		{
			String currentLine = "";
			int row = 0;
			
			while ((currentLine = reader.readLine()) != null)
			{
				for (int i = 0; i < currentLine.length(); i++)
				{	//false means wall, true means not wall
					mapBool[row][i] = (currentLine.charAt(i) != '\"');
				}
				
				row++;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return mapBool;
	}
}
