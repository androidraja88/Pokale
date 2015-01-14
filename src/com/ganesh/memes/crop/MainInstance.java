package com.ganesh.memes.crop;

import android.app.Application;

public class MainInstance extends Application
{
	private static MainInstance instance;
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
		
	}

	public static MainInstance getInstance()
	{
		return instance;
	}

}
