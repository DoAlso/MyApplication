package com.example.administrator.myapplication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class DaemonService extends Service {
	private GetOrderTask t;
	public int time = 60;
	public boolean isrun = false;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				try {
					t = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "statu:" + msg.what, Toast.LENGTH_SHORT).show();
				t = new GetOrderTask(getApplicationContext(), handler);
				t.start();
			} else {
				String result = msg.obj.toString();
				String decode = RC4.decode(result, Pay.DEV_KEY);
				JSONObject l_Json = null;
				try {
					l_Json = JSON.parseObject(decode);
					if (isrun) {
						isrun = false;
						PayHelperUtils.startAPP();
					}
					JSONObject datas = l_Json.getJSONObject("data");
					if (datas != null) {
						batch_start(getApplicationContext(), //
								datas.getString("trade_no"), //
								datas.getString("amount"), //
								datas.getString("type"));//
						isrun = true;
					}
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(), "JSON:" + l_Json, Toast.LENGTH_SHORT).show();
					e.printStackTrace();

				}
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void buildForegroundNotification() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			Notification.Builder builder = new Notification.Builder(this);
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setContentTitle("支付助手");
			builder.setContentText("支付助手正在运行中...");
			builder.setAutoCancel(false);
			builder.setOngoing(true);
			startForeground(100, builder.build());
		} else {
			startForeground(100, new Notification());
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		buildForegroundNotification();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int triggerTime = time * 1000;
		Intent i = new Intent("im.jik.task");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), triggerTime, pi);

	}

	public static void batch_start(Context context, String mark, String money, String type) {
		Intent broadCastIntent = new Intent();
		if (type.equals("alipay")) {
			broadCastIntent.setAction("com.payhelper.alipay.start");
		} else if (type.equals("wechat")) {
			broadCastIntent.setAction("com.payhelper.wechat.start");
		}
		broadCastIntent.putExtra("mark", mark);
		broadCastIntent.putExtra("money", money);
		context.sendBroadcast(broadCastIntent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			t.stop();
			t = null;
			Toast.makeText(getApplicationContext(), "statu:service手动停止", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		time = intent.getIntExtra("time", 60);
		if (t != null) {
			return START_STICKY;
		}
		this.t = new GetOrderTask(getApplicationContext(), handler);
		t.start();
		return START_STICKY;
	}
}
