package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.rube.RubeScene;
import com.badlogic.gdx.rube.reader.RubeSceneReader;
import com.badlogic.gdx.rube.reader.serializer.RubeCustomPropertySerializer;
import com.badlogic.gdx.rube.reader.serializer.RubeSceneSerializer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;


public class RubeSceneLoader extends SynchronousAssetLoader<RubeScene, RubeSceneLoader.RubeSceneParameter> {
	
	private final RubeSceneReader reader;
	private final RubeSceneDependenciesReader dependenciesReader;
	
	public RubeSceneLoader (FileHandleResolver resolver) {
		super(resolver);
		
		reader = new RubeSceneReader();
		dependenciesReader = new RubeSceneDependenciesReader();
	}


	@Override
	public RubeScene load (AssetManager assetManager, String fileName, RubeSceneParameter parameter) {

		FileHandle sceneFile = resolve(fileName);	
		
		return reader.readScene(sceneFile);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, RubeSceneParameter parameter) {

		FileHandle sceneFile = resolve(fileName);	
		
		RubeSceneDependencies dependencies = dependenciesReader.readDependencies(sceneFile.parent().path(), sceneFile);

		return dependencies.getDependencies();
	}

	static public class RubeSceneParameter extends AssetLoaderParameters<RubeScene> {
		public RubeSceneParameter () {
		}
	}
	
	static class RubeSceneDependencies
	{
		Array<AssetDescriptor> dependencies;
		
		RubeSceneDependencies()
		{
			dependencies = new Array<AssetDescriptor>();
		}
		
		Array<AssetDescriptor> getDependencies() {
			return dependencies;
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
				serializer.basePath = basePath;
				dependencies = json.fromJson(RubeSceneDependencies.class, file);	
			} 
			catch (SerializationException ex) 
			{
				throw new SerializationException("Error reading file: " + file, ex);
			}
			return dependencies;
		}
		
		static public class RubeSceneDependenciesSerializer extends ReadOnlySerializer<RubeSceneDependencies>
		{
			public String basePath;
			
			
			@SuppressWarnings("rawtypes")
			@Override
			public RubeSceneDependencies read(Json json, Object jsonData,	Class type) {
				
				RubeSceneDependencies rubeDependencies = new RubeSceneDependencies();
				
				Array<AssetDescriptor> dependencies = rubeDependencies.getDependencies();
				
		//		RubeCustomProperty customProperty = json.readValue("customProperties", RubeCustomProperty.class, jsonData);
				
				Array<ImageInfo> infos = json.readValue("image", Array.class, ImageInfo.class, jsonData);
				
				if(infos != null)
				{
					for(int i=0; i < infos.size; ++i)
					{
						ImageInfo info = infos.get(i);
						
						TextureParameter parameter = new TextureParameter();
						
						if(info.filter == 0)
						{
							parameter.minFilter = TextureFilter.Nearest;
							parameter.magFilter = TextureFilter.Nearest;
						}
						else
						{
							parameter.minFilter = TextureFilter.Linear;
							parameter.magFilter = TextureFilter.Linear;
						}
						
						AssetDescriptor<Texture> desc = new AssetDescriptor<Texture>(basePath + "/" + info.file, Texture.class, parameter );
						
						dependencies.add(desc);
					}
				}
				
				return rubeDependencies;
			}
			
			static class ImageInfo
			{
				public String file;
				public int 	  filter;
			}
		}
	}

}
