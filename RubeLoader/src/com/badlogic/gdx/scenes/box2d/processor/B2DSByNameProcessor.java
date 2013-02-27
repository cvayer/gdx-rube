package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.utils.ObjectMap;

public class B2DSByNameProcessor extends B2DSProcessor
{
	ObjectMap<String, Object> objectByName;
	ObjectMap<Object, String> namesByObjects;
	
	public B2DSByNameProcessor()
	{
		objectByName = new ObjectMap<String, Object>();
		namesByObjects = new ObjectMap<Object, String>();
	}
	
	public <T> T get(Class<T> _type, String _name)
	{
		if(_name != null && _type != null)
			return _type.cast(objectByName.get(_name));
		return null;
	}
	
	public String getName(Object _object)
	{
		if(_object != null)
			return namesByObjects.get(_object);
		return null;
	}
	
	@Override
	public void dispose()
	{
		objectByName.clear();
		namesByObjects.clear();
	}
	
	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_body != null && _name != null)
		{
			objectByName.put(_name, _body);
			namesByObjects.put(_body, _name);
		}
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_fixture != null && _name != null)
		{
			objectByName.put(_name, _fixture);
			namesByObjects.put(_fixture, _name);
		}
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_joint != null && _name != null)
		{
			objectByName.put(_name, _joint);
			namesByObjects.put(_joint, _name);
		}
	}
	
	@Override
	public void onAddImage(B2DSImage _image, String _name, Body _body, B2DSCustomProperty _customProperty) 
	{
		
	}

	@Override
	public void onRemoveBody(Body _body) 
	{
		if(_body != null)
		{
			String name = getName(_body);
			objectByName.remove(name);
			namesByObjects.remove(_body);
		}
	}

	@Override
	public void onRemoveFixture(Fixture _fixture)
	{
		if(_fixture != null)
		{
			String name = getName(_fixture);
			objectByName.remove(name);
			namesByObjects.remove(_fixture);
		}
	}

	@Override
	public void onRemoveJoint(Joint _joint) 
	{
		if(_joint != null)
		{
			String name = getName(_joint);
			objectByName.remove(name);
			namesByObjects.remove(_joint);
		}
	}
}
