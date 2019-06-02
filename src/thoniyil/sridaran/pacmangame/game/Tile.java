package thoniyil.sridaran.pacmangame.game;

import java.util.HashSet;

import thoniyil.sridaran.pacmangame.game.entity.Entity;

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
