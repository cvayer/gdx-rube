package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoaderParameters;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class Box2DSceneSerializer extends ReadOnlySerializer<Box2DScene>
{
	protected 	final Box2DScene 							scene;
	protected 	final Box2DSceneLoaderParameters 			parameters;
	
	public Box2DSceneSerializer(Json _json, Box2DSceneLoaderParameters _parameters)
	{
		parameters = _parameters;
		
		scene = new Box2DScene(parameters.definitions);
		
		if(parameters.customPropertiesSerializer != null)
		{
			_json.setSerializer(B2DSCustomProperty.class, parameters.customPropertiesSerializer);
			parameters.customPropertiesSerializer.setScene(scene);
		}
			
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Box2DScene read(Json json, Object jsonData, Class type) 
	{
		if(scene.world == null)
			onReadScene(scene, json, jsonData, type);
		else
			onReadWorldOnly(scene.world, json, jsonData, type);
			
		return scene;
	}
	
	public abstract void onReadScene(Box2DScene scene, Json json, Object jsonData, Class<?> type);
	public abstract void onReadWorldOnly(World world, Json json, Object jsonData, Class<?> type);
}
