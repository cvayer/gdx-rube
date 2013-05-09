package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Serializer to read a {@link Vector2} from a RUBE .json file. <br/>
 * It was added because sometime a (0,0) vector can be store as a simple 0, and the readValue method of the Json object can't cope with it.
 * @author clement.vayer
 */
@SuppressWarnings("rawtypes")
class Vector2Serializer extends ReadOnlySerializer<Vector2>
{
	@Override
	public Vector2 read(Json json, JsonValue jsonData, Class type) 
	{
		Vector2 vector = new Vector2(0.0f, 0.0f); 
		
		try 
		{
			json.readValue(float.class, jsonData);
		} 
		catch (SerializationException e) 
		{
			vector.x = json.readValue("x", float.class, 0.0f, jsonData);
			vector.y = json.readValue("y", float.class, 0.0f, jsonData);
		}
				
		return vector;
	}

}
