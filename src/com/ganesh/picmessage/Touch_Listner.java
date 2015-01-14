package com.ganesh.picmessage;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Touch_Listner implements OnTouchListener{
	


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


        final ImageView view = (ImageView) v;

    	FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) v.getLayoutParams();
    	 FrameLayout frame_lay  = (FrameLayout) v.getParent();
     
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
//                     matrix.set(savedMatrix);
////                     float dx = event.getX() - start.x;
////                     float dy = event.getY() - start.y;
////                     matrix.postTranslate(dx, dy);
//                     //view.setImageMatrix(matrix);
//                     view.setImageMatrix(matrix);
//                     int x = (int) event.getRawX();
//             		int y = (int) event.getRawY();
//             		mParams.leftMargin = x - 150;
//             		mParams.topMargin = y - 210;
//                     
//                     view.setLayoutParams(mParams);
                     matrix.set(drag(v, event, view));
                 } else if (mode == ZOOM) {
                     float newDist = spacing(event);
                     if (newDist > 10f) {
//                         matrix.set(savedMatrix);
                         float scale = (newDist / oldDist);
//                         matrix.postScale(scale, scale, mid.x, mid.y);
//                       
//                         Log.e("ZOOM", "Scale- "+scale+", X-"+mid.x+", Y-"+mid.y);

                         matrix.set(Rotate(v, event, view,scale));
                         
                     }
                     if (lastEvent != null && event.getPointerCount() == 3) {
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
                       
                         view.setLayoutParams(mParams);
                     }
                 }
                 break;
        }
        view.setImageMatrix(matrix);
        frame_lay.invalidate();
		return true;
      
    
    
	}
	
	private Matrix drag(View v, MotionEvent event,ImageView mMainImg) {
		FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) mMainImg.getLayoutParams();
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		mParams.leftMargin = x - 150;
		mParams.topMargin = y - 210;
		mMainImg.setLayoutParams(mParams);
		return mMainImg.getImageMatrix();
		

	}
	
	
	private Matrix Rotate(View v, MotionEvent event,ImageView mMainImg, float scale) {
		FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) mMainImg.getLayoutParams();
		
		Matrix matr=new Matrix();
		matr.set(mMainImg.getImageMatrix());
		matr.postScale(scale, scale, mid.x, mid.y);
		mMainImg.setImageMatrix(matr);
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		mParams.leftMargin = x + 150;
		mParams.topMargin = y + 210;
		mMainImg.setLayoutParams(mParams);
		return mMainImg.getImageMatrix();
		

	}

}
