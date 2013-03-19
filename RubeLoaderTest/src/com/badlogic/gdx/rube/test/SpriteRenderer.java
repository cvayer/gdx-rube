package com.badlogic.gdx.rube.test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.graphics.g2d.RubePolygonSprite;
import com.badlogic.gdx.rube.graphics.g2d.RubeSprite;
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
	
	public void initFromScene(RubeScene _scene, AssetManager assetManager)
	{
		addImages(_scene, _scene.getImages(), assetManager);
		addFixtures(_scene);
	}
	
	public void addImages(RubeScene _scene, Array<RubeImage> _images, AssetManager assetManager)
	{
		if(_images != null)
		{
			for(int i=0; i < _images.size; ++i)
			{
				addImage(_scene, _images.get(i), assetManager);
			}
		}
	}

	public void addImage(RubeScene _scene, RubeImage _image, AssetManager assetManager) {
		
		if(_image instanceof RubeImage)
		{
			RubeImage image = (RubeImage)_image;
			
			
			Texture texture = null;
			RubeSprite sprite = null;
			
			if(assetManager != null)
			{
				// If the scene uses atlases instead of a bunch of texture we get it instead
				if(_scene.usesAtlas())
				{
					TextureAtlas atlas = assetManager.get("data/" + _scene.getAtlasFilePath(0), TextureAtlas.class);
					if(atlas != null)
					{
						TextureRegion region = atlas.findRegion(image.file);
						if(region != null)
							sprite = new RubeSprite(region, image);	
					}
				}
				else // else we get the textures
				{
					texture = assetManager.get("data/" + image.file, Texture.class);
					sprite = new RubeSprite(texture, image);	
				}
			}
			else // If we don't use the asset manager we create a texture if needed
			{
				texture = textureMap.get("data/" + image.file);
				if(texture == null)
				{
					texture = new Texture("data/" + image.file);
					textureMap.put("data/" + image.file, texture);
				}
				sprite = new RubeSprite(texture, image);	
			}
			

			if(sprite != null)
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
					// We create a texture if needed
					textureName = "data/" + textureName;
					Texture texture = textureMap.get(textureName);
					
					if(texture == null)
					{
						texture = new Texture(textureName);
						texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
						textureMap.put(textureName, texture);
					}
					
					// And use that texture plus fixture infos to create a polygon sprite
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
		Values<Texture> textures = textureMap.values();
		
		while(textures.hasNext())
    	{
			Texture texture = textures.next();
			texture.dispose();
    	}
	}
}
