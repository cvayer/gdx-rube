gdx-rube
==========
This is a RUBE scene JSON loader and scene for libGdx.  It reads JSON data output by RUBE and creates and populates
a Box2D world with the bodies, joints, and fixtures defined therein.  It includes support for custom properties
and images.   

This repo contains a fully self-contained Libgdx test for reference.

Thanks to Tescott for the help !

About RUBE
==========
From https://www.iforce2d.net/rube/:

R.U.B.E stands for Really Useful Box2D Editor. This editor allows you to graphically manipulate 
a Box2D world and save it to a file. You can then load the saved file in your game/app and run the world.

General
=======
The loader consists of several serializers to read in objects from the RUBE JSON output:

	* Body
	* Fixture
	* Image
	* Joint
	* World
	* RubeWorld
	* Vector2
	
Creating a physics world populated with Box2D objects is really simple. You have two methods : 

The direct method : 

		RubeSceneReader loader = new RubeSceneReader();
		RubeScene scene = loader.readScene(Gdx.files.internal("data/yourscene.json"));

Using a libGdx Assetmanager :

		AssetManager assetManager = new AssetManager();
		assetManager.setLoader(RubeScene.class, new RubeSceneLoader(new InternalFileHandleResolver()));
		assetManager.load("data/yourscene.json", RubeScene.class);
			
Then when it's loaded, retrieve your scene like you do for any other Asset

		RubeScene scene = assetManager.get("data/yourscene.json", RubeScene.class);
		
Several scene objects are created by the readScene method.  These objects can be used for post-processing operations:

	* scene.world: This object is the Box2D physics world and is populated with the bodies, joints, and fixtures from the JSON file.
	* scene.getBodies(): This method returns an array of bodies created.
	* scene.getFixtures(): This method returns an array of fixtures created.
	* scene.getJoints(): This method returns an array of joints created.
	* scene.getImages(): This method returns an array of RubeImages defined in the JSON file.  Note: it is up to the app to perform all rendering.
	* scene.getProperty(Object object) : This method returns the RubeCustomProperty linked to the object.
	* scene.get(Class<T> type, String name) : This method returns the wanted object by name. type can be Body, Fixture, Joint or RubeImage.
	* scene.getImage(Body body) : This method returns an array of RubeImage linked to the body.
	
If the scene data is no longer needed, scene.clear() can be executed to free up any references.  Note that this does not alter or delete the world.  It is up
to the underlying application to handle body deletions from the Box2D physics world.

Image loading
=======

When using the AssetManager method to load a RubeScene, by default all images referenced by the RubeImages are added as scene dependencies and are loaded.

You can also use TextureAtlases if you prefer (recommanded solution) by adding a customProperty to your scene world named "atlas" ( and then "atlas1", "atlas2", etc).
That property must be a String and should contain the path to your atlas, relative to the json file.

If the system find a world property with that name, it will then load the atlas instead of all the seperate images, and strip the file field in the RubeImages, so that it can be used as a region id in the atlas.

If you don't want the textures to be loaded, or if you prefer to manage them your way, add a RubeSceneParameter when loading the scene, with the boolean "loadImages" set to false.

RubeLoaderTest
==============
This loads in a test file that includes custom property and image info.  Use the mouse to pan and zoom.  On Android touch the screen to pan.

The included rendering is for demo purposes only. 

The palm.json scene has examples of both kinds of images - ones referenced based on a particular body and others referenced to the world origin. 

It also contains a "TextureMask" property on some fixtures, to create RubePolygonSprites, that will fill the fixture. (see the renderer for examples)

You can load them both with or without the AssetManager (by selecting the right line in the pop up widows)

The palmAtlas.json is the same as the palm.json but with an atlas property on the world. (to test both image loading methods)

General Setup
-------------
1. Clone the repo to a local directory.
2. Open up Eclipse.  Set workspace to that local directory.
3. File > Import > General > Existing projects into workspace > Next > Browse > Ok > Select All > Finish

Android Setup
-------------
1. You may see an error if you don't have the same SDK installed.  No worries!  Right-click RubeLoaderTest-Android > Properties > Android.  Check installed SDK.  Click ok.
2. Right-click RubleLoaderTest-Android > Run As.. > Android Application
3. If you have an Android device connected to your machine, it should automatically install and launch.

Desktop Setup
-------------
1. The project should be auto built.  Right-click on RubeLoaderTest-desktop > Run As... > Java Application
2. Select "RubeLoaderTestDesktop". 


TODO List
=========
1. Add pinch-zooming for Android target
2. Generalize the fixture property use to load images for RubePolygonSprite.
