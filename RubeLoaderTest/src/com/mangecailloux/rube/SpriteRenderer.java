package com.mangecailloux.rube;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class SpriteRenderer
{

	Array<Sprite> sprites;
	ObjectMap<Sprite, Body> bodies;
	ObjectMap<Sprite, RubeImage> images;
	
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
			
			sprite.translate(-image.center.x, -image.center.y);
			
			sprite.setRotation(image.angle);
			
			sprite.setSize(image.width, image.height);
			
			sprite.setOrigin(image.center.x + image.width /2, image.center.y + image.height /2);
			
			sprites.add(sprite);
			
			bodies.put(sprite, _image.body);
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
				sprite.setPosition(body.getPosition().x, body.getPosition().y);
				sprite.setRotation((body.getAngle() + image.angle)* 180.0f / 3.14f);
				
			}
			else
			{
				sprite.setPosition(0.0f, 0.0f);
			}
		
			sprite.translate(- image.width /2, - image.height /2);
			sprite.translate(image.center.x, image.center.y);
			
			
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
