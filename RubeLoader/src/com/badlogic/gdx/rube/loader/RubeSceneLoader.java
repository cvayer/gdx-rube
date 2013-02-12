package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneCustomPropertySerializer;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoader;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStores.Box2DSceneStoresDefinition;

public class RubeSceneLoader extends Box2DSceneLoader
{
	public RubeSceneLoader(Box2DSceneStoresDefinition _definitions, Box2DSceneCustomPropertySerializer _customPropertiesSerializer)
	{
		super(_definitions, _customPropertiesSerializer);
	}
	
	public RubeSceneLoader(Box2DSceneCustomPropertySerializer _customPropertiesSerializer)
	{
		super(_customPropertiesSerializer);
	}
	
	public RubeSceneLoader()
	{
		super();
	}
	
	@Override
	public Box2DSceneSerializer getSceneSerializer(Box2DSceneStoresDefinition _definitions, Box2DSceneCustomPropertySerializer _customPropertiesSerializer) 
	{
		return new RubeSceneSerializer(json, _definitions, _customPropertiesSerializer);
	}

}
