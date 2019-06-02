package thoniyil.sridaran.pacmangame.game;

import java.util.HashSet;

import thoniyil.sridaran.pacmangame.game.entity.Entity;
import thoniyil.sridaran.pacmangame.game.entity.MovableEntity;
import thoniyil.sridaran.pacmangame.game.entity.Position;

public class Tile
{
	// top left corner of tile (not pixel coord. grid coord)
	private Position position;
	private HashSet<Entity> entities;
	
	public Tile(Position position)
	{
		this(position, null);
	}
	
	public Tile(Position position, Entity initialEntity)
	{
		entities = new HashSet<>(3, 1.0f);
		this.position = position;
		if (initialEntity != null)
			entities.add(initialEntity);
	}
	
	public Position getTopLeftCorner()
	{
		return position;
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
	
	public void removeNonStatic()
	{
		entities.removeIf(e -> e instanceof MovableEntity);
	}
	
	//https://stackoverflow.com/questions/5690351/java-stringlist-toarray-gives-classcastexception
	public Entity[] getEntities()
	{
		return entities.toArray(new Entity[0]);
	}
	
	public Entity[] getEntitiesSorted()
	{
		// paint first in the beginning, paint last at the end
		// lower the paint precedence, the earlier get painted
		// paint precedence ascending
		Entity[] entities = getEntities();
		
		for (int i = 1; i < entities.length; i++)
		{
			if (i != 0 && entities[i - 1].paintPrecedence() > entities[i].paintPrecedence())
			{
				Entity temp = entities[i - 1];
				entities[i - 1] = entities[i];
				entities[i] = temp;
				i--;
			}
		}
		
		return entities;
	}
}
