package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStore;
import com.badlogic.gdx.scenes.box2d.store.Box2DSceneStores.Box2DSceneStoresDefinition;

/**
 * Loads a json file and returns a {@link Box2DScene}.
 * @author clement.vayer
 *
 */
public abstract class Box2DSceneLoader 
{
	protected final Json json;
	protected final Box2DSceneStoresDefinition definitions;
	
	public Box2DSceneLoader(Box2DSceneStoresDefinition _definitions, Box2DSceneCustomPropertySerializer _customPropertiesSerializer)
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		definitions = _definitions;
		json.setSerializer(Box2DScene.class, getSceneSerializer(_definitions, _customPropertiesSerializer));
	}
	
	public Box2DSceneLoader(Box2DSceneCustomPropertySerializer _customPropertiesSerializer)
	{
		this(new Box2DSceneStoresDefinition(), _customPropertiesSerializer);
	}
	
	public Box2DSceneLoader()
	{
		this(new Box2DSceneStoresDefinition(), null);
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
		definitions.addStore(_type);
	}
	
	public abstract Box2DSceneSerializer getSceneSerializer(Box2DSceneStoresDefinition _definitions, Box2DSceneCustomPropertySerializer _customPropertiesSerializer);
}
