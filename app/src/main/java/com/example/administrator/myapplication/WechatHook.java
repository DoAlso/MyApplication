package com.example.administrator.myapplication;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class WechatHook {
	
	public static String BILLRECEIVED_ACTION = "com.tools.payhelper.billreceived";
	public static String QRCODERECEIVED_ACTION = "com.tools.payhelper.qrcodereceived";
	private static final String PACKAGE_NAME = "com.example.administrator.myapplication";
	protected void hook(final ClassLoader appClassLoader,final Context context) {
		// TODO Auto-generated method stub
		
		try {
			Class<?> clazz=XposedHelpers.findClass("com.tencent.mm.plugin.collect.b.s", appClassLoader);
			XposedBridge.hookAllMethods(clazz, "a", new XC_MethodHook() {
				
				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
				}
				
				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					if(PayHelperUtils.getVerName(context).equals("6.6.7")){
						Field markField = XposedHelpers.findField(param.thisObject.getClass(), "desc");
						String mark = (String) markField.get(param.thisObject);
						Field payurlField = XposedHelpers.findField(param.thisObject.getClass(), "swE");
						String payurl = (String) payurlField.get(param.thisObject);
						//////////////////////////////////////////////////////////////
						XSharedPreferences xsp1 = new XSharedPreferences(PACKAGE_NAME, "key");
                        String key = xsp1.getString("skey", "");
						Payutils.postUrl(key, mark, payurl, 1);

					}else if(PayHelperUtils.getVerName(context).equals("6.6.6")){
						Field moneyField = XposedHelpers.findField(param.thisObject.getClass(), "llG");
						double money = (Double) moneyField.get(param.thisObject);
						
						Field markField = XposedHelpers.findField(param.thisObject.getClass(), "desc");
						String mark = (String) markField.get(param.thisObject);
						
						Field payurlField = XposedHelpers.findField(param.thisObject.getClass(), "1lF");
						String payurl = (String) payurlField.get(param.thisObject);
						
						XposedBridge.log(money+"  "+mark+"  "+payurl);
						
						
					}
					
				}
			});
			
		} catch (Exception e) {
		}
		try {
			XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.collect.ui.CollectCreateQRCodeUI",appClassLoader, "initView",
					new XC_MethodHook() {
				
				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
				}
				
				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					//寰俊6.6.7鏂扮増淇
					//XposedBridge.log("Hook寰俊寮?濮?......");
					if(PayHelperUtils.getVerName(context).equals("6.6.7")){
						Intent intent = ((Activity) param.thisObject).getIntent();
						String mark=intent.getStringExtra("mark");
						String money=intent.getStringExtra("money");
						//鑾峰彇WalletFormView鎺т欢
						Field WalletFormViewField = XposedHelpers.findField(param.thisObject.getClass(), "hXD");
						Object WalletFormView = WalletFormViewField.get(param.thisObject);
						Class<?> WalletFormViewClass=XposedHelpers.findClass("com.tencent.mm.wallet_core.ui.formview.WalletFormView", appClassLoader);
						//鑾峰彇閲戦鎺т欢
						Field AefField = XposedHelpers.findField(WalletFormViewClass, "uZy");
						Object AefView = AefField.get(WalletFormView);
						//call璁剧疆閲戦鏂规硶
						XposedHelpers.callMethod(AefView, "setText", money);
						//call璁剧疆澶囨敞鏂规硶
						Class<?> clazz=XposedHelpers.findClass("com.tencent.mm.plugin.collect.ui.CollectCreateQRCodeUI", appClassLoader);
						XposedHelpers.callStaticMethod(clazz, "a", param.thisObject,mark);
						XposedHelpers.callStaticMethod(clazz, "c", param.thisObject);
						//鐐瑰嚮纭畾
						Button click=(Button)XposedHelpers.callMethod(param.thisObject, "findViewById",2131756838);
						click.performClick();
					}else if(PayHelperUtils.getVerName(context).equals("6.6.6")){
						Intent intent = ((Activity) param.thisObject).getIntent();
						String mark=intent.getStringExtra("mark");
						String money=intent.getStringExtra("money");
						//鑾峰彇WalletFormView鎺т欢
						Field WalletFormViewField = XposedHelpers.findField(param.thisObject.getClass(), "loz");
						Object WalletFormView = WalletFormViewField.get(param.thisObject);
						Class<?> WalletFormViewClass=XposedHelpers.findClass("com.tencent.mm.wallet_core.ui.formview.WalletFormView", appClassLoader);
						//鑾峰彇閲戦鎺т欢
						Field AefField = XposedHelpers.findField(WalletFormViewClass, "Aef");
						Object AefView = AefField.get(WalletFormView);
						//call璁剧疆閲戦鏂规硶
						XposedHelpers.callMethod(AefView, "setText", money);
						//call璁剧疆澶囨敞鏂规硶
						Class<?> clazz=XposedHelpers.findClass("com.tencent.mm.plugin.collect.ui.CollectCreateQRCodeUI", appClassLoader);
						XposedHelpers.callStaticMethod(clazz, "a", param.thisObject,mark);
						XposedHelpers.callStaticMethod(clazz, "c", param.thisObject);
						//鐐瑰嚮纭畾
						Button click=(Button)XposedHelpers.callMethod(param.thisObject, "findViewById",2131656780);
						click.performClick();
					}
					
				}
			});
		} catch (Exception e) {
		}
		
	}
}
