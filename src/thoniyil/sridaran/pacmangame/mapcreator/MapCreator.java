package thoniyil.sridaran.pacmangame.mapcreator;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class MapCreator extends Application
{
	private static Map currentMap;
	private static int tileSize = 20;
	private static TogglableImageView[][] icons;
	private static Stage primaryStage;
	
	private static TextField widthField;
	private static TextField heightField;
	
	private static int h = 20;
	private static int w = 20;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage) throws Exception
	{
		primaryStage = stage;
		Scene mapCreatorScene = getMapCreatorScene();
		stage.setScene(mapCreatorScene);
		stage.show();
		stage.sizeToScene();
		//System.out.println("(" + pane.getHeight() + ", " + pane.getHeight() + ")");
	}
	
	private Scene getMapCreatorScene()
	{
		VBox master = new VBox();
		HBox topBar = new HBox(10);
		GridPane pane = new GridPane();
		initTopBar(topBar);
		initPane(pane);
		master.getChildren().add(topBar);
		master.getChildren().add(pane);
		pane.setMaxSize(w * tileSize, h * tileSize);
		pane.setPadding(new Insets(0, -(w * tileSize) / 4, -(h * tileSize) / 4, 0));
		Scene sc = new Scene(master);
		//pane.setOnMouseClicked((MouseEvent e) -> System.out.println("Scene clicked"));
		return sc;
	}
	
	private void initTopBar(HBox topBar)
	{
		Label width = new Label("Width");
		Label height = new Label("Height");
		
		widthField = new TextField();
		heightField = new TextField();
		
		widthField.setMaxWidth(40.0);
		heightField.setMaxWidth(40.0);
		
		Button refreshButton = new Button("Refresh");
		refreshButton.setOnAction((ActionEvent e) -> {
			try
			{
				w = Integer.parseInt(widthField.getText());
				h = Integer.parseInt(heightField.getText());
				
				if (h > 40 || w > 40)
					tileSize = 15;
				else if (h < 15 || w < 15)
					tileSize = 30;
				
				TogglableImageView.resizeImages();
				
				primaryStage.setScene(getMapCreatorScene());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		topBar.getChildren().addAll(width, widthField, height, heightField, refreshButton);
	}
	
	private void initPane(GridPane pane)
	{
		icons = new TogglableImageView[h][w];
		for (int r = 0; r < h; r++)
		{
			RowConstraints con = new RowConstraints();
	        // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
	        con.setPrefHeight(tileSize);
	        pane.getRowConstraints().add(con);
	        
	        ColumnConstraints col = new ColumnConstraints();
	        col.setPrefWidth(tileSize);
	        pane.getColumnConstraints().add(col);
	        
			for (int c = 0; c < w; c++)
			{
				icons[r][c] = new TogglableImageView();
				
				GridPane.setHalignment(icons[r][c], HPos.CENTER);
				GridPane.setValignment(icons[r][c], VPos.CENTER);
				
				pane.add(icons[r][c], c, r);
			}
		}
	}
	
	public static void toggleTile(int x, int y)
	{
		currentMap.toggle(x, y);
	}

	public static void writeMapToFile(String fileName)
	{
		try {
			ImageIO.write(currentMap.renderMap(tileSize), "jpg", new File(fileName + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getTileSize()
	{
		return tileSize;
	}
}
