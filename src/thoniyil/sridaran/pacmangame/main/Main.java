package thoniyil.sridaran.pacmangame.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main 
{
	private JFrame frame;
	
	public Main() {
		frame = new JFrame();
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
