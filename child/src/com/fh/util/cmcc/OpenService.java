package com.fh.util.cmcc;

import java.util.Date;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fh.util.cmcc.Cmcc;
import com.fh.util.cmcc.Request;
import com.fh.util.cmcc.Response;
import com.fh.util.cmcc.XxtOpenUtil;

public class OpenService {
	
	public static Response openService(String requestXML,String msgType){
		Log mlog = LogFactory.getLog("messagelogger");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Cmcc.class);
		//广东校讯通开放平台WebService请求地址
		factory.setAddress("http://api.ydxxt.com/services/eduSOAP");
		Cmcc service = (Cmcc) factory.create();

		Request request = new Request();
		request.setPerformCode("zsga");
		request.setBody(requestXML);
		request.setMsgSeq("83896097"); 		  //消息序列号
		request.setMsgType(msgType);     //消息类型
		request.setTimeStamp(new Date().toString());
		request.setVersion("1.0");
		request.setSkey(XxtOpenUtil.getSkey(request,"d85c3612-d998-49c7-b6eb-a9c27b293a6d"));

		//响应报文体
		Response response = service.edu(request);
//		System.out.println("result=" + response.getResult());
//		System.out.println("desc=" + response.getDesc());
//		System.out.println("msgType=" + response.getMsgType());
		//mlog.debug(response.toString());
		String body = response.getBody();
		return response;
	}
	
	
	public static Response getStudentInfo(String requestXML,String msgType){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Cmcc.class);
		//广东校讯通开放平台WebService请求地址
		factory.setAddress("http://api.ydxxt.com/services/eduSOAP");
		Cmcc service = (Cmcc) factory.create();

		Request request = new Request();
		request.setPerformCode("zsga");
		request.setBody(requestXML);
		request.setMsgSeq("83896097"); 		  //消息序列号
		request.setMsgType(msgType);     //消息类型
		request.setTimeStamp(new Date().toString());
		request.setVersion("1.0");
		request.setSkey(XxtOpenUtil.getSkey(request,"d85c3612-d998-49c7-b6eb-a9c27b293a6d"));

		//响应报文体
		Response response = service.edu(request);
		
		return response;
		
	}

}
