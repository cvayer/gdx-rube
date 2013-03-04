package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.loader.RubeSceneLoader;
import com.badlogic.gdx.rube.loader.serializer.RubeCustomPropertySerializer;
import com.badlogic.gdx.rube.loader.serializer.RubeImageSerializer;
import com.badlogic.gdx.scenes.box2d.Box2DScene;
import com.badlogic.gdx.scenes.box2d.loader.Box2DSceneLoaderParameters;
import com.badlogic.gdx.scenes.box2d.processor.B2DSByNameStore;
import com.badlogic.gdx.scenes.box2d.processor.B2DSProcessorsDefinition;


public class RubeLoaderTest implements ApplicationListener {
	private OrthographicCamera camera;
	private RubeSceneLoader	loader;
	private Box2DScene	scene;
	private Box2DDebugRenderer renderer;
	private SpriteProcessor		render;
	private SpriteBatch       batch;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		float cameraViewportWidth = 40.0f;
		camera = new OrthographicCamera(cameraViewportWidth, cameraViewportWidth*h/w);
		
		Box2DSceneLoaderParameters params = new Box2DSceneLoaderParameters();
		
		params.customPropertiesSerializer = new RubeCustomPropertySerializer();
		params.imageSerializer = new RubeImageSerializer();
		params.definitions = new B2DSProcessorsDefinition();
		params.definitions.addProcessor(B2DSByNameStore.class);
		params.definitions.addProcessor(SpriteProcessor.class);
		
		loader = new RubeSceneLoader(params);
	
		scene = loader.loadScene(Gdx.files.internal("data/images.json"));
	//	loader.loadScene(Gdx.files.internal("data/documentA2.json"));
		
		B2DSByNameStore store = scene.getProcessor(B2DSByNameStore.class);
		
		if(store != null)
		{
			Body body = store.get(Body.class, "body1");
		}
		
		render = scene.getProcessor(SpriteProcessor.class);
		
		renderer = new Box2DDebugRenderer();
		
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() 
	{
		scene.dispose();
		renderer.dispose();
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		scene.step();
		
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
