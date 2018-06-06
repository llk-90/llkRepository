package com.fh.controller.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.controller.weixin.common.GetConfig;
import com.fh.util.WXGetTokenAndTicket;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxUtil extends HttpServlet {

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

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		String[] str = { wx_token, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

		// 确认请求来至微信
		if (digest.equals(signature)) {
			response.getWriter().print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * String openId = ""; try { Map<String, String> map =
		 * parseXml(request); if (map.get("FromUserName") != null) { openId =
		 * map.get("FromUserName"); } } catch (Exception e) { }
		 */
		/*
		 * List<Map<String, Object>> ulist = getUserInfo(wx_appid, wx_appsecret,
		 * request, response); for (Map<String, Object> map : ulist) { for
		 * (Map.Entry<String, Object> entry : map.entrySet()) { String key =
		 * entry.getKey().toString(); String value =
		 * entry.getValue().toString(); System.out.println(key + ":" + value); }
		 * }
		 */
	}

	/**
	 * 获得用户信息
	 * 
	 * @param token
	 * @param openId
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static Map<String, Object> getUserInfo(String openId, HttpServletRequest request) throws IOException, ParseException {
		String token = getToken(wx_appid, wx_appsecret, request);
		String url = loadJson("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openId
				+ "&lang=zh_CN");
		Map<String, Object> ulist = parseJSON2Map(url);
		return ulist;
	}

	/**
	 * 获得access_token
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getToken(String appid, String secret, HttpServletRequest request)
			throws IOException, ParseException {
		String token = null;
		String time = null;
		String jsapi_ticket = null;
		Configure configure = new Configure();
		String appId = configure.getAppId();
		String appSecret = configure.getAppSecret();
		ServletContext applicationContext = request.getSession().getServletContext();
		if (applicationContext.getAttribute("token_time") != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

			// 将application 中存放的时间加两小时得到appDate
			java.util.Date appDate = sdf.parse((String) applicationContext.getAttribute("token_time"));
			Calendar c = Calendar.getInstance();
			c.setTime(appDate);
			c.add(Calendar.HOUR_OF_DAY, +2);
			appDate = c.getTime();

			Date now = new Date();
			System.out.println(now + "=============" + appDate);
			if (now.before(appDate)) {
				token = (String) applicationContext.getAttribute("token");
				jsapi_ticket = (String) applicationContext.getAttribute("jsapi_ticket");
			} else {
				token = WXGetTokenAndTicket.getAccess_token(appId, appSecret);
				jsapi_ticket = WXGetTokenAndTicket.getJsApiTicket(token);
				time = GetConfig.getTime();
				applicationContext.setAttribute("token", token);
				applicationContext.setAttribute("jsapi_ticket", jsapi_ticket);
				applicationContext.setAttribute("token_time", time);
			}
		} else {
			token = WXGetTokenAndTicket.getAccess_token(appId, appSecret);
			jsapi_ticket = WXGetTokenAndTicket.getJsApiTicket(token);
			time = GetConfig.getTime();
			applicationContext.setAttribute("token", token);
			applicationContext.setAttribute("jsapi_ticket", jsapi_ticket);
			applicationContext.setAttribute("token_time", time);
		}
		return token;
	}

	/**
	 * 获取jsticket
	 * 
	 * @return
	 */
	public static String getJsTicket(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
		String ticket = "";
		String json = loadJson(url);
		Map<String, Object> mapTicket = parseJSON2Map(json);
		if (mapTicket.get("ticket") != null) {
			ticket = mapTicket.get("ticket").toString();
		}
		return ticket;
	}

	/**
	 * 链接url返回json
	 * 
	 * @param url
	 * @return
	 */
	public static String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * json转list
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject("[" + jsonStr + "]");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * json转map
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
}
