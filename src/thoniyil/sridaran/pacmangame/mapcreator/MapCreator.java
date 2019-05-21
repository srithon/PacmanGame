package thoniyil.sridaran.pacmangame.mapcreator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
import javafx.stage.Stage;
import thoniyil.sridaran.pacmangame.main.LandingPage;
import thoniyil.sridaran.pacmangame.main.MapParser;

public class MapCreator
{
	private static int tileSize = 20;
	
	private static int h = 20;
	private static int w = 20;
	
	private Scene currentScene;
	private MapCreatorLayout layout;
	
	public MapCreator(Image initialMap)
	{
		currentScene = new Scene((layout = new MapCreatorLayout(w, h, initialMap)));
		mirrorMap(MapParser.parseImage(initialMap));
	}
	
	public MapCreator()
	{
		currentScene = getMapCreatorScene();
	}
	
	public Scene getCurrentScene()
	{
		return currentScene;
	}
	
	private Scene getMapCreatorScene()
	{
		return getMapCreatorScene(null);
	}
	
	public void refreshScene()
	{
		currentScene = getMapCreatorScene();
	}

	private Scene getMapCreatorScene(Image initialImage)
	{
		if (master != null)
			master.getChildren().clear();
		if (topBar != null)
			topBar.getChildren().clear();
		if (pane != null)
			pane.getChildren().clear();
		
		master = new VBox();
		topBar = new HBox(10);
		pane = new GridPane();
		
		initTopBar();
		initPane();
		
		master.getChildren().add(topBar);
		master.getChildren().add(pane);
		
		return new Scene(master);
	}
	
	static void mirrorMap(boolean[][] map)
	{
		TogglableImageView[][] icons = layout.getIcons();
		for (int r = 0; r < map.length; r++)
		{
			for (int c = 0; c < map[r].length; c++)
			{
				icons[r][c].setState(map[r][c]);
			}
		}
	}
	
	/*public static void toggleTile(int x, int y)
	{
		currentMap.toggle(x, y);
	}*/
	
	public void writeMapToFile()
	{
		int count = 1;
		
		File file;
		
		while ((file = new File("maps/map-" + count + ".jpg")).exists())
			count++;
		
		writeMapToFile(file);
	}

	public void writeMapToFile(File file)
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
		BufferedImage im = new BufferedImage(renderTileSize * map[0].length, renderTileSize * map.length, BufferedImage.TYPE_BYTE_GRAY);
		byte[] pixels = ((DataBufferByte) (im.getRaster().getDataBuffer())).getData();
		
		int pixelIndex = 0;
		
		for (int r = 0; r < map.length; r++)
		{
			for (int i = 0; i < renderTileSize; i++)
				for (int c = 0; c < map[0].length; c++)
				{
					byte pixelValue = (map[r][c].getState()) ? (byte) 255 : 0;
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
