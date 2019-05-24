package thoniyil.sridaran.pacmangame.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.ui.Board;
import thoniyil.sridaran.pacmangame.mapcreator.MapCreator;

public class LandingPage extends Application
{
	private static Stage stage;
	
	private static MapCreator mc;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		LandingPage.stage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("LandingPageFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent e) -> {
        	if (stage.getScene() instanceof Board)
        		((Board) stage.getScene()).stop();
        	stage.close();
        });
	}
	
	public static void openGame(Image selectedImage)
	{
		GameController.createBoard(selectedImage);
		Board pacmanScene = GameController.getBoard();
		stage.setScene(pacmanScene);
	}
	
	public static void openMapCreator()
	{
		openMapCreator(null);
	}
	
	public static void openMapCreator(Image mapTemplate)
	{
		//TODO implement <edit map>
		if (mc == null)
		{
			mc = new MapCreator();
		}
		
		stage.setScene(mc.getMapCreatorScene(mapTemplate));
		stage.sizeToScene();
	}
	
	public static void resizeStage()
	{
		stage.sizeToScene();
	}
}
