package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;

public class Blank extends Entity implements Static
{
	/*private static final Image blankImage;
	
	static
	{
		blankImage = new Image("file:blank.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
	}*/
	
	public Blank(Position p)
	{
		super(p);
	}

	public Blank(int x, int y)
	{
		super(x, y);
	}

	public Image getImage()
	{
		//return blankImage;
		return null;
	}
}
