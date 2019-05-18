package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Coin extends Entity implements Consumable, StaticEntity
{
	private static Image coinIcon;
	
	static
	{
		coinIcon = new Image("file:coin.png", Board.TILE_SIZE / 1.5, Board.TILE_SIZE / 1.5, false, true);
	}
	
	public Coin(int x, int y)
	{
		super(x, y);
	}
	
	public static void setImage(Image coinIcon)
	{
		Coin.coinIcon = coinIcon;
	}
	
	public static Image getStaticImage()
	{
		return coinIcon;
	}
	
	public Image getImage()
	{
		return coinIcon;
	}
	
	public void consume()
	{
		GameController.useCoin(this);
	}
}
