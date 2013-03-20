package com.mangecailloux.rube;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.rube.test.RubeLoaderTest;

public class RubeLoaderTestDesktop {
static final String GAME_NAME = "RubeLoaderTest";
	
	/**
	 * @param args
	 */
	public static void mainLaunch(int width, int height, boolean useAssetManager, String sceneToLoad)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = GAME_NAME;
		cfg.useGL20 = true;
		cfg.width = width;
		cfg.height = height;
		new LwjglApplication(new RubeLoaderTest(useAssetManager, sceneToLoad), cfg);
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
	         public void run()
	         {
	            // -------------------------------------------------------------
	            // Display mode selection
	            // -------------------------------------------------------------
	            String[] modes = { "portrait", "landscape" };
	            JComboBox modeVals = new JComboBox(modes);
	            modeVals.setSelectedItem(modes[1]); // default to landscape
	            
	            // -------------------------------------------------------------
	            // AssetManager Mode
	            // -------------------------------------------------------------
	            String[] asset = { "Use", "Do not use" };
	            JComboBox assetVals = new JComboBox(asset);
	            assetVals.setSelectedItem(asset[0]); // default to  do not use
	            
	            // -------------------------------------------------------------
	            // scene Mode
	            // -------------------------------------------------------------
	            String[] scene = { "palm.json", "palmAtlas.json" };
	            JComboBox sceneVals = new JComboBox(scene);
	            assetVals.setSelectedItem(scene[0]); // default to  do not use

	            // -------------------------------------------------------------
	            // Resolution selection
	            // -------------------------------------------------------------
	            String[] resolutions =
	            {
	                  "iPhone (320x480)",
	                  "iPhone 4 (640x960)",
	                  "iPhone 5 (640x1136)",
	                  "iPad (1536x2048)",
	                  "iPad (768x1024)",
	                  "WVGA800 (480x800)",
	                  "WVGA854 (480x854)",
	                  "Galaxy Tab (600x1024)",
	                  "Nook Color (565x1024)",
	                  "Nexus 7 (800x1280)" };
	            JComboBox resVals = new JComboBox(resolutions);
	            resVals.setSelectedItem(resolutions[5]);
	            
	            
	            final JComponent[] inputs = new JComponent[] {
	                  new JLabel("Select simulated resolution"),
	                  resVals,
	                  new JLabel("Select simulated orientation"),
	                  modeVals,
	                  new JLabel("Use AssetManager"),
	                  assetVals,
	                  new JLabel("Scene to load"),
	                  sceneVals,
	            };
	            int result = JOptionPane.showConfirmDialog(null, inputs,"Sim Options",JOptionPane.OK_CANCEL_OPTION);
	            System.out.println("User Selected: " + resVals.getSelectedItem() + " " + modeVals.getSelectedItem() + " " + result);
	            
	            String resolutionResult;

	            // -------------------------------------------------------------
	            // App launch
	            // -------------------------------------------------------------

	            boolean isPortrait = modeVals.getSelectedItem().equals("portrait");
	            resolutionResult = (String)resVals.getSelectedItem();
	            
	            boolean useAssetManager = assetVals.getSelectedItem().equals("Use");
	            
	            String sceneToLoad = sceneVals.getSelectedItem().toString();

	            if (result == 0)
	            {
	               if (resolutionResult != null && resolutionResult.length() > 0)
	               {
	                  Matcher m = Pattern.compile("(\\d+)x(\\d+)").matcher(resolutionResult);
	                  m.find();
	                  int w = Integer.parseInt(m.group(isPortrait ? 1 : 2));
	                  int h = Integer.parseInt(m.group(isPortrait ? 2 : 1));
	                  mainLaunch(w, h, useAssetManager, sceneToLoad);
	               }
	            }
	         }
	      });
	}
}
