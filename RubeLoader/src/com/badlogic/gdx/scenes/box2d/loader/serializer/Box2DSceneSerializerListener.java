package com.badlogic.gdx.scenes.box2d.loader.serializer;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

public  interface  Box2DSceneSerializerListener
{
	public void onAddWorld(World _world);
	public void onAddBody(Body _body, String _name);
	public void onAddFixture(Fixture _fixture, String _name);
	public void onAddJoint(Joint _joint, String _name);
}
