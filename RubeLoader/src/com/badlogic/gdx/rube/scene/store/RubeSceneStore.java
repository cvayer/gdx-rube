package com.badlogic.gdx.rube.scene.store;

import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializerListener;

public abstract class  RubeSceneStore<T> implements RubeSceneSerializerListener<T>
{
	@Override
	public abstract void onAdd(T _object, String _name);
	
	public static class RubeSceneStoreDefinition
	{
		public final Class<?> type;
		public final Class<? extends RubeSceneStore<?>> storeType;
		
		public RubeSceneStoreDefinition(Class<?> _type, Class<? extends RubeSceneStore<?>> _storeType)
		{
			type = _type;
			storeType = _storeType;
		}
	}
}
