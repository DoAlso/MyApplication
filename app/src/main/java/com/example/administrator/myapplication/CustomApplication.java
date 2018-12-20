package com.example.administrator.myapplication;


import android.app.Application;
import android.content.Context;

/**
 * 自定义全�?Applcation�?
 * 
 * @ClassName: CustomApplication
 * @Description: TODO
 * @author smile
 * @date 2014-5-19 下午3:25:00
 */
public class CustomApplication extends Application {

	public static CustomApplication mInstance;
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		mInstance = this;
	}

	public static CustomApplication getInstance() {
		return mInstance;
	}

	public static Context getContext() {
		return context;
	}
}
