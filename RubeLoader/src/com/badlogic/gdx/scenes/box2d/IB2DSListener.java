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

/**
 * Interfaces for listening to Box2DScene events.
 * @author clement.vayer
 */
public  interface  IB2DSListener {
	/**
	 * Listener used when objects are added to a scene.
	 * @author clement.vayer
	 */
	public  interface  IB2DSAddListener {
		/**
		 * Called when the {@link World} is added to the scene. <br/>
		 * When called by a {@link RubeSceneLoader} the world is already populated by bodies, joints, etc.
		 * @param _world Box2D world of the scene.
		 * @param _customProperty {@link B2DSCustomProperty} of the world. Can be null.
		 */
		public void onAddWorld(World _world, B2DSCustomProperty _customProperty);
		
		/**
		 * Called when a {@link Body} is added to the scene.
		 * @param _body Body added.
		 * @param _name Name of the body.
		 * @param _customProperty {@link B2DSCustomProperty} of the body. Can be null.
		 */
		public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty);
		
		/**
		 * Called when a {@link Fixture} is added to the scene.
		 * @param _fixture Fixture added.
		 * @param _name Name of the fixture.
		 * @param _customProperty {@link B2DSCustomProperty} of the fixture. Can be null.
		 */
		public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty);
		
		/**
		 * Called when a {@link Joint} is added to the scene.
		 * @param _joint Joint added.
		 * @param _name Name of the joint.
		 * @param _customProperty {@link B2DSCustomProperty} of the joint. Can be null.
		 */
		public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty);
		
		/**
		 * Called when a {@link B2DSImage} is added to the scene.
		 * @param _image Image added.
		 * @param _name Name of the image.
		 * @param _body {@link Body} it's attached to.
		 * @param _customProperty {@link B2DSCustomProperty} of the image. Can be null.
		 */
		public void onAddImage(B2DSImage _image, String _name, Body _body, B2DSCustomProperty _customProperty);
	}
	
	/**
	 * Listener used when objects are removed from a scene.
	 * @author clement.vayer
	 */
	public  interface  IB2DSRemoveListener {
		/**
		 * Called when a {@link Body} is removed from the {@link World}
		 * @param _body The removed Body.
		 */
		public void onRemoveBody(Body _body);
		
		/**
		 * Called when a {@link Fixture} is removed from the {@link Body}
		 * @param _fixture The removed Fixture.
		 */
		public void onRemoveFixture(Fixture _fixture);
		
		/**
		 * Called when a {@link Joint} is removed from the {@link World}
		 * @param _joint The removed Joint.
		 */
		public void onRemoveJoint(Joint _joint);
	}
}
