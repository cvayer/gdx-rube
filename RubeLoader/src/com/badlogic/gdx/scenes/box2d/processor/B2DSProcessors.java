/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSRemoveListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;

public class B2DSProcessors implements IB2DSAddListener, IB2DSRemoveListener
{
	private final ObjectMap<Class<?>, B2DSProcessor> processorsByType;
	private final Array<B2DSProcessor> processors;
	/** {@link Box2DScene} the processors belong to. */
	private final Box2DScene 		scene;
	
	public B2DSProcessors(Box2DScene _scene, B2DSProcessorsDefinition _definition)
	{
		scene = _scene;
		processors = new Array<B2DSProcessor>(false, 2);
		processorsByType = new ObjectMap<Class<?>, B2DSProcessor>(2);
		
		if(_definition != null)
		{
			for(int i=0; i < _definition.getProcessorsCount(); ++i)
			{
				B2DSProcessor store = _definition.createProcessors(i);
				if(store != null)
					addProcessor(store);
			}
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
	
	public void addProcessor(B2DSProcessor _processor)
	{
		if(_processor == null)
			return;
		
		if(processorsByType.get(_processor.getClass()) != null)
			throw new GdxRuntimeException("addProcessor  : adding a processor with the same class as an existing one, abort.");
		
		_processor.setScene(scene);
		processors.add(_processor);
		processorsByType.put(_processor.getClass(), _processor);
	}
	
	public <T extends B2DSProcessor> T getProcessor(Class<T > _type)
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
