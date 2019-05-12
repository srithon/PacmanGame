package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Coin extends Entity
{
	private static Image coinIcon;
	
	static
	{
		try {
			coinIcon = ImageIO.read(new File("coin.png")).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
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
