package com.fh.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.entity.Ibaby;
import com.fh.entity.IbabyParam;  


/** 
 * IBabyUtil工具类 
 * 向爱贝多设备平台请求数据时,将请求参数定义在IbabyBean内.
 * 参考:[设备平台XML接口imeiV1.2.pdf]
 * @author      
 * @create      2017-6-7  
 */ 
public class IBabyUtil  {
	
	/**
	 * 获取设备平台信息
	 * 
	 * @param xmlString
	 * @return Map<String, Ibaby> 返回ibaby的entry 
	 * @throws Exception
	 */
	public static Map<String, Ibaby> getDeviceInfo(String xmlString) throws Exception {   

		Map<String, Ibaby> map = new HashMap<String, Ibaby>();
        byte[] xmlData = xmlString.getBytes("utf-8");
		
        DataInputStream input = null;
        java.io.ByteArrayOutputStream output = null;

        try{   
             //获得到位置服务的链接   
            URL url = new URL(Const.IBABYURL);   
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            //将xml数据发送到位置服务   
            urlCon.setRequestProperty("Content-Type", "text/xml");   
            urlCon.setRequestProperty("Content-length",java.lang.String.valueOf(xmlData.length));   
            
            //DataOutputStream printout = new DataOutputStream(new BufferedOutputStream(urlCon.getOutputStream()));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(xmlData);   
            printout.flush();   
            printout.close();   
            
            //input = new DataInputStream(new BufferedInputStream(urlCon.getInputStream()));
            input = new DataInputStream(urlCon.getInputStream());
            output = new java.io.ByteArrayOutputStream();  
            byte[] bufferByte = new byte[256];   
                int l = -1;   
                while ((l = input.read(bufferByte)) > -1) {    
                	output.write(bufferByte, 0, l);   
                	output.flush();   
                }
            
            byte[] rResult;   
            rResult = output.toByteArray();
            String responseString = new String(rResult,0,rResult.length,"utf-8");
            System.out.println(responseString);
            
            Ibaby ibaby = JaxbUtil.converyToJavaBean(responseString, Ibaby.class); 
			
    		// 将服务端信息传送到画面上
            // bean需要解析后才能使用
    		map.put("result", ibaby);
        }   
        catch(Exception e){
            e.printStackTrace();   
        }   
        finally {   
            try {
            	output.close();   
                input.close();   
            }   
            catch (Exception ex) {}   
        }
		return map;
	}

	/**
	 * 获取xml模板信息
	 * 
	 * @param 
	 * @return
	 */
	public static String getXmlInfo(String loginName, String action, String password, List<String> keyname, List<String> keyvalue) {  
       
    	Ibaby ibaby = new Ibaby();
    	
    	IbabyParam param ;
    	List<IbabyParam> ibabyParam = new ArrayList<IbabyParam>();
    	for(int i = 0; i < keyname.size(); i++){
    		param = new IbabyParam();
    		param.setName(keyname.get(i));
    		param.setDescript(keyvalue.get(i));
    		ibabyParam.add(param);
    	}
    	ibaby.setParam(ibabyParam);
    	
		String loginNamechar = loginName;
		char[] t1 = loginName.toCharArray();
		for (int i = 0; i < t1.length; i++) {
		    if (java.lang.Character.toString(t1[i]).matches(
			    "[\\u4E00-\\u9FA5]+")) {
		    	loginNamechar = String.valueOf(loginName.hashCode());
		    	break;
		    }
		}
    	ibaby.setAction(action);
    	ibaby.setCheckSum(MD5.md5("1.0" + action + loginNamechar + MD5.md5(password)));
    	ibaby.setLoginName(loginName);
    	
    	ibaby.setVersion("1.0");
        
        String retXml = JaxbUtil.convertToXml(ibaby, "utf-8"); 
        
        return retXml;  
    } 
}
