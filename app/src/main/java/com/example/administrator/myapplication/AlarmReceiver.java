package com.example.administrator.myapplication;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private static final String  DEAMON_SERVICE= "com.example.administrator.myapplication.DaemonService";
	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (DEAMON_SERVICE.equalsIgnoreCase(service.service.getClassName())) {
				PayHelperUtils.startAPP();
				Toast.makeText(context, "守护成功", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		Toast.makeText(context, "service死亡", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(context, DaemonService.class);
		context.startService(i);
	}

}
