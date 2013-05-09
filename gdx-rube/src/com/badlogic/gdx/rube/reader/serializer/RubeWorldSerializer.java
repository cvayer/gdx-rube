package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Serializer to read a {@link World} from a RUBE .json file.
 * @author clement.vayer
 */
class RubeWorldSerializer extends RubeSerializer<World>
{
	private final RubeBodySerializer 	bodySerializer;
	private final RubeJointSerializer 	jointSerializer;

	
	public RubeWorldSerializer(Json _json)
	{	
		super();
		
		bodySerializer = new RubeBodySerializer(_json);
		_json.setSerializer(Body.class, bodySerializer);
		
		jointSerializer = new RubeJointSerializer(_json);
		_json.setSerializer(Joint.class, jointSerializer);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public World read(Json json, JsonValue jsonData, Class type) 
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
		
		// Bodies
		bodySerializer.setScene(scene);
		bodySerializer.setWorld(world);
		Array<Body> bodies = json.readValue("body", Array.class, Body.class, jsonData);
		
		// Joints
		// joints are done in two passes because gear joints reference other joints
		// First joint pass
		jointSerializer.setScene(scene);
		jointSerializer.init(world, bodies, null);
		Array<Joint> joints = json.readValue("joint", Array.class, Joint.class, jsonData);
		// Second joint pass
		jointSerializer.init(world, bodies, joints);
		joints = json.readValue("joint", Array.class, Joint.class, jsonData);
		
		
		RubeCustomProperty customProperty = null;
		if(json.getSerializer(RubeCustomProperty.class) != null)
			customProperty = json.readValue("customProperties", RubeCustomProperty.class, jsonData);
		
		scene.onAddWorld(world, customProperty);
		
		return world;
	}
}
