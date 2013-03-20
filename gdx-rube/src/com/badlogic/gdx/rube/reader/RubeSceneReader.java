package com.badlogic.gdx.rube.reader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.reader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Loader for Rube json files. Gives you a populated {@link RubeScene}.
 * @author clement.vayer
 */
public class RubeSceneReader
{
	/**Used to parse the Json files*/
	private final Json json;
	private final RubeSceneSerializer sceneSerializer;
	
	public RubeSceneReader() {
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		json.setIgnoreUnknownFields(true);
		
		sceneSerializer =  new RubeSceneSerializer(json);
		
		json.setSerializer(RubeScene.class, sceneSerializer);
	}
	
	/**
	 * 
	 * @param _file File to read.
	 * @return the scene described in the document.
	 */
	public RubeScene readScene(FileHandle file) {
		return readScene(file, false);
	}
	
	/**
	 * 
	 * @param file File to read.
	 * @param stripImageFile True if you want the filepath in the RubeImages to be stripped from path and extension. Useful when using a TextureAtlas
	 * @return the scene described in the document.
	 */
	public RubeScene readScene(FileHandle file, boolean stripImageFile) {
		RubeScene scene = null;
		try 
		{
			sceneSerializer.stripImageFile = stripImageFile;
			scene = json.fromJson(RubeScene.class, file);	
		} 
		catch (SerializationException ex) 
		{
			throw new SerializationException("Error reading file: " + file, ex);
		}
		return scene;
	}
}
