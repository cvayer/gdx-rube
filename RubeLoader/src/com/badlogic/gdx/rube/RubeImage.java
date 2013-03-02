package com.badlogic.gdx.rube;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.box2d.B2DSImage;

public class RubeImage extends B2DSImage
{
	public float 		angle = 0.0f;
	public Body	 		body  = null;
	public Vector2 		center = new Vector2();
	public String 		file = null;
	public int	  		filter;
	public String 		name = null;
	public float 		opacity = 1.0f;
	public int 			renderOrder = 0;
	public float 		scale = 1.0f;
	public float 		width = 0.0f;
	public float 		height = 0.0f;
	
	public RubeImage()
	{
		
	}
}
