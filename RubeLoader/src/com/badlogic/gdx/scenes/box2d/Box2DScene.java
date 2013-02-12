package com.badlogic.gdx.scenes.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStores;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStores.Box2DSceneStoresDefinition;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation.
 * @author clement.vayer
 *
 */
public class Box2DScene implements IBox2DSceneListener
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
	
	public Box2DScene(Box2DSceneStoresDefinition _definition)
	{
		stores 				= new Box2DSceneStores(_definition);
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

	@Override
	public void onAddWorld(World _world, Box2DSceneCustomProperty _customProperty) 
	{
		stores.onAddWorld(_world, _customProperty);
	}

	@Override
	public void onAddBody(Body _body, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		stores.onAddBody(_body, _name, _customProperty);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		stores.onAddFixture(_fixture, _name, _customProperty);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		stores.onAddJoint(_joint, _name, _customProperty);
	}
}
