package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.utils.Array;

public class B2DSProcessorsDefinition
{
	protected final Array<Class<? extends B2DSProcessor>> definitions;
	
	public B2DSProcessorsDefinition()
	{
		definitions = new Array<Class<? extends B2DSProcessor>>(false, 2);
	}
	
	public <T extends B2DSProcessor> void addProcessor(Class<T> _type)
	{
		definitions.add(_type);
	}
	
	public int getProcessorsCount()
	{
		return definitions.size;
	}
	
	public B2DSProcessor createProcessors(int _index)
	{
		if(_index < definitions.size)
		{
			try 
			{
				return definitions.get(_index).newInstance();		
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

