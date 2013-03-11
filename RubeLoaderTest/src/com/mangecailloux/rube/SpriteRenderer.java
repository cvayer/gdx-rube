package com.mangecailloux.rube;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.rube.RubeSprite;
import com.badlogic.gdx.utils.Array;

public class SpriteRenderer
{
	Array<Sprite> sprites;
	
	final Vector2 tmp = new Vector2();
	
	public SpriteRenderer()
	{
		sprites = new Array<Sprite>();
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
			
			image.file = "data/"+image.file;
			
			RubeSprite sprite = new RubeSprite(image);			
			sprites.add(sprite);
		}
	}
	
	public void render(SpriteBatch _batch)
	{
		for(int i =0; i  < sprites.size; ++i)
		{
			Sprite sprite = sprites.get(i);			
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
