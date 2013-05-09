package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Serializer to read a {@link Joint} from a RUBE .json file.
 * @author clement.vayer
 */
@SuppressWarnings("rawtypes")
class RubeJointSerializer extends RubeSerializer<Joint>
{
	World			world;
	Array<Body> 	bodies;
	Array<Joint> 	joints;
	
	private final MouseJointDefSerializer mouseJointDefSerializer;
	
	public RubeJointSerializer(Json _json)
	{
		super();
		
		_json.setSerializer(RevoluteJointDef.class, 	new RevoluteJointDefSerializer());
		_json.setSerializer(PrismaticJointDef.class, 	new PrismaticJointDefSerializer());
		_json.setSerializer(PulleyJointDef.class, 		new PulleyJointDefSerializer());
		_json.setSerializer(WeldJointDef.class, 		new WeldJointDefSerializer());
		_json.setSerializer(FrictionJointDef.class, 	new FrictionJointDefSerializer());
		_json.setSerializer(WheelJointDef.class, 		new WheelJointDefSerializer());
		_json.setSerializer(RopeJointDef.class, 		new RopeJointDefSerializer());
		_json.setSerializer(DistanceJointDef.class, 	new DistanceJointDefSerializer());
		_json.setSerializer(GearJointDef.class, 		new GearJointDefSerializer());
		
		mouseJointDefSerializer = new MouseJointDefSerializer();
		
		_json.setSerializer(MouseJointDef.class, 		mouseJointDefSerializer);
	}
	
	void init(World		_world, Array<Body> _bodies, Array<Joint> _joints)
	{
		world = _world;
		bodies = _bodies;
		joints = _joints;
	}
	
	@Override
	public Joint read(Json json, JsonValue jsonData, Class type) 
	{
		if(bodies == null || world == null)
			return null;
		
		int indexA = json.readValue("bodyA", int.class, bodies.size,  jsonData);
		int indexB = json.readValue("bodyB", int.class, bodies.size,  jsonData);
		
		if(indexA >= bodies.size || indexB >= bodies.size)
			return null;

		Joint				joint = null;
		JointDef 			jointDef = null;
		
		String jointType = json.readValue("type", String.class, jsonData);
		
		if(jointType == null)
			return null;
		
		// First pass
		if(joints == null && !jointType.equals("gear"))
		{
			if(jointType.equals("revolute"))
			{
				jointDef = json.readValue(RevoluteJointDef.class, jsonData);
			}
			else if(jointType.equals("prismatic"))
			{
				jointDef = json.readValue(PrismaticJointDef.class, jsonData);
			}
			else if(jointType.equals("distance"))
			{
				jointDef = json.readValue(DistanceJointDef.class, jsonData);
			}
			else if(jointType.equals("pulley"))
			{
				jointDef = json.readValue(PulleyJointDef.class, jsonData);
			}
			else if(jointType.equals("mouse"))
			{
				jointDef = json.readValue(MouseJointDef.class, jsonData);
			}
			else if(jointType.equals("wheel"))
			{
				jointDef = json.readValue(WheelJointDef.class, jsonData);
			}
			else if(jointType.equals("weld"))
			{
				jointDef = json.readValue(WeldJointDef.class, jsonData);
			}
			else if(jointType.equals("friction"))
			{
				jointDef = json.readValue(FrictionJointDef.class, jsonData);
			}
			else if(jointType.equals("rope"))
			{
				jointDef = json.readValue(RopeJointDef.class, jsonData);
			}
		}
		else if(joints != null && jointType.equals("gear")) // Second pass
		{
			jointDef = json.readValue(GearJointDef.class, jsonData);
		}
		
		if(jointDef != null)
		{
			jointDef.bodyA = bodies.get(indexA);
			jointDef.bodyB = bodies.get(indexB);
			jointDef.collideConnected = json.readValue("collideConnected", boolean.class, RubeDefaults.Joint.collideConnected, jsonData);
			
			joint = world.createJoint(jointDef);
			
			if(jointType.equals("mouse"))
			{
				((MouseJoint) joint).setTarget(mouseJointDefSerializer.target);
			}
			
			String name = json.readValue("name", String.class, jsonData);
			
			RubeCustomProperty customProperty = null;
			if(json.getSerializer(RubeCustomProperty.class) != null)
				customProperty = json.readValue("customProperties", RubeCustomProperty.class, jsonData);
			
			scene.onAddJoint(joint, name, customProperty);
		}
		
		return joint;
	}
	
	public class RevoluteJointDefSerializer extends ReadOnlySerializer<RevoluteJointDef>
	{	
		@Override
		public RevoluteJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			RevoluteJointDef defaults = RubeDefaults.Joint.revoluteDef;
			
