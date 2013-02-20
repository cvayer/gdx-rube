package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.Box2DScene;

public abstract class B2DSCustomPropertySerializer extends B2DSSerializer<B2DSCustomProperty> 
{
	public B2DSCustomPropertySerializer()
	{
		super();
	}
	
	public B2DSCustomPropertySerializer(Box2DScene _scene) {
		super(_scene);
	}
}
