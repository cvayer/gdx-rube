package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoader;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;

public class RubeSceneLoader extends Box2DSceneLoader
{
	@Override
	public Box2DSceneSerializer getSceneSerializer() {
		return new RubeSceneSerializer(json, storesDef);
	}

}
