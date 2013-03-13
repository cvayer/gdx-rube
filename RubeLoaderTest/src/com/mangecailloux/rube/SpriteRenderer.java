package com.mangecailloux.rube;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.rube.RubePolygonSprite;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.RubeSprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;

public class SpriteRenderer
{
	final Array<RubeSprite> sprites;
	final Array<RubePolygonSprite> polygonSprites;
	final private ObjectMap<String, Texture> textureMap;
	
	final Vector2 tmp = new Vector2();
	
	public SpriteRenderer()
	{
		sprites = new Array<RubeSprite>();
		polygonSprites = new Array<RubePolygonSprite>();
		textureMap = new ObjectMap<String, Texture>();
	}
	
	public void initFromScene(RubeScene _scene)
	{
		addImages(_scene.getImages());
		addFixtures(_scene);
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
			
			RubeSprite sprite = new RubeSprite(new Texture(image.file), image);			
			sprites.add(sprite);
		}
	}
	
	public void addFixtures(RubeScene _scene)
	{
		Array<Fixture> fixtures = _scene.getFixtures();
		
		for(int i=0; i < fixtures.size; ++i)
		{
			Fixture fixture = fixtures.get(i);
			RubeCustomProperty property = _scene.getProperty(fixture);
			if(property != null)
			{
				String textureName = property.getString("TextureMask", null);
				if(textureName != null)
				{
					textureName = "data/" + textureName;
					Texture texture = textureMap.get(textureName);
					
					if(texture == null)
					{
						texture = new Texture(textureName);
						texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
						textureMap.put(textureName, texture);
					}
					
					RubePolygonSprite sprite = RubePolygonSprite.createRubePolygonSprite(texture, fixture);
					if(sprite != null)
					{
						polygonSprites.add(sprite);
					}
					
				}
			}
		}
	}
	
	public void render(SpriteBatch _batch)
	{
		for(int i =0; i  < sprites.size; ++i)
		{
			RubeSprite sprite = sprites.get(i);			
			sprite.draw(_batch);
		}
	}
	
	public void render(PolygonSpriteBatch _polygonBatch)
	{		
		for(int i =0; i  < polygonSprites.size; ++i)
		{
			RubePolygonSprite sprite = polygonSprites.get(i);			
			sprite.draw(_polygonBatch);
		}
	}
	
	public void dispose()
	{
		for(int i =0; i  < sprites.size; ++i)
		{
			Sprite sprite = sprites.get(i);
			
			sprite.getTexture().dispose();
		}
		
		Values<Texture> textures = textureMap.values();
		
		while(textures.hasNext())
    	{
			Texture texture = textures.next();
			texture.dispose();
    	}
	}
}
