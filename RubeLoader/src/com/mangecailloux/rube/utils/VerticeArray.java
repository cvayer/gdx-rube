package com.mangecailloux.rube.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class VerticeArray 
{
	public float x[];
	public float y[];
	
	public VerticeArray()
	{
		
	}
	
	public Vector2[] toVector2()
	{
		if((x == null || y == null) || x.length != y.length || x.length == 0)
			return null;
		
		Vector2[] vertices = new Vector2[x.length];
		for(int i=0; i < x.length; ++i)
		{
			Vector2 vector = new Vector2(x[i], y[i]);
			vertices[i] =vector;
		}
		return vertices;
	}
}
