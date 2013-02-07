package com.mangecailloux.rube;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class RubeLoaderTestDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "RubeLoaderTest";
		cfg.useGL20 = true;
		cfg.width = 600;
		cfg.height = 480;
		
		new LwjglApplication(new RubeLoaderTest(), cfg);
	}
}
