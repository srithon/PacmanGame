package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import thoniyil.sridaran.pacmangame.game.ui.InputController;

public class Pacman extends Entity implements Changable
{
	private static Image pacmanClosed;
	private static Image pacmanOpen;
	
	private InputController controller;
	
	static
	{
		try
		{
			pacmanClosed = ImageIO.read(new File("pacmanClosed.jpeg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			pacmanOpen = ImageIO.read(new File("pacmanOpen.jpeg"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean open;
	
	public Pacman(int x, int y, InputController controller)
	{
		super(x, y);
		
		this.open = false;
		this.controller = controller;
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
	
	public void update()
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
	}
}
