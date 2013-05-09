package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Serializer to read a {@link RubeScene} from a RUBE .json file.
 * @author clement.vayer
 */
public class RubeSceneSerializer extends ReadOnlySerializer<RubeScene>
{
	private 	final RubeWorldSerializer worldSerializer;
	private 	final RubeImageSerializer imageSerializer;
	private		final RubeCustomPropertySerializer customPropertiesSerializer;
	public 			  boolean					   stripImageFile;
	
	public RubeSceneSerializer(Json _json)
	{		
		worldSerializer = new RubeWorldSerializer(_json);
		_json.setSerializer(World.class, worldSerializer);
		
		imageSerializer = new RubeImageSerializer();
		_json.setSerializer(RubeImage.class, imageSerializer);
		
		customPropertiesSerializer = new RubeCustomPropertySerializer();
		_json.setSerializer(RubeCustomProperty.class, customPropertiesSerializer);
	}


	@SuppressWarnings("rawtypes")
	@Override
	public RubeScene read(Json json, JsonValue jsonData, Class type) {

		RubeScene scene = new RubeScene();
		scene.stepsPerSecond 		= json.readValue("stepsPerSecond", 		int.class, RubeDefaults.Scene.stepsPerSecond, 		jsonData);
		scene.positionIterations 	= json.readValue("positionIterations", 	int.class, RubeDefaults.Scene.positionIterations, 	jsonData);
		scene.velocityIterations 	= json.readValue("velocityIterations", 	int.class, RubeDefaults.Scene.velocityIterations, 	jsonData);
		
		worldSerializer.setScene(scene);
		scene.world					= json.readValue(World.class,	jsonData);
		
		imageSerializer.setScene(scene);
		imageSerializer.stripImageFile = stripImageFile;
		json.readValue("image", Array.class, RubeImage.class, jsonData);
		return scene;
	}
}
