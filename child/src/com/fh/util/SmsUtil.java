package com.fh.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 创蓝短信接口
 */
public class SmsUtil {
	//系统管理员给会员分配账号
	public static String repairCodeMsg = "【肥东现代农业园管理中心】重要通知！肥东现代农业园管理中心已为您成功注册会员账户，账户名为本手机号，初始密码：{0} （请尽快登录肥东现代农业园管理中心APP修改初始密码）";
	public static String verifyCodeMsg = "【肥东现代农业园管理中心】 {0} 为了保护您或者您企业的帐号安全，验证短信请勿转发给其他人。";
	/***
	 * 手机端发送信息
	 * 
	 * @param mobile
	 *            机号码，多个号码使用","分割
	 * @param msg
	 *            短信内容
	 * @return 验证码
	 */
	public static void smsCodeSend(String mobile, String msg) throws Exception {
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
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
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

	/**
	 * 返回短信余额
	 * 
	 */
	public static String smsQueryBalance() throws Exception {
		String inputline = "";
		try {
			// 组建请求
			String straddr = "http://222.73.117.158/msg/QueryBalance?account=" + Const.SMACCOUNT + "&pswd="
					+ Const.SMPSWD;

			StringBuffer sb = new StringBuffer(straddr);
			System.out.println("URL:" + sb);

			// 发送请求
			URL url = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回结果
			while ((in.read()) != -1) {
				@SuppressWarnings("unused")
				String s1 = in.readLine();
				String s2 = in.readLine();
				String[] ss = s2.split(",");
				inputline = ss[1];
			}
		} catch (Exception e) {
			return "103";
		}
		return inputline;
	}

}

