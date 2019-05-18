package thoniyil.sridaran.pacmangame.mapcreator;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class TogglableImageView extends ImageView
{
	private boolean state;
	
	public TogglableImageView()
	{
		this(false);
	}
	
	public TogglableImageView(boolean state)
	{
		super();
		this.state = state;
		this.setOnDragDetected((MouseEvent e) -> this.startFullDrag());
		this.setOnMouseClicked((MouseEvent e) -> this.toggle());
		this.setOnMouseDragOver((MouseEvent e) -> this.toggle());
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
		System.out.println("Toggled");
		
		if (state)
			setImage(null);
		else
			setImage(Wall.getStaticImage());
	}
}
