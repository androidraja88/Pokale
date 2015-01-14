package com.ganesh.picmessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.SettingNotFoundException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ganesh.picmsg.adapter.ColorPickerDialog;
import com.ganesh.picmsg.adapter.Commet_Functions;
import com.ganesh.picmsg.adapter.Spinner_Font_Adapter;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.banner.Banner;

@SuppressLint("NewApi")
public class Edit_Image extends Activity  implements OnDragListener{
	
	private StartAppAd startAppAd = new StartAppAd(this);
	Touch_Listner touch;
	SeekBar seekbar_saturation,seekbar_brightness,seekbar_hue;
	   float curBrightnessValue = 0;
	   RelativeLayout rel_saturation,rel_brightness,rel_hue;
	boolean drag_head;

	Button btn_edit_saturation,btn_edit_hue,btn_edit_brightness;
	
	Button btn_set_saturation,btn_set_brightness,btn_set_hue;
	
  protected int Color_id_dialog=Color.BLACK; 
	String name,path;
	public static Drawable static_bmp;
	 float X_Balloon,Y_Balloon;
	 LinearLayout lin_stickers;
	
	Button btn_sticker,btn_export,btn_scale_rotate;
	    String [] list_font_path;
	    String selected_font_header;
	    int header_font_index,footer_font_index;
	    Button btn_add_header,btn_add_footer,btn_crop_image,btn_save_to_sd,btn_rotate_bg;
	    
	    Button btn_hats_helmets,btn_hair,btn_goggle,btn_romantic,btn_mustache;
	    
	    FrameLayout rel_image;
		private int requestCode=100;
		private int style_requestCode=20;
		private int requestCode_photo=10;
		ImageView img_main;
		
