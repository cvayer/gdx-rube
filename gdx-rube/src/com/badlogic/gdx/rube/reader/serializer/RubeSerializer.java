package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

/**
 * Base class for RUBE scene serializers. Used to carry the {@link RubeScene} around.
 * @author clement.vayer
 */
abstract class RubeSerializer<T> extends ReadOnlySerializer<T> {

	protected RubeScene scene;

	public RubeSerializer()
	{

	}
	
	public void setScene(RubeScene _scene)
	{
		scene = _scene;
	}
}
