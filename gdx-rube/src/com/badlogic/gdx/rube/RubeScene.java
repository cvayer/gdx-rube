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
 * It also keep a reference of all created objects ( {@link Body}, {@link Fixture}, {@link Joint}, {@link RubeImage} and {@link RubeCustomProperty} ), 
 * and allow you to retrieve them conveniently.<br/><br/>
 * If you copy those objects into your own system (framework, entity system, etc ) you can clear the scene with the {@link #clear()} function.<br/>
 * @author clement.vayer
 */
public class RubeScene {
	
	/** Tag to look for in the World's properties when searching for an atlas filename*/
	public static String atlasPropertyName = "atlas";
	
	/** Box2D {@link World} */
	public World world;
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	private final Array<Body> 							bodies;
	private final Array<Fixture> 						fixtures;
	private final Array<Joint> 							joints;
	private final Array<RubeImage> 						images;
	private final ObjectMap<Object, RubeCustomProperty> properties;
	private final ObjectMap<String, Body> 				bodiesByName;
	private final ObjectMap<String, Fixture> 			fixturesByName;
	private final ObjectMap<String, Joint> 				jointsByName;
	private final ObjectMap<String, RubeImage> 			imagesByName;
	private final ObjectMap<Body, Array<RubeImage>> 	imagesByBody;
	

	public RubeScene()	{
		
		bodies 			= new Array<Body>(false, 32);
		fixtures 		= new Array<Fixture>(false, 32);
		joints 			= new Array<Joint>(false, 32);
		images 			= new Array<RubeImage>(false, 16);
		properties 		= new ObjectMap<Object, RubeCustomProperty>();
		bodiesByName 	= new ObjectMap<String, Body>();
		fixturesByName 	= new ObjectMap<String, Fixture>();
		jointsByName 	= new ObjectMap<String, Joint>();
		imagesByName	= new ObjectMap<String, RubeImage>();
		imagesByBody 	= new ObjectMap<Body, Array<RubeImage>>();
	}
	
	/**
	 * @return all of the {@link Body} created when the scene was loaded.
	 */
	public Array<Body> getBodies() {
		return bodies;
	}
	
	/**
	 * @return all of the {@link RubeImage} created when the scene was loaded.
	 */
	public Array<RubeImage> getImages()	{
		return images;
	}
	
	/**
	 * @return all of the {@link Fixture} created when the scene was loaded.
	 */
	public Array<Fixture> getFixtures() {
		return fixtures;
	}
	
	/**
	 * @return all of the {@link Joint} created when the scene was loaded.
	 */
	public Array<Joint> getJoints() {
		return joints;
	}
	
	/**
	 * @param object the object we want to retrieve the property of. Can be a {@link World}, {@link Body}, {@link Fixture}, {@link Joint} or {@link RubeImage}.
	 * @return the {@link RubeCustomProperty} linked to a scene object.
	 */
	public RubeCustomProperty getProperty(Object object) {
		if(object != null)
		{
			return properties.get(object, null);
		}
		return null;
	}
	
	/**
	 * 
	 * @param type Class of the object to retrieve. Can be Body.class, Fixture.class, Joint.class or RubeImage.class.
	 * @param name Name of the object, as set in the RUBE Editor.
	 * @return the desired object if found, null else.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, String name) {
		
		if(name == null || type == null)
			return null;
		
		if(type == Body.class)
		{
			return (T) bodiesByName.get(name, null);
		}
		else if(type == Fixture.class)
		{
			return (T) fixturesByName.get(name, null);
		}
		else if(type == Joint.class)
		{
			return (T) jointsByName.get(name, null);
		}
		else if(type == RubeImage.class)
		{
			return (T) imagesByName.get(name, null);
		}
		return null;
	}
	
	/**
	 * @param type Class of the objects by name to retrieve. Can be Body.class, Fixture.class, Joint.class or RubeImage.class.
	 * @return the desired OjectMap if found, null else.
	 */
	@SuppressWarnings("unchecked")
	public <T> ObjectMap<String, T> getObjectsByName(Class<T> type) {
		
		if(type == null)
			return null;
		
		if(type == Body.class)
		{
			return (ObjectMap<String, T>) bodiesByName;
		}
		else if(type == Fixture.class)
		{
			return (ObjectMap<String, T>) fixturesByName;
		}
		else if(type == Joint.class)
		{
			return (ObjectMap<String, T>) jointsByName;
		}
		else if(type == RubeImage.class)
		{
			return (ObjectMap<String, T>) imagesByName;
		}
		return null;
	}
	
	/**
	 * @param body {@link Body} linked to the images.
	 * @return all the images associated with the body.
	 */
	public Array<RubeImage> getImage(Body body) {
		if(body != null)
		{
			return imagesByBody.get(body);
		}
		return null;
	}
	
	/**
	 * Clear of the containers of objects, in case you don't need the scene anymore. Do not change anything on the World. You have to dispose it when not needed anymore.
	 */
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
	
	/** 
	 * Called when the world is added to the scene. It's already populated with bodies, fuxtures and joints.
	 * @param world The {@link World} of the scene.
	 * @param customProperty {@link RubeCustomProperty} linked to the world.
	 */
	public void onAddWorld(World world, RubeCustomProperty customProperty) {
		properties.put(world, customProperty);
	}

	/**
	 * Called when a body is added to the scene.
	 * @param body The {@link Body} added to the scene.
	 * @param name Name of the body as set in the RUBE Editor.
	 * @param customProperty {@link RubeCustomProperty} linked to the body.
	 */
	public void onAddBody(Body body, String name, RubeCustomProperty customProperty) {
		
		bodies.add(body);
		properties.put(body, customProperty);
		bodiesByName.put(name, body);
	}

	/**
	 * Called when a body is added to the scene.
	 * @param fixture The {@link Fixture} added to the scene.
	 * @param name Name of the fixture as set in the RUBE Editor.
	 * @param customProperty {@link RubeCustomProperty} linked to the fixture.
	 */
	public void onAddFixture(Fixture fixture, String name, RubeCustomProperty customProperty) {
		fixtures.add(fixture);
		properties.put(fixture, customProperty);
		fixturesByName.put(name, fixture);
	}


	/**
	 * Called when a joint is added to the scene.
	 * @param joint The {@link Joint} added to the scene.
	 * @param name Name of the joint as set in the RUBE Editor.
	 * @param customProperty {@link RubeCustomProperty} linked to the joint.
	 */
	public void onAddJoint(Joint joint, String name, RubeCustomProperty customProperty)	{
		joints.add(joint);
		properties.put(joint, customProperty);
		jointsByName.put(name, joint);
	}
	
	/**
	 * Called when a image is added to the scene.
	 * @param image The {@link RubeImage} added to the scene.
	 * @param customProperty {@link RubeCustomProperty} linked to the image.
	 */
	public void onAddImage(RubeImage image, RubeCustomProperty customProperty) {
		images.add(image);
		properties.put(image, customProperty);
		if(image.name != null)
			imagesByName.put(image.name, image);
		if(image.body != null)
		{
			Array<RubeImage> images = imagesByBody.get(image.body);
			if(images == null)
			{
				images = new Array<RubeImage>(false, 1);
				imagesByBody.put(image.body, images);
			}
			
			images.add(image);
		}
	}
	
	/**
	 * Returns the path + filename of the atlas to use. <br/>
	 * By default will look for a world's property with the name "atlas" then "atlas1", "atlas2", etc.
	 * @param index if 0, will search for the atlas with no suffix, else the atlas stored in the property name RubeScene.atlasPropertyName + index
	 * @return the filepath if found, null else.
	 */
	public String getAtlasFilePath(int index)
	{
		if(world != null)
		{
			RubeCustomProperty properties = getProperty(world);
			if(properties != null)
			{
				if(index == 0)
				{
					return properties.getString(RubeScene.atlasPropertyName, null);
				}
				else
				{
					return properties.getString(RubeScene.atlasPropertyName + index, null);
				}
			}
		}
		return null;
	}
	
	/**
	 * @return true, if one of the properties of the world is as a name matching the atlasPropertyName static variable.
	 */
	public boolean usesAtlas()
	{
		if(world != null)
		{
			RubeCustomProperty properties = getProperty(world);
			if(properties != null)
			{
				if(properties.getString(RubeScene.atlasPropertyName, null) != null)
				{
					return true;
				}
			}
		}
		return false;
	}
}
