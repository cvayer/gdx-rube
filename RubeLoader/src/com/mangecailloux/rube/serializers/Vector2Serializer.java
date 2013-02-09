package com.mangecailloux.rube.serializers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.SerializationException;

public class Vector2Serializer extends ReadOnlySerializer<Vector2>
{
	@Override
	public Vector2 read(Json json, Object jsonData, Class type) 
	{
		Vector2 vector = new Vector2(0.0f, 0.0f); 
		
		try 
		{
			Float value = json.readValue(float.class, jsonData);
		} 
		catch (SerializationException e) 
		{
			vector.x = json.readValue("x", float.class, 0.0f, jsonData);
			vector.y = json.readValue("y", float.class, 0.0f, jsonData);
		}
				
		return vector;
	}

}
