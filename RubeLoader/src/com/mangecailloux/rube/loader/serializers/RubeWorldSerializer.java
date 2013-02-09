package com.mangecailloux.rube.loader.serializers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.mangecailloux.rube.RubeDefaults;
import com.mangecailloux.rube.RubeScene;

public class RubeWorldSerializer extends ReadOnlySerializer<RubeScene>
{
	public RubeWorldSerializer(Json json)
	{
		json.setSerializer(World.class, new WorldSerializer(json));
		json.setIgnoreUnknownFields(true);
	}
	
	@Override
	public RubeScene read(Json json, Object jsonData, Class type) 
	{
		RubeScene world = new RubeScene();
		
		world.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.World.stepsPerSecond, 		jsonData);
		world.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.World.positionIterations, 	jsonData);
		world.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.World.velocityIterations, 	jsonData);
		world.world					= json.readValue(World.class,	jsonData);
		return world;
	}

}
