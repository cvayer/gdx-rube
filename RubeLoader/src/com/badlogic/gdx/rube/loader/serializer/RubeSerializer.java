package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public abstract class RubeSerializer<T> extends ReadOnlySerializer<T> {

	protected RubeScene scene;

	public RubeSerializer()
	{

	}
	
	public void setScene(RubeScene _scene)
	{
		scene = _scene;
	}
}
