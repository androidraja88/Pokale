package com.ganesh.picmessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ganesh.picmsg.adapter.Commet_Functions;
import com.ganesh.picmsg.adapter.Edited_Adapter;
import com.ganesh.picmsg.adapter.Item;
import com.ganesh.picmsg.adapter.MyAdapter;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.banner.Banner;
@SuppressWarnings("deprecation")
public class MemesGallary extends TabActivity {
	TabHost tabHost;
	protected Resources res; 
	

	Edited_Adapter edited_adpter;
	MyAdapter meme_adpter;
	 GridView gridView_meme,gridView_edited;
	 List<Item> items_meme,items_edited;
	 String selected_tab_id;
	Commet_Functions commen=new Commet_Functions();
	
	private StartAppAd startAppAd = new StartAppAd(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		boolean titled =	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.meme_gallery);
		
		if(titled){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
		}
		
		gridView_meme=(GridView)findViewById(R.id.gridview_meme);
		gridView_edited=(GridView)findViewById(R.id.gridview_edited);
		
		tabinitilize();
		selected_tab_id="meme";  
	   	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ico_tab_backgroung); // unselected
       TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title); //Unselected Tabs
       tv.setTextColor(Color.parseColor("#2187BE"));
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

      	    @Override
      	    public void onTabChanged(String tabId) {

      	        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
      	        	 tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.ico_tab_backgroung); // unselected
           	        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //for Selected Tab
           	        tv.setTextColor(Color.parseColor("#FFFFFF"));
           	   
      	        	
      	        
      	        }
      	        
      	    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ico_tab_backgroung); // unselected
  	            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title); //Unselected Tabs
  	            tv.setTextColor(Color.parseColor("#2187BE"));
  	            
  	            if (tabId.equals("meme")) {
  	            	
  	              	try {
  	              		if (items_meme.isEmpty()) {
  	  	              		selected_tab_id="meme";  

  	  	                    items_meme=new ArrayList<Item>();
  	  	              		new Load_Images().execute("Memes");
						}
  	              		
  	              	} catch (Exception e) {
  	              		// TODO: handle exception
  	              	}


					}else {
						selected_tab_id="edited";  
				      	try {
				      		items_edited=new ArrayList<Item>();
				      		new Load_Images().execute("My List");
				      	} catch (Exception e) {
				      		// TODO: handle exception
				      	}
				      	
				     
					}

      	       
      	    }
      	});
		
        items_meme=new ArrayList<Item>();
    	new Load_Images().execute("Memes");
	
    	
    	
    	
    	
    	
		gridView_meme.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Item itm=items_meme.get(position);
				String name =itm.name;
				String path =itm.path;
				
				Intent i=new Intent(getBaseContext(), Edit_Image.class);
				i.putExtra("name", name);
				i.putExtra("path",path);
				startActivity(i);
			
			}
		});
    	
    	

		
	RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rel_memegallery);
		
		// Create new StartApp banner
		Banner startAppBanner = new Banner(this);
		RelativeLayout.LayoutParams bannerParameters = 
				new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
		bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		// Add the banner to the main layout
		mainLayout.addView(startAppBanner, bannerParameters);
	}

	private void tabinitilize() {
		// TODO Auto-generated method stub

		
		  res = getResources();  
        tabHost = getTabHost();  
        TabHost.TabSpec spec;  
        spec = tabHost.newTabSpec("meme").setIndicator("Memes").setContent(R.id.layoutTab1);  
        tabHost.addTab(spec);  
        spec = tabHost.newTabSpec("edited").setIndicator("Edited").setContent(R.id.layoutTab2);  
        tabHost.addTab(spec);  
        tabHost.setCurrentTab(0);  
        
		tabHost.setBackgroundResource(R.drawable.ico_tab_backgroung);
		
		
		int tabCount = tabHost.getTabWidget().getTabCount();
      for (int i = 0; i < tabCount; i++) {
          final View view = tabHost.getTabWidget().getChildTabViewAt(i);
          if ( view != null ) {
              // reduce height of the tab
              view.getLayoutParams().height *= 0.96;

              //  get title text view
              final View textView = view.findViewById(android.R.id.title);
              if ( textView instanceof TextView ) {
                  // just in case check the type

                  // center text
                  ((TextView) textView).setGravity(Gravity.CENTER);
                  // wrap text
                  ((TextView) textView).setSingleLine(false);
                  ((TextView) textView).setTypeface(null, Typeface.BOLD);
                  // explicitly set layout parameters
                  ((TextView) textView).setTextSize(19);
                  textView.getLayoutParams().height = ViewGroup.LayoutParams.FILL_PARENT;
                  textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
              }
          }
      }
      
      for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
	       	 tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.ico_tab_backgroung); // unselected
	        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //for Selected Tab
	        tv.setTextColor(Color.parseColor("#FFFFFF"));

	        	
	        
	        }
	}
	
 private class Load_Images extends AsyncTask<String, Void, String> {
		  	ProgressDialog pd;
		  	@Override
		  	protected void onPreExecute() {
		  	// TODO Auto-generated method stub
		  try {
			  	pd=new ProgressDialog(MemesGallary.this);
			  	pd.setMessage("Loading");
			  	pd.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  	
		  	}
		    @Override
		    protected String doInBackground(String... urls) {
		
		    
		    	
		    	
		    	if (urls[0].equals("My List")) {
				File folder = new File(Environment.getExternalStorageDirectory().toString()+"/PicMessage");   
			    if (!folder.exists())
			    {
			        folder.mkdir();
			    }
				File[] list;
				        try {
				        	
				            
							list = folder.listFiles();
				            if (list.length > 0) {
				                // This is a folder
				            	int id=0;
				                for (File file : list) {
				                	  try {
				                		  
				                   String path=file.getAbsolutePath();
				            
				            	 
				                  // String name=file.getName().replace("_", " ").replace(".png", "");
				                  	Bitmap list_bag=commen.decodeFile(file);
				                   if (!list_bag.equals(null)) {
				                	   
				                	   items_edited.add(new Item("PicMessage",  list_bag,id,path));   
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
		          	  } catch (Exception e) {
							// TODO: handle exception
				        	e.printStackTrace();
						}
		   	
		    		
				}else {
					
					List<String> arr_Asset_Folders=new ArrayList<String>();
					arr_Asset_Folders.add("A");
//					arr_Asset_Folders.add("B");
//					arr_Asset_Folders.add("C");
//					arr_Asset_Folders.add("D");
					for (String string : arr_Asset_Folders) {

					     String [] list;
					        try {
					        	
					            
								list = getAssets().list(string);
					            if (list.length > 0) {
					                // This is a folder
					            	int id=0;
					                for (String file : list) {
					                	  try {
					                   String path=string + "/" + file;
					                   
					                   String name=file.replace("_", " ").replace(".png", "");
					                  
					               	Bitmap list_bag=commen.getBitmapFromAsset(path,getApplicationContext());
					                   if (!list_bag.equals(null)) {
					                	   items_meme.add(new Item(name,  list_bag,id,path));   
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
					    	
					    	
					    	
					    	
					
					}
					
					
				}
		   
	      return "Done";
		    }

			@Override
			protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				pd.dismiss();
				
				if (selected_tab_id.equals("meme")) {
					meme_adpter=new MyAdapter(MemesGallary.this,items_meme);
					  gridView_meme.setAdapter(meme_adpter);
					new Load_Memes_Extra().execute();
				

				}else {
					edited_adpter=new Edited_Adapter(MemesGallary.this,items_edited);
					  gridView_edited.setAdapter(edited_adpter);

				}
				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			}
			
			
			
			
 }
			
			
			
			
			
			
			
			
			
			private class Load_Memes_Extra extends AsyncTask<String, Void, String> {
			  	ProgressDialog pd;
			  	@Override
			  	protected void onPreExecute() {
			  	// TODO Auto-generated method stub
			 
			  	}
			    @Override
			    protected String doInBackground(String... urls) {

						List<String> arr_Asset_Folders=new ArrayList<String>();
					
						arr_Asset_Folders.add("B");
						arr_Asset_Folders.add("C");
						arr_Asset_Folders.add("D");
						for (String string : arr_Asset_Folders) {

						     String [] list;
						        try {
						        	
						            
									list = getAssets().list(string);
						            if (list.length > 0) {
						                // This is a folder
						            	int id=0;
						                for (String file : list) {
						                	  try {
						                   String path=string + "/" + file;
						                   
						                   String name=file.replace("_", " ").replace(".png", "");
						               	Bitmap list_bag=commen.getBitmapFromAsset(path,getApplicationContext());
						                   if (!list_bag.equals(null)) {
						                	   items_meme.add(new Item(name,  list_bag,id,path));   
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
						    	
						    	
						    	
						    	
						
						}
						
						
					
			   
		      return "Done";
			    }

				@Override
				protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				try {
						meme_adpter=new MyAdapter(MemesGallary.this,items_meme);
						  gridView_meme.setAdapter(meme_adpter);

					 
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				}
			
			}
  

			
			@Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
				try {
					
					startAppAd.onBackPressed();
			
			} catch (Exception e) {
				// TODO: handle exception
			
			}
				super.onBackPressed();
				finish();
			}


			@Override
			protected void onDestroy()
			{
			        super.onDestroy();
			     
			        unbindDrawables(findViewById(R.id.rel_memegallery));
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
			
			
			/**
			 * Part of the activity's life cycle, StartAppAd should be integrated here.
			 */
			@Override
			public void onResume(){
				super.onResume();
				startAppAd.onResume();
			}

			/**
			 * Part of the activity's life cycle, StartAppAd should be integrated here
			 * for the home button exit ad integration.
			 */
			@Override
			public void onPause(){
				super.onPause();
				startAppAd.onPause();
			}


	
}
