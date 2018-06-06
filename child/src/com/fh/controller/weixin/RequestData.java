package com.fh.controller.weixin;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.sun.org.apache.xpath.internal.operations.And;

public class RequestData {
	/***
	 * 
	 * @author 860116021
	 * @param t
	 *            时间戳
	 * @param data
	 *            参数串
	 * @return ret 加密字符串
	 */
	public String encrypt(String type,String data) {
		if(type != null && data != null && data != ""){
			// 返回值
			String ret = "";

			int key = 0;

			for (int i = 0; i < type.length(); i++) {
				key += Integer.parseInt(String.valueOf(type.charAt(i)));
			}
			
			System.out.println("将功能码作为异或key：" + key);

			// 字符串逆序 
			String nixuString = new StringBuilder(data).reverse().toString();

			// 逐位异或 并  转换为16进制字符串 
			int k;
			StringBuffer sb = new StringBuffer();
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < nixuString.length(); i++) {
				k = nixuString.charAt(i) ^ key;
				String ele = Integer.toHexString(k & 0xFF);
				if (ele.length() == 1) {
					ele = '0' + ele;
				}
				sb.append(ele.toUpperCase() + " ");
				str.append(k);
			}
			String jz16 = sb.toString();
			System.out.println("逐位异或之后转换为16进制后的字符串：" + jz16.replace(" ", ""));
			String[] yh = jz16.split(" ");
			for (int i = 0; i < yh.length; i++) {
				ret +=  new StringBuilder(yh[i]).reverse().toString();
			}
			System.out.println("高低位互换之后的字符串："+ret);
			return ret;
		}else {
			System.out.println("输入数值错误！");
			return null;
		}
	}

	public static void main(String[] args) {
		RequestData requestData = new RequestData();
		String data = "mid=1234&iid=5678&type=0";
		String type ="1458097690";
		String ret = requestData.encrypt(type,data);
		System.out.println("最终加密字符串："+ret);
	}
}
