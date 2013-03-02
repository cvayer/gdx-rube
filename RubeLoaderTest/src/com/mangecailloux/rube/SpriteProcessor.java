package com.mangecailloux.rube;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.rube.RubeImage;
import com.badlogic.gdx.scenes.box2d.B2DSImage;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessor;
import com.badlogic.gdx.scenes.box2d.B2DSCustomProperty;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class SpriteProcessor extends B2DSProcessor
{

	Array<Sprite> sprites;
	ObjectMap<Sprite, Body> bodies;
	ObjectMap<Sprite, RubeImage> images;
	
	public SpriteProcessor()
	{
		sprites = new Array<Sprite>();
		bodies = new ObjectMap<Sprite, Body>();
		images = new ObjectMap<Sprite, RubeImage>();
	}
	
	@Override
	public void onAddWorld(World _world, B2DSCustomProperty _customProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddBody(Body _body, String _name,
			B2DSCustomProperty _customProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddFixture(Fixture _fixture, String _name,
			B2DSCustomProperty _customProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddJoint(Joint _joint, String _name,
			B2DSCustomProperty _customProperty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddImage(B2DSImage _image, String _name, Body _body, B2DSCustomProperty _customProperty) {
		
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
			
			bodies.put(sprite, _body);
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
				sprite.setRotation(body.getAngle()* 180.0f / 3.14f);
				
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

	@Override
	public void onRemoveBody(Body _body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveFixture(Fixture _fixture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveJoint(Joint _joint) {
		// TODO Auto-generated method stub
		
	}

}
