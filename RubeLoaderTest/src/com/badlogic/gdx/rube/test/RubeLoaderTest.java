package com.badlogic.gdx.rube.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.RubeSceneLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.graphics.g2d.RubePolygonSprite;
import com.badlogic.gdx.rube.reader.RubeSceneReader;


public class RubeLoaderTest implements ApplicationListener, InputProcessor {
	private OrthographicCamera 		camera;

	private Box2DDebugRenderer renderer;

	private SpriteBatch       	batch;
	private PolygonSpriteBatch 	polygonBatch;
	
	// used for pan and scanning with the mouse.
	private final Vector3 	mCamPos;
	private final Vector3 	mCurrentPos;
	private boolean			useAssetManager;
	private boolean			loaded;
	private AssetManager 	assetManager;
	private String 			scenefileName;
	
	// Reader to load a scene from a file
	private RubeSceneReader		reader;
	// Your scene
	private RubeScene			scene;
	// A simple custom renderer
	private SpriteRenderer		render;
	
	
	public RubeLoaderTest(boolean useAssetManager, String sceneToLoad)
	{
		mCamPos = new Vector3();
		mCurrentPos = new Vector3();
		
		this.useAssetManager = useAssetManager;
		scenefileName = "data/" + sceneToLoad;
	}

	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Gdx.input.setInputProcessor(this);
		
		renderer 		= new Box2DDebugRenderer();
		batch 			= new SpriteBatch();
		polygonBatch 	= new PolygonSpriteBatch();
		
		// This is a custom renderer. It doesn't do much, just create sprites based on the scene, and then render them sequentially.
		render 			= new SpriteRenderer();
		
		float cameraViewportWidth = 50.0f;
		camera = new OrthographicCamera(cameraViewportWidth, cameraViewportWidth*h/w);
		
		// Used when creating RubePolygonSprites, will affect the Uvs of the texture inside the shape
		RubePolygonSprite.setPixelPerMeters(w/cameraViewportWidth);
		
		
		if(!useAssetManager)
		{
			// No AssetManager method
			// 1. Create a reader
			reader = new RubeSceneReader();
			// 2. Read your scene
			scene = reader.readScene(Gdx.files.internal(scenefileName));
			// 3. (Optional) Post process you scene the way you want it, to organise or retrieve informations the way you see fit
			render.initFromScene(scene, null);
		}
		else
		{
			// AssetManager method
			assetManager = new AssetManager();
			// 1. Assign a loader to the RubeScene class, using the FileResolver of your choice.
			assetManager.setLoader(RubeScene.class, new RubeSceneLoader(new InternalFileHandleResolver()));
			
			// 2. Load you scene like any other asset
			assetManager.load(scenefileName, RubeScene.class);
			// 3. see the render method
			
			loaded = false;
		}		
	}

	@Override
	public void dispose() 
	{
		// Don't forget to dispose your Box2D world
		scene.world.dispose();
		// clear() will clear all arrays and ObjectMaps used to store scene objects, but will not dispose the world
		scene.clear();
		
		render.dispose();
		renderer.dispose();
		batch.dispose();
		polygonBatch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(useAssetManager)
		{
			if(!loaded)
			{
				if(assetManager.update())
				{
					loaded = true;
					// AssetManager method
					// 3. When the assetManager has finished loading the assets you can use your scene
					scene = assetManager.get(scenefileName, RubeScene.class);
					
					// 4. (Optional) Post process you scene the way you want it, to organise or retrieve informations the way you see fit
					render.initFromScene(scene, assetManager);
				}
				return;
			}
		}
		
		// Run the world simulation using the data from the scene.
		// Warning : It's just an example, in a real case, you will need to fix your simulation delta time, to avoid having the simulation
		// having different speed depending on the device.
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
