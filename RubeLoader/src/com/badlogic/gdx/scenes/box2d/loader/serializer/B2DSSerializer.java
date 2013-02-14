package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class B2DSSerializer<T> extends ReadOnlySerializer<T> implements IB2DSAddListener
{
	private 	Box2DScene scene;
	
	public B2DSSerializer()
	{
		scene = null;
	}

	public B2DSSerializer(Box2DScene _scene)
	{
		scene = _scene;
	}
	
	public void setScene(Box2DScene _scene)
	{
		scene = _scene;
	}
	
	public Box2DScene getScene()
	{
		return scene;
	}

	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddWorld(_world, _customProperty);
	}

	@Override
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddBody(_body, _name, _customProperty);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddFixture(_fixture, _name, _customProperty);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty)
	{
		if(scene != null)
			scene.onAddJoint(_joint, _name, _customProperty);
	}
}
