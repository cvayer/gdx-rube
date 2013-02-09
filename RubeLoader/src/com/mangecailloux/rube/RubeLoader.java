package com.mangecailloux.rube;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.mangecailloux.rube.serializers.RubeWorldSerializer;
import com.mangecailloux.rube.serializers.WorldSerializer;

public class RubeLoader 
{
	Json json;
	
	public RubeLoader()
	{
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		json.setSerializer(RubeWorld.class, new RubeWorldSerializer(json));
	}
	
	public RubeWorld loadWorld(FileHandle _file)
	{
		RubeWorld world = null;
		try {
			world = json.fromJson(RubeWorld.class, _file);
		} catch (SerializationException ex) {
			throw new SerializationException("Error reading file: " + _file, ex);
		}
		return world;
	}
}
