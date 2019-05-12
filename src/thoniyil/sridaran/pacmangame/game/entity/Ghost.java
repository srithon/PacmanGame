package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost extends Entity implements Changable
{
	private static Image ghostIcon;
	
	static
	{
		try {
			ghostIcon = ImageIO.read(new File("ghost.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Ghost(int x, int y)
	{
		super(x, y);
	}

	public Image getImage()
	{
		return ghostIcon;
	}
	
	public static void setIcon(Image ghostIcon)
	{
		Ghost.ghostIcon = ghostIcon;
	}
	
	public void update()
	{
		//TODO
	}
}
