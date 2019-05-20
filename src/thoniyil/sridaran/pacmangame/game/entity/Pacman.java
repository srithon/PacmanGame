package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Pacman extends MovableEntity
{	
	private static Image pacmanClosed;
	private static Image pacmanOpen;
	
	static
	{
		pacmanClosed = new Image("file:icons/pacmanClosed.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
		pacmanOpen = new Image("file:icons/pacmanOpen.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
	}
	
	private boolean open;
	
	public Pacman(Position p)
	{
		this(p.getX(), p.getY());
	}
	
	public Pacman(int x, int y)
	{
		super(x, y);
		
		this.open = false;
	}
	
	public Image getImage()
	{
		if (open)
		{
			return pacmanOpen;
		}
		else
		{
			return pacmanClosed;
		}
	}
	
	public void animate()
	{
		open = !open;
	}
	
	public void move()
	{
		setLastPosition(getPosition());
		GameController.moveCharacter(GameController.getPacmanMoveDirection());
	}
	
	/*public void update()
	{
		Position currentPos = getPosition();
		
		if (controller.pressingDown())
			if (currentPos.downSafe())
				return;
		if (controller.pressingUp())
			if (currentPos.upSafe())
				return;
		if (controller.pressingLeft())
			if (currentPos.leftSafe())
				return;
		if (controller.pressingRight())
			if (currentPos.rightSafe())
				return;
	}*/
}
