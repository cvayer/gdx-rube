package com.mangecailloux.rube.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.mangecailloux.rube.RubeScene;
import com.mangecailloux.rube.loader.serializers.RubeWorldSerializer;

/**
 * Loads a json file and returns a {@link RubeScene}.
 * @author clement.vayer
 *
 */
public class RubeSceneLoader 
{
	private final Json json;
	
	public RubeSceneLoader()
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		json.setSerializer(RubeScene.class, new RubeWorldSerializer(json));
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
