package thoniyil.sridaran.pacmangame.main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

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
			{
				int color = reader.getArgb(c * 10 + 5, r * 10 + 5);
				map[r][c] = color > -16777216 / 2;
				// This sees if it is closer to min value: (-2)^24 or max value: -1
			}
		}
		
		return map;
	}
}
