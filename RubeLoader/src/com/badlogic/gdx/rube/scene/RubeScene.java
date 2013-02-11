package com.badlogic.gdx.rube.scene;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.rube.scene.store.RubeSceneStore;
import com.badlogic.gdx.rube.scene.store.RubeSceneStores;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation.
 * @author clement.vayer
 *
 */
public class RubeScene 
{
	/** Box2D {@link World} */
	public World world;
	
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	private final RubeSceneStores		   stores;
	
	public RubeScene()
	{
		stores 				= new RubeSceneStores();
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
	
	public void addStore(RubeSceneStore<?> _store)
	{
		stores.addStore(_store);
	}
	
	public <T extends RubeSceneStore<?>> T getStore(Class<T> _type)
	{
		return stores.getStore(_type);
	}
}
