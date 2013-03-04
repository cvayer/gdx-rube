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
import com.badlogic.gdx.utils.ObjectMap;

/**
 * A {@link B2DSProcessor} that allows storing and retrieving of {@link Box2DScene} objects ( Bodies, joints, custom properties, etc.)
 * by name.
 * @author clement.vayer
 */
public class B2DSByNameStore extends B2DSProcessor
{
	// We store the objects two ways
	ObjectMap<String, Object> objectByName;
	ObjectMap<Object, String> namesByObject;
	
	public B2DSByNameStore()
	{
		objectByName = new ObjectMap<String, Object>();
		namesByObject = new ObjectMap<Object, String>();
	}
	
	/**
	 * Return the object with the wanted name.
	 * @param _type Class of the object you want to retrieve
	 * @param _name Name of the object you want to retrieve
	 * @return the desired casted object, or null if not found.
	 */
	public <T> T get(Class<T> _type, String _name)
	{
		if(_name != null && _type != null)
			return _type.cast(objectByName.get(_name));
		return null;
	}
	
	public String getName(Object _object)
	{
		if(_object != null)
			return namesByObject.get(_object, null);
		return null;
	}
	
	@Override
	public void dispose()
	{
		objectByName.clear();
		namesByObject.clear();
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
			namesByObject.put(_body, _name);
		}
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_fixture != null && _name != null)
		{
			objectByName.put(_name, _fixture);
			namesByObject.put(_fixture, _name);
		}
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_joint != null && _name != null)
		{
			objectByName.put(_name, _joint);
			namesByObject.put(_joint, _name);
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
			namesByObject.remove(_body);
		}
	}

	@Override
	public void onRemoveFixture(Fixture _fixture)
	{
		if(_fixture != null)
		{
			String name = getName(_fixture);
			objectByName.remove(name);
			namesByObject.remove(_fixture);
		}
	}

	@Override
	public void onRemoveJoint(Joint _joint) 
	{
		if(_joint != null)
		{
			String name = getName(_joint);
			objectByName.remove(name);
			namesByObject.remove(_joint);
		}
	}
}
