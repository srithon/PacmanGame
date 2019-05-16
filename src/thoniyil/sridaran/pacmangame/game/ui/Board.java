package thoniyil.sridaran.pacmangame.game.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.UpdateThreadHandler;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Consumable;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class Board extends Application
{
	public static final int TILE_SIZE;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	private static int initialCoinCount;
	
	private static boolean[][] map;
	
	private static HashMap<Integer, Entity> entities;
	
	private static HashSet<Entity> entitiesToRefresh;
	
	private static ImageView[][] icons;
	
	private static ArrayList<Ghost> ghosts;
	private static Pacman pacman;
	private static ArrayList<PowerUp> powerUps;
	
	private static Scene sc;
	
	private static GridPane pane;
	
	private static InputController controller;
	
	private static UpdateThreadHandler updater;
	
	static
	{
		/*
		 * TODO
		 * Have WIDTH and HEIGHT set by
		 * the MapParser
		 */
		
		WIDTH = 17;
		HEIGHT = 19;
		
		TILE_SIZE = 25;
		
		icons = new ImageView[HEIGHT][WIDTH];
		
		pane = new GridPane();
		
		entities = new HashMap<>();
		entitiesToRefresh = new HashSet<>(GameController.GHOST_COUNT + 1, 1.0f);
	}
	
	/*public Board(boolean[][] map)
	{
		this.map = map;
	}*/
	
	public static void setController(InputController controller)
	{
		Board.controller = controller;
	}
	
	public static int getPositionHash(Position j)
	{
		return getPositionHash(j.getX(), j.getY());
	}
	
	public static int getPositionHash(int x, int y)
	{
		return y * map[0].length + x;
	}
	
	public static void deleteEntity(Entity e)
	{
		entities.put(getPositionHash(e.getPosition()), new Blank());
	}
	
	public static void beginInitialization(int updatesPerSecond)
	{
		updater = new UpdateThreadHandler(updatesPerSecond);
		
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
			//e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Ghost> getGhosts()
	{
		return ghosts;
	}
	
	public static ArrayList<PowerUp> getPowerUps()
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
		icons[p.getY()][p.getX()] = new ImageView();
		icons[p.getY()][p.getX()].setImage(e.getImage());
		entities.put(getPositionHash(p), e);
	}
	
	public static Entity replaceEntity(Entity e)
	{
		Position p = e.getPosition();
		icons[p.getY()][p.getX()].setImage(e.getImage());
		return entities.put(getPositionHash(p), e); //get the entity underneath
	}
	
	public void putEntities()
	{ //TODO
		ghosts = new ArrayList<>();
		
		for (int i = 0; i < GameController.GHOST_COUNT; i++)
		{
			Ghost g = new Ghost(randomAvailablePosition());
			ghosts.add(g);
			placeEntity(g);
		}
		
		pacman = new Pacman(randomAvailablePosition());
		placeEntity(pacman);
		
		for (int i = 0; i < map.length; i++)
		{
			RowConstraints con = new RowConstraints();
            // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
            con.setPrefHeight(TILE_SIZE + 0.25);
            pane.getRowConstraints().add(con);
			
			for (int j = 0; j < map[i].length; j++)
			{
				icons[i][j] = new ImageView();
				
				if (map[i][j])
				{
					Coin c = new Coin(j, i);
					placeEntity(c);
					//icons[i][j].setImage(Coin.getStaticImage());
					//initialCoinCount++;
				}
				else
				{
					icons[i][j].setImage(Wall.getStaticImage());
				}
				
				GridPane.setHalignment(icons[i][j], HPos.CENTER);
				GridPane.setValignment(icons[i][j], VPos.CENTER);
				
				pane.add(icons[i][j], j, i);
			}
		}
		
		powerUps = new ArrayList<>();
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
		
		sc = new Scene(pane);
		sc.addEventHandler(KeyEvent.KEY_PRESSED, controller);
		prim.setOnCloseRequest((WindowEvent e) ->
		{
			updater.stop();
			prim.close();
		});
		prim.setScene(sc);
		prim.show();
		
		updater.begin();
	}
	
	/*public static void paint(Entity e)
	{
		Position p = e.getPosition();
		icons[p.getY()][p.getX()].setImage(e.getImage());
	}*/
	
	public static void refresh()
	{
		/*for (Coin i : coins)
		{
			paint(i);
		}*/
		
		for (Entity e : entitiesToRefresh)
		{
			replaceEntity(e);
		}
		
		entitiesToRefresh.clear();
		
		for (Ghost i : ghosts)
		{
			Entity m = replaceEntity(i);
			
			if (m instanceof Coin || m instanceof PowerUp)
				entitiesToRefresh.add(m);
		}
		
		/*for (PowerUp i : powerUps)
		{
			paint(i);
		}*/
		
		pacman.animate();
		
		replaceEntity(entities.get(getPositionHash(pacman.getLastPosition())));
		
		Entity j = replaceEntity(pacman);
		if (j instanceof Consumable)
		{
			((Consumable) j).consume();
		}
	}
	
	/*public static int getCoinArrayListIndex(int x, int y)
	{
		Position r = new Position(x, y);
		int ind = y * icons[0].length + x;
		Position c = null;
		
		while ((c = coins.get(ind).getPosition()).compareTo(r) > WIDTH) //c is after r
		{
			y--;
			
			ind = y * icons[0].length + x;
		}
		
		while ((c = coins.get(ind).getPosition()).compareTo(r) > 1)
		{
			x--;
			
			ind = y * icons[0].length + x;
		}
		
		return ind;
	}*/
}
