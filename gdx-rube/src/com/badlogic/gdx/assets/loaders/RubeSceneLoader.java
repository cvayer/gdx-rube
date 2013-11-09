package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.reader.RubeSceneReader;
import com.badlogic.gdx.rube.reader.serializer.RubeCustomPropertySerializer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

/**
 * <p> Loader of {@link RubeScene} for the {@link AssetManager}. </p>
 * <p> By default will try to load the images as dependencies. <br/>
 * If there is a world property with the name "atlas" ( and then "atlas1", "atlas2", etc ) the atlases referenced by the properties will be added to the dependencies. <br/>
 * Else each textures referenced by the RubeImages will be added as dependencies.
 * </p>
 * @author clement.vayer
 */
public class RubeSceneLoader extends SynchronousAssetLoader<RubeScene, RubeSceneLoader.RubeSceneParameter> {
	
	private final RubeSceneReader reader;
	private final RubeSceneDependenciesReader dependenciesReader;
	private 	  RubeSceneDependencies 	  rubeDependencies;
	
	public RubeSceneLoader (FileHandleResolver resolver) {
		super(resolver);
		reader = new RubeSceneReader();
		dependenciesReader = new RubeSceneDependenciesReader();
	}
	
	@Override
	public RubeScene load(AssetManager assetManager, String fileName, FileHandle file, RubeSceneParameter parameter) {
		
		boolean stripImageFile = false;
		
		if(rubeDependencies != null)
		{
			stripImageFile = rubeDependencies.useAtlas;
		}
		
		return  reader.readScene(file, stripImageFile);
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, RubeSceneParameter parameter) {
		
		Array<AssetDescriptor> dependencies = null;
		// We add the images as dependencies, except if there is a parameter and the loadImages boolean is set to false.
		if(parameter == null || parameter.loadImages)
		{
			rubeDependencies = dependenciesReader.readDependencies(file.parent().path(), file);
			dependencies = rubeDependencies.dependencies;
		}
		else
		{
			rubeDependencies = null;
		}

		return dependencies;
	}

	/**
	 * Parameters for the {@link RubeSceneLoader}
	 * @author clement.vayer
	 */
	static public class RubeSceneParameter extends AssetLoaderParameters<RubeScene> {
		
		/** set it to false if you want to handle the image loading of the scene outside of the RubeSceneLoader  */
		public boolean loadImages = true;
		
		/**
		 * 
		 * @param loadImages Set it to false, if you want to handle the loading of the textures outside of the RubeSceneLoader.
		 */
		public RubeSceneParameter (boolean loadImages) {
			this.loadImages = loadImages;
		}
	}
	
	/**
	 * Wrapper class for RubeScene dependencies.
	 */
	@SuppressWarnings("rawtypes")
	static class RubeSceneDependencies
	{
		public final Array<AssetDescriptor> dependencies;
		public		 boolean				useAtlas;
		
		RubeSceneDependencies()
		{
			dependencies = new Array<AssetDescriptor>();
			useAtlas = false;
		}
	}
	
	static class RubeSceneDependenciesReader
	{
		/**Used to parse the Json files*/
		private final Json json;
		private final RubeSceneDependenciesSerializer serializer;
		
		public RubeSceneDependenciesReader() {
			json = new Json();
			json.setTypeName(null);
			json.setUsePrototypes(false);
			json.setIgnoreUnknownFields(true);
			serializer = new RubeSceneDependenciesSerializer();
			json.setSerializer(RubeCustomProperty.class, new RubeCustomPropertySerializer());
			json.setSerializer(RubeSceneDependencies.class, serializer);
		}
		
		/**
		 * 
		 * @param _file File to read.
		 * @return the scene described in the document.
		 */
		public RubeSceneDependencies readDependencies(String basePath, FileHandle file) {
			RubeSceneDependencies dependencies = null;
			try 
			{
				serializer.basePath = basePath + "/";
				dependencies = json.fromJson(RubeSceneDependencies.class, file);	
			} 
			catch (SerializationException ex) 
			{
				throw new SerializationException("Error reading file: " + file, ex);
			}
			return dependencies;
		}
		
		static class RubeSceneDependenciesSerializer extends ReadOnlySerializer<RubeSceneDependencies>
		{
			public String basePath;
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public RubeSceneDependencies read(Json json, JsonValue jsonData,	Class type) {
				
				RubeSceneDependencies rubeDependencies = new RubeSceneDependencies();
				
				Array<AssetDescriptor> dependencies = rubeDependencies.dependencies;
				
				RubeCustomProperty customProperty = json.readValue("customProperties", RubeCustomProperty.class, jsonData);
				
				// Test if we want atlas mode
				
				rubeDependencies.useAtlas = false;
				if(customProperty != null)
				{
					String atlas = customProperty.getString(RubeScene.atlasPropertyName, null);
					
					if(atlas != null)
					{
						AssetDescriptor<TextureAtlas> atlasDesc = new AssetDescriptor<TextureAtlas>(basePath +  atlas, TextureAtlas.class);
						dependencies.add(atlasDesc);
						rubeDependencies.useAtlas  = true;
						
						int i = 1;
						do
						{
							atlas = customProperty.getString(RubeScene.atlasPropertyName + i, null);
							++i;
							if(atlas != null)
							{
								String path = basePath +  atlas;
								if(!doesDependencyExists(dependencies, path, TextureAtlas.class))
								{
									atlasDesc = new AssetDescriptor<TextureAtlas>(basePath +  atlas, TextureAtlas.class);
									dependencies.add(atlasDesc);
								}
							}
						}
						while(atlas != null);
					}	
				}
				
				
				if(!rubeDependencies.useAtlas ) // Regular texture dependencies
				{
					Array<ImageInfo> infos = json.readValue("image", Array.class, ImageInfo.class, jsonData);
					
					if(infos != null)
					{
						for(int i=0; i < infos.size; ++i)
						{
							ImageInfo info = infos.get(i);
							
							String path = basePath + info.file;
							if(!doesDependencyExists(dependencies, path, Texture.class))
							{
								TextureParameter parameter = new TextureParameter();
								if(info.filter == 1)
								{
									parameter.minFilter = TextureFilter.Linear;
									parameter.magFilter = TextureFilter.Linear;
								}
								
								AssetDescriptor<Texture> desc = new AssetDescriptor<Texture>(path, Texture.class, parameter );
								dependencies.add(desc);
							}
						}
					}
				}
			
				return rubeDependencies;
			}
			
			@SuppressWarnings("rawtypes")
			boolean doesDependencyExists(Array<AssetDescriptor> dependencies, String path, Class<?> type)
			{
				for(int i = 0; i < dependencies.size; ++i)
				{
					if(dependencies.get(i).fileName.equals(path) && dependencies.get(i).type.equals(type))
						return true;
				}
				return false;
			}
			
			static class ImageInfo
			{
				public String file;
				public int 	  filter;
			}

		}
	}
}
