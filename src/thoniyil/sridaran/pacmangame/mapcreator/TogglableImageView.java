package thoniyil.sridaran.pacmangame.mapcreator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class TogglableImageView extends ImageView
{
	private static Image emptyImage;
	private static Image wallImage;
	
	private static short dragCount;
	
	private short lastDrag;
	
	static
	{
		emptyImage = new Image("file:icons/blank.png", 20, 20, false, true);
		wallImage = new Image("file:icons/wall.png", 20, 20, false, true);
		
		dragCount = 0;
	}
	
	private boolean state;
	
	public TogglableImageView()
	{
		this(false);
	}
	
	public TogglableImageView(boolean state)
	{
		super();
		this.state = state;
		lastDrag = -1;
		this.setOnDragDetected((MouseEvent e) -> {
			this.startFullDrag();
			dragCount++;
		});
		this.setOnMouseClicked((MouseEvent e) -> this.toggle());
		this.setOnMouseDragOver((MouseEvent e) -> {
			if (lastDrag != dragCount)
			{
				this.toggle();
				lastDrag = dragCount;
			}
		});
		this.setImage(wallImage);
	}
	
	public void setState(boolean state)
	{
		this.state = state;
		refreshImage();
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public void toggleState()
	{
		state = !state;
	}
	
	public void toggle()
	{
		toggleState();
		//System.out.println("Toggled");
		refreshImage();
	}
	
	private void refreshImage()
	{
		if (state)
			setImage(emptyImage);
		else
			setImage(wallImage);
	}
	
	public static void resizeImages(int tileSize)
	{
		emptyImage = new Image("file:icons/blank.png", tileSize, tileSize, false, true);
		wallImage = new Image("file:icons/wall.png", tileSize, tileSize, false, true);
	}
}
