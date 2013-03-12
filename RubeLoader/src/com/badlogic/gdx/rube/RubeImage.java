package com.badlogic.gdx.rube;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Representation of the properties of an image in a RUBE json file.
 * @author clement.vayer
 */
public class RubeImage
{
	/** angle in radians */
	public 		 float 		angle = 0.0f;
	/** {@link Body} the image is linked to */
	public 		 Body	 	body  = null;
	/** Offset from the center of the body */
	public final Vector2 	center = new Vector2();
	/** Path of the texture file, relative to the folder the json file is saved to*/
	public 		 String 	file = null;
	/**	Filter of the texture ( 0 = nearest, 1 = linear )									*/
	public 		 int	  	filter;
	/** Name of the image given in the editor */
	public 		 String 	name = null;
	/** opacity between 0 and 1 */
	public 		 float 		opacity = 1.0f;
	/** render order if needed ( you can then sort your textures to have the right draw order )*/
	public 		 int 		renderOrder = 0;
	/** scale the image has been scaled to to have the current width and height */
	public 		 float 		scale = 1.0f;
	/** width of the image */
	public 		 float 		width = 0.0f;
	/** height of the image */
	public 		 float 		height = 0.0f;
	/** true of the image has been flipped horizontally */
	public 		 boolean	flip = false;
	/** Color to tint the image */
	public final Color  	color = new Color();
}
