package mx.cinvestav.cs.mthesis.android.factories;

import mx.cinvestav.cs.mthesis.android.AndEngineManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class AndEngineUIObjectFactory {

	public static Rectangle createRectangle(float width, float height,
			Color color) {
		Rectangle newRect = new Rectangle(
				AndEngineManager.getInstance().getDeviceWidth() / 2, 
				AndEngineManager.getInstance().getDeviceHeight() / 2, 
				width, height,
				AndEngineManager.getInstance().getEngine().getVertexBufferObjectManager()) {
			
			boolean grabbed = false;
			
			@Override
			public boolean onAreaTouched(TouchEvent event,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (event.getAction()) {
				case TouchEvent.ACTION_DOWN:
					grabbed = true;
					break;

				case TouchEvent.ACTION_MOVE:
					if (grabbed) {
						this.setPosition(event.getX() - this.getWidth() * 0.5f,
								event.getY() - this.getHeight() * 0.5f);
					}
					break;
					
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_CANCEL:
					if (grabbed) grabbed = false;
					break;
				}
				
				return true;
			}
		};
		
		newRect.setColor(color);
		
		return newRect;
	}
}
