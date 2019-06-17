package thoniyil.sridaran.pacmangame.game;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.active.Effect;
import thoniyil.sridaran.pacmangame.game.active.Modifier;
import thoniyil.sridaran.pacmangame.game.entity.Blank;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Consumable;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.Static;
import thoniyil.sridaran.pacmangame.game.ui.Board;
import thoniyil.sridaran.pacmangame.game.ui.InputController;
import thoniyil.sridaran.pacmangame.main.LandingPage;
import thoniyil.sridaran.pacmangame.main.MapParser;

public class GameController
{
	public static final int GHOST_COUNT = 2;
	public static final int UPDATES_PER_SECOND = 10;
	public static final int MAX_LIVES = 3;

	private static final int DEFAULT_POINTS_PER_COIN = 10;
	private static int currentPointsPerCoin = DEFAULT_POINTS_PER_COIN;

	static final int TOTAL_ROUNDS = 1;

	private static int currentRound = 0;

	private static ScoreTracker score;

	//private static Board board;
	private static InputController controller;

	private static Direction pacmanDirection;

	private static Direction activePacmanDirection;

	private static ArrayList<Effect> currentEffects;

	private static Board board;

	private static AudioClip pacmanChomp;

	private static boolean active;

	static
	{
		pacmanChomp = new AudioClip("file:sounds/pacman_chomp.wav");
		pacmanChomp.setVolume(0.20);
	}

	/*public static Board getBoard()
	{
		return board;
	}*/

	public static Board createBoard(Image boardImage)
	{
		Board.setMap(MapParser.parseImage(boardImage));


		if (board == null)
		{
			controller = new InputController();
			Board.setController(controller);
			score = new ScoreTracker(TOTAL_ROUNDS);
			board = new Board(UPDATES_PER_SECOND);
			currentEffects = new ArrayList<>();
		}
		else
		{
			board.init();
			score.reset();
			Board.initUTD(UPDATES_PER_SECOND);
		}

		active = true;

		return board;
	}

	public static SimpleStringProperty getObservableScore()
	{
		return score.getObservableScore();
	}

	public static void resetScore()
	{
		score.reset();
	}

	public static Board createBoard()
	{
		Board.stopUTD();
		if (board == null)
			board = new Board(UPDATES_PER_SECOND);
		return board;
	}

	public static Board getBoard()
	{
		if (board == null)
		{
			controller = new InputController();
			currentEffects = new ArrayList<>();
			score = new ScoreTracker(TOTAL_ROUNDS);
			Board.setController(controller);
			board = new Board(UPDATES_PER_SECOND);
		}

		return board;
	}

	public static int getScore()
	{
		return score.getScore(currentRound);
	}

	public static void moveCharacter(Direction dir)
	{
		Position pacPos = Board.getPacman().getPosition();
		pacmanDirection = dir;
		pacPos.move(dir);
	}

	public static void setNextMoveChar(Direction d)
	{
		activePacmanDirection = d;
	}

	public static Direction getPacmanMoveDirection()
	{
		return activePacmanDirection;
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
		if (j instanceof Consumable && j instanceof Static)
			return true;

		for (Effect e : currentEffects)
		{
			if (e.getModifier() == Modifier.POWER_PELLET)
			{
				return true;
			}
		}

		return !(j instanceof Ghost || j instanceof Blank);
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

		Entity lastPos = Board.getEntity(pacman.getLastPosition());
		if (lastPos instanceof Ghost && ((Ghost) lastPos).getDirection() == Direction.getOppositeDirection(pacmanDirection))
		{
			GameController.gameOver();
		}
		Entity currentPos = Board.getPacmanReplacedEntity();
		if (isConsumable(currentPos))
		{
			//System.out.println(currentPos.getClass().getSimpleName() + " is consumable");
			((Consumable) currentPos).consume();

			if (Board.noCoinsLeft())
			{
				Board.stopUTD();
				LandingPage.openPauseMenu(null);
			}
		}
		else if (currentPos instanceof Ghost)
		{
			GameController.gameOver();
		}
	}

	public static void gameOver()
	{
		Board.stopUTD();
		System.out.println("Life Lost!");
		
		if (!Board.removeLife())
		{
			GameController.endGame();
			return;
		}
		
		Board.removeAllMovables();
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		Board.initUTD(UPDATES_PER_SECOND);
	}

	public static void endGame()
	{
		System.out.println("Game Over!");
		LandingPage.openPauseMenu(true);
	}

	public static void consumeGhost(Ghost g)
	{
		System.out.println("Consume");
		score.increment(currentPointsPerCoin * 5);
		Board.deleteMoving(g);
	}

	public static void pause()
	{
		Board.stopUTD();
		active = false;
		LandingPage.openPauseMenu(false);
	}

	public static void resume()
	{
		active = true;
		Board.initUTD(UPDATES_PER_SECOND);
	}

	public static void useCoin(Coin c)
	{
		if (active)
		{
			score.increment(currentPointsPerCoin);
			Board.deleteEntity(c);
			pacmanChomp.play();
		}
	}

	public static int getCurrentRound()
	{
		return currentRound;
	}
}
