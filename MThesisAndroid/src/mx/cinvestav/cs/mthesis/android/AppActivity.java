package mx.cinvestav.cs.mthesis.android;

import java.io.IOException;

import mx.cinvestav.cs.mthesis.android.factories.AndEngineUIObjectFactory;
import mx.cinvestav.cs.mthesis.android.net.KryoNetController;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.util.color.Color;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AppActivity extends LayoutGameActivity {

	private static final String LOG_TAG = "AppActivity";
	
//	private LayoutTransition rootTransition;

	private float cameraWidth, cameraHeight;
	
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		Log.d(LOG_TAG, "onCreate");
		super.onCreate(pSavedInstanceState);
		
//		RelativeLayout rootLayout = (RelativeLayout) 
//				findViewById(R.id.layout_canvas_root);
		
//		rootTransition = new LayoutTransition();
//		rootLayout.setLayoutTransition(rootTransition);
//		rootTransition.setDuration(300);
		
		Button btnToolsMenu = (Button) findViewById(R.id.btn_toggle_tools);
		btnToolsMenu.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				final RelativeLayout toolsLayout = (RelativeLayout) 
						findViewById(R.id.layout_tools);
				
				// Attributes for button
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) 
						v.getLayoutParams();
				
				// Toggle toolbar visibility and change button background
				if (toolsLayout.getVisibility() == View.GONE) {
					toolsLayout.setVisibility(View.VISIBLE);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_tools_right));
				} else if (toolsLayout.getVisibility() == View.VISIBLE) {
					toolsLayout.setVisibility(View.GONE);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_tools_left));
				}
				v.setLayoutParams(params);
			}
		});
		
		Button btnAddRectangle = (Button) findViewById(R.id.test_add_view);
		btnAddRectangle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (AndEngineManager.getInstance().getCurrentScene() != null) {
					Color rectColor = new Color(0, 1, 0, 1);
					Rectangle newRect = AndEngineUIObjectFactory.createRectangle(
							200, 100, rectColor);
					AndEngineManager.getInstance().getCurrentScene().
						attachChild(newRect);
					AndEngineManager.getInstance().getCurrentScene().
						registerTouchArea(newRect);
				}
			}
		});
		
		Button btnToggleGestures = (Button) findViewById(R.id.btn_toggle_gesture);
		btnToggleGestures.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (AndEngineManager.getInstance().isLayerShown()) {
					AndEngineManager.getInstance().hideLayer();
				} else {
					AndEngineManager.getInstance().showConnectionLayer();
				}
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					KryoNetController.getInstance().connect();
				} catch (IOException e) {
					KryoNetController.getInstance().disconnect();
					Log.w(LOG_TAG, getResources().getString(R.string.no_server));
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), 
									getResources().getString(R.string.no_server), 
									Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d(LOG_TAG, "onCreateEngineOptions");
		
		// Get display width and height, used for the camera object
		Display display = getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point point = new Point();
			display.getSize(point);
			cameraWidth = point.x;
			cameraHeight = point.y;
		} else {
			cameraWidth = display.getWidth();
			cameraHeight = display.getHeight();
		}
		Log.d(LOG_TAG, "display size x,y = " + cameraWidth + "," + cameraHeight);
		
		EngineOptions engineOpts = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, 
				new RatioResolutionPolicy(cameraWidth, cameraHeight), 
				new Camera(0, 0, cameraWidth, cameraHeight));
		
		engineOpts.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		
		return engineOpts;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback 
			onCreateResourcesCallback) throws Exception {
		Log.d(LOG_TAG, "onCreateResources");
		
		AndEngineManager.getInstance().setup(this.getEngine(), cameraWidth, cameraHeight, getApplicationContext());
		
		onCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) 
			throws Exception {
		Log.d(LOG_TAG, "onCreateScene");
		
		this.getEngine().registerUpdateHandler(new FPSLogger());
		
		Scene scene = new Scene();
		scene.setBackground(new Background(Color.WHITE));
		AndEngineManager.getInstance().setCurrentScene(scene);
		
		onCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback 
			onPopulateSceneCallback) throws Exception {
		Log.d(LOG_TAG, "onPopulateScene");
		
		onPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	protected int getLayoutID() {
		return R.layout.surface_layout;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.surface_view;
	}

}
