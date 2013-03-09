package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

public class RubeSceneLoader
{
	protected final Json json;
	
	public RubeSceneLoader()
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		json.setIgnoreUnknownFields(true);
		json.setSerializer(RubeScene.class, new RubeSceneSerializer(json));
	}
	
	/**
	 * 
	 * @param _file File to read.
	 * @return the scene described in the document.
	 */
	public RubeScene loadScene(FileHandle _file)
	{
		RubeScene scene = null;
		try 
		{
			scene = json.fromJson(RubeScene.class, _file);	
		} 
		catch (SerializationException ex) 
		{
			throw new SerializationException("Error reading file: " + _file, ex);
		}
		return scene;
	}

}
