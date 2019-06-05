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

	public void run()
	{
		Pacman pacman = Board.getPacman();
		ArrayList<Ghost> ghosts = Board.getGhosts();

		GameController.setNextMoveChar(Direction.randomDirection());

		while (!stopped)
		{
			long startTime = System.currentTimeMillis();
<<<<<<< HEAD

=======
			
>>>>>>> da72d29cf3868dd45c91ecfce659d6d2bf7797e8
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
				e.printStackTrace();
			}
		}
	}

	public void stop()
	{
		stopped = true;
	}
}