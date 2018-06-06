package com.fh.util.cmcc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XxtOpenUtil {
	/**
	 * 检查第三方系统的合法性
	 * 
	 * @param requestVO
	 * @return 如果合法就是true,否则就是false
	 */
	public static String getSkey(Request requestVO, String platformKey) {
		// 数据有效性签名，Md5（PerformCode + TimeStamp + MsgSeq + MsgType + platformkey）
		// 输出32位全大写字母，其中platformkey是约定的密匙（和PerformCode一起由校讯通平台分配）
		StringBuffer mdVal = new StringBuffer();
		mdVal.append(requestVO.getPerformCode())
				.append(requestVO.getTimeStamp()).append(requestVO.getMsgSeq())
				.append(requestVO.getMsgType()).append(platformKey);
		// 声明消息摘要对象
		MessageDigest md = null;
		// 创建消息摘要
		try {
			md = MessageDigest.getInstance("MD5");
			// 将口令的数据传给消息摘要对象
			md.update(mdVal.toString().getBytes());
			// 获得消息摘要的字节数组
			byte[] digest = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(digest[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hexString.append(hex.toUpperCase());
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}
