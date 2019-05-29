package thoniyil.sridaran.pacmangame.mapcreator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import thoniyil.sridaran.pacmangame.main.LandingPage;
import thoniyil.sridaran.pacmangame.main.MapParser;

public class MapCreator
{
	private static int tileSize = 20;
	private static TogglableImageView[][] icons;
	
	private static int h = 20;
	private static int w = 20;
	
	public Scene getMapCreatorScene()
	{
		VBox master = new VBox();
		HBox topBar = new HBox(10);
		GridPane pane = new GridPane();
		initTopBar(topBar);
		initPane(pane);
		master.getChildren().add(topBar);
		master.getChildren().add(pane);
		pane.setMaxSize(w * tileSize, h * tileSize);
		//pane.setPadding(new Insets(0, -(w * tileSize) / 4, -(h * tileSize) / 4, 0));
		Scene sc = new Scene(master);
		//pane.setOnMouseClicked((MouseEvent e) -> System.out.println("Scene clicked"));
		return sc;
	}
	
	public Scene getMapCreatorScene(Image startMap)
	{
		if (startMap == null)
			return getMapCreatorScene();
		
		h = (int) startMap.getHeight() / 10;
		w = (int) startMap.getWidth() / 10;
		
		VBox master = new VBox();
		HBox topBar = new HBox(10);
		GridPane pane = new GridPane();
		initTopBar(topBar);
		initPane(pane, MapParser.parseImage(startMap));
		master.getChildren().add(topBar);
		master.getChildren().add(pane);
		pane.setMaxSize(w * tileSize, h * tileSize);
		//pane.setPadding(new Insets(0, -(w * tileSize) / 4, -(h * tileSize) / 4, 0));
		Scene sc = new Scene(master);
		//pane.setOnMouseClicked((MouseEvent e) -> System.out.println("Scene clicked"));
		return sc;
	}
	
	private void initTopBar(HBox topBar)
	{
		Label width = new Label("Width");
		Label height = new Label("Height");
		
		TextField widthField = new TextField();
		TextField heightField = new TextField();
		
		widthField.setMaxWidth(40.0);
		heightField.setMaxWidth(40.0);
		
		Button refreshButton = new Button("Refresh");
		refreshButton.setOnAction((ActionEvent e) -> {
			try
			{
				try
				{
					w = Integer.parseInt(widthField.getText());
					h = Integer.parseInt(heightField.getText());
				}
				catch (NumberFormatException b)
				{
					
				}
				
				if (h > 40 || w > 40)
					tileSize = 15;
				else if (h < 15 || w < 15)
					tileSize = 30;
				
				TogglableImageView.resizeImages(tileSize);
				LandingPage.openMapCreator();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		Button saveButton = new Button("Save");
		saveButton.setOnAction((ActionEvent e) -> writeMapToFile());
		
		topBar.getChildren().addAll(width, widthField, height, heightField, refreshButton, saveButton);
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
	
	private void initPane(GridPane pane, boolean[][] map)
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
				icons[r][c] = new TogglableImageView(map[r][c]);
				
				GridPane.setHalignment(icons[r][c], HPos.CENTER);
				GridPane.setValignment(icons[r][c], VPos.CENTER);
				
				pane.add(icons[r][c], c, r);
			}
		}
	}
	
	/*public static void toggleTile(int x, int y)
	{
		currentMap.toggle(x, y);
	}*/
	
	public static void writeMapToFile()
	{
		int count = 1;
		
		File file;
		
		while ((file = new File("maps/map-" + count + ".jpg")).exists())
			count++;
		
		writeMapToFile(file);
	}

	public static void writeMapToFile(File file)
	{
		try {
			ImageIO.write(renderMap(icons, 10), "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getTileSize()
	{
		return tileSize;
	}
	
	public static BufferedImage renderMap(TogglableImageView[][] map, int renderTileSize)
	{
		BufferedImage im = new BufferedImage(renderTileSize * map[0].length, renderTileSize * map.length, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) (im.getRaster().getDataBuffer())).getData();
		
		int pixelIndex = 0;
		
		for (int r = 0; r < map.length; r++)
		{
			for (int i = 0; i < renderTileSize; i++)
				for (int c = 0; c < map[0].length; c++)
				{
					int pixelValue = (map[r][c].getState()) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
					int finalPixel = pixelIndex + renderTileSize;
					while (pixelIndex < finalPixel)
					{
						pixels[pixelIndex] = pixelValue;
						pixelIndex++;
					}
				}
		}
		
		return im;
	}
}