package com.mangecailloux.rube.serializers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.mangecailloux.rube.RubeDefaults;
import com.mangecailloux.rube.utils.VerticeArray;

public class FixtureSerializer extends ReadOnlySerializer<Fixture>
{
	private Body body;
//	private final FixtureDef def = new FixtureDef();
	private final PolygonShapeSerializer polygonShapeSerializer = new PolygonShapeSerializer();
	private final EdgeShapeSerializer	 edgeShapeSerializer = new EdgeShapeSerializer();
	private final ChainShapeSerializer 	 chainShapeSerializer	= new ChainShapeSerializer();
	
	void setBody(Body _body)
	{
		body = _body;
	}
	
	@Override
	public Fixture read(Json json, Object jsonData, Class type) 
	{
		if(body == null)
			return null;
			
		json.setIgnoreUnknownFields(true);
		
		FixtureDef def = new FixtureDef();
		json.readFields(def, jsonData);
		
		json.setSerializer(PolygonShape.class, polygonShapeSerializer);
		json.setSerializer(EdgeShape.class, edgeShapeSerializer);
		json.setSerializer(ChainShape.class, chainShapeSerializer);
		
		PolygonShape polygon = json.readValue("polygon", PolygonShape.class, jsonData);
		
		if(polygon != null)
		{
			def.shape = polygon;
		}
		else
		{
			EdgeShape edge = json.readValue("polygon", EdgeShape.class, jsonData);
			if(edge != null)
			{
				def.shape = edge;
			}
			else
			{
				chainShapeSerializer.setReadLoop(false);
				ChainShape chain = json.readValue("chain", ChainShape.class, jsonData);
				if(chain != null)
				{
					def.shape = chain;
				}
			}
		}
		
		Fixture fixture = body.createFixture(def);
		return fixture;
	}
	
	public static class PolygonShapeSerializer extends ReadOnlySerializer<PolygonShape>
	{
		// We need to create a VerticeArray or else we got a ClassNotFoundException WTF ?
		VerticeArray v = new VerticeArray();
		
		@Override
		public PolygonShape read(Json json, Object jsonData, Class type)
		{
			VerticeArray vertices = json.readValue("vertices", VerticeArray.class, jsonData);
			
			// We return null for too small or too big polygons
			if(vertices.x.length <= 2 || vertices.x.length > 8)
				return null;
			
			PolygonShape shape = new PolygonShape();
			shape.set(vertices.toVector2());

			return shape; 
		}
	}
	
	public static class EdgeShapeSerializer extends ReadOnlySerializer<EdgeShape>
	{
		// We need to create a VerticeArray or else we got a ClassNotFoundException WTF ?
		VerticeArray v = new VerticeArray();
		
		@Override
		public EdgeShape read(Json json, Object jsonData, Class type)
		{
			VerticeArray vertices = json.readValue("vertices", VerticeArray.class, jsonData);
			
			EdgeShape shape = null;
			if(vertices != null)
			{
				// polygon with two vertices case
				// We return null for too small or too big polygons
				if(vertices.x.length != 2)
					return null;
				
				shape = new EdgeShape();
				shape.set(vertices.x[0], vertices.y[0],vertices.x[1], vertices.y[1]);
			}	
			else
			{
				//TODO regular case
			}

			return shape; 
		}
	}
	
	public static class ChainShapeSerializer extends ReadOnlySerializer<ChainShape>
	{
		// We need to create a VerticeArray or else we got a ClassNotFoundException WTF ?
		VerticeArray v = new VerticeArray();
				
		private boolean readloop;
		
		public void setReadLoop(boolean _readloop)
		{
			readloop = _readloop;
		}
		
		@Override
		public ChainShape read(Json json, Object jsonData, Class type)
		{
			ChainShape chain = null;
			if(!readloop)
			{
				VerticeArray vertices = json.readValue("vertices", VerticeArray.class, jsonData);
				
				chain = new ChainShape();
				
				chain.createChain(vertices.toVector2());
				
				boolean hasPrevVertex = json.readValue("hasPrevVertex", boolean.class, false, jsonData);
				boolean hasNextVertex = json.readValue("hasNextVertex", boolean.class, false, jsonData);
				
				if(hasPrevVertex)
				{
					Vector2 prev = json.readValue("prevVertex", Vector2.class, jsonData);
					chain.setPrevVertex(prev);
				}
				
				if(hasNextVertex)
				{
					Vector2 next = json.readValue("nextVertex", Vector2.class, jsonData);
					chain.setNextVertex(next);
				}
			}
			else
			{
				
			}
			return chain; 
		}
	}
}
