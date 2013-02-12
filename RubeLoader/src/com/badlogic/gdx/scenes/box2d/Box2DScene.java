package com.badlogic.gdx.scenes.box2d;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation.
 * @author clement.vayer
 *
 */
public class Box2DScene 
{
	/** Box2D {@link World} */
	public World world;
	
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	private final Box2DSceneStores		   stores;
	
	public Box2DScene(Box2DSceneStores _stores)
	{
		stores 				= new Box2DSceneStores();
		stepsPerSecond 		= RubeDefaults.World.stepsPerSecond;
		positionIterations 	= RubeDefaults.World.positionIterations;
		velocityIterations 	= RubeDefaults.World.velocityIterations;
	}
	
	/**
	 * Convenience method to update the Box2D simulation with the parameters read from the scene.
	 */
	public void step()
	{
		if(world != null)
		{
			float dt = 1.0f/stepsPerSecond;
			world.step(dt, velocityIterations, positionIterations);
		}
	}
	
	public <T extends Box2DSceneStore> T getStore(Class<T> _type)
	{
		return stores.getStore(_type);
	}
	
	static public class Box2DSceneStores 
	{
		private final ObjectMap<Class<?>, Box2DSceneStore> stores;
		
		public Box2DSceneStores()
		{
			stores = new ObjectMap<Class<?>, Box2DSceneStore>(2);
		}
		
		public void addStore(Box2DSceneStore _store)
		{
			stores.put(_store.getClass(), _store);
		}
		
		public <T extends Box2DSceneStore> T getStore(Class<T > _type)
		{
			return _type.cast(stores.get(_type));
		}
	}
}
