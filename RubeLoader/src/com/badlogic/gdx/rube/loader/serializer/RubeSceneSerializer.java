package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class RubeSceneSerializer extends Box2DSceneSerializer
{
	public RubeSceneSerializer(Json json, Array<Class<? extends Box2DSceneStore>> _storesDef)
	{
		super(json, _storesDef);
		json.setSerializer(World.class, new RubeWorldSerializer(json, listeners));
	}

	@Override
	public void onRead(Box2DScene scene, Json json, Object jsonData, Class<?> type) 
	{
		scene.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.World.stepsPerSecond, 		jsonData);
		scene.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.World.positionIterations, 	jsonData);
		scene.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.World.velocityIterations, 	jsonData);
		scene.world					= json.readValue(World.class,	jsonData);
	}

}
