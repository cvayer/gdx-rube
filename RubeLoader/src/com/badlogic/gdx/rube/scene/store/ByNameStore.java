package com.badlogic.gdx.rube.scene.store;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.utils.ObjectMap;

public class ByNameStore<T> extends RubeSceneStore<T>
{
	ObjectMap<String, T> objects;
	
	public ByNameStore()
	{
		objects = new ObjectMap<String, T>();
	}

	@Override
	public void onAdd(T _object, String _name) 
	{
		objects.put(_name, _object);
	}
	
	public T get(String _name)
	{
		return objects.get(_name);
	}
	
	public static class BodiesByNameStore extends ByNameStore<Body> {}
	
	public static class FixturesByNameStore extends ByNameStore<Fixture> {}
	
	public static class JointsByNameStore extends ByNameStore<Joint> {}
}
