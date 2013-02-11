package com.badlogic.gdx.rube.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.rube.loader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.rube.scene.RubeScene;
import com.badlogic.gdx.rube.scene.store.RubeSceneStore;
import com.badlogic.gdx.rube.scene.store.RubeSceneStore.RubeSceneStoreDefinition;

/**
 * Loads a json file and returns a {@link RubeScene}.
 * @author clement.vayer
 *
 */
public class RubeSceneLoader 
{
	private final Json json;
	private final Array<RubeSceneStoreDefinition> storesDef;
	
	public RubeSceneLoader()
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		storesDef = new Array<RubeSceneStoreDefinition>(false, 2);

		json.setSerializer(RubeScene.class, new RubeSceneSerializer(json, storesDef));
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
	
	public <T extends RubeSceneStore<?>> void addStore(Class<?> _type, Class<T> _storeType)
	{
		storesDef.add(new RubeSceneStoreDefinition(_type, _storeType));
	}
}
