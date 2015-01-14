package com.ganesh.picmessage;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyTouchListener implements OnTouchListener{
	


	public Matrix matrix = new Matrix();
	    private Matrix savedMatrix = new Matrix();
	    // we can be in one of these 3 states
	    private static final int NONE = 0;
	    private static final int DRAG = 1;
	    private static final int ZOOM = 2;
	    private int mode = NONE;
	    // remember some things for zooming
	    private PointF start = new PointF();
	    private PointF mid = new PointF();
	    
	    private float oldDist = 1f;
	    private float d = 0f;
	    private float newRot = 0f;
	    private float[] lastEvent = null;
	
	

	float prev_angle=0,current_angle=0;
	
	
	

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private void animate(double fromDegrees, double toDegrees, long durationMillis,ImageView wheel) {
     
	    	final RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
	    	RotateAnimation.RELATIVE_TO_SELF, 0.5f,
	    	RotateAnimation.RELATIVE_TO_SELF, 0.5f);
	    	rotate.setDuration(durationMillis);
	    	rotate.setFillEnabled(true);
	    	rotate.setFillAfter(true);
	    	wheel.startAnimation(rotate);
	    	Log.e("current_angle", ""+current_angle);
	    	 savedMatrix.set(wheel.getMatrix());
	    	// matrix.set(wheel.getMatrix());
    	}
    
    
    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
		ImageView view=(ImageView)v;
   
		FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) view.getLayoutParams();
	
		mParams.width=FrameLayout.LayoutParams.FILL_PARENT;
		mParams.height=FrameLayout.LayoutParams.FILL_PARENT;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                  
                	
                	matrix.set(drag(v, event, view,mParams));
                	
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if ((lastEvent != null && event.getPointerCount() == 3) || Edit_Image.rotate_enabled) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }
	
	
	private Matrix drag(View v, MotionEvent event,ImageView mMainImg, LayoutParams mParams) {
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		mParams.leftMargin = x - 1;
		mParams.topMargin = y - 1;
		mMainImg.setLayoutParams(mParams);
		return mMainImg.getMatrix();
		

	}

}
