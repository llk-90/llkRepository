package com.fh.controller.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.fh.util.Const;

@Controller
@RequestMapping(value = "/getData")
public class GetDatas extends BaseController{
	
	/** 
     * 获取jsapi相关参数map 
     * @param jsapi_ticket 
     * @param url 
     * @return 
     */ 
	@RequestMapping(value = "/dataList")
	public Map<String, String> getJsSignMap(String url, HttpServletRequest request) throws IOException, ParseException {
		String token = WxUtil.getToken(Const.WEIXIN_APPID, Const.WEIXIN_SECRET, request);
    	String jsapi_ticket = WxUtil.getJsTicket(token);
        Map<String, String> map = new HashMap<String, String>();  
        String signature = "";
        String nonce_str = UUID.randomUUID().toString();  
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);  
        String s = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;  
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(s.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        map.put("url", url);  
        map.put("jsapi_ticket", jsapi_ticket);  
        map.put("nonceStr", nonce_str);  
        map.put("timestamp", timestamp);  
        map.put("signature", signature);  
        return map;  
    } 
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
