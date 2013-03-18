package com.mangecailloux.rube;

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
	private SpriteRenderer		render;
	private SpriteBatch       	batch;
	private PolygonSpriteBatch 	polygonBatch;
	
	// used for pan and scanning with the mouse.
	private final Vector3 mCamPos;
	private final Vector3 mCurrentPos;
	
	private RubeSceneReader	loader;
	private RubeScene	scene;
	
	private boolean		useAssetManager;
	private boolean		loaded;
	private AssetManager assetManager;
	private String 		scenefileName;
	
	
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
		
		// Used when creating RubePolygonSprites, 
		RubePolygonSprite.setPixelPerMeters(w/cameraViewportWidth);
		
		
		if(!useAssetManager)
		{
			loader = new RubeSceneReader();
			scene = loader.readScene(Gdx.files.internal(scenefileName));
			render.initFromScene(scene, null);
		}
		else
		{
			assetManager = new AssetManager();
			assetManager.setLoader(RubeScene.class, new RubeSceneLoader(new InternalFileHandleResolver()));
			
			assetManager.load(scenefileName, RubeScene.class);
			
			loaded = false;
		}		
	}

	@Override
	public void dispose() 
	{
		scene.world.dispose();
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
					// Init renderer
					
					scene = assetManager.get(scenefileName, RubeScene.class);
					render.initFromScene(scene, assetManager);
				}
				return;
			}
		}
		

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
