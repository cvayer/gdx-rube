package com.mangecailloux.rube;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class SpriteRenderer
{

	Array<Sprite> sprites;
	ObjectMap<Sprite, Body> bodies;
	ObjectMap<Sprite, RubeImage> images;
	
	final Vector2 tmp = new Vector2();
	
	public SpriteRenderer()
	{
		sprites = new Array<Sprite>();
		bodies = new ObjectMap<Sprite, Body>();
		images = new ObjectMap<Sprite, RubeImage>();
	}
	
	public void addImages(Array<RubeImage> _images)
	{
		if(_images != null)
		{
			for(int i=0; i < _images.size; ++i)
			{
				addImage(_images.get(i));
			}
		}
	}

	public void addImage(RubeImage _image) {
		
		if(_image instanceof RubeImage)
		{
			RubeImage image = (RubeImage)_image;
			
			Texture texture = new Texture("data/" + image.file );
			Sprite sprite = new Sprite(texture);
			
			sprite.flip(image.flip, false);
			sprite.setColor(image.color);
			sprite.setSize(image.width, image.height);
			sprite.setOrigin(image.width /2, image.height /2);
			
			if(_image.body != null)
			{
				bodies.put(sprite, _image.body);
			
				
				float bodyAngle = _image.body.getAngle()* MathUtils.radiansToDegrees;
				
				tmp.set(_image.center).rotate(bodyAngle).add(_image.body.getPosition()).sub(image.width/2, image.height/2);
				
				sprite.setRotation(bodyAngle + image.angle* MathUtils.radiansToDegrees);
			}
			else
			{
				tmp.set(_image.center).sub(image.width /2, image.height /2);
				sprite.setRotation(image.angle* MathUtils.radiansToDegrees);
			}
			
			sprite.setPosition(tmp.x, tmp.y);
			
			sprites.add(sprite);
			images.put(sprite, image);
		}
	}
	
	public void render(SpriteBatch _batch)
	{
		for(int i =0; i  < sprites.size; ++i)
		{
			Sprite sprite = sprites.get(i);
			Body body = bodies.get(sprite);
			RubeImage image = images.get(sprite);
			
			if(body != null)
			{
				float bodyAngle = body.getAngle() * MathUtils.radiansToDegrees;
				tmp.set(image.center).rotate(bodyAngle).add(image.body.getPosition()).sub(image.width/2, image.height/2);
				
				sprite.setPosition(tmp.x, tmp.y);
				sprite.setRotation(bodyAngle + image.angle* MathUtils.radiansToDegrees);
				
			}		
			
			sprite.draw(_batch);
		}
	}
	
	public void dispose()
	{
		for(int i =0; i  < sprites.size; ++i)
		{
			Sprite sprite = sprites.get(i);
			
			sprite.getTexture().dispose();
		}
	}
}
