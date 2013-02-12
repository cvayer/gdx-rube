package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.IBox2DSceneListener;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class BaseBox2DSceneSerializer<T> extends ReadOnlySerializer<T> implements IBox2DSceneListener
{
	private 	Box2DScene scene;
	
	public BaseBox2DSceneSerializer()
	{
		scene = null;
	}

	public BaseBox2DSceneSerializer(Box2DScene _scene)
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
	public void onAddWorld(World _world, Box2DSceneCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddWorld(_world, _customProperty);
	}

	@Override
	public void onAddBody(Body _body, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddBody(_body, _name, _customProperty);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		if(scene != null)
			scene.onAddFixture(_fixture, _name, _customProperty);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, Box2DSceneCustomProperty _customProperty)
	{
		if(scene != null)
			scene.onAddJoint(_joint, _name, _customProperty);
	}
}
