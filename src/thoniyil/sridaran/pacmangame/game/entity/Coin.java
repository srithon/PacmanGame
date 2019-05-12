package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends Entity
{
	private static Image coinIcon;
	
	static
	{
		try {
			coinIcon = ImageIO.read(new File("coin.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Coin(int x, int y)
	{
		super(x, y);
	}
	
	public static void setImage(Image coinIcon)
	{
		Coin.coinIcon = coinIcon;
	}
	
	public Image getImage()
	{
		return coinIcon;
	}
}
