package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PowerPellet extends PowerUp
{
	private static Image powerPelletIcon;
	
	static
	{
		try {
			powerPelletIcon = ImageIO.read(new File("power_pellet.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PowerPellet(int x, int y)
	{
		super(x, y);
	}
	
	public Image getImage()
	{
		return powerPelletIcon;
	}
}
