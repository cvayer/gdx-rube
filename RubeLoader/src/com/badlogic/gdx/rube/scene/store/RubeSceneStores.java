package com.badlogic.gdx.rube.scene.store;

import com.badlogic.gdx.utils.ObjectMap;

public class RubeSceneStores 
{
	private final ObjectMap<Class<?>, RubeSceneStore<?>> stores;
	
	public RubeSceneStores()
	{
		stores = new ObjectMap<Class<?>, RubeSceneStore<?>>(2);
	}
	
	public void addStore(RubeSceneStore<?> _store)
	{
		stores.put(_store.getClass(), _store);
	}
	
	public <T extends RubeSceneStore<?>> T getStore(Class<T > _type)
	{
		return _type.cast(stores.get(_type));
	}
}
