package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.property.Box2DSceneCustomProperty;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStores.Box2DSceneStoresDefinition;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class Box2DSceneSerializer extends ReadOnlySerializer<Box2DScene>
{
	protected 	final Box2DScene 							scene;
	protected 	final Box2DSceneCustomPropertySerializer 	customPropertiesSerializer;
	
	public Box2DSceneSerializer(Json _json, Box2DSceneStoresDefinition _definition,  Box2DSceneCustomPropertySerializer _customPropertiesSerializer)
	{
		customPropertiesSerializer = _customPropertiesSerializer;
		scene = new Box2DScene(_definition);
		
		if(customPropertiesSerializer != null)
		{
			_json.setSerializer(Box2DSceneCustomProperty.class, customPropertiesSerializer);
			customPropertiesSerializer.setScene(scene);
		}
			
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Box2DScene read(Json json, Object jsonData, Class type) 
	{
		if(scene.world == null)
			onRead(scene, json, jsonData, type);
		else
			onRead(scene.world, json, jsonData, type);
			
		return scene;
	}
	
	public abstract void onRead(Box2DScene scene, Json json, Object jsonData, Class<?> type);
	public abstract void onRead(World world, Json json, Object jsonData, Class<?> type);
}
