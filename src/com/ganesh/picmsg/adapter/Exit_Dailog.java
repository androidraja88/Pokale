package com.ganesh.picmsg.adapter;

import com.startapp.android.publish.StartAppAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.PowerManager.WakeLock;

public class Exit_Dailog {
static Context _context;
static Activity _activity;
static String _packagename;
public Exit_Dailog() {
	super();
	// TODO Auto-generated constructor stub
}

public static final String MyPREFERENCES = "ahare_pref" ;
static SharedPreferences sharedpreferences;
static StartAppAd startAppAd;
public Exit_Dailog(Context context,Activity activity,StartAppAd startAppobject,String packagename) {
	super();
	Exit_Dailog._context = context;
	Exit_Dailog._activity=activity;
	Exit_Dailog.startAppAd=startAppobject;
	Exit_Dailog._packagename=packagename;
	sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	ExitDialog();
}

public static void ExitDialog() {
	// TODO Auto-generated method stub
  	try {
  		String inf=sharedpreferences.getString(rate_click, "");
		   if (inf.equals("")) {
				With_Rete_Button();
			}else {
				AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
				builder.setMessage("Are you sure you want to exit?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {

					        	 
					        			
					        			   try {

							        		   startAppAd.onBackPressed();
							        			_activity.finish();
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
	} catch (Exception e) {
		// TODO: handle exception
		With_Rete_Button();
	}


}

static String rate_click="clicked";

private static void With_Rete_Button() {
	// TODO Auto-generated method stub
	AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
	builder.setMessage("Are you sure you want to exit?")
	       .setCancelable(false)
	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {

		        	   try {
		        		 
		        			startAppAd.onBackPressed();
		        			
		        			_activity.finish();
					} catch (Exception e) {
						// TODO: handle exception
					}
	        	   
	        	   
	           }
	       })
	    		
	 		.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		        	   Editor editor = sharedpreferences.edit();
		        	   editor.putString(rate_click, "clicked");
		        	   editor.commit();
		        	   Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id="+_packagename));
						_activity.startActivity(intent);
		                dialog.dismiss();
		                
		                
		                
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


}
