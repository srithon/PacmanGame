package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.ui.Board;

@Deprecated
public class Wall extends Entity
{
	private static Image wallIcon;
	
	static
	{
		wallIcon = new Image("file:wall.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
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
