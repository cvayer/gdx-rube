package com.badlogic.gdx.rube;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class RubeSprite extends Sprite {

	private final Vector2 tmp = new Vector2();
	
	private			Body	body;
	private 		float	offsetAngle;
	private final   Vector2 offsetPosition;
	
	/** Creates a sprite with width, height, and texture region equal to the size of the texture. */
	public RubeSprite (Texture texture, RubeImage imageData) {
		super(texture);
		offsetPosition = new Vector2();
		setupFromImage(imageData);
	}

	public RubeSprite (TextureRegion region, RubeImage imageData) {
		super(region);
		offsetPosition = new Vector2();
		setupFromImage(imageData);
	}
	
	public RubeSprite (RubeImage imageData) {
		super(new Texture(imageData.file));
		offsetPosition = new Vector2();
		setupFromImage(imageData);
	}
	
	void setupFromImage(RubeImage imageData)
	{
		flip(imageData.flip, false);
		setColor(imageData.color);
		setSize(imageData.width, imageData.height);
		
		float halfWidth = imageData.width / 2.0f;
		float halfHeight = imageData.height /2.0f;
		
		setOrigin(halfWidth, halfHeight);
		
		body = imageData.body;
		
		offsetAngle = imageData.angle* MathUtils.radiansToDegrees;
		offsetPosition.set(imageData.center);
		
		if(body != null)
		{
			float bodyAngle = body.getAngle()* MathUtils.radiansToDegrees;
			tmp.set(offsetPosition).rotate(bodyAngle).add(body.getPosition()).sub(halfWidth, halfHeight);
			setRotation(bodyAngle + offsetAngle);
		}
		else
		{
			tmp.set(imageData.center).sub(halfWidth, halfHeight);
			setRotation(offsetAngle);
		}
		
		setPosition(tmp.x, tmp.y);
	}
	
	public void set(RubeSprite sprite)
	{
		super.set(sprite);
		body = sprite.body;
		offsetAngle = sprite.offsetAngle;
		offsetPosition.set(sprite.offsetPosition);
	}
	
	@Override
	public void draw (SpriteBatch spriteBatch) {
		updateBodyInfos();
		super.draw(spriteBatch);
	}

	@Override
	public void draw (SpriteBatch spriteBatch, float alphaModulation) {
		
		updateBodyInfos();
		super.draw(spriteBatch, alphaModulation);
	}
	
	void updateBodyInfos()
	{
		if(body != null)
		{
			float bodyAngle = body.getAngle() * MathUtils.radiansToDegrees;
			tmp.set(offsetPosition).rotate(bodyAngle).add(body.getPosition()).sub(getWidth()/2.0f, getHeight()/2.0f);
			setPosition(tmp.x, tmp.y);
			setRotation(bodyAngle + offsetAngle);
			
		}	
	}
}
