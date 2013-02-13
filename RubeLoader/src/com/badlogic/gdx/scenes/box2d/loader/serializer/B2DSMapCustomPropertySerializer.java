package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.property.B2DSMapCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

@SuppressWarnings("rawtypes")
public class B2DSMapCustomPropertySerializer extends B2DSCustomPropertySerializer
{
	public B2DSMapCustomPropertySerializer() 
	{
		super();
	}
	
	public B2DSMapCustomPropertySerializer(Box2DScene _scene) 
	{
		super(_scene);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public B2DSCustomProperty read(Json json, Object jsonData, Class type) 
	{
		B2DSMapCustomProperty custom = null;
		
		Array<ObjectMap<String,?>> customProperties = json.readValue(Array.class, ObjectMap.class, jsonData);
		
	    if (customProperties != null)
	    {
	    	custom = new B2DSMapCustomProperty();
	    	
		    for (int i = 0; i < customProperties.size; i++)
		    {
		    	ObjectMap<String, ?> property = customProperties.get(i);
		    	String propertyName = (String)property.get("name");
		        if (property.containsKey("string"))
		        {
		        	custom.addString(propertyName, json.readValue(String.class, property.get("string")));
		        }
		        else if (property.containsKey("int"))
		        {
		           custom.addInt(propertyName, json.readValue(int.class, property.get("int")));
		        }
		        else if (property.containsKey("float"))
		        {
		           custom.addFloat(propertyName, json.readValue(float.class, property.get("float")));
		        }
		        else if (property.containsKey("vec2"))
		        {
		           custom.addVec2(propertyName, json.readValue(Vector2.class, property.get("vec2")));
		        }
		        else if (property.containsKey("bool"))
		        {
		           custom.addBool(propertyName, json.readValue(boolean.class, property.get("bool")));
		        }
		    }
	   }
		
		return custom;
	}

}
