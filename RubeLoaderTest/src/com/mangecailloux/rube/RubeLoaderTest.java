package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.loader.RubeSceneLoader;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.store.ByNameStore;


public class RubeLoaderTest implements ApplicationListener {
	private OrthographicCamera camera;
	private RubeSceneLoader	loader;
	private Box2DScene	scene;
	private Box2DDebugRenderer renderer;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(50, 50*h/w);
		
		loader = new RubeSceneLoader();
		
		loader.addStore(ByNameStore.class);
		
		scene = loader.loadScene(Gdx.files.internal("data/documentA.json"));
		
		ByNameStore store = scene.getStore(ByNameStore.class);
		
		if(store != null)
		{
			Body body = store.get(Body.class, "body1");
		}
		
		renderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose() 
	{
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
