package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.rube.scene.RubeScene;
import com.badlogic.gdx.rube.scene.store.RubeSceneStore;
import com.badlogic.gdx.rube.scene.store.RubeSceneStore.RubeSceneStoreDefinition;

public class RubeSceneSerializer extends ReadOnlySerializer<RubeScene>
{
	private final RubeSceneSerializerListeners listeners;
	private final Array<RubeSceneStoreDefinition> storesDef;
	
	public RubeSceneSerializer(Json json, Array<RubeSceneStoreDefinition> _storesDef)
	{
		storesDef = _storesDef;
		listeners = new RubeSceneSerializerListeners();
		json.setSerializer(World.class, new WorldSerializer(json, listeners));
		json.setIgnoreUnknownFields(true);
	}
	
	@Override
	public RubeScene read(Json json, Object jsonData, Class type) 
	{
		RubeScene scene = new RubeScene();
		
		for(int i=0; i < storesDef.size; ++i)
		{
			try 
			{
				RubeSceneStore<?> store = storesDef.get(i).storeType.newInstance();
				
				scene.addStore(store);
				listeners.addListener(storesDef.get(i).type, store);
				
			} catch (InstantiationException e) {
				// TODO cast Serialization Exception
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO cast Serialization Exception
				e.printStackTrace();
			}
		}
		
		
		scene.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.World.stepsPerSecond, 		jsonData);
		scene.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.World.positionIterations, 	jsonData);
		scene.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.World.velocityIterations, 	jsonData);
		scene.world					= json.readValue(World.class,	jsonData);
		
		listeners.clear();
		
		return scene;
	}

}
