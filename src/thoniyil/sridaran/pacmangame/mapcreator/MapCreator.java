package thoniyil.sridaran.pacmangame.mapcreator;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class MapCreator extends Application
{
	private static Map currentMap;
	private static int tileSize = 20;
	private static Label currentFileName;
	private static TogglableImageView[][] icons;
	
	private static final int h = 20;
	private static final int w = 20;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage) throws Exception
	{
		GridPane pane = new GridPane();
		initPane(pane);
		Scene sc = new Scene(pane);
		stage.setScene(sc);
		stage.show();
	}
	
	private void initPane(GridPane pane)
	{
		icons = new TogglableImageView[h][w];
		for (int r = 0; r < w; r++)
		{
			RowConstraints con = new RowConstraints();
	        // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
	        con.setPrefHeight(tileSize);
	        pane.getRowConstraints().add(con);
	        
	        ColumnConstraints col = new ColumnConstraints();
	        col.setPrefWidth(tileSize);
	        pane.getColumnConstraints().add(col);
	        
			for (int c = 0; c < h; c++)
			{
				icons[r][c] = new TogglableImageView();
				
				icons[r][c].setImage(Wall.getStaticImage());
				
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
}
