package com.badlogic.gdx.rube.reader.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.rube.RubeCustomProperty;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.rube.reader.serializer.utils.RubeVertexArray;

/**
 * Serializer to read a {@link Fixture} from a RUBE .json file.
 * @author clement.vayer
 */
@SuppressWarnings("rawtypes")
class RubeFixtureSerializer extends RubeSerializer<Fixture>
{
	private Body body;
	private final ChainShapeSerializer 	 chainShapeSerializer;
	
	public RubeFixtureSerializer(Json _json)
	{
		super();
		
		chainShapeSerializer	= new ChainShapeSerializer();
		
		_json.setSerializer(PolygonShape.class, new PolygonShapeSerializer());
		_json.setSerializer(EdgeShape.class, new EdgeShapeSerializer());
		_json.setSerializer(CircleShape.class, new CircleShapeSerializer());
		_json.setSerializer(ChainShape.class, chainShapeSerializer);
	}
	
	void setBody(Body _body)
	{
		body = _body;
	}
	
	@Override
	public Fixture read(Json json, JsonValue jsonData, Class type) 
	{
		if(body == null)
			return null;
		
		FixtureDef defaults = RubeDefaults.Fixture.definition;
		
		FixtureDef def = new FixtureDef();
		
		def.friction = json.readValue("friction", float.class, defaults.friction, jsonData);
		def.density = json.readValue("density", float.class, defaults.density, jsonData);
		def.restitution = json.readValue("restitution", float.class, defaults.restitution, jsonData);
		def.isSensor = json.readValue("sensor", boolean.class, defaults.isSensor, jsonData);
		
		def.filter.maskBits = json.readValue("filter-maskBits", short.class, defaults.filter.maskBits, jsonData);
		def.filter.categoryBits = json.readValue("filter-categoryBits", short.class, defaults.filter.categoryBits, jsonData);
		def.filter.groupIndex = json.readValue("filter-groupIndex", short.class, defaults.filter.groupIndex, jsonData);
		
		CircleShape circle = json.readValue("circle", CircleShape.class, jsonData);
		
		if(circle != null)
		{
			def.shape = circle;
		}
		else
		{
			EdgeShape edge = json.readValue("edge", EdgeShape.class, jsonData);
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
				else
				{
					chainShapeSerializer.setReadLoop(true);
					chain = json.readValue("loop", ChainShape.class, jsonData);
					if(chain != null)
					{
						def.shape = chain;
					}
					else
					{
						PolygonShape polygon = json.readValue("polygon", PolygonShape.class, jsonData);
						
						if(polygon != null)
						{
							def.shape = polygon;
						}
						else
						{
							edge = json.readValue("polygon", EdgeShape.class, jsonData);
							if(edge != null)
							{
								def.shape = edge;
							}
						}
					}
				}
			}
		}
		
		Fixture fixture = body.createFixture(def);
		def.shape.dispose();
		String name = json.readValue("name", String.class, jsonData);
		
		RubeCustomProperty customProperty = null;
		if(json.getSerializer(RubeCustomProperty.class) != null)
			customProperty = json.readValue("customProperties", RubeCustomProperty.class, jsonData);
		
		scene.onAddFixture(fixture, name, customProperty);
		return fixture;
	}
	
	static class CircleShapeSerializer extends ReadOnlySerializer<CircleShape>
	{	
		@Override
		public CircleShape read(Json json, JsonValue jsonData, Class type)
		{			
			CircleShape shape = null;

			Vector2 position = json.readValue("center", Vector2.class, jsonData);
			float	radius	 = json.readValue("radius", float.class, jsonData);
			
			if(position != null)
			{
				shape = new CircleShape();
				shape.setRadius(radius);
				shape.setPosition(position);
			}
			
			return shape; 
		}
	}
	
	static class PolygonShapeSerializer extends ReadOnlySerializer<PolygonShape>
	{	
		@Override
		public PolygonShape read(Json json, JsonValue jsonData, Class type)
		{
			RubeVertexArray vertices = json.readValue("vertices", RubeVertexArray.class, jsonData);
			
			// We return null for too small or too big polygons
			if(vertices.x.length <= 2 || vertices.x.length > 8)
				return null;
			
			PolygonShape shape = new PolygonShape();
			shape.set(vertices.toVector2());
			return shape; 
		}
	}
	
	static class EdgeShapeSerializer extends ReadOnlySerializer<EdgeShape>
	{		
		@Override
		public EdgeShape read(Json json, JsonValue jsonData, Class type)
		{
			EdgeShape shape = null;
			
			Vector2 vertex1 = json.readValue("vertex1", Vector2.class, jsonData);
			Vector2 vertex2 = json.readValue("vertex2", Vector2.class, jsonData);
			
			if(vertex1 != null && vertex2 != null)
			{
				shape = new EdgeShape();
				shape.set(vertex1, vertex2);
			}
			else
			{
				// If the vertices don't exist maybe we try to read a two vertices-polygon
				RubeVertexArray vertices = json.readValue("vertices", RubeVertexArray.class, jsonData);
				if(vertices != null)
				{
					if(vertices.x.length != 2)
						return null;
					
					shape = new EdgeShape();
					shape.set(vertices.x[0], vertices.y[0],vertices.x[1], vertices.y[1]);
				}	
			}
			
			return shape; 
		}
	}
	
	static class ChainShapeSerializer extends ReadOnlySerializer<ChainShape>
	{		
		private boolean readloop;
		
		public void setReadLoop(boolean _readloop)
		{
			readloop = _readloop;
		}
		
		@Override
		public ChainShape read(Json json, JsonValue jsonData, Class type)
		{
			ChainShape chain = null;
			RubeVertexArray vertices = json.readValue("vertices", RubeVertexArray.class, jsonData);
			if(vertices != null)
			{
				chain = new ChainShape();
				if(!readloop)
				{
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
					chain.createLoop(vertices.toVector2());
				}
			}
			return chain; 
		}
	}
}
