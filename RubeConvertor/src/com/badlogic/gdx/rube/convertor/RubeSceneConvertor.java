package com.badlogic.gdx.rube.convertor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.rube.convertor.description.RubeSceneDescription;
import com.badlogic.gdx.rube.convertor.serializer.RubeSceneDescriptionSerializer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.SerializationException;

public class RubeSceneConvertor 
{
	private Settings settings;
	private Json 	 json;
	
	public RubeSceneConvertor()
	{
		this(new Settings());
	}
	
	public RubeSceneConvertor(Settings _settings)
	{
		settings = _settings;
		json = new Json(OutputType.minimal);
		
		json.setSerializer(RubeSceneDescription.class, new RubeSceneDescriptionSerializer(json));
	}
	
	public void process(String _input, String _output)
	{
		process(new File(_input), new File(_output));
	}
	
	public void process(File _input, File _output)
	{
		try 
		{
			RubeSceneDescription desc = json.fromJson( RubeSceneDescription.class, new FileReader(_input));
			
			json.toJson(desc, RubeSceneDescription.class, new FileWriter(_output));
			
		} catch (IOException e) {
			throw new SerializationException(e);
		}
		
	}
	
	public void process(FileHandle _input, FileHandle _output)
	{
		RubeSceneDescription desc = json.fromJson( RubeSceneDescription.class, _input);
		
		json.toJson(desc, RubeSceneDescription.class, _output);
	}
	
	public static class Settings
	{
		public Settings()
		{
			
		}
	}
}
