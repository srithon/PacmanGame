package thoniyil.sridaran.pacmangame.main;

import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;

public class Main 
{
	private GameController game;
	
	public Main()
	{
		game = new GameController(new Board(MapParser.getMap()));
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
