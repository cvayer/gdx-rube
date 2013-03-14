package com.badlogic.gdx.rube.reader.serializer.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Used for serialize/unserialize vertex arrays stored with coordinates in separate arrays. <br/>
 * Example :  <br/>
 *  {<br/>
 *    "x" : [ 0.50, 0.50, -0.50, -0.50 ],<br/>
 *    "y" : [ -0.50, 0.50, 0.50, -0.50 ]<br/>
 *  }<br/>
 * 
 * @author clement.vayer
 *
 */
public class RubeVertexArray 
{
	/** x coordinates of the vertices */
	public float x[];
	/** y coordinates of the vertices */
	public float y[];
	
	/**
	 * 
	 * @return a new Vector2 Array. Can be null if x and y don't meet the requirements.
	 */
	public Vector2[] toVector2()
	{
		// lenght of x and y should be the same and not null
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
