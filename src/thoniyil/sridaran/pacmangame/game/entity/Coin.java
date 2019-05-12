package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;

import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Coin extends Entity
{
	private static Image coinIcon;
	
	static
	{
		coinIcon = new Image("coin.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
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
