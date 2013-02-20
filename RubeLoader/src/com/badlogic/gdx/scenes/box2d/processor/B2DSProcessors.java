package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSRemoveListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class B2DSProcessors implements IB2DSAddListener, IB2DSRemoveListener
{
	private final ObjectMap<Class<?>, B2DSProcessor> processorsByType;
	private final Array<B2DSProcessor> processors;
	
	public B2DSProcessors(B2DSProcessorsDefinition _definition)
	{
		processors = new Array<B2DSProcessor>(false, 2);
		processorsByType = new ObjectMap<Class<?>, B2DSProcessor>(2);
		
		for(int i=0; i < _definition.getProcessorsCount(); ++i)
		{
			B2DSProcessor store = _definition.createProcessors(i);
			if(store != null)
				addStore(store);
		}
	}
	
	public void dispose()
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).dispose();
		}
		processors.clear();
		processorsByType.clear();
	}
	
	public void addStore(B2DSProcessor _store)
	{
		processors.add(_store);
		processorsByType.put(_store.getClass(), _store);
	}
	
	public <T extends B2DSProcessor> T getStore(Class<T > _type)
	{
		return _type.cast(processorsByType.get(_type));
	}

	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onAddWorld(_world, _customProperty);
		}
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onAddBody(_body, _name, _customProperty);
		}
		
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onAddFixture(_fixture, _name, _customProperty);
		}
		
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onAddJoint(_joint, _name, _customProperty);
		}
	}
	
	@Override
	public void onAddImage(B2DSImage _image, String _name, Body _body, B2DSCustomProperty _customProperty) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onAddImage(_image, _name, _body,  _customProperty);
		}	
	}

	@Override
	public void onRemoveWorld(World _world) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onRemoveWorld(_world);
		}
		
	}

	@Override
	public void onRemoveBody(Body _body) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onRemoveBody(_body);
		}
		
	}

	@Override
	public void onRemoveFixture(Fixture _fixture) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onRemoveFixture(_fixture);
		}
		
	}

	@Override
	public void onRemoveJoint(Joint _joint) 
	{
		for(int i=0; i< processors.size; ++i)
		{
			processors.get(i).onRemoveJoint(_joint);
		}
	}
}
