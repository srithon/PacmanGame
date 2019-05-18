package thoniyil.sridaran.pacmangame.game;

import java.util.ArrayList;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.active.Effect;
import thoniyil.sridaran.pacmangame.game.active.Modifier;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Consumable;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.PowerPellet;
import thoniyil.sridaran.pacmangame.game.entity.StaticConsumable;
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
	
	private static Direction pacmanDirection;
	
	private static ArrayList<Effect> currentEffects;
	
	/*public static Board getBoard()
	{
		return board;
	}*/
	
	public static void start()
	{
		controller = new InputController();
		currentEffects = new ArrayList<>();
		score = new ScoreTracker(TOTAL_ROUNDS);
		Board.setController(controller);
		Board.beginInitialization(UPDATES_PER_SECOND);
	}
	
	public static void moveCharacter(Direction dir)
	{
		Position pacPos = Board.getPacman().getPosition();
		pacmanDirection = dir;
		pacPos.move(dir);
	}
	
	public static void executeEffect(Effect effect)
	{
		currentEffects.add(effect);
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
	
	public static boolean isConsumable(Entity j)
	{
		if (j instanceof StaticConsumable)
			return true;
		
		for (Effect e : currentEffects)
		{
			if (e.getModifier() == Modifier.POWER_PELLET)
			{
				return true;
			}
		}
		
		return !(j instanceof Ghost);
	}
	
	public static void handleCollision()
	{
		/*
		 * Look at pacman's last position
		 * 	If instanceof Ghost
		 * 	  GameOver
		 * Look at pacman's current position
		 * 	If GameController.isConsumable()
		 * 	  consume()
		 *  Elif instanceof Ghost
		 *    GameOver
		 * 
		 */
		
		Pacman pacman = Board.getPacman();
		
		Entity lastPos = Board.getEntity(Board.getPositionHash(pacman.getLastPosition()));
		if (lastPos instanceof Ghost && ((Ghost) lastPos).getDirection() == Direction.getOppositeDirection(pacmanDirection))
		{
			GameController.gameOver();
		}
		Entity currentPos = Board.getEntity(Board.getPositionHash(pacman.getPosition()));
		if (isConsumable(currentPos))
			((Consumable) currentPos).consume();
		else if (currentPos instanceof Ghost)
			GameController.gameOver();
	}
	
	public static void gameOver()
	{
		System.out.println("Game Over!");
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
