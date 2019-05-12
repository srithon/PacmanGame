package thoniyil.sridaran.pacmangame.main;

import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Main
{
	public Main()
	{
		Board.setMap(MapParser.getMap());
		GameController.start();
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
