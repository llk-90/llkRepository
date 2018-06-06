package com.fh.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

public class SendSmsMsg {

	// 注册，用户认证验证码
	public static String verifyCodeMsg = "您好，您的验证码是 {0} （掌上关爱验证码）为了保护您或者您的帐号安全，验证短信请勿转发给其他人。";
	/***
	 * 手机端发送信息
	 * 
	 * @param mobile
	 *            机号码，多个号码使用","分割
	 * @param msg
	 *            短信内容
	 * @return 验证码
	 * 
	 */
	//↓↓↓↓修改返回值↓↓↓↓
	public static boolean smsCodeSend(String mobile, String msg) throws Exception {
		boolean flag = false;
		//↑↑↑↑修改返回值↑↑↑↑
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(Const.SMURL, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("account", Const.SMACCOUNT),
					new NameValuePair("pswd", Const.SMPSWD), new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", "true"), new NameValuePair("msg", msg),
					new NameValuePair("extno", null), });
			int result = client.executeMethod(method);
			//短信发送成功
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				//↓↓↓↓修改返回值↓↓↓↓
				flag = true;
				//↑↑↑↑修改返回值↑↑↑↑
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
		//↓↓↓↓修改返回值↓↓↓↓
		return flag;
		//↑↑↑↑修改返回值↑↑↑↑
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return 验证码
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("([3-6])(d+)");
		Matcher m = pattern.matcher("aabbccbdd4dd");
		while(m.find()){
			System.out.println(m.group());
			System.out.println(m.group(1));
			System.out.println(m.group(2));
		}
	}
	
}
