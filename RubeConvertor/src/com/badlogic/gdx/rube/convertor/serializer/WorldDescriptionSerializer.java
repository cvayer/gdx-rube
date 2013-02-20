package com.badlogic.gdx.rube.convertor.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.rube.convertor.description.BodyDescription;
import com.badlogic.gdx.rube.convertor.description.WorldDescription;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;

public class WorldDescriptionSerializer implements Serializer<WorldDescription>
{
	private BodyDescriptionSerializer bodySerializer;
	
	public WorldDescriptionSerializer(Json _json)
	{
		bodySerializer = new BodyDescriptionSerializer(_json);
		_json.setSerializer(BodyDescription.class, bodySerializer);
	}
	
	@Override
	public void write(Json json, WorldDescription object, Class knownType) {
		
		json.writeObjectStart();
		
		json.writeObjectEnd();
	}

	@Override
	public WorldDescription read(Json json, Object jsonData, Class type) 
	{
		WorldDescription world = new WorldDescription();
		
		world.allowSleep 		= json.readValue("allowSleep", boolean.class, RubeDefaults.World.allowSleep, jsonData);
		world.autoClearForces 	= json.readValue("autoClearForces", boolean.class, RubeDefaults.World.autoClearForces, jsonData);
		world.continuousPhysics = json.readValue("continuousPhysics", boolean.class, RubeDefaults.World.continuousPhysics, jsonData);
		world.warmStarting 		= json.readValue("warmStarting", boolean.class, RubeDefaults.World.warmStarting, jsonData);
		world.gravity 			= json.readValue("gravity", Vector2.class, RubeDefaults.World.gravity, jsonData);
		
		bodySerializer.setWorld(world);
		world.bodies = json.readValue("body", Array.class, BodyDescription.class, jsonData);
		
		return world;
	}

}
