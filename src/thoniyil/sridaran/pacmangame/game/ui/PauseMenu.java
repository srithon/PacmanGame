package thoniyil.sridaran.pacmangame.game.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import thoniyil.sridaran.pacmangame.game.GameController;

public class PauseMenu extends Scene
{
	private static PauseMenu instance;
	private static Label scoreLabel;
	
	private PauseMenu(Parent root)
	{
		super(root);
	}
	
	public static void createInstance()
	{
		Parent root = null;
		
		try {
			root = FXMLLoader.load(PauseMenu.class.getResource("PauseMenuFXML.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		instance = new PauseMenu(root);
	}
	
	public static void setLabel(Label l)
	{
		scoreLabel = l;
	}
	
	public static PauseMenu getInstance()
	{
		if (instance == null)
			createInstance();
		scoreLabel.setText("Current score: " + GameController.getScore());
		return instance;
	}
}
