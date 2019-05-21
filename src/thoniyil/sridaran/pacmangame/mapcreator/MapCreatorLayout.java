package thoniyil.sridaran.pacmangame.mapcreator;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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

public class MapCreatorLayout extends VBox
{
	private HBox topBar;
	private GridPane pane;
	
	private TogglableImageView[][] icons;
	
	private int tileSize;
	
	public MapCreatorLayout(int xDim, int yDim)
	{
		this(xDim, yDim, (xDim > 40 || yDim > 40) ? 10 : 20, null);
	}
	
	public MapCreatorLayout(int xDim, int yDim, Image initialImage)
	{
		this(xDim, yDim, (xDim > 40 || yDim > 40) ? 10 : 20, initialImage);
	}
	
	public MapCreatorLayout(int xDim, int yDim, int tileSize, Image initialImage)
	{
		this.tileSize = tileSize;
		icons = new TogglableImageView[yDim][xDim];
		topBar = new HBox(10);
		pane = new GridPane();
		pane.setMaxSize(xDim * tileSize, yDim * tileSize);
		TogglableImageView.resizeImages(tileSize);
		initTopBar();
		initPane();
		
		if (initialImage != null)
			MapCreator.mirrorMap(MapParser.parseImage(initialImage));
		else
			System.out.println("Initial Image is null");
		
		this.getChildren().addAll(topBar, pane);
	}
	
	public TogglableImageView[][] getMap()
	{
		return icons;
	}
	
	private void initTopBar()
	{
		topBar.getChildren().clear();
		
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
				int w = Integer.parseInt(widthField.getText());
				int h = Integer.parseInt(heightField.getText());
				MapCreator.refresh(w, h);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		Button saveButton = new Button("Save");
		saveButton.setOnAction((ActionEvent e) -> MapCreator.writeMapToFile());
		
		topBar.getChildren().addAll(width, widthField, height, heightField, refreshButton, saveButton);
	}
	
	private void initPane()
	{
		pane.getChildren().clear();
		pane = new GridPane();
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
}
