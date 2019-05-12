package thoniyil.sridaran.pacmangame.game.ui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.active.Direction;

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
		
		switch (c)
		{
			case UP: GameController.moveCharacter(Direction.UP); break;
			case DOWN: GameController.moveCharacter(Direction.DOWN); break;
			case LEFT: GameController.moveCharacter(Direction.LEFT); break;
			case RIGHT: GameController.moveCharacter(Direction.RIGHT); break;
		}
	}
}
