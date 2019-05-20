package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class PowerPellet extends PowerUp implements Static, Consumable
{
	private static Image powerPelletIcon;
	
	static
	{
		powerPelletIcon = new Image("icons/powerPellet.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
	}
	
	public PowerPellet(int x, int y)
	{
		super(x, y);
	}
	
	public Image getImage()
	{
		return powerPelletIcon;
	}
	
	public void consume()
	{
		GameController.executeEffect(getEffect());
	}
}
