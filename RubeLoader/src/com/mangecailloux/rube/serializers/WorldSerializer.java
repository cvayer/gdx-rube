package com.mangecailloux.rube.serializers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.mangecailloux.rube.RubeDefaults;

public class WorldSerializer extends ReadOnlySerializer<World>
{
	private final BodySerializer bodySerializer = new BodySerializer();
	
	@Override
	public World read(Json json, Object jsonData, Class type) 
	{
		boolean allowSleep = json.readValue("allowSleep", boolean.class, RubeDefaults.World.allowSleep, jsonData);
		boolean autoClearForces = json.readValue("autoClearForces", boolean.class, RubeDefaults.World.autoClearForces, jsonData);
		boolean continuousPhysics = json.readValue("continuousPhysics", boolean.class, RubeDefaults.World.continuousPhysics, jsonData);
		boolean warmStarting = json.readValue("warmStarting", boolean.class, RubeDefaults.World.warmStarting, jsonData);
		
		Vector2 gravity = json.readValue("gravity", Vector2.class, RubeDefaults.World.gravity, jsonData);
		
		World world = new World(gravity, allowSleep);
		world.setAutoClearForces(autoClearForces);
		world.setContinuousPhysics(continuousPhysics);
		world.setWarmStarting(warmStarting);
		
		json.setSerializer(Body.class, bodySerializer);
		
		bodySerializer.setWorld(world);
		json.readValue("body", Array.class, Body.class, jsonData);
		
		return world;
	}

}