		private GestureDetector gestureDetector;
		Bitmap bitmap_main;
		Drawable updated_bmp; 
		  FrameLayout.LayoutParams lp_rel = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	      
		Button btn_relese_touch;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean titled =	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.editimage);
		if(titled){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar);
		}
		btn_sticker=(Button)findViewById(R.id.btn_sticker);
		
		btn_export=(Button)findViewById(R.id.btn_export);
		btn_scale_rotate=(Button)findViewById(R.id.btn_scale_rotate);
		btn_add_header=(Button)findViewById(R.id.btn_add_header);
		btn_add_footer=(Button)findViewById(R.id.btn_add_footer);
		
		btn_edit_hue=(Button)findViewById(R.id.btn_edit_hue);
		btn_edit_brightness=(Button)findViewById(R.id.btn_edit_brightness);
		btn_edit_saturation=(Button)findViewById(R.id.btn_edit_saturation);
		btn_crop_image=(Button)findViewById(R.id.btn_crop_image);
		btn_save_to_sd=(Button)findViewById(R.id.btn_save);
		btn_rotate_bg=(Button)findViewById(R.id.btn_rotate_bg);
		btn_relese_touch=(Button)findViewById(R.id.btn_relese_touch);
		
		btn_set_saturation=(Button)findViewById(R.id.btn_set_saturation);
		btn_set_brightness=(Button)findViewById(R.id.btn_set_brightness);
		btn_set_hue=(Button)findViewById(R.id.btn_set_hue);
		
		 rel_image = (FrameLayout)findViewById(R.id.rel_image);
		 img_main=(ImageView)findViewById(R.id.imageview_main);
		header_font_index=1;
		footer_font_index=1;
		selected_font_header="myfonts/BEBAS.ttf";
		lin_stickers=(LinearLayout)findViewById(R.id.linear_options_sticker);
		rel_saturation=(RelativeLayout)findViewById(R.id.rel_saturation);
		rel_brightness=(RelativeLayout)findViewById(R.id.rel_brightness);
		rel_hue=(RelativeLayout)findViewById(R.id.rel_hue);
		rotate_enabled=true;
		
		
		seekbar_saturation=(SeekBar)findViewById(R.id.seekbar_saturation);
		seekbar_brightness=(SeekBar)findViewById(R.id.seekbar_brightness);
		seekbar_hue=(SeekBar)findViewById(R.id.seekbar_hue);

		 gestureDetector = new GestureDetector(Edit_Image.this, new GestureListener());
		
		btn_hats_helmets=(Button)findViewById(R.id.btn_add_hats);
		btn_hair=(Button)findViewById(R.id.btn_add_hair);
		btn_goggle=(Button)findViewById(R.id.btn_add_goggles);
		btn_romantic=(Button)findViewById(R.id.btn_add_romantic);
		btn_mustache=(Button)findViewById(R.id.btn_add_mustache);
		
		  //lp_rel.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		btn_hats_helmets.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				//Draw_Word_Balloon("ballon", R.drawable.ss);
				
				Intent mIntent=new Intent(getBaseContext(), Styles_Dialog.class);
				mIntent.putExtra("style", "hats");
				startActivityForResult(mIntent, style_requestCode);
				
			}
		});
		
		
		btn_hair.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				//Draw_Word_Balloon("ballon", R.drawable.ss);
				
				Intent mIntent=new Intent(getBaseContext(), Styles_Dialog.class);
				mIntent.putExtra("style", "hair");
				startActivityForResult(mIntent, style_requestCode);
				
			}
		});
		
		
		btn_goggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				//Draw_Word_Balloon("ballon", R.drawable.ss);
				
				Intent mIntent=new Intent(getBaseContext(), Styles_Dialog.class);
				mIntent.putExtra("style", "goggles");
				startActivityForResult(mIntent, style_requestCode);
				
			}
		});
		
		
		btn_romantic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				//Draw_Word_Balloon("ballon", R.drawable.ss);
				
				Intent mIntent=new Intent(getBaseContext(), Styles_Dialog.class);
				mIntent.putExtra("style", "romantic");
				startActivityForResult(mIntent, style_requestCode);
				
			}
		});

		
		btn_mustache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				//Draw_Word_Balloon("ballon", R.drawable.ss);
				
				Intent mIntent=new Intent(getBaseContext(), Styles_Dialog.class);
				mIntent.putExtra("style", "mustache");
				startActivityForResult(mIntent, style_requestCode);
				
			}
		});
		
		
		
		btn_scale_rotate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    
					try {
						if (rotate_enabled) {
							rotate_enabled=false;
							btn_scale_rotate.setBackgroundResource(R.drawable.move_ico);
						}else {
							rotate_enabled=true;
							btn_scale_rotate.setBackgroundResource(R.drawable.circle_ico);
						}
					} catch (OutOfMemoryError e) {
						// TODO: handle exception
					}catch (Exception e) {
						// TODO: handle exception
					}
				
			}
		});
		
		btn_sticker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				if (lin_stickers.getVisibility()==View.GONE) {
					Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.bottom_up);

					lin_stickers.startAnimation(bottomUp);
					lin_stickers.setVisibility(View.VISIBLE);
				}else {
					Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.bottom_down);

					lin_stickers.startAnimation(bottom_down);
					lin_stickers.setVisibility(View.GONE);
				}
				
			}
		});
		
		
		
		try {
			 Bundle extras = getIntent().getExtras();
			    String action = getIntent().getAction();

			    // if this is from the share menu
			    if (Intent.ACTION_SEND.equals(action)) {   
			        if (extras.containsKey(Intent.EXTRA_STREAM)) {
			            // Get resource path
			        	 Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
			        	 path = parseUriToFilename(uri);
			        	 name="gallerypic";
			        }
					}
			    
			    else {
		        	try {
		    			name=getIntent().getExtras().getString("name");
		    			path=getIntent().getExtras().getString("path");
		    			
		    			
		    	
		    		
		    		}catch(OutOfMemoryError s)
		    		{
		    			s.printStackTrace();
		    		}catch (Exception e) {
		    			// TODO: handle exception
		    			e.printStackTrace();
		    		}
		        	
		        	
		        	
		    		
			    }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		new Load_Bitmap().execute(path);
	
		
		
//		
//		try {
//
//			// Action Bar
//			ActionBar actions = getSupportActionBar();
//			
//			 BitmapDrawable background = new BitmapDrawable (BitmapFactory.decodeResource(getResources(), R.drawable.title)); 
//			 background.setTileModeX(android.graphics.Shader.TileMode.REPEAT); 
//			 actions.setBackgroundDrawable(background);
//			 actions.setDisplayShowHomeEnabled(false);
//			 actions.setDisplayShowTitleEnabled(false);
//			 actions.setDisplayUseLogoEnabled(false);
//		
//		}catch(OutOfMemoryError s)
//		{
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}

		btn_add_header.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				final String button_id="header";
				final Dialog dialog = new Dialog(Edit_Image.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
				dialog.setContentView(R.layout.settext_dialog);
				
				Spinner spin_fonts = (Spinner)dialog.findViewById(R.id.spinner_font);
				final EditText edit_msg=(EditText)dialog.findViewById(R.id.edit_dialog_msg);
				final EditText edit_size=(EditText)dialog.findViewById(R.id.edit_dialog_size);
				final Button btn_color=(Button)dialog.findViewById(R.id.btn_dialog_color);
				Button btn_save=(Button)dialog.findViewById(R.id.btn_dialog_save);
				TextView tv_title=(TextView)dialog.findViewById(R.id.tv_dialog_title);
				final TextView tv_dialog_msg=(TextView)dialog.findViewById(R.id.tv_dialog_sample_text);
		
				
				
				
				 try {
			        	
			            String fontpath="myfonts";
			            list_font_path = getAssets().list(fontpath);
			            String [] list_fonts=new String[list_font_path.length];// assigning large size;
			          
			         
			            if (list_font_path.length > 0) {
			                // This is a folder
			            
			                for (int i = 0; i < list_font_path.length; i++) {
			                	String file =list_font_path[i];
			                	  try {
			                		  String name=file.replace(".ttf", "");
			                  
			                	   list_fonts[i]=name;

			                	  }catch(OutOfMemoryError e)
			                	  {
			                		  e.printStackTrace();
			                		
			                	  }catch (Exception e) {
			              			// TODO: handle exception
			                      	e.printStackTrace();
			              		}
							
			                }
			            }

			           Spinner_Font_Adapter ma = new Spinner_Font_Adapter(Edit_Image.this, getBaseContext(), list_font_path);
			           spin_fonts.setAdapter(ma);
			            
			           spin_fonts.setSelection(header_font_index);
			        }catch(OutOfMemoryError e)
			  	  {
			  		  e.printStackTrace();
			  	  } catch (IOException e) {
			        	e.printStackTrace();
			        }catch (Exception e) {
						// TODO: handle exception
			        	e.printStackTrace();
					}
					
					
					
					
					
					
					try {
						
				        TextView command = (TextView) rel_image.findViewWithTag(button_id);
				        
				        edit_msg.setText(command.getText().toString());
				        tv_dialog_msg.setText(command.getText().toString());
				        tv_dialog_msg.setTextColor(command.getTextColors());
				        btn_color.setTextColor(command.getTextColors());
				        tv_dialog_msg.setTypeface(command.getTypeface());
				        tv_dialog_msg.setTextSize((int)command.getTextSize()/2);
				        edit_size.setText(String.valueOf((int)command.getTextSize()/2));
				      spin_fonts.setSelection(header_font_index);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					
					
					
					tv_title.setText("Header Message");
					
					
					edit_msg.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							try {
								tv_dialog_msg.setText(arg0.toString());
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
					
					edit_size.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							try {
								tv_dialog_msg.setTextSize(Float.valueOf(arg0.toString()));
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
					btn_color.setBackgroundColor(Color_id_dialog);
					tv_dialog_msg.setTextColor(Color_id_dialog);
					btn_color.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							if (!edit_msg.getText().toString().isEmpty() && !edit_size.getText().toString().isEmpty()) {
								Show_Color(btn_color,tv_dialog_msg);
								
								btn_color.setBackgroundColor(Color_id_dialog);
								tv_dialog_msg.setTextColor(Color_id_dialog);
							}else {
								if (!edit_msg.getText().toString().isEmpty())
								{
									edit_msg.setError("*");
								}else {
									edit_size.setError("*");
								}
							}
						}
					});
					
					

					  
			        spin_fonts.setOnItemSelectedListener(new OnItemSelectedListener() {
			            @Override
			            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			            	  
							if (!edit_msg.getText().toString().isEmpty() && !edit_size.getText().toString().isEmpty()) {
								String fontpath="myfonts";

								selected_font_header=fontpath + "/" +list_font_path[position];
									
								    Typeface  mFace = Typeface.createFromAsset(getApplicationContext().getAssets(),selected_font_header);
							    
                            tv_dialog_msg.setTypeface(Typeface.create(mFace, Typeface.NORMAL));
                            header_font_index=position;
							}
							else
							{
								String fontpath="myfonts";

								selected_font_header=fontpath + "/" +list_font_path[position];
							    Typeface  mFace = Typeface.createFromAsset(getApplicationContext().getAssets(),selected_font_header);
							    
	                            tv_dialog_msg.setTypeface(Typeface.create(mFace, Typeface.NORMAL));
								
	                           
							}
			            }

			            @Override
			            public void onNothingSelected(AdapterView<?> parentView) {
			                // your code here
			            }

			        });
			        
			        
				
			        btn_save.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							rel_image.buildDrawingCache();
							Bitmap bm = rel_image.getDrawingCache();
							
							Draw_Text(button_id, bm, tv_dialog_msg, true);
							dialog.dismiss();
						}
					});
				
				dialog.show();
			}
		});
		
		btn_add_footer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
				final String button_id="footer";
				final Dialog dialog = new Dialog(Edit_Image.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
				dialog.setContentView(R.layout.settext_dialog);
				
				Spinner spin_fonts = (Spinner)dialog.findViewById(R.id.spinner_font);
				final EditText edit_msg=(EditText)dialog.findViewById(R.id.edit_dialog_msg);
				final EditText edit_size=(EditText)dialog.findViewById(R.id.edit_dialog_size);
				final Button btn_color=(Button)dialog.findViewById(R.id.btn_dialog_color);
				Button btn_save=(Button)dialog.findViewById(R.id.btn_dialog_save);
				TextView tv_title=(TextView)dialog.findViewById(R.id.tv_dialog_title);
				final TextView tv_dialog_msg=(TextView)dialog.findViewById(R.id.tv_dialog_sample_text);
		
				
				
				
				 try {
			        	
			            String fontpath="myfonts";
			            list_font_path = getAssets().list(fontpath);
			            String [] list_fonts=new String[list_font_path.length];// assigning large size;
			          
			         
			            if (list_font_path.length > 0) {
			                // This is a folder
			            
			                for (int i = 0; i < list_font_path.length; i++) {
			                	String file =list_font_path[i];
			                	  try {
			                		  String name=file.replace(".ttf", "");
			                  
			                	   list_fonts[i]=name;

			                	  }catch(OutOfMemoryError e)
			                	  {
			                		  e.printStackTrace();
			                		
			                	  }catch (Exception e) {
			              			// TODO: handle exception
			                      	e.printStackTrace();
			              		}
							
			                }
			            }
			            
			            
				           Spinner_Font_Adapter ma = new Spinner_Font_Adapter(Edit_Image.this, getBaseContext(), list_font_path);
				           spin_fonts.setAdapter(ma);
				           spin_fonts.setSelection(footer_font_index);
			        }catch(OutOfMemoryError e)
			  	  {
			  		  e.printStackTrace();
			  	  } catch (IOException e) {
			        	e.printStackTrace();
			        }catch (Exception e) {
						// TODO: handle exception
			        	e.printStackTrace();
					}
					
					
					
					
					
					
					try {
					    TextView command = (TextView) rel_image.findViewWithTag(button_id);
				        
				        edit_msg.setText(command.getText().toString());
				        tv_dialog_msg.setText(command.getText().toString());
				        tv_dialog_msg.setTextColor(command.getTextColors());
				        btn_color.setTextColor(command.getTextColors());
				        tv_dialog_msg.setTypeface(command.getTypeface());
				        tv_dialog_msg.setTextSize(command.getTextSize()/2);
				        edit_size.setText(String.valueOf(command.getTextSize()/2));
				        spin_fonts.setSelection(footer_font_index);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					
					
					
					tv_title.setText("Footer Message");
					
					
					edit_msg.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							try {
								tv_dialog_msg.setText(arg0.toString());
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
					
					edit_size.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								int arg3) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							try {
								tv_dialog_msg.setTextSize(Float.valueOf(arg0.toString()));
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
					btn_color.setBackgroundColor(Color_id_dialog);
					tv_dialog_msg.setTextColor(Color_id_dialog);
					btn_color.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							if (!edit_msg.getText().toString().isEmpty() && !edit_size.getText().toString().isEmpty()) {
								Show_Color(btn_color,tv_dialog_msg);
								
								btn_color.setBackgroundColor(Color_id_dialog);
								tv_dialog_msg.setTextColor(Color_id_dialog);
							}
						}
					});
					
					

					  
			        spin_fonts.setOnItemSelectedListener(new OnItemSelectedListener() {
			            @Override
			            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			            	  
							if (!edit_msg.getText().toString().isEmpty() && !edit_size.getText().toString().isEmpty()) {
								String fontpath="myfonts";

								selected_font_header=fontpath + "/" +list_font_path[position];
									
								    Typeface  mFace = Typeface.createFromAsset(getApplicationContext().getAssets(),selected_font_header);
							    
                            tv_dialog_msg.setTypeface(Typeface.create(mFace, Typeface.NORMAL));
                            footer_font_index=position;	
							}
							else
							{
								String fontpath="myfonts";

								selected_font_header=fontpath + "/" +list_font_path[position];
									
							    Typeface  mFace = Typeface.createFromAsset(getApplicationContext().getAssets(),selected_font_header);
							    
	                            tv_dialog_msg.setTypeface(Typeface.create(mFace, Typeface.NORMAL));
								
                            }
			            }

			            @Override
			            public void onNothingSelected(AdapterView<?> parentView) {
			                // your code here
			            }

			        });
			        
			        
				
			        btn_save.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							rel_image.buildDrawingCache();
							Bitmap bm = rel_image.getDrawingCache();
							
							Draw_Text(button_id, bm, tv_dialog_msg, false);
							dialog.dismiss();
						}
					});
				
				dialog.show();
				
			
			}
		});
		
		btn_save_to_sd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				lin_stickers.setVisibility(View.GONE);
				Save_To_Sdcard();

			}
		});
		
		
		btn_crop_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			
				
			
				
			//	rel_image.buildDrawingCache();
			
				 //Bitmap  bitmap_old = Bitmap.createBitmap(rel_image.getDrawingCache());
				
					  
				  //  Canvas canvas = new Canvas(bitmap_old);
				  // Canvas canvas = new Canvas(bitmap_old);
					//  BitmapDrawable bmd=new BitmapDrawable(bitmap_old); 
		     			
			 			
					//  rel_image.setBackground(bmd);
				
					  
						
						//rel_image.buildDrawingCache();
					//	Bitmap bm = Bitmap.createBitmap(rel_image.getDrawingCache());
				static_bmp=new BitmapDrawable(getResources(),bitmap_main);
				
				Intent mIntent=new Intent(getBaseContext(), CropImageUI.class);
				startActivityForResult(mIntent, requestCode);
			}
		});
		
		btn_export.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lin_stickers.setVisibility(View.GONE);
			
				String save_path=Save_To_Sdcard();

				Bitmap mBitmap=BitmapFactory.decodeFile(save_path);
				
				
				Bitmap icon = mBitmap;
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("image/*");
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
				try {
				    f.createNewFile();
				    FileOutputStream fo = new FileOutputStream(f);
				    fo.write(bytes.toByteArray());
				} catch (IOException e) {                       
				        e.printStackTrace();
				}
				share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+save_path));
				startActivity(Intent.createChooser(share, "Share Image"));

	
			}
		});
		
		btn_rotate_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					
					 
					 Bitmap bmp_tmp = null;
					 
						try {
								
							bmp_tmp= rotateImage(bitmap_main);
							bitmap_main=null;
							bitmap_main=bmp_tmp;
						} catch (FileNotFoundException e) {
							System.out.println("File Not Found?");
							e.printStackTrace();
						} catch (IOException e) {
							System.out.println("IO Exception?");
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
					 
					
					 
					 
					 
					 
					 
					 Bitmap scaled_bmp=commen.Scaled_Bitmap(bitmap_main, img_main.getWidth(), img_main.getHeight());
						img_main.setBackground( new BitmapDrawable(getResources(),scaled_bmp));
	
						
				} catch (OutOfMemoryError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		

		
		btn_edit_saturation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
						if (rel_saturation.getVisibility()==View.GONE) {
							Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(),
						            R.anim.left_to_right);

							rel_saturation.startAnimation(bottomUp);
							rel_saturation.setVisibility(View.VISIBLE);
						}else {
							Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
						            R.anim.right_to_left);

							rel_saturation.startAnimation(bottom_down);
							rel_saturation.setVisibility(View.GONE);
							seekbar_saturation.setProgress(256);

							img_main.setBackground( new BitmapDrawable(getResources(),bitmap_main));
							//updated_bmp=null;
						}
						

				
			}
		});
		
		
		btn_set_saturation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
			            R.anim.right_to_left);

				rel_saturation.startAnimation(bottom_down);
				rel_saturation.setVisibility(View.GONE);
				updated_bmp=new BitmapDrawable(getResources(),bitmap_main);
				bitmap_main=null;
				bitmap_main=commen.drawableToBitmap(updated_bmp);
				
			}
		});
		

		
		btn_edit_hue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (rel_hue.getVisibility()==View.GONE) {
					Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.left_to_right);

					rel_hue.startAnimation(bottomUp);
					rel_hue.setVisibility(View.VISIBLE);
				}else {
					Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.right_to_left);

					rel_hue.startAnimation(bottom_down);
					rel_hue.setVisibility(View.GONE);
					seekbar_hue.setProgress(180);
				}
				
			}
		});
		
		
		btn_edit_brightness.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rel_brightness.getVisibility()==View.GONE) {
					Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.left_to_right);

					rel_brightness.startAnimation(bottomUp);
					rel_brightness.setVisibility(View.VISIBLE);
				}else {
					Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
				            R.anim.right_to_left);

					rel_brightness.startAnimation(bottom_down);
					rel_brightness.setVisibility(View.GONE);
					seekbar_brightness.setProgress((int) curBrightnessValue);
					img_main.setBackground( new BitmapDrawable(getResources(),bitmap_main));
				}
				
			}
		});
		
		
		
		
		btn_set_brightness.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
			            R.anim.right_to_left);

				rel_brightness.startAnimation(bottom_down);
				rel_brightness.setVisibility(View.GONE);
				updated_bmp=new BitmapDrawable(getResources(),bitmap_main);
				bitmap_main=null;
				bitmap_main=commen.drawableToBitmap(updated_bmp);;
			}
		});
		
		
		
		
		
		seekbar_saturation.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				
				
				
				 if (bitmap_main != null) {
				    	
					   int progressSat = seekbar_saturation.getProgress();

					   float sat = (float) progressSat / 256;
					 
						Toast.makeText(getApplicationContext(), ""+progress, Toast.LENGTH_SHORT).show();
						updated_bmp = commen.Saturation(getApplicationContext(), commen.drawableToBitmap(new BitmapDrawable(getResources(),bitmap_main)), sat);
						
						img_main.setBackground(updated_bmp);
					  }
				


			}
		});
		
		
		   try {
		    curBrightnessValue = android.provider.Settings.System.getInt(
		      getContentResolver(),
		      android.provider.Settings.System.SCREEN_BRIGHTNESS);
		   } catch (SettingNotFoundException e) {
		    e.printStackTrace();
		   }

		     int screen_brightness = (int) curBrightnessValue;
		    seekbar_brightness.setMax(1000);
		    seekbar_brightness.setProgress(screen_brightness);
		   
		
		seekbar_brightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				
				
				
				 if (bitmap_main != null) {

					
					   int progressVal = seekbar_brightness.getProgress();
					   float settingVal = (float)progressVal / curBrightnessValue;
					 
						updated_bmp = commen.Brightness(getApplicationContext(), commen.drawableToBitmap(new BitmapDrawable(getResources(),bitmap_main)), (int) settingVal);
					
						img_main.setBackground(updated_bmp);
						
						 try {
							    curBrightnessValue = android.provider.Settings.System.getInt(
							      getContentResolver(),
							      android.provider.Settings.System.SCREEN_BRIGHTNESS);
							   } catch (SettingNotFoundException e) {
							    e.printStackTrace();
							   }
						
					  }
				


			}
		});
		
		
		
		seekbar_hue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				
				
				
				 if (bitmap_main != null) {

					   int progressSat = seekbar_hue.getProgress();

					   float sat = (float) progressSat / 180;
					 
						Toast.makeText(getApplicationContext(), ""+progress, Toast.LENGTH_SHORT).show();
						updated_bmp = commen.hue(getApplicationContext(), commen.drawableToBitmap(new BitmapDrawable(getResources(),bitmap_main)), (int) sat);
	
						img_main.setBackground(updated_bmp);
					  }
				


			}
		});
		
		
		btn_set_hue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation bottom_down = AnimationUtils.loadAnimation(getBaseContext(),
			            R.anim.right_to_left);

				rel_hue.startAnimation(bottom_down);
				rel_hue.setVisibility(View.GONE);
				//seekbar_hue.setProgress(180);
				updated_bmp=new BitmapDrawable(getResources(),bitmap_main);
				bitmap_main=null;
				bitmap_main=commen.drawableToBitmap(updated_bmp);;
			}
		});
		
		
	RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rel_editimage);
		
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
		
		
		btn_relese_touch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			for (ImageView img : image_list) {
				img.setOnTouchListener(null);
			}	
			}
		});
	}
	public static void sendViewToBack(final View child) {
	    final ViewGroup parent = (ViewGroup)child.getParent();
	    if (null != parent) {
	        parent.removeView(child);
	        parent.addView(child, 0);
	    }
	}

	
	
	public  OnClickListener GetClick(Button child) {
		
		
		return null;
	  
	}
	protected String Save_To_Sdcard() {
		// TODO Auto-generated method stub

		 
		
		rel_image.buildDrawingCache();
		 Bitmap bitmap_old;
		 if (croper_imagepath.equals("?")) {
			  bitmap_old = Bitmap.createBitmap(rel_image.getDrawingCache());
			  
		}else {
			Bitmap mutableBitmap=BitmapFactory.decodeFile(croper_imagepath);
			
			Bitmap workingBitmap = Bitmap.createBitmap(mutableBitmap);
			bitmap_old = workingBitmap.copy(Bitmap.Config.RGB_565, true);
		
		}
		
			
			  
		  //  Canvas canvas = new Canvas(bitmap_old);
		   Canvas canvas = new Canvas(bitmap_old);
		
//	        try {
//	        	
//	        	TextView command = (TextView) rel_image.findViewWithTag("header");
//	        	  int header_size=(int) command.getTextSize();
//	        	  header_size=header_size/2;
//	        	  ColorStateList c_color=command.getTextColors();
//	        	  int header_color = c_color.getDefaultColor();
//				Paint paint_head=getDrawPaint(command, header_size, header_color,true);
//				    command.setDrawingCacheEnabled(true);
//					//canvas.drawText(command.getText().toString(), X_Head, Y_Head, paint_head);
//				    canvas.drawBitmap(command.getDrawingCache(), 0, 0, paint_head);
//					
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//	
//	        
//	        try {
//	        	 
//		        TextView command_footer = (TextView) rel_image.findViewWithTag("footer");
//		    
//	        	int footer_size=(int) command_footer.getTextSize();
//	        	footer_size=footer_size/2;
//	        	ColorStateList f_color=command_footer.getTextColors();
//	        	int footer_color=f_color.getDefaultColor();
//			    Paint paint_foot=getDrawPaint(command_footer, footer_size, footer_color,false);
//				   command_footer.setDrawingCacheEnabled(true);
//					//canvas.drawText(command_footer.getText().toString(), X_Foot, Y_Foot, paint_foot);
//					  canvas.drawBitmap(command_footer.getDrawingCache(), 0, 0, paint_foot); 
//				
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//	        
//	        
//	        
//	        try {
//	        	
//	        	 
//		        ImageView command_ballon = (ImageView) rel_image.findViewWithTag("ballon");
//		    
//		        
//		        Paint paint_head = new Paint();
//		        //paint.setShader(shader);
//		        paint_head.setAntiAlias(true);
//	        	 command_ballon.setDrawingCacheEnabled(true);
//					//canvas.drawText(command.getText().toString(), X_Head, Y_Head, paint_head);
//				    canvas.drawBitmap(command_ballon.getDrawingCache(), command_ballon.getLeft(), command_ballon.getTop(), paint_head);
//					
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
	 
	  BitmapDrawable bmd=new BitmapDrawable(bitmap_old); 
//			     			
//			
//	  rel_image.setBackground(bmd);
//			
//	  rel_image.buildDrawingCache();
		   
			Bitmap	bitmap = commen.drawableToBitmap(bmd);
			
			 File folder = new File(Environment.getExternalStorageDirectory().toString()+"/PicMessage");   
         if (!folder.exists()){
         	folder.mkdir();
         }
         Long tsLong = System.currentTimeMillis()/1000;
		    String ts = tsLong.toString(); 
       
         File image = new File(folder, "PicMessage_"+ts+".png");
			
	  
	    
	    boolean success = false;

	    // Encode the file as a PNG image.
	    FileOutputStream outStream;
	    try {

	        outStream = new FileOutputStream(image);
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream); 
	        /* 100 to keep full quality of the image */

	        outStream.flush();
	        outStream.close();
	        success = true;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	if (success) {
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		   Toast.makeText(getBaseContext(), "Image Path:- "+image.toString(), Toast.LENGTH_LONG).show();
	}
	return image.toString();
	    
	
	}


	protected Paint getDrawPaint(TextView command,int header_size,int Color_id,boolean head) {    
        
        
        Paint paint_head = new Paint();
      //  paint_head.setStyle(Style.FILL);
        paint_head.setColor(Color_id);
	  //  paint.setTypeface(tf);
        paint_head.setTextAlign(Align.CENTER);

        paint_head.setTextSize(convertToPixels(Edit_Image.this,header_size));
	   

	    
	    Rect textRect = new Rect();
	    paint_head.getTextBounds(command.getText().toString(), 0, command.getText().toString().length(), textRect);
		return paint_head;
	   
	    }


	int initial_Color;

	public Bitmap bmp_blank;
	protected void Show_Color(final Button btn_color, final TextView tv_color) {
		// TODO Auto-generated method stub
	

		        initial_Color = 0;

		        initial_Color=Color_id_dialog;
				
		        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initial_Color, new ColorPickerDialog.OnColorSelectedListener() {

		            @Override
		            public void onColorSelected(int color) {
		            	showToast(color,btn_color,tv_color);
		            	
		            }

		        });
		        colorPickerDialog.show();
				
				
		    
	}
    private void showToast(int color, TextView btn_color, TextView tv_color) {
        String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color);

        Color_id_dialog=Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
		
		btn_color.setBackgroundColor(Color_id_dialog);
		tv_color.setTextColor(Color_id_dialog);
		hideSoftKeyboard();
	
    }

	
	public static int convertToPixels(Context context, int nDP)
	{
	    final float conversionScale = context.getResources().getDisplayMetrics().density;

	    return (int) ((nDP * conversionScale) + 0.5f) ;

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	

	  private class Load_Bitmap extends AsyncTask<String, Void, Bitmap> {
		  	ProgressDialog pd;
		  	@Override
		  	protected void onPreExecute() {
		  	// TODO Auto-generated method stub
		  try {
			  	pd=new ProgressDialog(Edit_Image.this);
			  	pd.setMessage("Loading");
			  	pd.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  	
		  	}
		    @Override
		    protected Bitmap doInBackground(String... urls) {
		    	Bitmap bm = null;
		    	
		                 if (name.equals("gallerypic") || name.contains("PicMessage")) {
		                	  try {
									 bm=BitmapFactory.decodeFile(urls[0]);
									 
								} catch (OutOfMemoryError e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									
									  try {
										  BitmapFactory.Options options = new BitmapFactory.Options();
									        options.inSampleSize = 3;
											 bm=BitmapFactory.decodeFile(urls[0],options);
											 
										} catch (OutOfMemoryError e2) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (Exception e2) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}else {
							  try {
									 bm=commen.getBitmapFromAsset(urls[0],getApplicationContext());
									 
								} catch (OutOfMemoryError e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}

		    	
		    	
		    	
		    	
		    	
		      return bm;
		    }

			@Override
			protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				pd.dismiss();
				
				try {
					Bitmap scaled_bmp=commen.Scaled_Bitmap(result, img_main.getWidth(), img_main.getHeight());
					img_main.setBackground( new BitmapDrawable(getResources(),scaled_bmp));
					img_main.setDrawingCacheEnabled(true);
					bitmap_main = scaled_bmp;
				} catch (Exception e) {
					// TODO: handle exception
					img_main.setBackground( new BitmapDrawable(getResources(),result));
					img_main.setDrawingCacheEnabled(true);
					bitmap_main = result;
				}
				sendViewToBack(img_main);
							  //img.setVisibility(View.GONE);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
//			img_main.setOnTouchListener(new OnTouchListener() {
//				
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					// TODO Auto-generated method stub
//					touch.Touch_Handler(v,event);
//					return true;
//				}
//			});
			
			}
			

		  }
	  
	  
		private void Draw_Text(String btn_id,Bitmap mutableBitmap,final TextView tv_msg_dialog,boolean isheader) {
			// TODO Auto-generated method stub
		    View command = rel_image.findViewWithTag(btn_id);
		    rel_image.removeView(command);

	        final TextView  tv_msg=new TextView(this);
	        tv_msg.setText(tv_msg_dialog.getText().toString());
	        tv_msg.setTextColor(tv_msg_dialog.getTextColors());
	        tv_msg.setTextSize(tv_msg_dialog.getTextSize());
	        tv_msg.setTypeface(tv_msg_dialog.getTypeface());
	        tv_msg.setBackgroundResource(R.drawable.bubble_a);
	        tv_msg.setPadding(5, 5, 5, 5);
	        tv_msg.setTag(btn_id);
	        
	        
	        
	        tv_msg.setTag(btn_id);
	        
	        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		      
	        if (isheader) {
	        	tv_msg.setGravity(Gravity.CENTER_HORIZONTAL);
	        
			}else {
				tv_msg.setGravity(Gravity.CENTER_HORIZONTAL);
		
			}
	      
	   
	       
	      
	        rel_image.addView(tv_msg, lp);

		    tv_msg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), ""+tv_msg.getTag().toString(), Toast.LENGTH_LONG).show();
				}
			});

	        drag_head=true;

		}
		
		



			@Override
			public boolean onDrag(View layoutview, DragEvent dragevent) {
			      int action = dragevent.getAction();
			      switch (action) {
			      case DragEvent.ACTION_DRAG_STARTED:
			          Log.d("LOGCAT", "Drag event started");
			    	break;
			      case DragEvent.ACTION_DRAG_ENTERED:
			    	  Log.d("LOGCAT", "Drag event entered into "+layoutview.toString());
			    	break;
			      case DragEvent.ACTION_DRAG_EXITED:
			    	  Log.d("LOGCAT", "Drag event exited from "+layoutview.toString());
			    	break;
			      case DragEvent.ACTION_DROP:
			    	try {
			    		Log.d("LOGCAT", "Dropped");
				    	View view = (View) dragevent.getLocalState();
				        ViewGroup owner = (ViewGroup) view.getParent();
				        
//				        ViewGroup layout = (ViewGroup) findViewById(R.id.rel_image);
//				        View command = layout.findViewWithTag("footer");
//				        
				        
				        owner.removeView(view);
				        
				        float X=dragevent.getX();
				        float Y=dragevent.getY();

//				        if (drag_head) {
//					        
//					        X_Head=X;
//					        Y_Head=Y;
//					        
//						}else {
//							   X_Foot=X;
//						        Y_Foot=Y;	
//						}
				        
				        
				        X_Balloon=X;
				        Y_Balloon=Y;
				        
				        view.setX(X);
				        view.setY(Y);
				        RelativeLayout container = (RelativeLayout) layoutview;
				        container.addView(view);
				        view.setVisibility(View.VISIBLE);
					} catch (Exception e) {
						// TODO: handle exception
					}
			        break;
			      case DragEvent.ACTION_DRAG_ENDED:
			    		  Log.d("LOGCAT", "Drag ended");
				      break;
			      default:
			        break;
			      }
			      return true;
		    }



			public  void hideSoftKeyboard() {
				try {

				    InputMethodManager inputMethodManager = (InputMethodManager)  Edit_Image.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
				    inputMethodManager.hideSoftInputFromWindow(Edit_Image.this.getCurrentFocus().getWindowToken(), 0);
				
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		
			public static boolean rotate_enabled;
			public static  List<ImageView> image_list=new ArrayList<ImageView>();
			String drag_btn_id;
			int imgview_index=0;
			//SquareImageView[] list_image=new SquareImageView[50];	
			private void Draw_Word_Balloon(final String btn_id,Bitmap resource) {
				// TODO Auto-generated method stub
		 
//		 
//		      
//		        final SquareImageView my_ballon = new SquareImageView(this);
//		        my_ballon.setAdjustViewBounds(true);
//		        my_ballon.setTag(btn_id);
//		        my_ballon.setId(imgview_index);
//		        my_ballon.setScaleType(ScaleType.MATRIX);
//		       
//		         my_ballon.setImageBitmap(resource);
//		      
//
//		      
////		        
//		        drag_btn_id=btn_id;
//		       my_ballon.setLayoutParams(lp_rel);
////
//		        rel_image.addView(my_ballon);
//
//		        my_ballon.setOnDragListener(this);
//		        my_ballon.getLayoutParams().height=152;
//		        my_ballon.getLayoutParams().width=552;
//		        
//		        int[] values=new int[2];
//		        my_ballon.getLocationOnScreen(values);
//		       // Toast.makeText(getApplicationContext(), "Image- X= "+values[0]+", Y="+values[1], Toast.LENGTH_SHORT).show();
//		        
//		        image_list.add(my_ballon);
//
//
//		        image_list.get(imgview_index).setOnClickListener(handlonclick(image_list.get(imgview_index)));
//		       
//				
				ImageView  image = new ImageView(this);
		        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( 
		                FrameLayout.LayoutParams.FILL_PARENT,
		                FrameLayout.LayoutParams.FILL_PARENT);

		         Display display = getWindowManager().getDefaultDisplay(); 
		          int width = display.getWidth();  // deprecated
		          int height = display.getHeight();  // deprecated
		          image.setLayoutParams(params);
		image.setScaleType(ImageView.ScaleType.MATRIX);
		      image.setImageBitmap(resource);
		      image.setTag(btn_id);
		      image.setId(imgview_index);
		      rel_image.addView(image);
		        image.getLayoutParams().height = rel_image.getHeight();
		        image.getLayoutParams().width =  rel_image.getWidth();
		   
		       int[] values = new int[2]; 
		        image.getLocationOnScreen(values);
		        System.out.println("x location::"+values[0]);
		        System.out.println(" y location::"+values[1]);


		       image.setOnTouchListener(new Touch_Listner());
		       

				
//			        my_ballon.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							ImageView view=(ImageView)v;
//							view.setOnTouchListener(new MyTouchListener());
//						}
//					});
				
				
				
				
				
				
				
				
				
//		        my_ballon.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						my_ballon.requestLayout();
//						Toast.makeText(getBaseContext(), ""+btn_id, Toast.LENGTH_SHORT).show();
//						touch=new Touch_Listner(my_ballon, Edit_Image.this);
//						my_ballon.setImageMatrix(touch.getMatrix());
//						my_ballon.invalidate();
//					}
//				});
		        
		        
//		        my_ballon.setOnTouchListener(new OnTouchListener() {
//					
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						// TODO Auto-generated method stub
//				        my_ballon.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Toast.makeText(getBaseContext(), ""+btn_id, Toast.LENGTH_SHORT).show();
//								touch=new Touch_Listner(my_ballon, Edit_Image.this);
//							}
//						});
//						return true;
//					}
//				});
		        
		        
//		        my_ballon.setOnFocusChangeListener(new OnFocusChangeListener() {
//					
//					@Override
//					public void onFocusChange(View v, boolean hasFocus) {
//						// TODO Auto-generated method stub
//						if (!hasFocus) {
//							my_ballon.setOnClickListener(new OnClickListener() {
//								
//								@Override
//								public void onClick(View v) {
//									// TODO Auto-generated method stub
//									touch=new Touch_Listner(my_ballon, Edit_Image.this);
//								}
//							});
//						}
//					}
//				});
			}
			
			
			private OnClickListener handlonclick(ImageView my_ballon) {
				// TODO Auto-generated method stub
				return new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SquareImageView img=(SquareImageView)v;
						Toast.makeText(getBaseContext(), ""+img.getId(), Toast.LENGTH_SHORT).show();
					}
				};
			}
			@Override
			public boolean onTouchEvent(MotionEvent e) {
			    return gestureDetector.onTouchEvent(e);
			}


			Commet_Functions commen=new Commet_Functions();
			String croper_imagepath="?";
			@Override
			protected void onActivityResult(int _requestCode, int resultCode, Intent data) {
				if(requestCode==_requestCode&&resultCode==RESULT_OK)
				{
					croper_imagepath=data.getStringExtra("cropImagePath");
					//rel_image.removeAllViews();
					
				
					try {
						Bitmap mutableBitmap=BitmapFactory.decodeFile(croper_imagepath);
						
						Bitmap workingBitmap = Bitmap.createBitmap(mutableBitmap);
						Bitmap bitmap_croped = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
						
						
						
						Bitmap scaled_bmp = commen.Scaled_Bitmap(bitmap_croped, img_main.getWidth(), img_main.getHeight());
						img_main.setBackground(new BitmapDrawable(getResources(),scaled_bmp));
						bitmap_main=null;
						bitmap_main=scaled_bmp;
					} catch (OutOfMemoryError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				
					
				}
				
				
				if(style_requestCode==_requestCode)
				{
				
					
					try {
						String res_id=data.getStringExtra("stylepath");
						 Bitmap myBitmap = commen.getBitmapFromAsset(res_id, getApplicationContext());
						Draw_Word_Balloon("ballunid_"+imgview_index,myBitmap);
						imgview_index=imgview_index+1;
					} catch (OutOfMemoryError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				if(requestCode_photo==_requestCode&&resultCode==RESULT_OK)
				{
				
					
					try {
						img_main.setBackground(static_bmp);
						bitmap_main=null;
						bitmap_main=commen.drawableToBitmap(static_bmp);
					} catch (OutOfMemoryError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				super.onActivityResult(_requestCode, resultCode, data);
			}


		
			 
		    
		    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		        @Override
		        public boolean onDown(MotionEvent e) {
		            return true;
		        }
		        // event when double tap occurs
		        @Override
		        public boolean onDoubleTap(MotionEvent e) {
		            float x = e.getX();
		            float y = e.getY();

		            Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");

		            return true;
		        }
		    }
		    
			private Bitmap rotateImage(Bitmap bitmap) throws FileNotFoundException, IOException ,Exception
			{
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				Bitmap bitmap_temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix, true);

				return bitmap_temp;
			}
			
			@Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
				
				
				

				AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Image.this);
				builder.setMessage("Are you sure you want to exit from Editor?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {

					        	 
					        			
					        			   try {

					        					startAppAd.onBackPressed();
					        					   bitmap_main.recycle();
					        					finish();
					        					
										} catch (Exception e) {
											// TODO: handle exception
											
										}
					        			
					        
				           }
				       })
				    	
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.dismiss();
				           }
				       });
				
				AlertDialog alert = builder.create();
				alert.show();
			
				
				
				
				
				
				
				
				
				
				
			
			}
			


			@Override
			protected void onDestroy()
			{
			        super.onDestroy();
			        bitmap_main.recycle();
			        unbindDrawables(findViewById(R.id.rel_editimage));
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

			public String parseUriToFilename(Uri uri) {
			String selectedImagePath = null;
			String filemanagerPath = uri.getPath();
			
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			
			if (cursor != null) {
			//Here you will get a null pointer if cursor is null
			//This can be if you used OI file manager for picking the media
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
			}
			
			if (selectedImagePath != null) {
			return selectedImagePath;
			}
			else if (filemanagerPath != null) {
			return filemanagerPath;
			}
			return null;
			}


}