			RevoluteJointDef def = new RevoluteJointDef();
			
			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.referenceAngle 	= json.readValue("refAngle", 		float.class, defaults.referenceAngle, jsonData);
				def.enableLimit 	= json.readValue("enableLimit", 	boolean.class, defaults.enableLimit, jsonData);
				def.lowerAngle 		= json.readValue("lowerLimit", 		float.class, defaults.lowerAngle, jsonData);
				def.upperAngle 		= json.readValue("upperLimit",  	float.class, defaults.upperAngle, jsonData);
				def.enableMotor 	= json.readValue("enableMotor", 	boolean.class, defaults.enableMotor, jsonData);
				def.motorSpeed 		= json.readValue("motorSpeed",  	float.class, defaults.motorSpeed, jsonData);
				def.maxMotorTorque 	= json.readValue("maxMotorTorque", 	float.class, defaults.maxMotorTorque, jsonData);
			}
			
			return def; 
		}
	}
	
	public class PrismaticJointDefSerializer extends ReadOnlySerializer<PrismaticJointDef>
	{	
		@Override
		public PrismaticJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			PrismaticJointDef defaults = RubeDefaults.Joint.prismaticDef;
			
			PrismaticJointDef def = new PrismaticJointDef();

			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				
				Vector2 localAxis = json.readValue("localAxisA", Vector2.class, defaults.localAxisA, jsonData);
				if(localAxis == null)
					localAxis = json.readValue("localAxis1", Vector2.class, defaults.localAxisA, jsonData);
				if(localAxis!=null)
					def.localAxisA.set(localAxis);
				
				def.referenceAngle 		= json.readValue("refAngle", 		float.class, 	defaults.referenceAngle, 	jsonData);
				def.enableLimit 		= json.readValue("enableLimit", 	boolean.class, 	defaults.enableLimit, 		jsonData);
				def.lowerTranslation 	= json.readValue("lowerLimit", 		float.class, 	defaults.lowerTranslation, 	jsonData);
				def.upperTranslation 	= json.readValue("upperLimit", 		float.class, 	defaults.upperTranslation, 	jsonData);
				def.enableMotor 		= json.readValue("enableMotor", 	boolean.class, 	defaults.enableMotor, 		jsonData);
				def.motorSpeed 			= json.readValue("motorSpeed", 		float.class, 	defaults.motorSpeed, 		jsonData);
				def.maxMotorForce 		= json.readValue("maxMotorForce",	float.class, 	defaults.maxMotorForce, 	jsonData);
			}
			
			return def; 
		}
	}
	
	public class DistanceJointDefSerializer extends ReadOnlySerializer<DistanceJointDef>
	{	
		@Override
		public DistanceJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			DistanceJointDef defaults = RubeDefaults.Joint.distanceDef;
			
			DistanceJointDef def = new DistanceJointDef();

			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.length 			= json.readValue("length", 			float.class, 	defaults.length, 		jsonData);
				def.frequencyHz 	= json.readValue("frequency", 		float.class, 	defaults.frequencyHz, 	jsonData);
				def.dampingRatio 	= json.readValue("dampingRatio", 	float.class, 	defaults.dampingRatio, 	jsonData);
			}
			
			return def; 
		}
	}
	
	public class PulleyJointDefSerializer extends ReadOnlySerializer<PulleyJointDef>
	{	
		@Override
		public PulleyJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			PulleyJointDef defaults = RubeDefaults.Joint.pulleyDef;
			
			PulleyJointDef def = new PulleyJointDef();

			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			Vector2 groundAnchorA = json.readValue("groundAnchorA", Vector2.class, defaults.groundAnchorA, jsonData);
			Vector2 groundAnchorB = json.readValue("groundAnchorB", Vector2.class, defaults.groundAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null && groundAnchorA != null && groundAnchorB !=null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				defaults.groundAnchorA.set(groundAnchorA);
				defaults.groundAnchorB.set(groundAnchorB);
				
				def.lengthA 	= json.readValue("lengthA", float.class, 	defaults.lengthA, 	jsonData);
				def.lengthB 	= json.readValue("lengthB", float.class, 	defaults.lengthB, 	jsonData);
				def.ratio 		= json.readValue("ratio", 	float.class, 	defaults.ratio, 	jsonData);
			}
			
			return def; 
		}
	}
	
	public class MouseJointDefSerializer extends ReadOnlySerializer<MouseJointDef>
	{	
		public Vector2 target;
		
		@Override
		public MouseJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			MouseJointDef defaults = RubeDefaults.Joint.mouseDef;
			
			MouseJointDef def = new MouseJointDef();

			// Don't forget to set the target to the joint once it's created
			target = json.readValue("target", Vector2.class, defaults.target, jsonData);
			
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.target, jsonData);
			
			if(target != null && anchorB != null)
			{
				def.target.set(anchorB);
				def.maxForce 		= json.readValue("maxForce", 		float.class, 	defaults.maxForce, 		jsonData);
				def.frequencyHz 	= json.readValue("frequency", 		float.class, 	defaults.frequencyHz, 	jsonData);
				def.dampingRatio 	= json.readValue("dampingRatio", 	float.class, 	defaults.dampingRatio, 	jsonData);
			}
			
			return def; 
		}
	}
	
	public class WeldJointDefSerializer extends ReadOnlySerializer<WeldJointDef>
	{	
		@Override
		public WeldJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			WeldJointDef defaults = RubeDefaults.Joint.weldDef;
			
			WeldJointDef def = new WeldJointDef();
			
			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.referenceAngle 	= json.readValue("refAngle", float.class, defaults.referenceAngle, jsonData);
			}
			
			return def; 
		}
	}
	
	public class FrictionJointDefSerializer extends ReadOnlySerializer<FrictionJointDef>
	{	
		@Override
		public FrictionJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			FrictionJointDef defaults = RubeDefaults.Joint.frictionDef;
			
			FrictionJointDef def = new FrictionJointDef();
			
			Vector2 anchorA = json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB = json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.maxForce 	= json.readValue("maxForce", float.class, defaults.maxForce, jsonData);
				def.maxTorque 	= json.readValue("maxTorque", float.class, defaults.maxTorque, jsonData);
			}
			
			return def; 
		}
	}
	
	public class WheelJointDefSerializer extends ReadOnlySerializer<WheelJointDef>
	{	
		@Override
		public WheelJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			WheelJointDef defaults = RubeDefaults.Joint.wheelDef;
			
			WheelJointDef def = new WheelJointDef();

			Vector2 anchorA 	= json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB 	= json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);
			Vector2 localAxisA 	= json.readValue("localAxisA", Vector2.class, defaults.localAxisA, jsonData);
			
			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.localAxisA.set(localAxisA);
				
				def.enableMotor 		= json.readValue("enableMotor", 		boolean.class, 	defaults.enableMotor, 		jsonData);
				def.motorSpeed 			= json.readValue("motorSpeed", 			float.class, 	defaults.motorSpeed, 		jsonData);
				def.maxMotorTorque 		= json.readValue("maxMotorTorque",		float.class, 	defaults.maxMotorTorque, 	jsonData);
				def.frequencyHz 		= json.readValue("springFrequency", 	float.class, 	defaults.frequencyHz, 		jsonData);
				def.dampingRatio 		= json.readValue("springDampingRatio", 	float.class, 	defaults.dampingRatio, 		jsonData);
			}
			
			return def; 
		}
	}
	
	public class RopeJointDefSerializer extends ReadOnlySerializer<RopeJointDef>
	{	
		@Override
		public RopeJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			RopeJointDef defaults = RubeDefaults.Joint.ropeDef;
			
			RopeJointDef def = new RopeJointDef();

			Vector2 anchorA 	= json.readValue("anchorA", Vector2.class, defaults.localAnchorA, jsonData);
			Vector2 anchorB 	= json.readValue("anchorB", Vector2.class, defaults.localAnchorB, jsonData);

			if(anchorA != null && anchorB != null)
			{
				def.localAnchorA.set(anchorA);
				def.localAnchorB.set(anchorB);
				def.maxLength 	= json.readValue("maxLength", 		float.class, 	defaults.maxLength, 		jsonData);
			}
			
			return def; 
		}
	}
	
	public class GearJointDefSerializer extends ReadOnlySerializer<GearJointDef>
	{	
		@Override
		public GearJointDef read(Json json, JsonValue jsonData, Class type)
		{	
			if(joints == null )
				return null;
			
			GearJointDef defaults = RubeDefaults.Joint.gearDef;
			
			GearJointDef def = null;

			int joint1 = json.readValue("joint1", 	int.class, 	joints.size, 		jsonData);
			int joint2 = json.readValue("joint2", 	int.class, 	joints.size, 		jsonData);
			
			if(joint1 < joints.size && joint2 < joints.size)
			{
				def = new GearJointDef();
				def.joint1 = joints.get(joint1);
				def.joint2 = joints.get(joint2);
				def.ratio 	= json.readValue("ratio", 		float.class, 	defaults.ratio, 		jsonData);
			}
			
			return def; 
		}
	}

}
