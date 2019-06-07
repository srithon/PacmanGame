package thoniyil.sridaran.pacmangame.game.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PauseMenu extends Scene
{
	private static PauseMenu instance;
	
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
	
	public static PauseMenu getInstance()
	{
		if (instance == null)
			createInstance();
		return instance;
	}
}
