package com.badlogic.gdx.rube.graphics.g2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 *  A RubeSprite extends from {@link PolygonSprite} with additional information to properly follow a {@link Body}
 * @author clement.vayer
 *
 */
public class RubePolygonSprite extends PolygonSprite
{
	// Static
	private static float pixelsPerMeters = 100.0f;
	
	private static Vector2 tmp = new Vector2();
	
	public static float getPixelsPerMeters(){
		return pixelsPerMeters;
	}
	
	/**
	 * Sets the pixels per meters ratio to map the textures to the fixtures
	 * @param pixelsPerMeters How many pixels we be needed to fill one Box2D meter.
	 */
	public static void setPixelPerMeters(float pixelsPerMeters) {
		RubePolygonSprite.pixelsPerMeters = pixelsPerMeters;
	}

	/**
	 * Convenience method to create a new RubePolygonSprite from a {@link Fixture}. <br/>
	 * Can return null if the Fixture doesn't meet the requirement (not a polygon fixture or body is null).
	 * @param texture {@link Texture} to use to draw the sprite.
	 * @param fixture Reference {@link Fixture} to create the sprite.
	 * @return The newly created RubePolygonSprite.
	 */
	public static RubePolygonSprite createRubePolygonSprite(Texture texture, Fixture fixture)
	{
		if(texture != null && fixture != null && fixture.getBody() != null && fixture.getType() == Shape.Type.Polygon)
		{
			PolygonShape shape = (PolygonShape)fixture.getShape();
			int vertexCount = shape.getVertexCount();
			float [] vertices = new float[vertexCount*2];
			Body body = fixture.getBody();
			
			// static bodies are texture aligned and do not get drawn based off of the related body.
			if (body.getType() == BodyType.StaticBody)
			{
				for (int i = 0; i < vertexCount; ++i)
				{
					shape.getVertex(i, tmp);
					tmp.rotate(body.getAngle()*MathUtils.radiansToDegrees).add(body.getPosition()); // convert local coordinates to world coordinates to that textures are aligned
					vertices[i*2] = tmp.x*pixelsPerMeters;
					vertices[i*2+1] = tmp.y*pixelsPerMeters;
				}
				return new RubePolygonSprite(texture, vertices);
			}
			else
			{
				// all other fixtures are aligned based on their associated body. 
				for (int i = 0; i < vertexCount; ++i)
				{
					shape.getVertex(i, tmp);
					vertices[i*2] = tmp.x*pixelsPerMeters;
					vertices[i*2+1] = tmp.y*pixelsPerMeters;
				}
				return new RubePolygonSprite(texture, vertices, body);
			}
		}
		return null;
	}
	
	// non static stuff
	private Body body;
	
	public RubePolygonSprite(Texture texture, float[] vertices) {
		this(new PolygonRegion(new TextureRegion(texture), vertices), null);
		
	}
	
	public RubePolygonSprite(Texture texture, float[] vertices, Body body) {
		this(new PolygonRegion(new TextureRegion(texture), vertices), body);
		
	}
	
	public RubePolygonSprite(PolygonRegion region, Body body) {
		super(region);
		
		this.body = body;
		
		setOrigin(0.0f, 0.0f);
		setSize(region.getRegion().getRegionWidth()/pixelsPerMeters, region.getRegion().getRegionHeight()/pixelsPerMeters);
		
		if(body != null)
		{
			float bodyAngle = body.getAngle() * MathUtils.radiansToDegrees;
			setPosition(body.getPosition().x, body.getPosition().y);
			setRotation(bodyAngle);
		}
	}
	
	/**
	 * Copy every attributes of the parameter.
	 * @param sprite RubePolygonSprite to copy.
	 */
	public void set(RubePolygonSprite sprite)
	{
		super.set(sprite);
		body = sprite.body;
	}
	
	@Override
	public void draw (PolygonSpriteBatch spriteBatch) {
		updateBodyInfos();
		super.draw(spriteBatch);
	}

	@Override
	public void draw (PolygonSpriteBatch spriteBatch, float alphaModulation) {
		updateBodyInfos();
		super.draw(spriteBatch, alphaModulation);
	}
	
	void updateBodyInfos()
	{
		if(body != null)
		{
			float bodyAngle = body.getAngle() * MathUtils.radiansToDegrees;
			setPosition(body.getPosition().x, body.getPosition().y);
			setRotation(bodyAngle);
		}	
	}

}
