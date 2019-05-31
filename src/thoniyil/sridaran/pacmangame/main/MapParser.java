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
import thoniyil.sridaran.pacmangame.mapcreator.TileState;

public class MapParser
{
	public static TileState[][] getRandomMap()
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
	
	public static TileState[][] parseImage(Image mapImage)
	{
		PixelReader reader = mapImage.getPixelReader();
		
		int height = (int) mapImage.getHeight() / 10;
		int width = (int) mapImage.getWidth() / 10;
		
		System.out.println("Height: " + height);
		System.out.println("Width: " + width);
		
		TileState[][] map = new TileState[height][width];
		
		for (int r = 0; r < height; r++)
		{
			for (int c = 0; c < width; c++)
				map[r][c] = TileState.colorToState(reader.getColor(c * 10 + 5, r * 10 + 5));
			
			System.out.println();
		}
		
		return map;
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
