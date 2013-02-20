package com.badlogic.gdx.rube.convertor.serializer;

import com.badlogic.gdx.rube.convertor.description.RubeSceneDescription;
import com.badlogic.gdx.rube.convertor.description.WorldDescription;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;

public class RubeSceneDescriptionSerializer implements Serializer<RubeSceneDescription> 
{
	public RubeSceneDescriptionSerializer(Json _json)
	{
		_json.setIgnoreUnknownFields(true);
		_json.setSerializer(WorldDescription.class, new WorldDescriptionSerializer(_json));
	}
	
	@Override
	public void write(Json json, RubeSceneDescription object, Class knownType) 
	{
		RubeSceneDescription defaults = new RubeSceneDescription();
		
		json.writeObjectStart();
		if(object.stepsPerSecond != defaults.stepsPerSecond)
			json.writeValue("stepsPerSecond", object.stepsPerSecond, int.class);
		
		if(object.positionIterations != defaults.positionIterations)
			json.writeValue("positionIterations", object.positionIterations, int.class);
		
		if(object.velocityIterations != defaults.velocityIterations)
			json.writeValue("velocityIterations", object.velocityIterations, int.class);
		
		json.writeValue("world", object.world, WorldDescription.class);
		json.writeObjectEnd();
	}

	@Override
	public RubeSceneDescription read(Json json, Object jsonData, Class type) 
	{
		RubeSceneDescription desc = new RubeSceneDescription();
		desc.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, desc.stepsPerSecond, 		jsonData);
		desc.positionIterations 	= json.readValue("positionIterations", 	int.class, desc.positionIterations, 	jsonData);
		desc.velocityIterations 	= json.readValue("velocityIterations", 	int.class, desc.velocityIterations, 	jsonData);
		desc.world					= json.readValue(WorldDescription.class,	jsonData);
		return desc;
	}
}
