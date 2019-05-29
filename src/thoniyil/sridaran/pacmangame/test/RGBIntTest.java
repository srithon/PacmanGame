package thoniyil.sridaran.pacmangame.test;

import javafx.scene.paint.Color;
import thoniyil.sridaran.pacmangame.main.MapParser;

public class RGBIntTest
{
	public static void main(String[] args)
	{
		System.out.println(MapParser.getRGB(Color.BEIGE));
		System.out.println(Integer.toBinaryString(MapParser.getRGB(Color.BEIGE))); //rgb(245,245,220)
	}
}
