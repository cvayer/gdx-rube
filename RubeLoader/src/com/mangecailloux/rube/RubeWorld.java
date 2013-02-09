package com.mangecailloux.rube;

import com.badlogic.gdx.physics.box2d.World;

public class RubeWorld 
{
	public World world;
	public int   stepsPerSecond;
	public int   positionIterations;
	public int   velocityIterations;
	
	public RubeWorld()
	{
		stepsPerSecond 		= RubeDefaults.World.stepsPerSecond;
		positionIterations 	= RubeDefaults.World.positionIterations;
		velocityIterations 	= RubeDefaults.World.velocityIterations;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void setWorld(World _world)
	{
		world = _world;
	}
	
	public void step()
	{
		if(world != null)
		{
			float dt = 1.0f/stepsPerSecond;
			world.step(dt, velocityIterations, positionIterations);
		}
	}
}
