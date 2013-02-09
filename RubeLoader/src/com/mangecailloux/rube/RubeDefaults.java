package com.mangecailloux.rube;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class RubeDefaults 
{
	public static class World
	{
		public static final Vector2 gravity = new Vector2(0.0f, -9.81f);
		public static 		boolean	allowSleep = true;
		public static 		boolean	autoClearForces = true;
		public static 		boolean	continuousPhysics = true;
		public static 		boolean	warmStarting = true;
		
		public static		int   stepsPerSecond = 30;
		public static		int   positionIterations = 3;
		public static		int   velocityIterations = 8;
	}
	
	public static class Body
	{
		public static BodyDef definition = new BodyDef();
	}
	
	public static class Fixture
	{
		public static FixtureDef definition = new FixtureDef();
	}
}
