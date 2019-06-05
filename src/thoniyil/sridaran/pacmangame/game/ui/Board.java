package thoniyil.sridaran.pacmangame.game.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thoniyil.sridaran.pacmangame.game.GameController;
import thoniyil.sridaran.pacmangame.game.Tile;
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
	
	private static int initialCoinCount;
	
	private static boolean[][] map;
	
	private static HashMap<Integer, Tile> tiles;
	
	private static HashSet<Tile> tilesToRefresh;
	
	private static ImageView[][] icons;
	
	private static ArrayList<Ghost> ghosts;
	private static Pacman pacman;
	private static ArrayList<PowerUp> powerUps;
	
	private static Group mainLayoutGroup;
	private static Canvas canvas;
	
	private static InputController controller;
	
	private static UpdateThreadHandler updater;
	
	private static Entity pacmanReplaced;
	
	public Board(int updatesPerSecond)
	{
		super(mainLayoutGroup);
		mainLayoutGroup.getChildren().add(canvas = new Canvas(map[0].length * TILE_SIZE, map.length * TILE_SIZE));
		updater = new UpdateThreadHandler(updatesPerSecond);
		init();
	}
	
	static
	{
		TILE_SIZE = 25;
		
		//icons = new ImageView[HEIGHT][WIDTH];
		
		mainLayoutGroup = new Group();
		
		tiles = new HashMap<>();
		tilesToRefresh = new HashSet<>(GameController.GHOST_COUNT + 1, 1.0f);
	}
	
	public static void setController(InputController controller)
	{
		Board.controller = controller;
	}
	
	public static int getPositionHash(Position j)
	{
		return getPositionHash(j.getX(), j.getY());
	}
	
	public static int getPositionHash(float x, float y)
	{
		return (int) (y * map[0].length) + (int) x;
	}
	
	public static void deleteEntity(Entity e)
	{
		//Entity blank = new Blank(e.getPosition());
		//entities.put(getPositionHash(e.getPosition()), blank);
		Tile[] t = getTiles(e.getPosition());
		
		for (Tile c : t)
		{
			c.removeEntity(e);
			addToTilesToRefresh(c);
		}
	}
	
	public static void setMap(boolean[][] map)
	{
		Board.map = map;
		Board.WIDTH = map[0].length;
		Board.HEIGHT = map.length;
		icons = new ImageView[HEIGHT][WIDTH];
	}
	
	private static Tile[] getTiles(Position p)
	{
		float x = p.getX();
		float y = p.getY();
		
		//System.out.println("Get Tiles: " + x + " " + y);
		
		HashSet<Tile> positionTiles = new HashSet<>(3, 0.66f);
		
		positionTiles.add(tiles.get(getPositionHash(x, y)));
		
		//System.out.println(positionTiles);
		
		if ((int) x + 1 != (int) x)
		{
			positionTiles.add(tiles.get(getPositionHash(x + 1, y)));
			
			if ((int) y + 1 != (int) y)
			{
				positionTiles.add(tiles.get(getPositionHash(x + 1, y + 1)));
			}
		}
		if ((int) y + 1 != (int) y)
		{
			positionTiles.add(tiles.get(getPositionHash(x, y + 1)));
		}
		
		positionTiles.remove(null);
		
		//System.out.println(positionTiles);
		
		return positionTiles.toArray(new Tile[0]);
	}
	
	/*
	public static boolean isEmpty(Position pos)
	{
		return isEmpty(pos.getX(), pos.getY());
	}
	*/
	
	/*
	public static boolean isEmpty(float x, float y)
	{
		if (!isEmpty((int) x, (int) y))
			return false;
		
		if (!isEmpty((int) (x + GameController.TILE_PADDING), (int) (y + GameController.TILE_PADDING)))
			return false;
		
		return isEmpty((int) (x - GameController.TILE_PADDING), (int) (y - GameController.TILE_PADDING));
	}
	*/
	
	public static boolean isEmpty(int x, int y)
	{
		System.out.println("Checking (" + x + ", " + y + ")");
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
	
	public static Entity[] getEntities(int posHash)
	{
		return tiles.get(posHash).getEntities();
	}
	
	public void placeEntity(Entity e)
	{
		paint(e);
		int hash = getPositionHash(e.getPosition());
		tiles.putIfAbsent(hash, new Tile(e.getPosition()));
		Tile t = tiles.get(hash);
		t.addEntity(e);
	}
	
	/*
	public static Entity replaceEntity(Entity e)
	{
		Position p = e.getPosition();
		paint(e);
		return entities.put(getPositionHash(p), e); //get the entity underneath
	}
	*/
	
	public static void paint(Entity e)
	{
		float[] xy = getPositionCoordinates(e.getPosition());
		// TODO draw on screen using graphics context
		canvas.getGraphicsContext2D().drawImage(e.getImage(), xy[0], xy[1]);
	}
	
	public static float[] getPositionCoordinates(Position p)
	{
		return getPositionCoordinates(p.getX(), p.getY());
	}
	
	public static float[] getPositionCoordinates(float x, float y)
	{
		return new float[] { x * Board.TILE_SIZE, y * Board.TILE_SIZE };
	}
	
	public void putEntities()
	{
		for (int i = 0; i < map.length; i++)
		{	
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
					// is this legal?
					Wall w = new Wall(j, i);
					placeEntity(w);
				}
			}
		}
		
		ghosts = new ArrayList<>();
		
		for (int i = 0; i < GameController.GHOST_COUNT; i++)
		{
			Ghost g = new Ghost(randomAvailablePosition());
			ghosts.add(g);
			placeEntity(g);
		}
		
		pacman = new Pacman(randomAvailablePosition());
		placeEntity(pacman);
		
		powerUps = new ArrayList<>();
	}
	
	public Position randomAvailablePosition()
	{
		int x, y;
		while (!isEmpty(x = (int) (Math.random() * map[0].length), y = (int) (Math.random() * map.length)))
			System.out.println(x + " " + y);
		
		return new Position(x, y);
	}
	
	public void stop()
	{
		updater.stop();
	}
	
	public void init()
	{
		putEntities();
		
		this.addEventHandler(KeyEvent.KEY_PRESSED, controller);
		
		/*
		this.setOnCloseRequest((WindowEvent e) ->
		{
			updater.stop();
		});
		*/
		
		updater.begin();
	}
	
	private static void clearTile(Tile t)
	{
		Position p = t.getTopLeftCorner();
		float[] xy = getPositionCoordinates(p);
		canvas.getGraphicsContext2D().clearRect(xy[0], xy[1], Board.TILE_SIZE, Board.TILE_SIZE);
	}
	
	private static void paintTile(Tile t)
	{
		clearTile(t);
		for (Entity e : t.getEntitiesSorted())
			paint(e);
	}
	
	/*public static void paint(Entity e)
	{
		Position p = e.getPosition();
		icons[p.getY()][p.getX()].setImage(e.getImage());
	}*/
	
	public static void refresh()
	{
		for (Tile t : tilesToRefresh)
		{
			clearTile(t);
			t.removeNonStatic();
			paintTile(t);
		}
		
		tilesToRefresh.clear();
		
		pacman.animate();
		
		for (Ghost i : ghosts)
		{
			handleMovement(i);
		}
		
		handlePacmanMovement();
		
		//GameController.handleCollision();
		
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
		/*
		Entity currentPos = replaceEntity(pacman);
		if (!(currentPos instanceof Consumable))
			entitiesToRefresh.add(currentPos);
		pacmanReplaced = currentPos;
		*/
		
		Tile[] pacmanCurrentTiles = getTiles(pacman.getPosition());
		
		for (Tile t : pacmanCurrentTiles)
		{
			t.addEntity(pacman);
			addToTilesToRefresh(t);
			if (t.numEntities() > 1)
			{
				Entity[] entities = t.getEntities();
				for (Entity e : entities)
				{
					if (e instanceof Pacman)
						continue;
					
					if (e instanceof Consumable && e instanceof Static)
						((Consumable) e).consume();
					else if (e instanceof Ghost)
						if (GameController.isConsumable(e))
							GameController.consumeGhost((Ghost) e);
						else
							GameController.gameOver();
				}
			}
			paintTile(t);
		}
	}
	
	public static void handleMovement(MovableEntity entity)
	{
		Tile[] currentTiles = getTiles(entity.getPosition());
		
		for (Tile t : currentTiles)
		{
			t.addEntity(entity);
			addToTilesToRefresh(t);
			paintTile(t);
		}
	}
	
	public static void deleteMoving(MovableEntity e)
	{
		deleteEntity(e);
		
		if (e instanceof Ghost)
		{
			ghosts.remove(e);
		}
	}
	
	public static void addToTilesToRefresh(Tile j)
	{
		tilesToRefresh.add(j);
	}
}
