package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.serializer.Box2DSceneSerializer;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;

/**
 * Loads a json file and returns a {@link Box2DScene}.
 * @author clement.vayer
 *
 */
public abstract class Box2DSceneLoader 
{
	protected final Json json;
	protected final Array<Class<? extends Box2DSceneStore>> storesDef;
	
	public Box2DSceneLoader()
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		storesDef = new Array<Class<? extends Box2DSceneStore>>(false, 2);

		json.setSerializer(Box2DScene.class, getSceneSerializer());
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
	
	public <T extends Box2DSceneStore> void addStore(Class<T> _type)
	{
		storesDef.add(_type);
	}
	
	public abstract Box2DSceneSerializer getSceneSerializer();
}
