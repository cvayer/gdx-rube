package com.badlogic.gdx.scenes.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessor;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessors;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessorsDefinition;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation.
 * @author clement.vayer
 *
 */
public class Box2DScene implements IB2DSAddListener
{
	/** Box2D {@link World} */
	public World world;
	
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	private final B2DSProcessors		   processors;
	
	public Box2DScene(B2DSProcessorsDefinition _definition)
	{
		processors 			= new B2DSProcessors(_definition);
		stepsPerSecond 		= RubeDefaults.World.stepsPerSecond;
		positionIterations 	= RubeDefaults.World.positionIterations;
		velocityIterations 	= RubeDefaults.World.velocityIterations;
	}
	
	public void dispose()
	{
		world.dispose();
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
	
	public <T extends B2DSProcessor> T getStore(Class<T> _type)
	{
		return processors.getStore(_type);
	}

	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		processors.onAddWorld(_world, _customProperty);
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		processors.onAddBody(_body, _name, _customProperty);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		processors.onAddFixture(_fixture, _name, _customProperty);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		processors.onAddJoint(_joint, _name, _customProperty);
	}
}
