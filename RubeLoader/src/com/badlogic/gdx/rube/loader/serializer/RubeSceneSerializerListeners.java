package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class RubeSceneSerializerListeners 
{
	private final ObjectMap<Class<?>, Array<? extends RubeSceneSerializerListener<?>>> listeners;
	
	protected RubeSceneSerializerListeners()
	{
		listeners = new ObjectMap<Class<?>, Array<? extends RubeSceneSerializerListener<?>>>(2);
	}
	
	public <T extends RubeSceneSerializerListener<?>> void addListener(Class<?> _type, T _listener)
	{
		Array<T> listenersArray = (Array<T>) listeners.get(_type);
		
		if(listenersArray == null)
		{
			listenersArray = new Array<T>(false, 2);
			listeners.put(_type, listenersArray);
		}
		
		listenersArray.add(_listener);
	}
	
	public <T> void onRead(Class<T> _type, T _object, String _name)
	{
		Array<RubeSceneSerializerListener<T>> listenersArray = (Array<RubeSceneSerializerListener<T>>) listeners.get(_type);
		
		if(listenersArray != null)
		{
			for(int i=0; i< listenersArray.size; ++i)
			{
				listenersArray.get(i).onAdd(_object, _name);
			}
		}
	}
	
	public void clear()
	{
		listeners.clear();
	}
}
