package com.badlogic.gdx.rube.convertor.description;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.IntArray;

public class BodyDescription 
{
	public String 				name;
	public BodyDef 				body;
	public int 					massDataIndex = -1;
	public IntArray				fixturesIndexes;
	
	public BodyDescription()
	{
		body = new BodyDef();
		
		fixturesIndexes = new IntArray();
	}
}
