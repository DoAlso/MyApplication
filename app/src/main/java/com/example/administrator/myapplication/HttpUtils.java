package com.example.administrator.myapplication;


import java.io.IOException;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import de.robv.android.xposed.XposedBridge;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 异步的Post请求
     *
     * @param url    url
     * @param params params
     * @return responseStr
     */
    public static void postAsyn(String url, Map<String, String> params, final Handler handler) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key).toString());
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = Message.obtain();
                message.what = response.code();
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }
    public static void postAsyn(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key).toString());
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                XposedBridge.log("onFailure!!!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                XposedBridge.log(result+"onResponse!!!");
            }
        });
    }
}
