package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class GetBodyStr {
	
	public static JSONObject getBodyStr(HttpServletRequest request){
		
		BufferedReader br;
		String str = "success",str2, wholeStr = "";
		try {
			br = request.getReader();
			while((str2 = br.readLine()) != null){
				wholeStr += str2;
			}
		} catch (IOException e2) {
		}
		return JSONObject.fromObject(wholeStr);
	}
}
