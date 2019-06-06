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
		pacmanClosed = new Image("file:icons/pacmanClosed.jpg", Board.ICON_SIZE, Board.ICON_SIZE, false, true);
		pacmanOpen = new Image("file:icons/pacmanOpen_grayBackground.png", Board.ICON_SIZE, Board.ICON_SIZE, false, true);
	}
	
	private boolean open;
	
	public Pacman(Position p)
	{
		this(p.getX(), p.getY());
	}
	
	public Pacman(float x, float y)
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
	
	public int paintPrecedence()
	{
		if (GameController.isPowerPelletActive())
			return Integer.MAX_VALUE;
		else
			return 1;
	}
}
