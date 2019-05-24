package thoniyil.sridaran.pacmangame.main;

import java.io.File;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class LandingPageFXMLController
{
	private Image[] mapImages;
	private File[] mapFiles;
	
	@FXML
    private ListView<String> mapListView;
	
	public void handleMouseClick(MouseEvent e)
	{
    	if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2)
    	{
    		LandingPage.openMapCreator(getSelectedMap());
    	}
	}

	//called by FXML loader after done loading objects
    public void initialize()
    {
        mapFiles = new File("maps/").listFiles((File i) -> i.getName().endsWith(".jpg"));
        System.out.println(Arrays.toString(mapFiles));
        mapImages = new Image[mapFiles.length];
        for (int i = 0; i < mapFiles.length; i++)
        {
        	mapImages[i] = new Image("file:" + mapFiles[i]);
        }
        ObservableList<String> mapStringList = FXCollections.observableArrayList();
        for (File i : mapFiles)
        	mapStringList.add(i.getName());
        mapListView.setItems(mapStringList);
        mapListView.setOrientation(Orientation.HORIZONTAL);
        //mapListView.getSelectionModel().getSelectedIndex();
        
        //https://stackoverflow.com/questions/25570803/image-in-javafx-listview
        mapListView.setCellFactory(listView -> new ListCell<String>() {
		    private ImageView imageView = new ImageView();
			    @Override
			    public void updateItem(String fileName, boolean empty) {
			        super.updateItem(fileName, empty);
			        if (empty) {
			            setText(null);
			            setGraphic(null);
			        } else {
			            Image image = fetch(fileName);
			            imageView.setImage(image);
			            imageView.setFitHeight(180);
			            imageView.setPreserveRatio(true);
			            //https://stackoverflow.com/questions/10949461/javafx-2-click-and-double-click
			            setText(fileName);
			            setGraphic(imageView);
			        }
			    }
		    });
    }
    
    private Image fetch(String file)
    {
    	for (int i = 0; i < mapFiles.length; i++)
    		if (mapFiles[i].getName().equals(file))
    			return mapImages[i];
    	System.out.println("null image: " + file);
    	return null;
    }
    
    private Image getSelectedMap()
    {
    	try
    	{
    		return mapImages[mapListView.getSelectionModel().getSelectedIndex()];
    	}
    	catch (ArrayIndexOutOfBoundsException e)
    	{
    		return mapImages[(int) Math.random() * mapImages.length];
    	}
    }

    @FXML
    private void handlePlayButtonPress(ActionEvent event) {
    	System.out.println("Play Button Pressed");
        LandingPage.openGame(getSelectedMap());
    }
    
    @FXML
    private void handleMapCreatorButtonPress(ActionEvent event)
    {
    	System.out.println("Map Creator Pressed");
    	LandingPage.openMapCreator();
    }
}
