package com.badlogic.gdx.rube.convertor.description;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Array;

public class WorldDescription 
{
	public boolean allowSleep 			= true;
	public boolean autoClearForces 		= true;
	public boolean continuousPhysics 	= true;
	public boolean warmStarting 		= true;
	public Vector2 gravity 				= new Vector2(0.0f, -9.81f);
	
	public Array<MassData> 				massDatas;
	public Array<FixtureDescription> 	fixtures;
	public Array<BodyDescription> 		bodies;
	public Array<JointDescription> 		joints;
	
	public WorldDescription()
	{
		
	}
	
	public int addMassData(MassData _data)
	{
		if(_data == null)
			return -1;
		
		for(int i = 0; i < massDatas.size; ++ i)
		{
			MassData m = massDatas.get(i);
			
			if(m.center.x == _data.center.x && m.center.y == _data.center.y && m.mass == _data.mass && m.I == _data.I)
			{
				return i;
			}
		}
		
		massDatas.add(_data);
		
		return (massDatas.size - 1);
	}
	
	public int addFixture(FixtureDescription _fixture)
	{
		return 0;
		
	}
}
