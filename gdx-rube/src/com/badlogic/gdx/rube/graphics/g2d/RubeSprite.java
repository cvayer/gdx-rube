package com.badlogic.gdx.rube.graphics.g2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.rube.RubeImage;

/**
 * A RubeSprite extends from {@link Sprite} with additional information to properly follow a {@link Body}
 * @author clement.vayer
 */
public class RubeSprite extends Sprite {

	private final Vector2 tmp = new Vector2();
	
	private			Body	body;
	private 		float	offsetAngle;
	private final   Vector2 offsetPosition;
	
	/**
	 * Creates a RubeSprite and setup it with the information in the {@link RubeImage}.
	 * @param texture The {@link Texture} to use for the sprite.
	 * @param imageData Parameters to init and draw the sprite correctly.
	 */
	public RubeSprite (Texture texture, RubeImage imageData) {
		super(texture);
		offsetPosition = new Vector2();
		setupFromImage(imageData);
	}

	/**
	 * Creates a RubeSprite and setup it with the information in the {@link RubeImage}.
	 * @param region The {@link TextureRegion} to use for the sprite.
	 * @param imageData Parameters to init and draw the sprite correctly.
	 */
	public RubeSprite (TextureRegion region, RubeImage imageData) {
		super(region);
		offsetPosition = new Vector2();
		setupFromImage(imageData);
	}
	
	protected void setupFromImage(RubeImage imageData) {
		flip(imageData.flip, false);
		imageData.color.a *= imageData.opacity;
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
		
		if(imageData.filter == 0)
		{
			getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
		else
		{
			getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * Copy every attributes of the parameter.
	 * @param sprite RubeSprite to copy.
	 */
	public void set(RubeSprite sprite) {
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
	
	protected void updateBodyInfos() {
		if(body != null) {
			float bodyAngle = body.getAngle() * MathUtils.radiansToDegrees;
			tmp.set(offsetPosition).rotate(bodyAngle).add(body.getPosition()).sub(getWidth()/2.0f, getHeight()/2.0f);
			setPosition(tmp.x, tmp.y);
			setRotation(bodyAngle + offsetAngle);
		}	
	}
}
