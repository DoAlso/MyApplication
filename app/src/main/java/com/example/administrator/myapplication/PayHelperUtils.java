package com.example.administrator.myapplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Base64;

public class PayHelperUtils {
	
	public static String WECHATSTART_ACTION = "com.payhelper.wechat.start";
	public static String ALIPAYSTART_ACTION = "com.payhelper.alipay.start";
	public static String MSGRECEIVED_ACTION = "com.tools.payhelper.msgreceived";
	public static List<QrCodeBean> qrCodeBeans=new ArrayList<QrCodeBean>();
	public static List<OrderBean> orderBeans=new ArrayList<OrderBean>();
	
	/*
	 * 启动一个app
	 */
	public static void startAPP() {
		try {
			Intent intent = new Intent(CustomApplication.getInstance().getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			CustomApplication.getInstance().getApplicationContext().startActivity(intent);
		} catch (Exception e) {
		}
	}

	/**
	 * 将图片转换成Base64编码的字符串
	 * 
	 * @param path
	 * @return base64编码的字符串
	 */
	public static String imageToBase64(String path) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		InputStream is = null;
		byte[] data = null;
		String result = null;
		try {
			is = new FileInputStream(path);
			// 创建一个字符流大小的数组。
			data = new byte[is.available()];
			// 写入数组
			is.read(data);
			// 用默认的编码格式进行编码
			result = Base64.encodeToString(data, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		result = "\"data:image/gif;base64," + result + "\"";
		return result;
	}
	
	public static void sendAppMsg(String money,String mark,String type,Context context){
		Intent broadCastIntent = new Intent();
		if(type.equals("alipay")){
			broadCastIntent.setAction(ALIPAYSTART_ACTION);
		}else if(type.equals("wechat")){
			broadCastIntent.setAction(WECHATSTART_ACTION);
		}
        broadCastIntent.putExtra("mark", mark);
        broadCastIntent.putExtra("money", money);
        context.sendBroadcast(broadCastIntent);
	}
	
    
    /**  
     * 方法描述：判断某一应用是否正在运行  
     *  
     * @param context     上下文  
     * @param packageName 应用的包名  
     * @return true 表示正在运行，false表示没有运行  
     */  
    public static boolean isAppRunning(Context context, String packageName) {  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);  
        if (list.size() <= 0) {  
            return false;  
        }  
        for (ActivityManager.RunningTaskInfo info : list) {  
            if (info.baseActivity.getPackageName().equals(packageName)) {  
                return true;  
            }  
        }  
        return false;  
    }  
    
    /*
	 * 启动一个app
	 */
	public static void startAPP(Context context, String appPackageName) {
		try {
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
			context.startActivity(intent);
		} catch (Exception e) {
		}
	}
	
	
	
	public static void sendmsg(Context context,String msg){
		Intent broadCastIntent = new Intent();
		broadCastIntent.putExtra("msg", msg);
        broadCastIntent.setAction(MSGRECEIVED_ACTION);
        context.sendBroadcast(broadCastIntent);
	}
	

	
	public static String getCurrentDate(){
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
		return df.format(day);  
	}
	
	public static String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private static String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
    
    /**
	 * 获取当前本地apk的版本
	 *
	 * @param mContext
	 * @return
	 */
	public static int getVersionCode(Context mContext) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			sendmsg(mContext, "getVersionCode异常" + e.getMessage());
		}
		return versionCode;
	}

	/**
	 * 获取版本号名称
	 *
	 * @param context
	 *            上下文
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			sendmsg(context, "getVerName异常" + e.getMessage());
		}
		return verName;
	}
    
    public static String getServerUrl(Context context){
    	return readAssertResource(context, "server.txt");
    }
}
