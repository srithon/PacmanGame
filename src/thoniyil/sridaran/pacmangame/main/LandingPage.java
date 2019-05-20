package thoniyil.sridaran.pacmangame.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LandingPage extends Application
{
	private static Stage stage;
	
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
	}
	
	public static void openMapCreator()
	{
		openMapCreator(null);
	}
	
	public static void openMapCreator(Image mapTemplate)
	{
		
	}
}
