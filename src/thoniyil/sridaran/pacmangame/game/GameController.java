package thoniyil.sridaran.pacmangame.game;

import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.active.Modifier;
import thoniyil.sridaran.pacmangame.game.ui.Board;
import thoniyil.sridaran.pacmangame.game.ui.InputController;

public class GameController
{
	private static final int UPDATES_PER_SECOND = 3;
	//private static Board board;
	private static InputController controller;
	
	/*public static Board getBoard()
	{
		return board;
	}*/
	
	public static void start()
	{
		controller = new InputController();
		Board.setController(controller);
		Board.beginInitialization(UPDATES_PER_SECOND);
	}
	
	public static void moveCharacter(Direction dir)
	{
		Board.getPacman().getPosition().move(dir);
	}
	
	public static void executeEffect(Modifier modifier)
	{
		switch (modifier)
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
}
