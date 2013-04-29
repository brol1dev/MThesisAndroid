package mx.cinvestav.cs.mthesis.android.layers;

import org.andengine.engine.camera.hud.HUD;

public abstract class Layer extends HUD {
	
	public Layer() {
		this.setBackgroundEnabled(false);
	}
	
	// Abstract methods
	public abstract void showLayer();
	public abstract void hideLayer();
}
