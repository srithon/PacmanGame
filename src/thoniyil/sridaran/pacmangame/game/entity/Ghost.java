package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import thoniyil.sridaran.pacmangame.game.active.Direction;

public class Ghost extends Entity implements Changable, Movable
{
	private static Image ghostIcon;
	private Direction dir;
	
	static
	{
		try
		{
			ghostIcon = ImageIO.read(new File("ghost.jpeg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
			move(Direction.getOppositeDirection(turnDirection));
	}
}
