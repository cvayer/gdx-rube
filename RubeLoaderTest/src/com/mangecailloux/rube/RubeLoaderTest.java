package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.loader.RubeSceneLoader;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoaderParameters;
import com.badlogic.gdx.scenes.box2d.loader.serializer.B2DSMapCustomPropertySerializer;
import com.badlogic.gdx.scenes.box2d.processor.B2DSByNameProcessor;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessorsDefinition;


public class RubeLoaderTest implements ApplicationListener {
	private OrthographicCamera camera;
	private RubeSceneLoader	loader;
	private Box2DScene	scene;
	private Box2DDebugRenderer renderer;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(15, 15*h/w);
		
		Box2DSceneLoaderParameters params = new Box2DSceneLoaderParameters();
		
		params.customPropertiesSerializer = new B2DSMapCustomPropertySerializer();
		params.definitions = new B2DSProcessorsDefinition();
		params.definitions.addProcessor(B2DSByNameProcessor.class);
		
		loader = new RubeSceneLoader(params);
	
		scene = loader.loadScene(Gdx.files.internal("data/documentA.json"));
		loader.loadScene(Gdx.files.internal("data/documentA2.json"));
		
		B2DSByNameProcessor store = scene.getStore(B2DSByNameProcessor.class);
		
		if(store != null)
		{
			Body body = store.get(Body.class, "body1");
		}
		
		renderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose() 
	{
		scene.dispose();
		renderer.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		scene.step();
		
		
		renderer.render(scene.world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
