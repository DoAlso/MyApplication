package com.example.administrator.myapplication;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class GetOrderTask extends Thread {
	private Context context;
	private Handler handler;
	private static final String TASK_GET = "server/service/taskGet.do";
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SharedPreferences sharedPre1 = context.getSharedPreferences("key", Activity.MODE_PRIVATE);
			if (sharedPre1 != null) {
				String akey = sharedPre1.getString("akey", "");
				String wkey = sharedPre1.getString("wkey", "");
				String skey = sharedPre1.getString("skey", "");
				TaskBean loginBean = new TaskBean();
				loginBean.setAlipay_key(akey);
				loginBean.setWechat_key(wkey);
				loginBean.setKey(skey);
				String loginJson = JSON.toJSONString(loginBean);
				String encode = RC4.encode(loginJson, Pay.DEV_KEY);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("data", encode);
				HttpUtils.postAsyn(new StringBuilder(Pay.URL).append(TASK_GET).toString(), map, handler);
			}
		}
	}

	public GetOrderTask() {

	}

	public GetOrderTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}
}
