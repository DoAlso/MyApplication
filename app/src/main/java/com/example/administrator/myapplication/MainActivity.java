package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	EditText et_akey;
	EditText et_wkey;
	EditText et_skey;
	Button btn_save;
	Button btn_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_start = (Button) findViewById(R.id.Button01);
		btn_save = (Button) findViewById(R.id.button1);
		et_akey = (EditText) findViewById(R.id.editText2);
		et_wkey = (EditText) findViewById(R.id.editText1);
		et_skey = (EditText) findViewById(R.id.EditText01);

		SharedPreferences sp = getSharedPreferences("key", Activity.MODE_PRIVATE);
		if (sp != null) {
			String akey = sp.getString("akey", "");
			String wkey = sp.getString("wkey", "");
			String skey = sp.getString("skey", "");
			et_akey.setText(akey);
			et_wkey.setText(wkey);
			et_skey.setText(skey);
		}
		btn_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), DaemonService.class);
				startService(intent);
				Toast.makeText(getApplicationContext(), "启动成功", Toast.LENGTH_SHORT).show();
				// 注册定时回调广播
				AlarmReceiver alarmReceiver = new AlarmReceiver();
				IntentFilter alarmIntentFilter = new IntentFilter();
				alarmIntentFilter.addAction("im.jik.task");
				registerReceiver(alarmReceiver, alarmIntentFilter);
			}
		});
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String akey = et_akey.getText().toString();
				String wkey = et_wkey.getText().toString();
				String skey = et_skey.getText().toString();
				SharedPreferences sharedPre = getApplication().getSharedPreferences("key",
						Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPre.edit();
				if (akey == null || akey.equals("")) {
					akey = "-1";
				}
				if (wkey == null || wkey.equals("")) {
					wkey = "-1";
				}
				if (skey == null || skey.equals("")) {
					skey = "-1";
				}
				editor.putString("akey", akey);
				editor.putString("wkey", wkey);
				editor.putString("skey", skey);
				editor.commit();
				Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();;
			}
		});
	}
}
