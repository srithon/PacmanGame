package thoniyil.sridaran.pacmangame.game.ui;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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

	private static VBox wholeScreen;

	private static HBox scoreContainer;

	private static InputController controller;

	private static UpdateThreadHandler updater;

	private static Entity pacmanReplaced;

	private static Label scoreDisplay;

	private static ArrayList<ImageView> lives;
	
	private static int coinsLeft;

	public Board(int updatesPerSecond)
	{
		super(wholeScreen);
		wholeScreen.getChildren().addAll(pane, scoreContainer);
		scoreDisplay = new Label("Score: ");
		scoreDisplay.textProperty().bind(GameController.getObservableScore());
		scoreContainer.getChildren().add(scoreDisplay);
		scoreContainer.setAlignment(Pos.CENTER_LEFT);
		lives = new ArrayList<>();
		init();
		initUTD(updatesPerSecond);
	}

	static
	{
		TILE_SIZE = 25;

		pane = new GridPane();
		scoreContainer = new HBox(10);
		wholeScreen = new VBox();

		entitiesToRefresh = new HashSet<>(GameController.GHOST_COUNT + 1, 1.0f);
	}

	/*public Board(boolean[][] map)
	{
		this.map = map;
	}*/
	
	public static void removeAllMovables()
	{
		deleteMoving(pacman);
		System.out.println(pacman.getPosition());
		pacman.moveTo(bestRandomAvailablePosition(5));
		System.out.println(pacman.getPosition());
	}
	
	/*
	 * Out of n generated positions,
	 * returns the one with the largest
	 * min distance from a Ghost
	 */
	public static Position bestRandomAvailablePosition(int n)
	{
		Position bestPosition = null;
		double highestMin = Double.MIN_VALUE;
		
		for (int i = 0; i < n; i++)
		{
			Position p = randomAvailablePosition();
			double current = 0;
			double localMin = Double.MAX_VALUE;
			Position localBest = null;
			
			for (Ghost g : ghosts)
			{
				if ((current = p.distance(g.getPosition())) < localMin)
				{
					localMin = current;
					localBest = p;
				}
			}
			
			if (localMin > highestMin)
			{
				highestMin = localMin;
				bestPosition = localBest;
			}
		}
		
		return bestPosition;
	}
	
	/*
	 * Removes a life from the Board
	 * Returns true iff there are lives left
	 * after removal
	 * Returns false if last life was removed
	 * 		Game Over
	 */
	public static boolean removeLife()
	{
		try
		{
			final ImageView life = lives.remove(lives.size() - 1);
			Platform.runLater(() -> scoreContainer.getChildren().remove(life));
			return !lives.isEmpty();
		}
		catch (IndexOutOfBoundsException e)
		{
			return false; //THIS IS SOMETIMES RAISED!
		}
	}

	public static void initUTD(int updatesPerSecond)
	{
		if (updater != null)
			updater.stop();
		updater = new UpdateThreadHandler(updatesPerSecond);
		updater.begin();
	}
	
	public static void decrementCoins()
	{
		coinsLeft--;
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
		entities[p.getY()][p.getX()] = e;
	}

	public static Entity replaceEntity(Entity e)
	{
		Position p = e.getPosition();
		paint(e);
		Entity replacedEntity = entities[(int) p.getY()][(int) p.getX()];
		entities[p.getY()][p.getX()] = e;
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
					coinsLeft++;
					placeEntity(c);
					//icons[i][j].setImage(Coin.getStaticImage());
					//initialCoinCount++;
				}
				else
				{
					icons[i][j] = new ImageView();
					icons[i][j].setImage(Wall.getStaticImage());
				}

				//icons[i][j].setOnMouseClicked(new ImageViewClickHandler(i, j));

				GridPane.setHalignment(icons[i][j], HPos.CENTER);
				GridPane.setValignment(icons[i][j], VPos.CENTER);

				pane.add(icons[i][j], j, i);
			}
		}

		powerUps = new ArrayList<>();
	}

	public static Position randomAvailablePosition()
	{
		Position p = null;

		while (!isEmpty((p = new Position((int) (Math.random() * map[0].length), (int) (Math.random() * map.length)))))
			System.out.println(p);

		return p;
	}
	
	public static boolean noCoinsLeft()
	{
		return coinsLeft == 0;
	}

	public void init()
	{
		coinsLeft = 0;
		
		entities = new Entity[HEIGHT][WIDTH];
		pane.getChildren().clear();
		pane.getColumnConstraints().clear();
		pane.getRowConstraints().clear();
		putEntities();
		
		scoreContainer.getChildren().clear();
		scoreContainer.getChildren().add(scoreDisplay);
		
		Image heartImage = new Image("file:icons/heart.png");
		lives.clear();
		
		for (int i = 0; i < GameController.MAX_LIVES; i++)
		{
			ImageView iv2 = new ImageView();
			iv2.setImage(heartImage);
			iv2.setFitHeight(50);
			iv2.setPreserveRatio(true);
			lives.add(iv2);
			scoreContainer.getChildren().add(iv2);
		}
		
		System.out.println(lives.size());

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
}
