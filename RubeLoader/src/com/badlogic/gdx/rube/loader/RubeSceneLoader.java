package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoader;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoaderParameters;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSCustomPropertySerializer;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.store.B2DSProcessors.B2DSProcessorsDefinition;

public class RubeSceneLoader extends Box2DSceneLoader
{
	public RubeSceneLoader(Box2DSceneLoaderParameters _parameters)
	{
		super(_parameters);
	}
	
	@Override
	public Box2DSceneSerializer getSceneSerializer(Box2DSceneLoaderParameters _parameters) 
	{
		return new RubeSceneSerializer(json, _parameters);
	}

}
