package com.badlogic.gdx.rube.loader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.rube.loader.RubeDefaults;
import com.badlogic.gdx.rube.loader.serializer.utils.RubeVertexArray;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSImageSerializer;
import com.badlogic.gdx.utils.Json;

@SuppressWarnings("rawtypes")
public class RubeImageSerializer extends B2DSImageSerializer
{
	@Override
	public B2DSImage read(Json json, Object jsonData, Class type) 
	{
		if(bodies == null)
			return null;
		
		RubeImage defaults = RubeDefaults.Image.image;
		
		RubeImage image = new RubeImage();
		
		image.angle = json.readValue("angle", float.class, defaults.angle, jsonData);
		int bodyIndex = json.readValue("body", int.class, jsonData);
		if(bodyIndex >= 0 && bodyIndex < bodies.size)
			image.body = bodies.get(bodyIndex);
		
		image.center = json.readValue("center", Vector2.class, defaults.center, jsonData);
		
		RubeVertexArray corners = json.readValue("corners", RubeVertexArray.class, jsonData);
		if(corners != null)
		{
			image.corners = corners.toVector2();
		}
		else
			return null;
		
		image.file = json.readValue("file", String.class, jsonData);
		image.filter = json.readValue("filter", int.class, defaults.filter, jsonData);
		image.name = json.readValue("name", String.class, jsonData);
		image.opacity = json.readValue("opacity", float.class, defaults.opacity, jsonData);
		image.renderOrder = json.readValue("renderOrder", int.class, defaults.renderOrder, jsonData);
		image.scale = json.readValue("scale", float.class, defaults.scale, jsonData);
		
		B2DSCustomProperty customProperty = null;
		if(json.getSerializer(B2DSCustomProperty.class) != null)
			customProperty = json.readValue("customProperties", B2DSCustomProperty.class, jsonData);
		
		onAddImage(image, image.name, image.body, customProperty);
		
		return image;
	}

}
