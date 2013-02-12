package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.serializer.RubeWorldSerializer;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.Box2DScene.Box2DSceneStores;
import com.badlogic.gdx.scenes.box2d.loader.serializer.BaseBox2DSceneSerializer.Box2DSceneSerializerListeners;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class Box2DSceneSerializer extends ReadOnlySerializer<Box2DScene>
{
	protected 	final Box2DSceneSerializerListeners listeners;
	private 	final Array<Class<? extends Box2DSceneStore>> storesDef;
	
	public Box2DSceneSerializer(Json json, Array<Class<? extends Box2DSceneStore>> _storesDef)
	{
		storesDef = _storesDef;
		listeners = new Box2DSceneSerializerListeners();
		json.setIgnoreUnknownFields(true);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Box2DScene read(Json json, Object jsonData, Class type) 
	{
		// TODO put that in Box2DScene
		Box2DSceneStores stores = new Box2DSceneStores();
		listeners.clear();
		for(int i=0; i < storesDef.size; ++i)
		{
			try 
			{
				Box2DSceneStore store = storesDef.get(i).newInstance();
				stores.addStore(store);
				listeners.addListener(store);
				
			} catch (InstantiationException e) {
				// TODO cast Serialization Exception
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO cast Serialization Exception
				e.printStackTrace();
			}
		}
		
		Box2DScene scene = new Box2DScene(stores);
		
		onRead(scene, json, jsonData, type);
		
		return scene;
	}
	
	public abstract void onRead(Box2DScene scene, Json json, Object jsonData, Class<?> type);
}
