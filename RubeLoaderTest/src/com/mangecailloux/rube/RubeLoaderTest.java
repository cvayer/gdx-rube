package com.mangecailloux.rube;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.RubePolygonSprite;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.loader.RubeSceneLoader;


public class RubeLoaderTest implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	private RubeSceneLoader	loader;
	private RubeScene	scene;
	private Box2DDebugRenderer renderer;
	private SpriteRenderer		render;
	private SpriteBatch       	batch;
	private PolygonSpriteBatch 	polygonBatch;
	
	// used for pan and scanning with the mouse.
		private Vector3 mCamPos;
		private Vector3 mCurrentPos;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Gdx.input.setInputProcessor(this);
		
		mCamPos = new Vector3();
		mCurrentPos = new Vector3();
		
		float cameraViewportWidth = 50.0f;
		
		RubePolygonSprite.setPixelPerMeters(w/cameraViewportWidth);
		
		camera = new OrthographicCamera(cameraViewportWidth, cameraViewportWidth*h/w);
		
		
		loader = new RubeSceneLoader();
	
		scene = loader.loadScene(Gdx.files.internal("data/palm.json"));

		
		render = new SpriteRenderer();
		render.initFromScene(scene);
		
		renderer = new Box2DDebugRenderer();
		
		batch = new SpriteBatch();
		polygonBatch = new PolygonSpriteBatch();
	}

	@Override
	public void dispose() 
	{
		render.dispose();
		renderer.dispose();
		batch.dispose();
		polygonBatch.dispose();
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
		
		polygonBatch.setProjectionMatrix(camera.projection);
		polygonBatch.setTransformMatrix(camera.view);
		polygonBatch.begin();
		render.render(polygonBatch);
		polygonBatch.end();
		
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mCamPos.set(screenX,screenY,0);
		camera.unproject(mCamPos);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mCurrentPos.set(screenX,screenY,0);
		camera.unproject(mCurrentPos);
		camera.position.sub(mCurrentPos.sub(mCamPos));
		camera.update();
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		camera.zoom += (amount * 0.1f);
		if (camera.zoom < 0.1f)
		{
			camera.zoom = 0.1f;
		}
		camera.update();
		return true;
	}
}
