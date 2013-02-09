package com.mangecailloux.rube.serializers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.mangecailloux.rube.RubeDefaults;

public class BodySerializer extends ReadOnlySerializer<Body>
{
	private 	  World world;
	private final BodyDef def = new BodyDef();
	private final Vector2Serializer vector2Serializer = new Vector2Serializer();
	private final FixtureSerializer fixtureSerializer = new FixtureSerializer();

	public void setWorld(World _world)
	{
		world = _world;
	}
	
	@Override
	public Body read(Json json, Object jsonData, Class type) 
	{
		if(world == null)
			return null;
		BodyDef defaults = RubeDefaults.Body.definition;
		
		// as some Vector2 can be stored as a float we need a custom Vector2 Serializer :(
		json.setSerializer(Vector2.class, vector2Serializer);
		json.setSerializer(Fixture.class, fixtureSerializer);

		int bodyType = json.readValue("type", int.class, defaults.type.getValue(), jsonData);
		
		if(bodyType == BodyType.DynamicBody.getValue())
			def.type = BodyType.DynamicBody;
		else if(bodyType == BodyType.KinematicBody.getValue())
			def.type = BodyType.KinematicBody;
		else
			def.type = BodyType.StaticBody;
		
		def.position.set(		json.readValue("position", 			Vector2.class, defaults.position, 		jsonData));
		def.linearVelocity.set(	json.readValue("linearVelocity", 	Vector2.class, defaults.linearVelocity, jsonData));
		
		def.angle 			= json.readValue("angle", 			float.class, defaults.angle, 			jsonData);
		def.angularVelocity = json.readValue("angularVelocity", float.class, defaults.angularVelocity, 	jsonData);
		def.linearDamping 	= json.readValue("linearDamping", 	float.class, defaults.linearDamping, 	jsonData);
		def.angularDamping 	= json.readValue("angularDamping", 	float.class, defaults.angularDamping, 	jsonData);
		def.gravityScale 	= json.readValue("gravityScale", 	float.class, defaults.gravityScale, 	jsonData);
		
		def.allowSleep 		= json.readValue("allowSleep", 		boolean.class, defaults.allowSleep, 	jsonData);
		def.awake 			= json.readValue("awake",			boolean.class, defaults.awake, 			jsonData);
		def.fixedRotation 	= json.readValue("fixedRotation", 	boolean.class, defaults.fixedRotation, 	jsonData);
		def.bullet 			= json.readValue("bullet", 			boolean.class, defaults.bullet, 		jsonData);
		def.active 			= json.readValue("active", 			boolean.class, defaults.active, 		jsonData);
		
		Body body = world.createBody(def);
		
		if(def.type == BodyType.DynamicBody)
		{
			MassData mass = new MassData();
			mass.center.set(	json.readValue("massData-center",  Vector2.class, jsonData));
			
			mass.mass = json.readValue("massData-mass", 	float.class, 0.0f, 	jsonData);
			mass.I = json.readValue("massData-I", 	float.class, 0.0f, 	jsonData);
			
			if(mass.mass != 0.0f || mass.I != 0.0f || mass.center.x != 0.0f || mass.center.y != 0.0f)
				body.setMassData(mass);
		}
		
		fixtureSerializer.setBody(body);
		json.readValue("fixture", Array.class, Fixture.class, jsonData);
		
		return body;
	}

}
