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
package com.badlogic.gdx.rube;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation. <br/>
 * It also have a list of {@link B2DSProcessor} that will be calledback whenever something change in the scene. (adding or removing an object)
 * @author clement.vayer
 */
public class RubeScene {
	
	/** Box2D {@link World} */
	public World world;
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	private final Array<Body> 		bodies;
	private final Array<Fixture> 	fixtures;
	private final Array<Joint> 		joints;
	private final Array<RubeImage> 	images;
	
	private final ObjectMap<Object, RubeCustomProperty> properties;
	
	private final ObjectMap<String, Body> 				bodiesByName;
	private final ObjectMap<String, Fixture> 			fixturesByName;
	private final ObjectMap<String, Joint> 				jointsByName;
	private final ObjectMap<String, RubeImage> 			imagesByName;
	
	private final ObjectMap<Body, Array<RubeImage>> 			imagesByBody;
	/**
	 * Bod2DScene is created with a definition of all it's processors. <br/>
	 * That will ensure that the processors are created when the scene is loaded from a file.
	 * @param _definition {@link B2DSProcessorsDefinition} of the processors. Can be null.
	 */
	public RubeScene()	{
		
		bodies = new Array<Body>(false, 32);
		fixtures = new Array<Fixture>(false, 32);
		joints = new Array<Joint>(false, 32);
		images = new Array<RubeImage>(false, 16);
		
		properties = new ObjectMap<Object, RubeCustomProperty>();
		
		bodiesByName 	= new ObjectMap<String, Body>();
		fixturesByName 	= new ObjectMap<String, Fixture>();
		jointsByName 	= new ObjectMap<String, Joint>();
		imagesByName	= new ObjectMap<String, RubeImage>();
		
		imagesByBody = new ObjectMap<Body, Array<RubeImage>>();
	}
	
	public Array<Body> getBodies()
	{
		return bodies;
	}
	
	public Array<RubeImage> getImages()
	{
		return images;
	}
	
	public Array<Fixture> getFixtures()
	{
		return fixtures;
	}
	
	public Array<Joint> getJoints()
	{
		return joints;
	}
	
	public RubeCustomProperty getProperty(Object _object)
	{
		if(_object != null)
		{
			return properties.get(_object, null);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> _type, String _name) {
		
		if(_name == null || _type == null)
			return null;
		
		if(_type == Body.class)
		{
			return (T) bodiesByName.get(_name);
		}
		else if(_type == Fixture.class)
		{
			return (T) fixturesByName.get(_name);
		}
		else if(_type == Joint.class)
		{
			return (T) jointsByName.get(_name);
		}
		else if(_type == RubeImage.class)
		{
			return (T) imagesByName.get(_name);
		}
		return null;
	}
	
	public Array<RubeImage> getImage(Body _body)
	{
		if(_body != null)
		{
			return imagesByBody.get(_body);
		}
		return null;
	}
	
	public void clear() {
		bodies.clear();
		images.clear();
		fixtures.clear();
		joints.clear();
		properties.clear();
		bodiesByName.clear();
		fixturesByName.clear();
		jointsByName.clear();
		imagesByName.clear();
		imagesByBody.clear();
	}
	

	public void onAddWorld(World _world, RubeCustomProperty _customProperty) {
		properties.put(_world, _customProperty);
	}


	public void onAddBody(Body _body, String _name, RubeCustomProperty _customProperty) {
		
		bodies.add(_body);
		properties.put(_body, _customProperty);
		bodiesByName.put(_name, _body);
	}

	public void onAddFixture(Fixture _fixture, String _name, RubeCustomProperty _customProperty) {
		fixtures.add(_fixture);
		properties.put(_fixture, _customProperty);
		fixturesByName.put(_name, _fixture);
	}


	public void onAddJoint(Joint _joint, String _name, RubeCustomProperty _customProperty)	{
		joints.add(_joint);
		properties.put(_joint, _customProperty);
		jointsByName.put(_name, _joint);
	}
	

	public void onAddImage(RubeImage _image, RubeCustomProperty _customProperty) {
		images.add(_image);
		properties.put(_image, _customProperty);
		if(_image.name != null)
			imagesByName.put(_image.name, _image);
		if(_image.body != null)
		{
			Array<RubeImage> images = imagesByBody.get(_image.body);
			if(images == null)
			{
				images = new Array<RubeImage>(false, 4);
				imagesByBody.put(_image.body, images);
			}
			
			images.add(_image);
		}
	}
}
