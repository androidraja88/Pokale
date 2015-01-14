package com.ganesh.picmessage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	Drawable d=getDrawable();
    	if (d!=null) {
			int width=MeasureSpec.getSize(widthMeasureSpec);
			int height=MeasureSpec.getSize(heightMeasureSpec);
			
			if (width>=height) {
				width=(int)Math.ceil((float)height*(float)d.getIntrinsicWidth()/(float)d.getIntrinsicHeight());
			}else {
				height=(int)Math.ceil((float)width*(float)d.getIntrinsicHeight()/(float)d.getIntrinsicWidth());
			}
			setMeasuredDimension(width, height);
		}
    	else {
			setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		}
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}