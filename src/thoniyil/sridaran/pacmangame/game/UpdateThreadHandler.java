package thoniyil.sridaran.pacmangame.game;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
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
		
		t = new Thread(this::start);
		t.start();
	}
	
	public static void setNextMoveChar(Direction d)
	{
		moveChar = d;
	}
	
	public void start()
	{
		Board board = GameController.getBoard();
		
		Coin[] coins = board.getCoins();
		Pacman pacman = board.getPacman();
		Ghost[] ghosts = board.getGhosts();
		PowerUp[] powerUps = board.getPowerUps();
		
		while (!stopped)
		{
			pacman.getPosition().move(moveChar);
			
			for (Ghost g : ghosts)
			{
				g.update();
			}
			
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
