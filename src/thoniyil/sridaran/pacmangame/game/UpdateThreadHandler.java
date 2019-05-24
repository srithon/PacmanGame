package thoniyil.sridaran.pacmangame.game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class UpdateThreadHandler extends AnimationTimer
{
	private final long updateDelay;
	private long lastUpdate;
	
	public UpdateThreadHandler(final int updateHertz)
	{
		this.updateDelay = (int) ((1.0 / updateHertz) * 1e9);
		GameController.setNextMoveChar(Direction.randomDirection());
	}
	
	public void handle(long now)
	{
		if (now - lastUpdate > updateDelay)
		{
			Board.getPacman().move();
			
			for (Ghost g : Board.getGhosts())
			{
				g.update();
			}
			
			Board.refresh();
			
			this.lastUpdate = now;
		}
	}
}
