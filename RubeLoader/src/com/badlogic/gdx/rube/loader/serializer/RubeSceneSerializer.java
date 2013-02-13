package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoaderParameters;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;
import com.badlogic.gdx.utils.Json;

public class RubeSceneSerializer extends Box2DSceneSerializer
{
	private final RubeWorldSerializer worldSerializer;
	public RubeSceneSerializer(Json _json, Box2DSceneLoaderParameters _parameters)
	{
		super(_json, _parameters);
		_json.setIgnoreUnknownFields(true);
		
		worldSerializer = new RubeWorldSerializer(_json, scene);
		
		_json.setSerializer(World.class, worldSerializer);
	}

	@Override
	public void onReadScene(Box2DScene scene, Json json, Object jsonData, Class<?> type) 
	{
		scene.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.World.stepsPerSecond, 		jsonData);
		scene.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.World.positionIterations, 	jsonData);
		scene.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.World.velocityIterations, 	jsonData);
		scene.world					= json.readValue(World.class,	jsonData);
	}

	@Override
	public void onReadWorldOnly(World world, Json json, Object jsonData, Class<?> type) 
	{
		worldSerializer.onReadWorldContent(world, json, jsonData, type);
	}
}
