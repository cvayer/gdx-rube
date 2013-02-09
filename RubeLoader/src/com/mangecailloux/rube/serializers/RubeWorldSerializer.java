package com.mangecailloux.rube.serializers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.mangecailloux.rube.RubeDefaults;
import com.mangecailloux.rube.RubeWorld;

public class RubeWorldSerializer extends ReadOnlySerializer<RubeWorld>
{
	private final WorldSerializer worldSerializer = new WorldSerializer();
	
	public RubeWorldSerializer(Json json)
	{
		json.setSerializer(World.class, worldSerializer);
		json.setIgnoreUnknownFields(true);
	}
	
	@Override
	public RubeWorld read(Json json, Object jsonData, Class type) 
	{
		RubeWorld world = new RubeWorld();
		
		world.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.World.stepsPerSecond, 		jsonData);
		world.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.World.positionIterations, 	jsonData);
		world.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.World.velocityIterations, 	jsonData);
		world.world					= json.readValue(World.class,	jsonData);
		return world;
	}

}
