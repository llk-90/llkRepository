package com.fh.util;


import org.apache.commons.codec.digest.DigestUtils;



public class SecretUtils {
	
	public static String buildSecret(String rawStr) {
		return DigestUtils.shaHex(rawStr);
	}
	
	

	public static boolean isValid(String rawStr, String signature) {
		
		boolean result = DigestUtils.shaHex(rawStr).equals(signature);
		if (!result) {
			//MyLog.log("输入rawStr:" + rawStr + ",signature:" + signature + "，正确结果为:" + DigestUtils.shaHex(rawStr));
			System.out.println("输入rawStr:" + rawStr + ",signature:" + signature + "，正确结果为:" + DigestUtils.shaHex(rawStr));
		}
		return result;
	}
}
