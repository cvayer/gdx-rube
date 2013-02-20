package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.utils.Array;

public abstract class B2DSImageSerializer extends B2DSSerializer<B2DSImage> 
{
	protected Array<Body> bodies = null;
	
	public B2DSImageSerializer()
	{
		super();
	}
	
	public B2DSImageSerializer(Box2DScene _scene) {
		super(_scene);
	}
	
	public void setBodies(Array<Body> _bodies)
	{
		bodies = _bodies;
	}
}
