package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSCustomPropertySerializer;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.store.B2DSProcessor;
import com.badlogic.gdx.scenes.box2d.store.B2DSProcessors.B2DSProcessorsDefinition;

/**
 * Loads a json file and returns a {@link Box2DScene}.
 * @author clement.vayer
 *
 */
public abstract class Box2DSceneLoader 
{
	protected final Json json;
	protected final Box2DSceneLoaderParameters parameters;
	
	public Box2DSceneLoader(Box2DSceneLoaderParameters _parameters)
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		parameters = _parameters;
		json.setSerializer(Box2DScene.class, getSceneSerializer(parameters));
	}
	
	/**
	 * 
	 * @param _file File to read.
	 * @return the scene described in the document.
	 */
	public Box2DScene loadScene(FileHandle _file)
	{
		Box2DScene scene = null;
		try 
		{
			scene = json.fromJson(Box2DScene.class, _file);	
		} 
		catch (SerializationException ex) 
		{
			throw new SerializationException("Error reading file: " + _file, ex);
		}
		return scene;
	}
	
	public abstract Box2DSceneSerializer getSceneSerializer(Box2DSceneLoaderParameters _parameters);
}
