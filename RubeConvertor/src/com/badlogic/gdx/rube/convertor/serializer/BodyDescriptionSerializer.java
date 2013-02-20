package com.badlogic.gdx.rube.convertor.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.rube.convertor.description.BodyDescription;
import com.badlogic.gdx.rube.convertor.description.FixtureDescription;
import com.badlogic.gdx.rube.convertor.description.WorldDescription;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;

public class BodyDescriptionSerializer implements Serializer<BodyDescription>
{
	WorldDescription world;
	
	public BodyDescriptionSerializer(Json _json)
	{
		
	}
	
	public void setWorld(WorldDescription _world)
	{
		world = _world;
	}
	
	@Override
	public void write(Json json, BodyDescription object, Class knownType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BodyDescription read(Json json, Object jsonData, Class type) {
		
		if(world == null)
			return null;

		BodyDescription body = new BodyDescription();

		int bodyType = json.readValue("type", int.class, 0, jsonData);
		
		if(bodyType == BodyType.DynamicBody.getValue())
			body.body.type = BodyType.DynamicBody;
		else if(bodyType == BodyType.KinematicBody.getValue())
			body.body.type = BodyType.KinematicBody;
		else
			body.body.type = BodyType.StaticBody;
		
		body.body.position.set(		json.readValue("position", 			Vector2.class, body.body.position, 		jsonData));
		body.body.linearVelocity.set(	json.readValue("linearVelocity", 	Vector2.class, body.body.linearVelocity, jsonData));
		body.body.angle 			= json.readValue("angle", 			float.class, body.body.angle, 			jsonData);
		body.body.angularVelocity 	= json.readValue("angularVelocity", float.class, body.body.angularVelocity, 	jsonData);
		body.body.linearDamping 	= json.readValue("linearDamping", 	float.class, body.body.linearDamping, 	jsonData);
		body.body.angularDamping 	= json.readValue("angularDamping", 	float.class, body.body.angularDamping, 	jsonData);
		body.body.gravityScale 		= json.readValue("gravityScale", 	float.class, body.body.gravityScale, 	jsonData);
		body.body.allowSleep 		= json.readValue("allowSleep", 		boolean.class, body.body.allowSleep, 	jsonData);
		body.body.awake 			= json.readValue("awake",			boolean.class, body.body.awake, 			jsonData);
		body.body.fixedRotation 	= json.readValue("fixedRotation", 	boolean.class, body.body.fixedRotation, 	jsonData);
		body.body.bullet 			= json.readValue("bullet", 			boolean.class, body.body.bullet, 		jsonData);
		body.body.active 			= json.readValue("active", 			boolean.class, body.body.active, 		jsonData);
		
		
		if(body.body.type == BodyType.DynamicBody)
		{
			MassData massData = new MassData();
			
			massData.center.set(json.readValue("massData-center",  Vector2.class, massData.center, jsonData));
			massData.mass = json.readValue("massData-mass", 	float.class, 0.0f, 	jsonData);
			massData.I = json.readValue("massData-I", 	float.class, 0.0f, 	jsonData);
			
			body.massDataIndex = world.addMassData(massData);
		}
		
		Array<FixtureDescription> fixtures = json.readValue("fixture", Array.class, FixtureDescription.class, jsonData);
		
		for(int i=0; i < fixtures.size; ++i)
		{
			int index = world.addFixture(fixtures.get(i));
			
			body.fixturesIndexes.add(index);
		}
		
		
		body.name = json.readValue("name", String.class, jsonData);
		
		return body;
	}

}
