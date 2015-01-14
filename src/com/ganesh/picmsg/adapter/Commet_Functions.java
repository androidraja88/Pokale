package com.ganesh.picmsg.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Commet_Functions {
    public Bitmap getBitmapFromAsset(String strName,Context con) throws OutOfMemoryError, Exception
    {
        AssetManager assetManager = con.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap=null;
        try {
        	
        	  bitmap = BitmapFactory.decodeStream(istr);
            //bitmap=decodeStream_isrt(istr,170);
        	
       	istr.close();
        	istr=null;
		}catch (OutOfMemoryError e) {
			// TODO: handle exception
			try {
				  bitmap=decodeStream_isrt(istr,150);
		        	
			       	istr.close();
			        	istr=null;
			} catch (Exception e2) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
		}
        return bitmap;
    }
    
    
    public Bitmap Scaled_Bitmap(Bitmap originalImage, int width, float height) throws OutOfMemoryError, Exception
    {
    	Bitmap background = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
    	float originalWidth = originalImage.getWidth(), originalHeight = originalImage.getHeight();
    	Canvas canvas = new Canvas(background);
    	float scale = width/originalWidth;
    	float xTranslation = 0.0f, yTranslation = (height - originalHeight * scale)/2.0f;
    	Matrix transformation = new Matrix();
    	transformation.postTranslate(xTranslation, yTranslation);
    	transformation.preScale(scale, scale);
    	Paint paint = new Paint();
    	paint.setFilterBitmap(true);
    	canvas.drawBitmap(originalImage, transformation, paint);
    	return background;
    }
    
    


		public static Bitmap drawableToBitmap (Drawable drawable) {
		    if (drawable instanceof BitmapDrawable) {
		        return ((BitmapDrawable)drawable).getBitmap();
		    }
		
		    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(bitmap); 
		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		    drawable.draw(canvas);
		
		    return bitmap;
		}





		public Drawable adjustedContrast(Context con,Bitmap src, double value)
		{
			 int width = src.getWidth();
			 int height = src.getHeight();
			 // create output bitmap
			 Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
			 // color information
			 int A, R, G, B;
			 int pixel;
			 // get contrast value
			 double contrast = Math.pow((100 + value) / 100, 2);

			 // scan through all pixels
			 for(int x = 0; x < width; ++x) {
			  for(int y = 0; y < height; ++y) {
			   // get pixel color
			   pixel = src.getPixel(x, y);
			   A = Color.alpha(pixel);
			   // apply filter contrast for every channel R, G, B
			   R = Color.red(pixel);
			   R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
			   if(R < 0) { R = 0; }
			   else if(R > 255) { R = 255; }

			   G = Color.red(pixel);
			   G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
			   if(G < 0) { G = 0; }
			   else if(G > 255) { G = 255; }

			   B = Color.red(pixel);
			   B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
			   if(B < 0) { B = 0; }
			   else if(B > 255) { B = 255; }

			   // set new pixel color to output bitmap
			   bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			  }
			 }

		    return  new BitmapDrawable(con.getResources(),bmOut);
		}
		
		public  Drawable changeBitmapContrastBrightness(Context con,Bitmap bmp, float contrast, float brightness)
		{
		    ColorMatrix cm = new ColorMatrix(new float[]
		            {
		                contrast, 0, 0, 0, brightness,
		                0, contrast, 0, 0, brightness,
		                0, 0, contrast, 0, brightness,
		                0, 0, 0, 1, 0
		            });

		    Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

		    Canvas canvas = new Canvas(ret);

		    Paint paint = new Paint();
		    paint.setColorFilter(new ColorMatrixColorFilter(cm));
		    canvas.drawBitmap(bmp, 0, 0, paint);

		    return new BitmapDrawable(con.getResources(),ret);
		}
		
		
		public  Drawable hue(Context con,Bitmap source, float level) {
			   int width = source.getWidth();
			    int height = source.getHeight();
			    int[] pixels = new int[width * height];
			    float[] HSV = new float[3];
			    // get pixel array from source
			    source.getPixels(pixels, 0, width, 0, 0, width, height);
			     
			    int index = 0;
			    // iteration through pixels
			    for(int y = 0; y < height; ++y) {
			        for(int x = 0; x < width; ++x) {
			            // get current index in 2D-matrix
			            index = y * width + x;             
			            // convert to HSV
			            Color.colorToHSV(pixels[index], HSV);
			            // increase Saturation level
			            HSV[0] *= level;
			            HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], 360.0));
			            // take color back
			            pixels[index] |= Color.HSVToColor(HSV);
			        }
			    }
			    // output bitmap               
			    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);

		    return new BitmapDrawable(con.getResources(),bmOut);
		}
		
		public Drawable adjustedHue(Context con,Bitmap o, int deg)
		{
		   Bitmap srca = o;
		   Bitmap bitmap = srca.copy(Bitmap.Config.ARGB_8888, true);
		   for(int x = 0;x < bitmap.getWidth();x++)
		       for(int y = 0;y < bitmap.getHeight();y++){
		               int newPixel = hueChange(bitmap.getPixel(x,y),deg);
		               bitmap.setPixel(x, y, newPixel);
		       }

		   return new BitmapDrawable(con.getResources(),bitmap);
		}

		private int hueChange(int startpixel,int deg){
			   float[] hsv = new float[3];       //array to store HSV values
			   Color.colorToHSV(startpixel,hsv); //get original HSV values of pixel
			   hsv[0]=hsv[0]+deg;                //add the shift to the HUE of HSV array
			   hsv[0]=hsv[0]%360;                //confines hue to values:[0,360]
			   return Color.HSVToColor(Color.alpha(startpixel),hsv);
			}
		
		
		    public Drawable Saturation (Context con,Bitmap src, float settingSat) {

		    	  int w = src.getWidth();
		    	  int h = src.getHeight();

		    	  Bitmap bitmapResult = 
		    	    Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		    	  Canvas canvasResult = new Canvas(bitmapResult);
		    	  Paint paint = new Paint();
		    	  ColorMatrix colorMatrix = new ColorMatrix();
		    	  colorMatrix.setSaturation(settingSat);
		    	  ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
		    	  paint.setColorFilter(filter);
		    	  canvasResult.drawBitmap(src, 0, 0, paint);

		    	 

		    	  return  new BitmapDrawable(con.getResources(),bitmapResult);
		    	 }
		
		
		    public  Drawable Brightness(Context con,Bitmap src, int value) {
		        // get image size 
		    	   // image size
		        int width = src.getWidth();
		        int height = src.getHeight();
		        // create output bitmap
		        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		        // color information
		        int A, R, G, B;
		        int pixel;
		     
		        // scan through all pixels
		        for(int x = 0; x < width; ++x) {
		            for(int y = 0; y < height; ++y) {
		                // get pixel color
		                pixel = src.getPixel(x, y);
		                A = Color.alpha(pixel);
		                R = Color.red(pixel);
		                G = Color.green(pixel);
		                B = Color.blue(pixel);
		     
		                // increase/decrease each channel
		                R += value;
		                if(R > 255) { R = 255; }
		                else if(R < 0) { R = 0; }
		     
		                G += value;
		                if(G > 255) { G = 255; }
		                else if(G < 0) { G = 0; }
		     
		                B += value;
		                if(B > 255) { B = 255; }
		                else if(B < 0) { B = 0; }
		     
		                // apply new pixel color to output bitmap
		                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
		            }
		        }
		     
		        // return final image
		      
		        return new BitmapDrawable(con.getResources(),bmOut);
		    }
		
		
		  //decodes image and scales it to reduce memory consumption
		    public Bitmap decodeFile(File f){
		        try {
		            //Decode image size
		            BitmapFactory.Options o = new BitmapFactory.Options();
		            o.inJustDecodeBounds = true;
		            
		            o.inSampleSize  = 3;
		            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

		            //The new size we want to scale to
		            final int REQUIRED_SIZE=70;

		            //Find the correct scale value. It should be the power of 2.
		            int scale=1;
		            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
		                scale*=2;

		            //Decode with inSampleSize
		            BitmapFactory.Options o2 = new BitmapFactory.Options();
		            o2.inSampleSize=scale;
		            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		        } catch (FileNotFoundException e) {}
		        return null;
		    }
		    
		    
		    public Bitmap decodeStream_isrt(InputStream istr, int size) throws Exception{
		        try {
		            //Decode image size
		            BitmapFactory.Options o = new BitmapFactory.Options();
		            o.inJustDecodeBounds = true;
		            
		           // o.inSampleSize  = 3;
		            BitmapFactory.decodeStream(istr,null,o);

		            //The new size we want to scale to
		            final int REQUIRED_SIZE=size;

		            //Find the correct scale value. It should be the power of 2.
		            int scale=1;
		            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
		                scale*=2;

		            //Decode with inSampleSize
		            BitmapFactory.Options o2 = new BitmapFactory.Options();
		            o2.inSampleSize=scale;
		            return BitmapFactory.decodeStream(istr, null, o2);
		        }
		        catch (OutOfMemoryError e) {
					// TODO: handle exception
		        	if (size>=10) {
						int new_size=size-40;
						return decodeStream_isrt(istr,new_size);
					}else {
						   return null;
					}
				}
		        
		     
		    }
		    	
}
