package com.ganesh.picmessage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ganesh.memes.crop.FileUtil;
import com.ganesh.picmsg.adapter.Commet_Functions;
import com.ganesh.picmsg.adapter.Item;
import com.ganesh.picmsg.adapter.MyAdapter;

public class Styles_Dialog extends Activity {
	String style;
	MyAdapter meme_adpter;
	 GridView gridView_meme;
	 List<Item> items_meme;
	Commet_Functions commen=new Commet_Functions();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.styles_gallery);
		gridView_meme=(GridView)findViewById(R.id.gridview_styles);
		try {
			style=getIntent().getExtras().getString("style");
			  items_meme=new ArrayList<Item>();
            		new Load_Images().execute(style);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		gridView_meme.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Item itm=items_meme.get(position);
			
				String path =itm.path;
				
				Intent mIntent=new Intent();
				mIntent.putExtra("stylepath",path);
				setResult(RESULT_OK, mIntent);
				finish();
			}
		});
		
		
	}
	
	
	 private class Load_Images extends AsyncTask<String, Void, String> {
		  	ProgressDialog pd;
		  	@Override
		  	protected void onPreExecute() {
		  	// TODO Auto-generated method stub
		  try {
			  	pd=new ProgressDialog(Styles_Dialog.this);
			  	pd.setMessage("Loading");
			  	pd.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  	
		  	}
		    @Override
		    protected String doInBackground(String... urls) {
		
		    
		    	


					     String [] list;
					        try {
					        	
					            
								list = getAssets().list(urls[0]);
					            if (list.length > 0) {
					                // This is a folder
					            	int id=0;
					                for (String file : list) {
					                	  try {
					                   String path=urls[0] + "/" + file;
					                   
					                   String name=file.replace("_", " ").replace(".png", "");
					                   Bitmap bm=commen.getBitmapFromAsset(path,getApplicationContext());
					                   if (!bm.equals(null)) {
					                	   items_meme.add(new Item(name,  bm,id,path));   
					                       id=id+1;
									     }

					                   
					                	  }catch(OutOfMemoryError e)
					                	  {
					                		  e.printStackTrace();
					                		
					                	  }catch (Exception e) {
					              			// TODO: handle exception
					                      	e.printStackTrace();
					              		}
									
					                }
					            }
					        }catch(OutOfMemoryError e)
			          	  {
			          		  e.printStackTrace();
			          	  } catch (IOException e) {
					        	e.printStackTrace();
					        }catch (Exception e) {
								// TODO: handle exception
					        	e.printStackTrace();
							}

		   
	      return "Done";
		    }

			@Override
			protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				pd.dismiss();
		
					meme_adpter=new MyAdapter(Styles_Dialog.this,items_meme);
					  gridView_meme.setAdapter(meme_adpter);
			
				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			}
			
			
			
			
}
	 
	 
	 
		@Override
		protected void onPause()
		{
		        super.onPause();

		        unbindDrawables(findViewById(R.id.rel_stylegallery));
		        System.gc();
		}


		@Override
		protected void onDestroy()
		{
		        super.onDestroy();

		        unbindDrawables(findViewById(R.id.rel_stylegallery));
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
