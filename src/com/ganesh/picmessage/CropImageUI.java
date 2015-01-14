package com.ganesh.picmessage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;

import com.ganesh.memes.crop.CropImageView4;
import com.ganesh.memes.crop.FileUtil;
import com.ganesh.picmessage.R;

public class CropImageUI extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		
		setContentView(R.layout.crop_dialog);
		final CropImageView4 mCropImage=(CropImageView4)findViewById(R.id.cropImg);
		mCropImage.setDrawable(Edit_Image.static_bmp,200,200);
		findViewById(R.id.btn_setcrop).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						Bitmap bmp=mCropImage.getCropImage();
						FileUtil.writeImage(bmp, FileUtil.SDCARD_PAHT+"/crop.png", 100);
						Intent mIntent=new Intent();
						mIntent.putExtra("cropImagePath", FileUtil.SDCARD_PAHT+"/crop.png");
						setResult(RESULT_OK, mIntent);
						finish();
				
					}
				}).start();
			}
		});
	}
	
	
	@Override
	protected void onPause()
	{
	        super.onPause();

	        unbindDrawables(findViewById(R.id.rel_cropdialog));
	        System.gc();
	}


	@Override
	protected void onDestroy()
	{
	        super.onDestroy();

	        unbindDrawables(findViewById(R.id.rel_cropdialog));
	        System.gc();
	}
	
	private void unbindDrawables(View view)
	{
	        if (view.getBackground() != null)
	        {
	                view.getBackground().setCallback(null);
	        }
	        if (view instanceof ViewGroup && !(view instanceof AdapterView))
	        {
	                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
	                {
	                        unbindDrawables(((ViewGroup) view).getChildAt(i));
	                }
	                ((ViewGroup) view).removeAllViews();
	        }
	}
	
	
	
}
