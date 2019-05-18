package thoniyil.sridaran.pacmangame.game.ui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.UpdateThreadHandler;
import thoniyil.sridaran.pacmangame.game.active.Direction;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;

public class InputController implements EventHandler<KeyEvent>
{
/*	// TODO
	public boolean pressingUp()
	{
		return false;
	}
	
	public boolean pressingLeft()
	{
		return false;
	}
	
	public boolean pressingDown()
	{
		return false;
	}
	
	public boolean pressingRight()
	{
		return false;
	}
*/
	@Override
	public void handle(KeyEvent press)
	{
		KeyCode c = press.getCode();
		
		Pacman pacman = Board.getPacman();
		pacman.setLastPosition(pacman.getPosition());
		
		switch (c)
		{ //UP and DOWN are switched for some reason
			case UP: GameController.setNextMoveChar(Direction.DOWN); break;
			case DOWN: GameController.setNextMoveChar(Direction.UP); break;
			case LEFT: GameController.setNextMoveChar(Direction.LEFT); break;
			case RIGHT: GameController.setNextMoveChar(Direction.RIGHT); break;
		}
	}
}
