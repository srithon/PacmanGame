package thoniyil.sridaran.pacmangame.mapcreator;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public enum TileState
{
	WALL, EMPTY, PACMAN, GHOST, DEADEND;
	
	public Image getImage()
	{
		switch(this)
		{
			case WALL:
				return Wall.getStaticImage();
			case DEADEND:
			case EMPTY:
				return Coin.getStaticImage();
		}
		
		System.err.println(this.toString() + " called getImage() on!!!!!!! TileState");
		
		return null;
	}
}
