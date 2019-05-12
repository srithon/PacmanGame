package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@Deprecated
public class Wall extends Entity
{
	private static Image wallIcon;
	
	static
	{
		try
		{
			wallIcon = ImageIO.read(new File("wall.jpeg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Wall(int x, int y)
	{
		super(x, y);
	}
	
	public static void setImage(Image wallIcon)
	{
		Wall.wallIcon = wallIcon;
	}
	
	public Image getImage()
	{
		return wallIcon;
	}
	
	public static Image getStaticImage()
	{
		return wallIcon;
	}
}
