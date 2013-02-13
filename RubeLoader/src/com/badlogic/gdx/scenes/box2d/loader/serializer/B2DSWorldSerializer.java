package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSSerializer;
import com.badlogic.gdx.utils.Json;

public abstract class B2DSWorldSerializer extends B2DSSerializer<World>
{
	public B2DSWorldSerializer(Box2DScene _scene)
	{
		super(_scene);
	}
	
	public abstract void onReadWorldContent(World _world, Json _json, Object _jsonData, Class<?> _type);
}
