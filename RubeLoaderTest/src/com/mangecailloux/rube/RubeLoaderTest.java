package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.loader.RubeSceneLoader;


public class RubeLoaderTest implements ApplicationListener {
	private OrthographicCamera camera;
	private RubeSceneLoader	loader;
	private RubeScene	scene;
	private Box2DDebugRenderer renderer;
	private SpriteRenderer		render;
	private SpriteBatch       batch;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		float cameraViewportWidth = 40.0f;
		camera = new OrthographicCamera(cameraViewportWidth, cameraViewportWidth*h/w);
		
		
		loader = new RubeSceneLoader();
	
		scene = loader.loadScene(Gdx.files.internal("data/images.json"));

		
		render = new SpriteRenderer();
		render.addImages(scene.getImages());
		
		renderer = new Box2DDebugRenderer();
		
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() 
	{
		render.dispose();
		renderer.dispose();
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		scene.world.step(1.0f/scene.stepsPerSecond, scene.velocityIterations, scene.positionIterations);
		
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		batch.begin();
		render.render(batch);
		batch.end();
		
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
