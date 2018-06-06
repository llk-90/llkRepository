package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class WXGetTokenAndTicket {
	/**
     * 获取接口访问凭证
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
	 * @throws IOException 
     */
    public static String getAccess_token(String appid, String appsecret) throws IOException {
            //凭证获取(GET)
            String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
		StringBuffer sb = new StringBuffer(requestUrl);
//		System.out.println("URL:" + sb);

		// 发送请求
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String access_token = null;
		// 返回结果
		while ((in.read()) != -1) {
			String jsonMessage = "{"+in.readLine();
			JSONObject json=JSONObject.fromObject(jsonMessage);
			if (null != json) {
	           access_token = json.getString("access_token");
	        }
		}
		System.out.println("access_token ============= >"+access_token);
        return access_token;
    }
    
    
    /**
     * 调用微信JS接口的临时票据
     * 
     * @param access_token 接口访问凭证
     * @return
     * @throws IOException 
     */
    public static String getJsApiTicket(String access_token) throws IOException {
        String baseurl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String requestUrl = baseurl.replace("ACCESS_TOKEN", access_token);
        // 发起GET请求获取凭证
        StringBuffer sb = new StringBuffer(requestUrl);
        URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		// 返回结果
		String ticket = null;
		while ((in.read()) != -1) {
			String jsonMessage = "{"+in.readLine();
			JSONObject json=JSONObject.fromObject(jsonMessage);
			if (null != json) {
				ticket = json.getString("ticket");
	        }
		}
		System.out.println("access_token ============= >"+ticket);
        return ticket;
    }
}
