package thoniyil.sridaran.pacmangame.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import thoniyil.sridaran.pacmangame.mapcreator.Map;

public class MapParser
{
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
