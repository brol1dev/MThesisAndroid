package mx.cinvestav.cs.mthesis.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class ToolsLayout extends RelativeLayout {

	/** The radius of this layout */
	//private int radius;
	
	/** Style to draw view's background */
	private Paint bgPaint;
	
	public ToolsLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init();
		setWillNotDraw(false);
		Log.d(ToolsLayout.class.getSimpleName(), "initialized");
	}
	
	private void init() {
		bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bgPaint.setColor(Color.BLACK);
	}

//	@Override
//	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//		Log.d(ToolsLayout.class.getSimpleName(), "onLayout, child count: " + 
//				this.getChildCount());
//		
//		Button button = (Button) this.getChildAt(0);
//		button.setVisibility(View.VISIBLE);
//		button.layout(arg1, arg2, button.getMeasuredWidth(), button.getMeasuredHeight());
//		
////		super.onLayout(arg0, arg1, arg2, arg3, arg4);
//	}
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		
//		int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
//		int w = resolveSizeAndState(minW, widthMeasureSpec, 1);
//		
//		int minH = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
//		int h = resolveSizeAndState(minH, heightMeasureSpec, 0);
//		
//		Log.d(ToolsLayout.class.getSimpleName(), "measured size (wxh) = " + w + "x" + h);
//		setMeasuredDimension(w, h);
//	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(ToolsLayout.class.getSimpleName(), "drawing itself");
		super.onDraw(canvas);
		
		canvas.drawCircle(this.getMeasuredWidth(), this.getMeasuredHeight(), 
				Math.max(this.getMeasuredWidth(), this.getMeasuredHeight()), bgPaint);
	}

}
