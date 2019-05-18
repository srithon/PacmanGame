package thoniyil.sridaran.pacmangame.game.entity;

import javafx.scene.image.Image;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Ghost extends MovableEntity implements Changable, Consumable
{
	private static Image ghostIcon;
	private Direction dir;
	
	static
	{
		ghostIcon = new Image("file:ghost.png", Board.TILE_SIZE, Board.TILE_SIZE, false, true);
	}
	
	public Ghost(Position p)
	{
		super(p);
		dir = Direction.randomDirection();
	}
	
	public Ghost(int x, int y)
	{
		super(x, y);
		dir = Direction.randomDirection();
	}

	public Image getImage()
	{
		return ghostIcon;
	}
	
	public static void setIcon(Image ghostIcon)
	{
		Ghost.ghostIcon = ghostIcon;
	}
	
	public void update()
	{
		move();
	}
	
	public Direction getDirection()
	{
		return dir;
	}
	
	public void move()
	{
		if (Math.random() < 0.25 || !move(dir))
		{
			turnRandom();
		}
	}
	
	public boolean move(Direction dir)
	{
		Position cPos = getPosition();
		
		switch (dir)
		{
			case UP:
				if (!cPos.upSafe())
					return false;
				break;
			case DOWN:
				if (!cPos.downSafe())
					return false;
				break;
			case LEFT:
				if (!cPos.leftSafe())
					return false;
				break;
			case RIGHT:
				if (!cPos.rightSafe())
					return false;
				break;
			default:
				System.out.println("ERROR IN MOVE BOOLEAN IN GHOST: INVALID DIRETCION!");
				return false;
		}
		
		this.dir = dir;
		return true;
	}
	
	public void turnRandom()
	{
		Direction turnDirection = Direction.randomValidTurningDirection(dir);
		if (!move(turnDirection))
		{
			turnDirection = Direction.getOppositeDirection(turnDirection);
			if (!move(turnDirection))
			{
				if (!move(dir))
				{
					turnDirection = Direction.getOppositeDirection(dir);
					move(turnDirection);
					dir = turnDirection;
				}
			}
			else
			{
				dir = turnDirection;
			}
		}
		else
		{
			dir = turnDirection;
		}
	}
	
	public void consume()
	{
		GameController.consumeGhost(this);
	}
}
