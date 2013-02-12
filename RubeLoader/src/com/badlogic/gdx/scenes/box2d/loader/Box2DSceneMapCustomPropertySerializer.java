package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneMapCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

@SuppressWarnings("rawtypes")
public class Box2DSceneMapCustomPropertySerializer extends Box2DSceneCustomPropertySerializer
{
	public Box2DSceneMapCustomPropertySerializer() 
	{
		super();
	}
	
	public Box2DSceneMapCustomPropertySerializer(Box2DScene _scene) 
	{
		super(_scene);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Box2DSceneCustomProperty read(Json json, Object jsonData, Class type) 
	{
		Box2DSceneMapCustomProperty custom = null;
		
		Array<ObjectMap<String,?>> customProperties = json.readValue(Array.class, ObjectMap.class, jsonData);
		
	    if (customProperties != null)
	    {
	    	custom = new Box2DSceneMapCustomProperty();
	    	
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
