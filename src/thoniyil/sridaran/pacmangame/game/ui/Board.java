package thoniyil.sridaran.pacmangame.game.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class Board extends Application
{
	public static final int TILE_SIZE = 25;
	
	//public static final int WIDTH;
	//public static final int HEIGHT;
	
	private static boolean[][] map;
	
	private static ImageView[][] icons;
	
	private static Coin[] coins;
	private static Ghost[] ghosts;
	private static Pacman pacman;
	private static PowerUp[] powerUps;
	
	private static Scene sc;
	
	private static GridPane pane;
	
	static
	{
		//WIDTH = 28; //52
		//HEIGHT = 36; //13
		
		icons = new ImageView[19][17];
		
		pane = new GridPane();
	}
	
	/*public Board(boolean[][] map)
	{
		this.map = map;
	}*/
	
	public static void beginInitialization()
	{
		try {
			launch(new String[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setMap(boolean[][] map)
	{
		Board.map = map;
	}
	
	public static boolean isEmpty(Position pos)
	{
		return isEmpty(pos.getX(), pos.getY());
	}
	
	public static boolean isEmpty(int x, int y)
	{
		try
		{
			return (map[y][x]);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			return true;
		}
	}
	
	public static Coin[] getCoins()
	{
		return coins;
	}
	
	public static Ghost[] getGhosts()
	{
		return ghosts;
	}
	
	public static PowerUp[] getPowerUps()
	{
		return powerUps;
	}
	
	public static Pacman getPacman()
	{
		return pacman;
	}
	
	public void placeEntity(Entity e)
	{
		Position p = e.getPosition();
		System.out.println(p);
		//System.out.println(Board.isEmpty(p));
		icons[p.getY()][p.getX()] = new ImageView();
		icons[p.getY()][p.getX()].setImage(e.getImage());
	}
	
	public void putEntities()
	{ //TODO
		ghosts = new Ghost[2];
		ghosts[0] = new Ghost(randomAvailablePosition());
		ghosts[1] = new Ghost(randomAvailablePosition());
		pacman = new Pacman(randomAvailablePosition());
		placeEntity(pacman);
		placeEntity(ghosts[0]);
		placeEntity(ghosts[1]);
		coins = new Coin[0];
		powerUps = new PowerUp[0];
	}
	
	public Position randomAvailablePosition()
	{
		Position p = null;
		
		while (!isEmpty((p = new Position((int) (Math.random() * map[0].length), (int) (Math.random() * map.length)))))
			System.out.println(p);
		
		return p;
	}

	@Override
	public void start(Stage prim) throws Exception
	{
		prim.setTitle("Pacman Board");
		prim.setMinHeight(500);
		prim.setMinWidth(300);
		putEntities();
		
		for (int i = 0; i < icons.length; i++)
		{
			RowConstraints con = new RowConstraints();
            // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
            con.setPrefHeight(TILE_SIZE);
            pane.getRowConstraints().add(con);
			
			for (int j = 0; j < icons[i].length; j++)
			{
				if (!map[i][j])
				{
					icons[i][j] = new ImageView();
					icons[i][j].setImage(Wall.getStaticImage());
				}
				else if (icons[i][j] == null)
				{
					icons[i][j] = new ImageView();
					icons[i][j].setImage(Coin.getStaticImage());
					
				}
				
				GridPane.setHalignment(icons[i][j], HPos.CENTER);
				GridPane.setValignment(icons[i][j], VPos.CENTER);
				
				pane.add(icons[i][j], j, i);
			}
		}
		sc = new Scene(pane);
		prim.setScene(sc);
		prim.show();
	}
}
