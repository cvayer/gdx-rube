package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.BaseBox2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class RubeWorldSerializer extends BaseBox2DSceneSerializer<World>
{
	private final RubeBodySerializer 	bodySerializer;
	private final RubeJointSerializer 	jointSerializer;
	
	public RubeWorldSerializer(Json _json, Box2DScene _scene)
	{
		super(_scene);
	
		bodySerializer = new RubeBodySerializer(_json, _scene);
		_json.setSerializer(Body.class, bodySerializer);
		
		jointSerializer = new RubeJointSerializer(_json, _scene);
		_json.setSerializer(Joint.class, jointSerializer);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		// Bodies
		bodySerializer.setWorld(world);
		Array<Body> bodies = json.readValue("body", Array.class, Body.class, jsonData);
		
		// Joints
		// joints are done in two passes because gear joints reference other joints
		// First joint pass
		jointSerializer.init(world, bodies, null);
		Array<Joint> joints = json.readValue("joint", Array.class, Joint.class, jsonData);
		// Second joint pass
		jointSerializer.init(world, bodies, joints);
		joints = json.readValue("joint", Array.class, Joint.class, jsonData);
		
		Box2DSceneCustomProperty customProperty = null;
		if(json.getSerializer(Box2DSceneCustomProperty.class) != null)
			customProperty = json.readValue("customProperties", Box2DSceneCustomProperty.class, jsonData);
		
		onAddWorld(world, customProperty);
		
		return world;
	}

	public void onReadWorldContent(World _world, Json _json, Object _jsonData, Class<?> _type) 
	{
		// Bodies
		bodySerializer.setWorld(_world);
		Array<Body> bodies = _json.readValue("body", Array.class, Body.class, _jsonData);
		// Joints
		// joints are done in two passes because gear joints reference other joints
		// First joint pass
		jointSerializer.init(_world, bodies, null);
		Array<Joint> joints = _json.readValue("joint", Array.class, Joint.class, _jsonData);
		// Second joint pass
		jointSerializer.init(_world, bodies, joints);
		joints = _json.readValue("joint", Array.class, Joint.class, _jsonData);
	}

}
