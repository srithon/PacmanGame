package thoniyil.sridaran.pacmangame.game.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.main.LandingPage;

public class PauseMenuFXMLController
{
	@FXML
	private Label pointValue;
	@FXML
	private Button restart, resume;
	
	//auto called after finished loading
	public void initialize()
	{
		//pointValue.setText("Current score: " + GameController.getScore());
		PauseMenu.setLabel(pointValue);
		
		PauseMenu.setResumeButton(resume);
		PauseMenu.setRestartButton(restart);
	}
	
	@FXML
	private void handleExit()
	{
		LandingPage.openLandingPage();
	}
	
	@FXML
	private void handleResume()
	{
		LandingPage.openGame(true);
		GameController.resume();
	}
	
	@FXML
	private void handleRestart()
	{
		LandingPage.openGame(false);
	}
}
