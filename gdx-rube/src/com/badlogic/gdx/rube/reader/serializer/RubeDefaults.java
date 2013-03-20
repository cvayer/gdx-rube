package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.rube.RubeScene;

/**
 * Static data that give the default values to use for a bunch of parameters.
 * Default values can be changed if done before the loading of a {@link RubeScene}.
 * @author clement.vayer
 */
public class RubeDefaults 
{
	@SuppressWarnings("unused")
	private static Fixture fixture = new Fixture(); // done to call ctor of fixture and init default values
	
	public static class Scene
	{
		public static		int   				stepsPerSecond 		= 60;
		public static		int   				positionIterations 	= 3;
		public static		int   				velocityIterations 	= 8;
	}
	
	public static class World
	{
		public static final Vector2 			gravity 			= new Vector2(0.0f, -9.81f);
		public static 		boolean				allowSleep 			= true;
		public static 		boolean				autoClearForces	 	= true;
		public static 		boolean				continuousPhysics 	= true;
		public static 		boolean				warmStarting 		= true;
	}
	public static class Body
	{
		public static final BodyDef 			definition 		= new BodyDef();
	}
	public static class Fixture
	{
		public static final FixtureDef 			definition 		= new FixtureDef();
		
		Fixture()
		{
			definition.friction = 0.0f;
		}
	}
	public static class Joint
	{
		public static final boolean				collideConnected= false;
		public static final RevoluteJointDef 	revoluteDef 	= new RevoluteJointDef();
		public static final PrismaticJointDef 	prismaticDef	= new PrismaticJointDef();
		public static final DistanceJointDef 	distanceDef 	= new DistanceJointDef();
		public static final PulleyJointDef 		pulleyDef 		= new PulleyJointDef();
		public static final MouseJointDef 		mouseDef 		= new MouseJointDef();
		public static final GearJointDef 		gearDef 		= new GearJointDef();
		public static final WheelJointDef 		wheelDef 		= new WheelJointDef();
		public static final WeldJointDef 		weldDef 		= new WeldJointDef();
		public static final FrictionJointDef 	frictionDef 	= new FrictionJointDef();
		public static final RopeJointDef 		ropeDef 		= new RopeJointDef();
	}
	
	public static class Image
	{
		public static final RubeImage image = new RubeImage();
		public static final int [] colorArray = new int[]{255,255,255,255};
	}
}
