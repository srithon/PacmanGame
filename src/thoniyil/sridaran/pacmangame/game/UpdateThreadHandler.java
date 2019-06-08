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
	
	private volatile boolean stopped;
	
	private static Direction moveChar;
	
	private Thread t;
	
	public UpdateThreadHandler(double updatesPerSecond)
	{
		// 1000 millis/second
		updateDelay = (int) (1000 / updatesPerSecond);
		
		t = new Thread(this::run);
	}
	
	public void begin()
	{
		t.start();
	}
	
	public void restart()
	{
		stop();
		t = new Thread(this::run);
		begin();
	}
	
	public void setUpdateHertz(double updatesPerSecond)
	{
		updateDelay = (int) (1000 / updatesPerSecond);
	}
	
	public void run()
	{
		Pacman pacman = Board.getPacman();
		ArrayList<Ghost> ghosts = Board.getGhosts();
		
		Direction d = GameController.getPacmanMoveDirection();
		
		if (d == null)
			GameController.setNextMoveChar(Direction.randomDirection());
		else
			GameController.setNextMoveChar(d);
		
		while (!stopped)
		{
			//System.out.println("Working UTD is " + this);
			
			long startTime = System.currentTimeMillis();
			
			pacman.move();
			
			for (Ghost g : ghosts)
			{
				g.update();
			}
			
			Board.refresh();
			
			try
			{
				Thread.sleep((updateDelay - (System.currentTimeMillis() - startTime)));
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{

			}
		}
	}
	
	public void stop()
	{
		//System.out.println("Stopping UTD: " + this);
		stopped = true;
	}
}