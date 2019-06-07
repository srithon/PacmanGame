package thoniyil.sridaran.pacmangame.game.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.UpdateThreadHandler;
import thoniyil.sridaran.pacmangame.game.entity.Blank;
import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Consumable;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;
import thoniyil.sridaran.pacmangame.game.entity.MovableEntity;
import thoniyil.sridaran.pacmangame.game.entity.Pacman;
import thoniyil.sridaran.pacmangame.game.entity.Position;
import thoniyil.sridaran.pacmangame.game.entity.PowerUp;
import thoniyil.sridaran.pacmangame.game.entity.Static;
import thoniyil.sridaran.pacmangame.game.entity.Wall;

public class Board extends Scene
{
	public static final int TILE_SIZE;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	private static boolean[][] map;
	
	private static Entity[][] entities;
	
	private static HashSet<Entity> entitiesToRefresh;
	
	private static ImageView[][] icons;
	
	private static ArrayList<Ghost> ghosts;
	private static Pacman pacman;
	private static ArrayList<PowerUp> powerUps;
	
	private static GridPane pane;
	
	private static InputController controller;
	
	private static UpdateThreadHandler updater;
	
	private static Entity pacmanReplaced;
	
	public Board(int updatesPerSecond)
	{
		super(pane);
		init();
		initUTD(updatesPerSecond);
	}
	
	static
	{
		TILE_SIZE = 25;
		
		pane = new GridPane();
		
		entitiesToRefresh = new HashSet<>(GameController.GHOST_COUNT + 1, 1.0f);
	}
	
	/*public Board(boolean[][] map)
	{
		this.map = map;
	}*/
	
	public static void initUTD(int updatesPerSecond)
	{
		if (updater != null)
			updater.stop();
		updater = new UpdateThreadHandler(updatesPerSecond);
		updater.begin();
	}
	
	public static void stopUTD()
	{
		if (updater != null)
			updater.stop();
	}
	
	public static void resumeUTD()
	{
		updater.restart();
	}
	
	public static void setController(InputController controller)
	{
		Board.controller = controller;
	}
	
	public static Entity getEntity(Position p)
	{
		return entities[(int) p.getY()][(int) p.getX()];
	}
	
	public static void deleteEntity(Entity e)
	{
		Entity blank = new Blank(e.getPosition());
		entities[(int) e.getPosition().getY()][(int) e.getPosition().getX()] = blank;
		addToEntitiesToRefresh(blank);
	}
	
	public static void setMap(boolean[][] map)
	{
		Board.map = map;
		Board.WIDTH = map[0].length;
		Board.HEIGHT = map.length;
		icons = new ImageView[HEIGHT][WIDTH];
	}
	
	public static boolean[][] getMap()
	{
		return map;
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
		entities[(int) p.getY()][(int) p.getX()] = e;
	}
	
	public static Entity replaceEntity(Entity e)
	{
		Position p = e.getPosition();
		paint(e);
		Entity replacedEntity = entities[(int) p.getY()][(int) p.getX()];
		entities[(int) p.getY()][(int) p.getX()] = e;
		return replacedEntity; //get the entity underneath
	}
	
	public static void paint(Entity e)
	{
		Position p = e.getPosition();
		icons[p.getY()][p.getX()].setImage(e.getImage());
	}
	
	public void putEntities()
	{
		// !!!!!!!!!!!!!!!!
		// entities.clear();
		// !!!!!!!!!!!!!!!!
		
		if (ghosts != null)
			ghosts.clear();
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
				if (map[i][j])
				{
					Coin c = new Coin(j, i);
					placeEntity(c);
					//icons[i][j].setImage(Coin.getStaticImage());
					//initialCoinCount++;
				}
				else
				{
					icons[i][j] = new ImageView();
					icons[i][j].setImage(Wall.getStaticImage());
				}
				
				icons[i][j].setOnMouseClicked(new ImageViewClickHandler(i, j));
				
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
	
	public void init()
	{
		entities = new Entity[HEIGHT][WIDTH];
		pane.getChildren().clear();
		putEntities();
		
		this.addEventHandler(KeyEvent.KEY_PRESSED, controller);
		
		/*
		this.setOnCloseRequest((WindowEvent e) ->
		{
			updater.stop();
		});
		*/
		
		/*
		if (updater == null)
			initUTD(GameController.UPDATES_PER_SECOND);
		updater.begin();
		*/
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
		
		pacman.animate();
		
		for (Ghost i : ghosts)
		{
			handleMovement(i);
		}
		
		handlePacmanMovement();
		
		GameController.handleCollision();
		
		/*for (PowerUp i : powerUps)
		{
			paint(i);
		}*/
	}
	
	public static Entity getPacmanReplacedEntity()
	{
		return pacmanReplaced;
	}
	
	public static void handlePacmanMovement()
	{
		Entity currentPos = replaceEntity(pacman);
		if (!(currentPos instanceof Consumable))
			entitiesToRefresh.add(currentPos);
		pacmanReplaced = currentPos;
	}
	
	public static Entity handleMovement(MovableEntity entity)
	{
		Entity currentPos = replaceEntity(entity);
		if (currentPos instanceof Static)
			entitiesToRefresh.add(currentPos);
		return currentPos;
	}
	
	public static void deleteMoving(MovableEntity e)
	{
		deleteEntity(e);
		
		if (e instanceof Ghost)
		{
			ghosts.remove(e);
		}
	}
	
	private class ImageViewClickHandler implements EventHandler<MouseEvent>
	{
		private final int x;
		private final int y;
		
		public ImageViewClickHandler(final int x, final int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public void handle(MouseEvent click)
		{
			System.out.println("(" + x + ", " + y + "}");
		}
	}
	
	public static void addToEntitiesToRefresh(Entity j)
	{
		entitiesToRefresh.add(j);
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
