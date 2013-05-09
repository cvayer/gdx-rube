package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

/**
 * Serializer to read a {@link RubeCustomProperty} from a RUBE .json file.
 * @author clement.vayer
 */
@SuppressWarnings("rawtypes")
public class RubeCustomPropertySerializer extends ReadOnlySerializer<RubeCustomProperty>
{
	@SuppressWarnings("unchecked")
	@Override
	public RubeCustomProperty read(Json json, JsonValue jsonData, Class type) 
	{
		RubeCustomProperty custom = new RubeCustomProperty();
		
		for(int i = 0; i < jsonData.size(); ++i)
		{
			JsonValue child = jsonData.get(i);
			String name = child.getString("name");
			
			if(child.get("string") != null)
			{
				custom.addString(name, child.getString("string"));
			}
			else if (child.get("int") != null)
	        {
	           custom.addInt(name, child.getInt("int"));
	        }
	        else if (child.get("float") != null)
	        {
	           custom.addFloat(name, child.getFloat("float"));
	        }
	        else if (child.get("vec2") != null)
	        {
	           custom.addVec2(name, json.readValue(Vector2.class, child.get("vec2")));
	        }
	        else if (child.get("bool") != null)
	        {
	           custom.addBool(name, child.getBoolean("bool"));
	        }
		}
		
	/*	RubeCustomProperty custom = null;
		
		Array<JsonValue> customProperties = json.readValue(Array.class, JsonValue.class, jsonData);
		
	    if (customProperties != null)
	    {
	    	custom = new RubeCustomProperty();
	    	
		    for (int i = 0; i < customProperties.size; i++)
		    {
		    	JsonValue property = customProperties.get(i);
		    	
		    	String propertyName = property.getString("name");
		    			    	
		        if (property.getChild("string") != null)
		        {
		        	custom.addString(propertyName, property.getString("string"));
		        }
		        else if (property.getChild("int") != null)
		        {
		           custom.addInt(propertyName, property.getInt("int"));
		        }
		        else if (property.getChild("float") != null)
		        {
		           custom.addFloat(propertyName, property.getFloat("float"));
		        }
		        else if (property.getChild("vec2") != null)
		        {
		           custom.addVec2(propertyName, json.readValue(Vector2.class, property.get("vec2")));
		        }
		        else if (property.getChild("bool") != null)
		        {
		           custom.addBool(propertyName, property.getBoolean("bool"));
		        }
		    }
	   }*/
		
		return custom;
	}

}
