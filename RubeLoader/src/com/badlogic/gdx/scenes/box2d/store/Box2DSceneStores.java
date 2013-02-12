package com.badlogic.gdx.scenes.box2d.store;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.IBox2DSceneListener;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Box2DSceneStores implements IBox2DSceneListener
{
	private final ObjectMap<Class<?>, Box2DSceneStore> storesByType;
	private final Array<Box2DSceneStore> stores;
	
	public Box2DSceneStores(Box2DSceneStoresDefinition _definition)
	{
		stores = new Array<Box2DSceneStore>(false, 2);
		storesByType = new ObjectMap<Class<?>, Box2DSceneStore>(2);
		
		for(int i=0; i < _definition.getStoreCount(); ++i)
		{
			Box2DSceneStore store = _definition.createStore(i);
			if(store != null)
				addStore(store);
		}
		
	}
	
	public void addStore(Box2DSceneStore _store)
	{
		stores.add(_store);
		storesByType.put(_store.getClass(), _store);
	}
	
	public <T extends Box2DSceneStore> T getStore(Class<T > _type)
	{
		return _type.cast(storesByType.get(_type));
	}

	@Override
	public void onAddWorld(World _world, Box2DSceneCustomProperty _customProperty) 
	{
		for(int i=0; i< stores.size; ++i)
		{
			stores.get(i).onAddWorld(_world, _customProperty);
		}
	}

	@Override
	public void onAddBody(Body _body, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		for(int i=0; i< stores.size; ++i)
		{
			stores.get(i).onAddBody(_body, _name, _customProperty);
		}
		
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		for(int i=0; i< stores.size; ++i)
		{
			stores.get(i).onAddFixture(_fixture, _name, _customProperty);
		}
		
	}

	@Override
	public void onAddJoint(Joint _joint, String _name, Box2DSceneCustomProperty _customProperty) 
	{
		for(int i=0; i< stores.size; ++i)
		{
			stores.get(i).onAddJoint(_joint, _name, _customProperty);
		}
	}
	
	public static class Box2DSceneStoresDefinition
	{
		protected final Array<Class<? extends Box2DSceneStore>> definitions;
		
		public Box2DSceneStoresDefinition()
		{
			definitions = new Array<Class<? extends Box2DSceneStore>>(false, 2);
		}
		
		public <T extends Box2DSceneStore> void addStore(Class<T> _type)
		{
			definitions.add(_type);
		}
		
		public int getStoreCount()
		{
			return definitions.size;
		}
		
		public Box2DSceneStore createStore(int _index)
		{
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
}
