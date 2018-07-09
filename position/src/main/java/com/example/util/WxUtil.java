package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxUtil{

	private static final long serialVersionUID = 1L;
	private static final String wx_token = "fujisoft";
	//掌上关爱公众号appid
	public static final String wx_appid = "wx09b3926c38a66b9e";
//	public static final String wx_appid = "wx787b338aaa35fd56";
	//掌上关爱公众号appsecret
	public static final String wx_appsecret = "11978f1098470ad6579e10713af2d074";
//	public static final String wx_appsecret = "5846aee8ae146fec836535a857dd15d4";
	public static final String wx_http = "www.guanai100.cn";
//	public static final String wx_http = "b6db47a7.ngrok.io%2Fchild";
	//掌上关爱教师版公众号appid
	public static final String wx_teacher_appid = "wx30d4bb136a015fdc";
	//掌上关爱教师版公众号appsecret
	public static final String wx_teacher_appsecret = "fc81aa09bb3a8c8c12386e28bd836ff6";

	
	/**
     * 获取接口访问凭证
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return access_token 凭证
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
     * 获取jsapi_ticket
     * 
     * @param access_token 接口访问凭证
     * @return jsapi_ticket 
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
	
    /**
     * 获取签名signature
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}
    
    private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
    
    private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
    
}
