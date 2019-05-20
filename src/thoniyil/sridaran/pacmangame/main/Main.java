package thoniyil.sridaran.pacmangame.main;

import java.io.IOException;

import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;
import thoniyil.sridaran.pacmangame.mapcreator.MapCreator;

public class Main
{
	public Main()
	{
		//Board.setMap(MapParser.getMap());
		//GameController.start();
		//MapCreator.main(null);
		//MapParser.getRandomMap();
		LandingPage.main(null);
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
