package com.fh.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.fh.controller.weixin.common.GetConfig;

import net.sf.json.JSONObject;

public class WXgetPicture {
	 /** 
     *  
     * 根据文件id下载文件 
     *  
     *  
     *  
     * @param mediaId 
     *  
     *            媒体id 
	 * @throws ParseException 
	 * @throws IOException 
     *  
     * @throws Exception 
     */  
  
    public static InputStream getInputStream(String mediaId,HttpServletRequest request,String requestUrl) throws IOException, ParseException {  
    	String jsonStr = GetConfig.getParam(requestUrl, request);
    	JSONObject jsonObject = JSONObject.fromObject(jsonStr);
    	String accessToken = jsonObject.getString("token");
        InputStream is = null;  
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="  
                + accessToken + "&media_id=" + mediaId;  
        try {  
            URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet  
                    .openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
            http.connect();  
            // 获取文件转化为byte流  
            is = http.getInputStream();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return is;  
  
    }  
  
    /** 
     *  
     * 获取下载图片信息（jpg） 
     *  
     *  
     *  
     * @param mediaId 
     *  
     *            文件的id 
     *  
     * @throws Exception 
     */  
  
    public static void saveImageToDisk(String requestUrl,String mediaId, String picPath,HttpServletRequest request)  
            throws Exception {  
        InputStream inputStream = getInputStream(mediaId,request,requestUrl);
        byte[] data = new byte[10240];  
        int len = 0;  
        FileOutputStream fileOutputStream = null;  
        
        try {  
            fileOutputStream = new FileOutputStream(picPath+".png");
            while ((len = inputStream.read(data)) != -1) {  
                fileOutputStream.write(data, 0, len);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (fileOutputStream != null) {  
                try {  
                    fileOutputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * 图片下载 
     *  
     * @param accessToken 
     * @param mediaId 
     * @throws IOException 
     */  
  /*  public static void getPic(String accessToken, String mediaId,HttpRequest request) throws IOException {  
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";  
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(  
                "MEDIA_ID", mediaId);  
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null); 
        // 发送请求
        StringBuffer sb = new StringBuffer(requestUrl);
 		URL url = new URL(sb.toString());
 		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 		connection.setRequestMethod("GET");
 		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
 		// 返回结果
 		JSONObject jsonObject = null;
 		while ((in.read()) != -1) {
 			String jsonMessage = "{"+in.readLine();
 			jsonObject=JSONObject.fromObject(jsonMessage);
 		}
        System.out.println(jsonObject);  
    }  */
}
