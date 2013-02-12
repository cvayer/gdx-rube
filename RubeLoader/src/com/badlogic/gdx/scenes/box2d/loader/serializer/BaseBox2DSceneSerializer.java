package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class BaseBox2DSceneSerializer<T> extends ReadOnlySerializer<T> implements Box2DSceneSerializerListener
{
	private final Box2DSceneSerializerListeners listeners;

	public BaseBox2DSceneSerializer(Json _json, Box2DSceneSerializerListeners _listeners)
	{
		listeners = _listeners;
	}
	
	public Box2DSceneSerializerListeners getListeners()
	{
		return listeners;
	}

	@Override
	public void onAddWorld(World _world) 
	{
		listeners.onAddWorld(_world);
	}

	@Override
	public void onAddBody(Body _body, String _name) 
	{
		listeners.onAddBody(_body, _name);
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name) 
	{
		listeners.onAddFixture(_fixture, _name);
	}

	@Override
	public void onAddJoint(Joint _joint, String _name)
	{
		listeners.onAddJoint(_joint, _name);
	}
	
	public static class Box2DSceneSerializerListeners implements Box2DSceneSerializerListener
	{
		private final Array<Box2DSceneSerializerListener> listeners;
		
		public Box2DSceneSerializerListeners()
		{
			listeners = new Array<Box2DSceneSerializerListener>(false, 2);
		}
		
		public void addListener(Box2DSceneSerializerListener _listener)
		{
			listeners.add(_listener);
		}
		
		public void clear()
		{
			listeners.clear();
		}

		@Override
		public void onAddWorld(World _world) 
		{
			for(int i=0; i< listeners.size; ++i)
			{
				listeners.get(i).onAddWorld(_world);
			}
		}

		@Override
		public void onAddBody(Body _body, String _name) 
		{
			for(int i=0; i< listeners.size; ++i)
			{
				listeners.get(i).onAddBody(_body, _name);
			}
		}

		@Override
		public void onAddFixture(Fixture _fixture, String _name) 
		{
			for(int i=0; i< listeners.size; ++i)
			{
				listeners.get(i).onAddFixture(_fixture, _name);
			}
		}

		@Override
		public void onAddJoint(Joint _joint, String _name)
		{
			for(int i=0; i< listeners.size; ++i)
			{
				listeners.get(i).onAddJoint(_joint, _name);
			}
		}
	}
}
