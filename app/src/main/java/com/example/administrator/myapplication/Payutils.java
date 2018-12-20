package com.example.administrator.myapplication;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.example.administrator.myapplication.bean.QRCodeBean;


public class Payutils {
	private static final String WECHATURL = "server/service/uploadCode.do";

	public static void postUrl(String key, String mark, String QRCodeurl, int type) {
		QRCodeBean loginBean = new QRCodeBean();
		loginBean.setKey(key);
		loginBean.setOrder_id(mark);
		loginBean.setQrcode(QRCodeurl);

		String loginJson = JSON.toJSONString(loginBean);
		String encode = RC4.encode(loginJson, key);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("data", encode);
		HttpUtils.postAsyn(new StringBuilder(Pay.URL).append(WECHATURL).toString(), map);

	}
}
