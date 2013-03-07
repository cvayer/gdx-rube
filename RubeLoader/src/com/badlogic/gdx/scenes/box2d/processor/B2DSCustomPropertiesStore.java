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
 * A {@link B2DSProcessor} that allows storing and retrieving of {@link B2DSCustomProperty} linked to objects ( Bodies, joints, images, etc.).
 * @author clement.vayer
 */
public class B2DSCustomPropertiesStore extends B2DSProcessor
{
	ObjectMap<Object, B2DSCustomProperty> propertiesByObject;
	
	public B2DSCustomPropertiesStore()
	{
		propertiesByObject = new ObjectMap<Object, B2DSCustomProperty>();
	}
	
	/**
	 * Return the object with the wanted name.
	 * @param _type Class of the properties you want to retrieve
	 * @param _object object you want to retrieve the properties of.
	 * @return the desired casted properties, or null if not found.
	 */
	public <T> T get(Class<T> _type, Object _object)
	{
		if(_object != null && _type != null)
			return _type.cast(propertiesByObject.get(_object));
		return null;
	}
	
	@Override
	public void dispose()
	{
		propertiesByObject.clear();
	}
	
	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		if(_world != null && _customProperty != null)
		{
			propertiesByObject.put(_world, _customProperty);
		}
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_body != null && _customProperty != null)
		{
			propertiesByObject.put(_body, _customProperty);
		}
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_fixture != null && _customProperty != null)
		{
			propertiesByObject.put(_fixture, _customProperty);
		}
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty) 
	{
		if(_joint != null && _customProperty != null)
		{
			propertiesByObject.put(_joint, _customProperty);
		}
	}
	
	@Override
	public void onAddImage(B2DSImage _image, String _name, Body _body, B2DSCustomProperty _customProperty) 
	{
		if(_image != null && _name != null)
		{
			propertiesByObject.put(_image, _customProperty);
		}
	}

	@Override
	public void onRemoveBody(Body _body) 
	{
		if(_body != null)
		{
			propertiesByObject.remove(_body);
		}
	}

	@Override
	public void onRemoveFixture(Fixture _fixture)
	{
		if(_fixture != null)
		{
			propertiesByObject.remove(_fixture);
		}
	}

	@Override
	public void onRemoveJoint(Joint _joint) 
	{
		if(_joint != null)
		{
			propertiesByObject.remove(_joint);
		}
	}
}
