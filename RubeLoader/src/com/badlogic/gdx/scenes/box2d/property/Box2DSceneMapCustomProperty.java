package com.badlogic.gdx.scenes.box2d.property;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;

public class Box2DSceneMapCustomProperty extends Box2DSceneCustomProperty
{
	 private final ObjectIntMap<String> 		ints;
	 private final ObjectMap<String, Float> 	floats;
	 private final ObjectMap<String, String> 	strings;
	 private final ObjectMap<String, Vector2> 	vectors;
	 private final ObjectMap<String, Boolean> 	booleans;
	 
	 
	 public Box2DSceneMapCustomProperty()
	 {
		 ints = new ObjectIntMap<String>(2);
		 floats = new ObjectMap<String, Float>(2);
		 strings = new ObjectMap<String, String>(2);
		 vectors = new ObjectMap<String, Vector2>(2);
		 booleans = new ObjectMap<String, Boolean>(2);
	 }
	 
	 public void addInt(String _name, int _value)
	 {
		 ints.put(_name, _value);
	 }
	 
	 public void addFloat(String _name, float _value)
	 {
		 floats.put(_name, _value);
	 }
	 
	 public void addString(String _name, String _value)
	 {
		 strings.put(_name, _value);
	 }
	 
	 public void addVec2(String _name, Vector2 _value)
	 {
		 vectors.put(_name, _value);
	 }
	 
	 public void addBool(String _name, boolean _value)
	 {
		 booleans.put(_name, _value);
	 }
	 
	 public int getInt(String _name)
	 {
		 return ints.get(_name, 0);
	 }
	 
	 public float getFloat(String _name)
	 {
		 return floats.get(_name);
	 }
	 
	 public String getString(String _name)
	 {
		 return strings.get(_name);
	 }
	 
	 public Vector2 getVec2(String _name)
	 {
		 return vectors.get(_name);
	 }
	 
	 public Boolean getBool(String _name)
	 {
		 return booleans.get(_name);
	 }
}
