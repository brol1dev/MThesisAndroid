package mx.cinvestav.cs.mthesis.android.layers;

import mx.cinvestav.cs.mthesis.android.AndEngineManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.SurfaceGestureDetectorAdapter;

import android.content.Context;
import android.util.Log;

public class ConnectionLayer extends Layer {

	private static final String LOG_TAG = "ConnectionLayer";
	
	private ConnectionGestureDetector gestureDetector;
	
	public ConnectionLayer() {
		this.gestureDetector = new ConnectionGestureDetector(AndEngineManager.
				getInstance().getContext());
	}
	
	@Override
	public void showLayer() {
		Rectangle background = new Rectangle(0, 0, 
				AndEngineManager.getInstance().getDeviceWidth(), 
				AndEngineManager.getInstance().getDeviceHeight(), 
				AndEngineManager.getInstance().getEngine().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(TouchEvent touchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				return gestureDetector.onManagedTouchEvent(touchEvent);
			}
		};
		
		background.setColor(0f, 0f, 0f, 0.3f);
		this.attachChild(background);
		this.registerTouchArea(background);
		
		Log.d(LOG_TAG, "showing ConnectionLayer");
	}
	
	@Override
	public void hideLayer() {
		// TODO Auto-generated method stub
		
	}
	
	private class ConnectionGestureDetector extends 
		SurfaceGestureDetectorAdapter {
		
		private static final String LOG_TAG = "ConnectionLayer.ConnectionGestureDetector";
		
		public ConnectionGestureDetector(Context context) {
			super(context);
		}
		
		@Override
		protected boolean onSwipeUp() {
			Log.d(LOG_TAG, "onSwipeUp");
			return true;
		}
		
		@Override
		protected boolean onSwipeRight() {
			Log.d(LOG_TAG, "onSwipeRight");
			return true;
		}
		
		@Override
		protected boolean onSwipeDown() {
			Log.d(LOG_TAG, "onSwipeDown");
			return true;
		}
		
		@Override
		protected boolean onSwipeLeft() {
			Log.d(LOG_TAG, "onSwipeLeft");
			return true;
		}
	}
}
