package com.badlogic.gdx.scenes.box2d.loader;

import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSCustomPropertySerializer;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSImageSerializer;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessorsDefinition;

public class Box2DSceneLoaderParameters 
{
	public B2DSProcessorsDefinition 	definitions;
	public B2DSCustomPropertySerializer customPropertiesSerializer;
	public B2DSImageSerializer			imageSerializer;
}
