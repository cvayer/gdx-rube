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

import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSRemoveListener;
import com.badlogic.gdx.utils.Disposable;

/**
 * Base class for scene processors. <br/>
 * Processors listen to scene events ( adding/removing an object ) and then can "process" said object. 
 * Useful for storing objects, or creating new ones (like transforming B2DImage into Sprite)
 * @author clement.vayer
 */
public abstract class  B2DSProcessor implements IB2DSAddListener, IB2DSRemoveListener, Disposable
{
	/** {@link Box2DScene} the processor belong to. */
	protected Box2DScene scene;
	
	void setScene(Box2DScene scene){
		this.scene = scene;
	}
	
	/**
	 * Return the {@link B2DSProcessor}  matching the Class parameter.
	 * @param _type Class of the processor your request.
	 * @return the wanted processor, if found. Can be null.
	 */
	public <T extends B2DSProcessor> T getProcessor(Class<T> _type) {
		if(scene != null)
			return scene.getProcessor(_type);
		return null;
	}
	
	@Override
	public void dispose() {}
}
