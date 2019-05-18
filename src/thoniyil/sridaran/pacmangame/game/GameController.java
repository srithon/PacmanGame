package thoniyil.sridaran.pacmangame.game;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.active.Effect;
import thoniyil.sridaran.pacmangame.game.active.Modifier;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.ui.Board;
import thoniyil.sridaran.pacmangame.game.ui.InputController;

public class GameController
{
	public static final int GHOST_COUNT = 5;
	private static final int UPDATES_PER_SECOND = 10;
	
	private static final int DEFAULT_POINTS_PER_COIN = 10;
	private static int currentPointsPerCoin = DEFAULT_POINTS_PER_COIN;
	
	static final int TOTAL_ROUNDS = 1;
	
	private static int currentRound = 0;
	
	private static ScoreTracker score;
	
	//private static Board board;
	private static InputController controller;
	
	/*public static Board getBoard()
	{
		return board;
	}*/
	
	public static void start()
	{
		controller = new InputController();
		score = new ScoreTracker(TOTAL_ROUNDS);
		Board.setController(controller);
		Board.beginInitialization(UPDATES_PER_SECOND);
	}
	
	public static void moveCharacter(Direction dir)
	{
		Position pacPos = Board.getPacman().getPosition();
		pacPos.move(dir);
	}
	
	public static void executeEffect(Effect effect)
	{
		switch (effect.getModifier())
		{
			case CLOCK_SPEED:
				/*
				 * Slows down ghosts
				 * 
				 */
				
				
				
				break;
			case POWER_PELLET:
				/*
				 * Changes color of ghosts
				 * Makes ghosts "edible"
				 * 
				 */
				break;
		}
	}
	
	public static void consumeGhost(Ghost g)
	{
		System.out.println("Consume");
		score.increment(currentPointsPerCoin * 5);
		Board.deleteMoving(g);
	}
	
	public static void useCoin(Coin c)
	{
		score.increment(currentPointsPerCoin);
		Board.deleteEntity(c);
	}
	
	public static int getCurrentRound()
	{
		return currentRound;
	}
}
