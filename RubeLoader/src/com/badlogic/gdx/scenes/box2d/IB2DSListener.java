package com.badlogic.gdx.scenes.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.box2d.property.B2DSCustomProperty;

public  interface  IB2DSListener
{
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty);
	public void onAddBody(Body _body, String _name, B2DSCustomProperty _customProperty);
	public void onAddFixture(Fixture _fixture, String _name, B2DSCustomProperty _customProperty);
	public void onAddJoint(Joint _joint, String _name, B2DSCustomProperty _customProperty);
}
