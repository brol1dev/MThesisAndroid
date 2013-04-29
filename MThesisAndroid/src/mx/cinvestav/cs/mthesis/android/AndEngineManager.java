package mx.cinvestav.cs.mthesis.android;

import mx.cinvestav.cs.mthesis.android.layers.ConnectionLayer;
import mx.cinvestav.cs.mthesis.android.layers.Layer;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;

import android.content.Context;
import android.util.Log;

public class AndEngineManager {
	
	// CONSTANTS
	private static final String LOG_TAG = "AndEngineManager";

	private static final AndEngineManager INSTANCE = new AndEngineManager();
	
	// VARIABLES
	// Included to use in all project
	private Context context;
	private float deviceWidth;
	private float deviceHeight;
	private Engine engine;
	
	// Stores the scenes and layers currently shown in the app
	private Scene currentScene;
	private Layer currentLayer;
	private Scene placeholderModalScene;
	private boolean layerShown = false; 
	
	// Make constructors private
	private AndEngineManager() { }
	
	// Get the singleton instance
	public static AndEngineManager getInstance() {
		return INSTANCE;
	}
	
	// Must be called before using this instance
	public void setup(Engine engine, float cameraWidth, float cameraHeight, Context context) {
		this.engine = engine;
		this.deviceWidth = cameraWidth;
		this.deviceHeight = cameraHeight;
		this.context = context;
		
		Log.d(LOG_TAG, "Setup the camera and context");
	}
	
	public void showConnectionLayer() {
		showLayer(new ConnectionLayer(), false, false, true);
	}
	
	// Shows a layer as a child of the camera's HUD
	public void showLayer(final Layer layer, final boolean suspendSceneDrawing,
			final boolean suspendSceneUpdates, final boolean suspendSceneTouchEvents) {
		
		HUD cameraHud = new HUD();
		this.engine.getCamera().setHUD(cameraHud);
		
		if (suspendSceneDrawing || suspendSceneUpdates || suspendSceneTouchEvents) {
			this.engine.getCamera().getHUD().setChildScene(layer, suspendSceneDrawing, 
					suspendSceneUpdates, suspendSceneTouchEvents);
			
			if (this.placeholderModalScene == null) {
				this.placeholderModalScene = new Scene();
				this.placeholderModalScene.setBackgroundEnabled(false);
			}
			
			this.currentScene.setChildScene(this.placeholderModalScene, 
					suspendSceneDrawing, suspendSceneUpdates, suspendSceneTouchEvents);
		} else {
			this.engine.getCamera().getHUD().setChildScene(layer);
		}
		
		layer.setCamera(this.engine.getCamera());
		layer.showLayer();
		this.layerShown = true;
		this.currentLayer = layer;
	}
	
	// Hides the layer shown in the camera
	public void hideLayer() {
		if (layerShown) {
			this.engine.getCamera().getHUD().clearChildScene();
			
			if (this.currentScene.hasChildScene() && this.currentScene.
					getChildScene() == this.placeholderModalScene)
				this.currentScene.clearChildScene();
			
			this.engine.getCamera().setHUD(null);
			this.layerShown = false;
			this.currentLayer = null;
		}
	}
	
	// Getters & setters
	public Scene getCurrentScene() {
		Log.d(LOG_TAG, "Obtaining the current scene");
		return currentScene;
	}

	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}

	public Context getContext() {
		return context;
	}

	public float getDeviceWidth() {
		return deviceWidth;
	}

	public void setDeviceWidth(float deviceWidth) {
		this.deviceWidth = deviceWidth;
	}

	public float getDeviceHeight() {
		return deviceHeight;
	}

	public void setDeviceHeight(float deviceHeight) {
		this.deviceHeight = deviceHeight;
	}

	public Engine getEngine() {
		return engine;
	}

	public boolean isLayerShown() {
		return layerShown;
	}

	public void setLayerShown(boolean layerShown) {
		this.layerShown = layerShown;
	}
	
	public Layer getCurrentLayer() {
		return currentLayer;
	}
}
