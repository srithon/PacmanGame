package thoniyil.sridaran.pacmangame.game.entity;

import java.awt.Image;

public class Ghost extends Entity implements Changable
{
	public Ghost(int x, int y)
	{
		super(x, y);
	}

	@Override
	public Image getIcon()
	{
		return null;
	}
}
