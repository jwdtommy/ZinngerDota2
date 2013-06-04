package com.example.model;

import android.app.Application;
import android.util.Log;

public class Dota2App extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("Zinger","start Dota2App");
	}
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i("Zinger","onTerminate Dota2App");
	}
	
	
	
}
