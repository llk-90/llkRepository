package com.fh.controller.weixin.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.configure.ConfigureController;
import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.util.WXGetTokenAndTicket;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/getConfig")
public class GetConfig extends BaseController{
	public static String token = null;
	public static String time = null;
	public static String jsapi_ticket = null;
	/**
	 * 
	 * @param appId
	 *            公账号appId
	 * @param appSecret
	 * @param url
	 *            当前网页的URL，不包含#及其后面部分
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value="/getConfig")
	@ResponseBody
	public static String getParam(String url,HttpServletRequest request) throws IOException, ParseException {
		Configure configure = new Configure();
		String appId = configure.getAppId();
		String appSecret = configure.getAppSecret();
		ServletContext applicationContext = request.getSession().getServletContext();
		
		if(applicationContext.getAttribute("token_time")!=null){
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			
			//将application 中存放的时间加两小时得到appDate
			java.util.Date appDate=sdf.parse((String) applicationContext.getAttribute("token_time"));  
			Calendar c = Calendar.getInstance();
			c.setTime(appDate);
			c.add(Calendar.HOUR_OF_DAY, +2);
			appDate = c.getTime();
			
			Date now = new Date();
			System.out.println(now+"============="+appDate);
			if(now.before(appDate)){
				token = (String) applicationContext.getAttribute("token");
				jsapi_ticket = (String) applicationContext.getAttribute("jsapi_ticket");
			}else{
				token = WXGetTokenAndTicket.getAccess_token(appId, appSecret);
				jsapi_ticket = WXGetTokenAndTicket.getJsApiTicket(token);
				time = getTime();
				applicationContext.setAttribute("token", token);
				applicationContext.setAttribute("jsapi_ticket", jsapi_ticket);
				applicationContext.setAttribute("token_time", time);
			}
		}else{
			token = WXGetTokenAndTicket.getAccess_token(appId, appSecret);
			jsapi_ticket = WXGetTokenAndTicket.getJsApiTicket(token);
			time = getTime();
			applicationContext.setAttribute("token", token);
			applicationContext.setAttribute("jsapi_ticket", jsapi_ticket);
			applicationContext.setAttribute("token_time", time);
		}
		
		/*String url = getUrl( request);*/

		Map<String, String> params = sign(jsapi_ticket, url);
		params.put("appid", appId);

		JSONObject jsonObject = JSONObject.fromObject(params);
		jsonObject.put("token", token);
		String jsonStr = jsonObject.toString();
		System.out.println(jsonStr);
		return jsonStr;
	}

	private static String getUrl(HttpServletRequest request) {

		StringBuffer requestUrl = request.getRequestURL();

		String queryString = request.getQueryString();
		String url = requestUrl + "?" + queryString;
		return url;
	}

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String str;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(str.getBytes("UTF-8"));
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

	// 获取当前系统时间 用来判断access_token是否过期
	public static String getTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(dt));
		return sdf.format(dt);
	}
}