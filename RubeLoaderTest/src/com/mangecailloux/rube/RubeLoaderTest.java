package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


public class RubeLoaderTest implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private RubeLoader	loader;
	private RubeWorld	world;
	private Box2DDebugRenderer renderer;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(20, 20*h/w);
		batch = new SpriteBatch();
		
		loader = new RubeLoader();
		
		world = loader.loadWorld(Gdx.files.internal("data/documentA.json"));
		
		renderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		world.step();
		
		
		renderer.render(world.getWorld(), camera.combined);
		
		/*batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();*/
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
