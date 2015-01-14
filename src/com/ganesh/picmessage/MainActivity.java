package com.ganesh.picmessage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.ganesh.picmsg.adapter.Exit_Dailog;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.nativead.NativeAdDetails;
import com.startapp.android.publish.nativead.NativeAdPreferences;
import com.startapp.android.publish.nativead.NativeAdPreferences.NativeAdBitmapSize;
import com.startapp.android.publish.nativead.StartAppNativeAd;

public class MainActivity extends Activity{
	Button btn_meme,btn_gallegy;
	 private static int RESULT_LOAD_IMAGE = 1;
	    
	 

		/** StartAppAd object declaration */
		private StartAppAd startAppAd = new StartAppAd(this);
		
		/** StartApp Native Ad declaration */
		private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
		private NativeAdDetails nativeAd = null;
		
		
		/** Native Ad Callback */
		private AdEventListener nativeAdListener = new AdEventListener() {
			
			@Override
			public void onReceiveAd(Ad ad) {
				
				// Get the native ad
				ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
				if (nativeAdsList.size() > 0){
					nativeAd = nativeAdsList.get(0);
				}
				
				// Verify that an ad was retrieved
				if (nativeAd != null){
					
					// When ad is received and displayed - we MUST send impression
					nativeAd.sendImpression(MainActivity.this);
					
					
					
				}
			}
			
			@Override
			public void onFailedToReceiveAd(Ad ad) {
				
				// Error occurred while loading the native ad
			
			}
		};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		boolean titled =	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
StartAppSDK.init(this, "111373873", "212087132", true); //TODO: Replace with your IDs

		setContentView(R.layout.act_main);
		
		if(titled){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
		}
		
//		try {
//
//			// Action Bar
//			ActionBar actions = getSupportActionBar();
//			
//			 BitmapDrawable background = new BitmapDrawable (BitmapFactory.decodeResource(getResources(), R.drawable.title)); 
//			 background.setTileModeX(android.graphics.Shader.TileMode.REPEAT); 
//			// actions.setBackgroundDrawable(background);
//			
//			 actions.setDisplayShowHomeEnabled(false);
//			 actions.setDisplayShowTitleEnabled(false);
//			 actions.setDisplayUseLogoEnabled(false);
//			 actions.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
//			 actions.setCustomView(R.layout.custom_title_bar);
//		}catch(OutOfMemoryError s)
//		{
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
		
		btn_gallegy=(Button)findViewById(R.id.btn_main_gallary);
	   btn_meme=(Button)findViewById(R.id.btn_main_memes);
		
		
		btn_gallegy.setOnClickListener(new OnClickListener() {
			
		

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 
						startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		
		btn_meme.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	Intent i=new Intent(getBaseContext(), MemesGallary.class);
                i.putExtra("call_type", "meme");
				startActivity(i);
			}
		});
		

		/** Add Slider **/
		StartAppAd.showSlider(this);
		
		
		
		/** 
		 * Load Native Ad with the following parameters:
		 * 1. Only 1 Ad
		 * 2. Download ad image automatically
		 * 3. Image size of 150x150px
		 */
		startAppNativeAd.loadAd(
				new NativeAdPreferences()
					.setAdsNumber(1)
					.setAutoBitmapDownload(true)
					.setImageSize(NativeAdBitmapSize.SIZE150X150),
				nativeAdListener);	

		
	}
	
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	            String path=picturePath;
	        	Intent i=new Intent(getBaseContext(), Edit_Image.class);
				i.putExtra("name", "gallerypic");
				i.putExtra("path",path);
				 i.putExtra("call_type", "gallery");
				startActivity(i);
	         
	        }
	     }
	
	
	/**
	 * Runs when the native ad is clicked (either the image or the title).
	 * @param view
	 */
	public void freeAppClick(View view){
		if (nativeAd != null){
			nativeAd.sendClick(this);
		}
	}

	/**
	 * Part of the activity's life cycle, StartAppAd should be integrated here. 
	 */
	@Override
	public void onResume() {
		super.onResume();
		startAppAd.onResume();
	}

	/**
	 * Part of the activity's life cycle, StartAppAd should be integrated here
	 * for the home button exit ad integration.
	 */
	@Override
	public void onPause() {
		super.onPause();
		startAppAd.onPause();
	}

	/**
	 * Part of the activity's life cycle, StartAppAd should be integrated here
	 * for the back button exit ad integration.
	 */
	@Override
	public void onBackPressed() {
		 new Exit_Dailog(getApplicationContext(), this, startAppAd,"com.ganesh.picmessage");
	}
	
}
