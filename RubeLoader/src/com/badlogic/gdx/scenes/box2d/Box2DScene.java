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
package com.badlogic.gdx.scenes.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSRemoveListener;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessor;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessors;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessorsDefinition;
import com.badlogic.gdx.utils.Disposable;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation. <br/>
 * It also have a list of {@link B2DSProcessor} that will be calledback whenever something change in the scene. (adding or removing an object)
 * @author clement.vayer
 */
public class Box2DScene implements IB2DSAddListener, IB2DSRemoveListener, Disposable {
	
	/** Box2D {@link World} */
	public World world;
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	/** List of {@link B2DSProcessor}.	 */
	private final B2DSProcessors		   processors;
	
	/**
	 * Bod2DScene is created with a definition of all it's processors. <br/>
	 * That will ensure that the processors are created when the scene is loaded from a file.
	 * @param _definition {@link B2DSProcessorsDefinition} of the processors. Can be null.
	 */
	public Box2DScene(B2DSProcessorsDefinition _definition)	{
		processors 			= new B2DSProcessors(this, _definition);
		stepsPerSecond 		= 60;
		positionIterations 	= 3;
		velocityIterations 	= 8;
	}
	
	/**
	 * Dispose the Box2D {@link World} and all the processors ( if they have created disposable resources for instance }
	 */
	@Override
	public void dispose() {
		disposeProcessors();
		world.dispose();
		world = null;
	}
	
	/**
	 * Dispose only the processors and not the world
	 */
	public void disposeProcessors() {
		processors.dispose();
	}
	
	/**
	 * Convenience method to update the Box2D simulation with the parameters read from the scene. <br/>
	 * It's best to call it at fixed time intervals. ( defined by stepsPerSecond )
	 */
	public void step() {
		float dt = 1.0f/stepsPerSecond;
		step(dt);
	}
	
	/**
	 * Convenience method to update the Box2D simulation with the parameters read from the scene. <br/>
	 * It's best to call it at fixed time intervals.
	 */
	public void step(float _dt) {
		if(world != null)
		{
			world.step(_dt, velocityIterations, positionIterations);
		}
	}
	
	/**
	 * Return the {@link B2DSProcessor}  matching the Class parameter.
	 * @param _type Class of the processor your request.
	 * @return the wanted processor, if found. Can be null.
	 */
	public <T extends B2DSProcessor> T getProcessor(Class<T> _type) {
		return processors.getProcessor(_type);
	}

	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) {
		processors.onAddWorld(_world, _customProperty);
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) {
		processors.onAddBody(_body, _name, _customProperty);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) {
		processors.onAddFixture(_fixture, _name, _customProperty);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty)	{
		processors.onAddJoint(_joint, _name, _customProperty);
	}
	
	@Override
	public void onAddImage(B2DSImage _image, String _name, Body _body,	B2DSCustomProperty _customProperty) {
		processors.onAddImage(_image, _name, _body, _customProperty);
	}

	@Override
	public void onRemoveBody(Body _body) {
		processors.onRemoveBody(_body);
		
	}

	@Override
	public void onRemoveFixture(Fixture _fixture) {
		processors.onRemoveFixture(_fixture);
		
	}

	@Override
	public void onRemoveJoint(Joint _joint) {
		processors.onRemoveJoint(_joint);
	}
}
