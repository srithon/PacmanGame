package thoniyil.sridaran.pacmangame.game;

import java.util.ArrayList;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class UpdateThreadHandler
{
	private int updateDelay;
	
	private boolean stopped;
	
	private static Direction moveChar;
	
	private Thread t;
	
	public UpdateThreadHandler(int updatesPerSecond)
	{
		// 1000 millis/second
		updateDelay = 1000 / updatesPerSecond;
		
		t = new Thread(this::run);
	}
	
	public void begin()
	{
		t.start();
	}
	
	public static void setNextMoveChar(Direction d)
	{
		moveChar = d;
	}
	
	public void run()
	{
		Pacman pacman = Board.getPacman();
		ArrayList<Ghost> ghosts = Board.getGhosts();
		
		moveChar = Direction.UP;
		
		while (!stopped)
		{
			//System.out.println("Pacman Before: " + pacman.getPosition());
			
			pacman.setLastPosition(pacman.getPosition());
			pacman.getPosition().move(moveChar);
			
			//System.out.println("Pacman After: " + pacman.getPosition());
			
			for (Ghost g : ghosts)
			{
				g.update();
			}
			
			Board.refresh();
			
			try
			{
				Thread.sleep(updateDelay);
			}
			catch (InterruptedException e)
			{
				
			}
		}
	}
	
	public void stop()
	{
		stopped = true;
	}
}
