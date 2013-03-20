package com.badlogic.gdx.rube;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Representation of the custom properties of the RUBE scene.
 * Uses {@link ObjectMap} to store the different properties per type, with String as ids.
 * @author HeRMeS
 *
 */
public class RubeCustomProperty
{
	 private final ObjectIntMap<String> 		ints;
	 private final ObjectMap<String, Float> 	floats;
	 private final ObjectMap<String, String> 	strings;
	 private final ObjectMap<String, Vector2> 	vectors;
	 private final ObjectMap<String, Boolean> 	booleans;
	 
	 
	 public RubeCustomProperty()
	 {
		 ints = new ObjectIntMap<String>(2);
		 floats = new ObjectMap<String, Float>(2);
		 strings = new ObjectMap<String, String>(2);
		 vectors = new ObjectMap<String, Vector2>(2);
		 booleans = new ObjectMap<String, Boolean>(2);
	 }
	 
	 public void addInt(String name, int value) {
		 ints.put(name, value);
	 }
	 
	 public void addFloat(String name, float value) {
		 floats.put(name, value);
	 }
	 
	 public void addString(String name, String value) {
		 strings.put(name, value);
	 }
	 
	 public void addVec2(String name, Vector2 value) {
		 vectors.put(name, value);
	 }
	 
	 public void addBool(String name, boolean value) {
		 booleans.put(name, value);
	 }
	 
	 public int getInt(String name) {
		 return ints.get(name, 0);
	 }
	 
	 public float getFloat(String name) {
		 return floats.get(name);
	 }
	 
	 public String getString(String name) {
		 return strings.get(name);
	 }
	 
	 public Vector2 getVec2(String name) {
		 return vectors.get(name);
	 }
	 
	 public Boolean getBool(String name) {
		 return booleans.get(name);
	 }
	 
	 /**
	  * @return the property value if found, the defaultValue if not
	  */
	 public int getInt(String name, int defaultValue) {
		 return ints.get(name, defaultValue);
	 }
	 
	 /**
	  * @return the property value if found, the defaultValue if not
	  */
	 public float getFloat(String name, float defaultValue) {
		 return floats.get(name, defaultValue);
	 }
	 
	 /**
	  * @return the property value if found, the defaultValue if not
	  */
	 public String getString(String name, String defaultValue) {
		 return strings.get(name, defaultValue);
	 }
	 
	 /**
	  * @return the property value if found, the defaultValue if not
	  */
	 public Vector2 getVec2(String name, Vector2 defaultValue) {
		 return vectors.get(name, defaultValue);
	 }
	 
	 /**
	  * @return the property value if found, the defaultValue if not
	  */
	 public Boolean getBool(String name, boolean defaultValue) {
		 return booleans.get(name, defaultValue);
	 }
}
