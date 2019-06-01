package thoniyil.sridaran.pacmangame.game;

import java.util.HashSet;
import java.util.function.Consumer;

import thoniyil.sridaran.pacmangame.game.entity.Coin;
import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.Ghost;

public class Tile
{
	// does this need Position as an attribute?
	private HashSet<Entity> entities;
	
	public Tile()
	{
		this(null);
	}
	
	public Tile(Entity initialEntity)
	{
		entities = new HashSet<>(3, 1.0f);
		if (initialEntity != null)
			entities.add(initialEntity);
	}
	
	public int numEntities()
	{
		return entities.size();
	}
	
	public void clearSet()
	{
		entities.clear();
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	public void removeEntity(Entity e)
	{
		if (!entities.remove(e))
			System.out.println("Removed entity that was not in HashSet - Tile.java");
	}
	
	//https://stackoverflow.com/questions/5690351/java-stringlist-toarray-gives-classcastexception
	public Entity[] getEntities()
	{
		return entities.toArray(new Entity[0]);
	}
	
	/*
	public void handleCollision()
	{
		if (entities.size() < 2)
			return;
		
		for (Entity e : entities)
		{
			if (e instanceof Coin)
				GameController.useCoin((Coin) e); 
			else if (e instanceof Ghost)
				GameController.handleGhostCollision();
		}
	}
	*/
}
