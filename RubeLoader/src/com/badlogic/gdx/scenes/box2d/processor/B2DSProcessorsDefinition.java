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

import com.badlogic.gdx.utils.Array;

/**
 * Basically an Array of Class extending B2DSProcessor with some helper functions. <br/>
 * It's used when creating a {@link Box2DScene}.
 * @author clement.vayer
 *
 */
public class B2DSProcessorsDefinition {
	
	/** Classes of the processors to create when loading a scene */
	protected final Array<Class<? extends B2DSProcessor>> definitions;
	
	/**
	 * Creates a definition with an empty list.
	 */
	public B2DSProcessorsDefinition() {
		definitions = new Array<Class<? extends B2DSProcessor>>(false, 2);
	}
	
	/**
	 * Will add all the {@link B2DSProcessor} classes to the processor list when creating a scene.
	 * @param _processorTypes Array of classes to create when loading the scene
	 */
	public B2DSProcessorsDefinition(Class<? extends B2DSProcessor>... _processorTypes) {
		this();
		
		if(_processorTypes != null)
		{
			definitions.addAll(_processorTypes);
		}
	}
	
	/**
	 * Adds a processor type to the list.
	 * @param _type Class extending {@link B2DSProcessor}.
	 */
	public void addProcessor(Class<? extends B2DSProcessor> _type) {
		definitions.add(_type);
	}
	
	/**
	 * 
	 * @return the number of processors to create.
	 */
	int getProcessorsCount() {
		return definitions.size;
	}
	
	/**
	 * Create the processor at the given index in the list.
	 * @param _index Index of the processor to create. Must be inferior to {@link #getProcessorsCount()} .
	 * @return the newly created {@link B2DSProcessor}.
	 */
	B2DSProcessor createProcessors(int _index) {
		if(_index < definitions.size)
		{
			try 
			{
				return definitions.get(_index).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

