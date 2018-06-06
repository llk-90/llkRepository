package com.fh.util.wxpay;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

/**
 * 微信调起支付类
 * 
 * 
 */
public class WXPay {
	// public static String createPackageValue(String appid, String appKey,
	// String prepay_id) {
	// SortedMap<String, Object> nativeObj = createPackageValueObj(appid,
	// appKey, prepay_id);
	// return JSONObject.fromObject(nativeObj).toString();
	// }

	/**
	 * @param appid
	 * @param appKey
	 * @param prepay_id
	 *            appId="+appId+"&nonceStr="+noncestr + "
	 *            &package=prepay_id="+prepay_id+"&signType=MD5&timeStamp=
	 *            "+timestamp+"&key="+key
	 * @return
	 */
	public static SortedMap<String, Object> createPackageValueObj(String appid, String partnerid, String appKey,
			String prepay_id) {
		SortedMap<String, Object> nativeObj = new TreeMap<String, Object>();
		nativeObj.put("appId", appid);
		Random random = new Random();
		String randomStr = MD5.GetMD5String(String.valueOf(random.nextInt(10000)));
		nativeObj.put("nonceStr", MD5Util.MD5Encode(randomStr, "utf-8").toLowerCase());
		nativeObj.put("package", "prepay_id=" + prepay_id);
		nativeObj.put("signType", "MD5");
		nativeObj.put("timeStamp", OrderUtil.GetTimestamp());
		nativeObj.put("paySign", createSign(nativeObj, appKey));
		System.out.println(JSONObject.fromObject(nativeObj).toString());
		return nativeObj;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static Object createSign(SortedMap<String, Object> nativeObj, String AppKey) {
		StringBuffer sb = new StringBuffer();
		Set es = nativeObj.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"paySign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + AppKey);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}
}
