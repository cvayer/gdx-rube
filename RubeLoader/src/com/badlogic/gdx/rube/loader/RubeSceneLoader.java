package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Loader for Rube json files. Gives you a populated {@link RubeScene}.
 * @author clement.vayer
 */
public class RubeSceneLoader
{
	/**Used to parse the Json files*/
	private final Json json;
	
	public RubeSceneLoader() {
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
	public RubeScene loadScene(FileHandle file) {
		RubeScene scene = null;
		try 
		{
			scene = json.fromJson(RubeScene.class, file);	
		} 
		catch (SerializationException ex) 
		{
			throw new SerializationException("Error reading file: " + file, ex);
		}
		return scene;
	}
}
