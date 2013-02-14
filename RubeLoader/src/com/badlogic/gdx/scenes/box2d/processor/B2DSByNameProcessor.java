package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;
import com.badlogic.gdx.utils.ObjectMap;

public class B2DSByNameProcessor extends B2DSProcessor
{
	ObjectMap<String, Object> objects;
	
	public B2DSByNameProcessor()
	{
		objects = new ObjectMap<String, Object>();
	}
	
	public <T> T get(Class<T> _type, String _name)
	{
		if(_name != null && _type != null)
			return _type.cast(objects.get(_name));
		return null;
	}
	
	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_body != null && _name != null)
			objects.put(_name, _body);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_fixture != null && _name != null)
			objects.put(_name, _fixture);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_joint != null && _name != null)
			objects.put(_name, _joint);
	}
}
