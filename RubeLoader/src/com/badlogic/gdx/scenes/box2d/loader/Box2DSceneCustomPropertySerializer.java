package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;

public abstract class Box2DSceneCustomPropertySerializer extends BaseBox2DSceneSerializer<Box2DSceneCustomProperty> 
{
	public Box2DSceneCustomPropertySerializer()
	{
		super();
	}
	
	public Box2DSceneCustomPropertySerializer(Box2DScene _scene) {
		super(_scene);
	}
}
