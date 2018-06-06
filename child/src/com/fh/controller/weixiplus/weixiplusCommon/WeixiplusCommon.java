package com.fh.controller.weixiplus.weixiplusCommon;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.controller.weixin.WxUtil;
import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;
import com.fh.service.weixinplus.weixiplusCommon.WeixiplusGetInfo;
import com.fh.service.weixinplus.weixiplusCommon.WeixiplusGetInfo2;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.WXSign;
import com.fh.util.model.StringDefault;



@RestController
@RequestMapping(value = "/weixiplusCommon")
public class WeixiplusCommon extends BaseController {

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;
	
	@Resource(name = "weixiplusGetInfo")
	private WeixiplusGetInfo weixiplusGetInfo;
	
	@Resource(name = "weixiplusGetInfo2")
	private WeixiplusGetInfo2 weixiplusGetInfo2;
	
	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;
	
	
	/**
	 * 获取sign
	 * 
	 * @param pagedata 页面信息
	 * @return 是否成功
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getSign")
	public PageData get(HttpServletRequest request) throws Exception

	{
		PageData pageData = this.getPageData();				
		if (checkValueService.OpenidCheck(pageData.getString("openId"))) {
			PageData pd = new PageData();
			String jstoken = WxUtil.getJsTicket(WxUtil.getToken(WxUtil.wx_appid,WxUtil.wx_appsecret, request));
			pd.put("errorcode", errorMsg.Success(0));
			//System.out.println(pageData.getString("url"));
			pd.put("token", WXSign.sign(jstoken, pageData.getString("url")));
			pd.put(StringDefault.appid, WxUtil.wx_appid);
			pd.put(StringDefault.appsecret, WxUtil.wx_appsecret);
			return pd;
		} else {
			PageData pd = new PageData();
			//System.out.println(errorMsg.Success(2001));
			pd.put("errorcode", errorMsg.Success(2001));
			return pd;
		}
		 
	}
	
	/**
	 * 获取学生信息
	 * 
	 * @param openId
	 * @return 学生信息
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getRegistInfoList")
	public PageData getStuInfo() throws Exception {
		PageData pageData = this.getPageData();
		return weixiplusGetInfo.getStuInfo(pageData);
		
	}

	/**
	 * 获取下方tabbar代码
	 * 
	 * @param 
	 * @return html
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getTarBar")
	public PageData getTarBar() throws Exception {
		
		return weixiplusGetInfo.getHtmlTarBar();
		
	}

	
	/**
	 * 获取打卡信息
	 * 
	 * @param openId
	 * @return 学生打卡信息      
	 * @throws IOException   
	 */
	@RequestMapping(value = "/getAtteinfo")
	public PageData getAtteInfo() throws Exception {
		PageData pageData = this.getPageData();
		return weixiplusGetInfo2.getAtteinfo(pageData);
		
	}
	
	
	/**   
	 * 获取打卡信息
	 * 
	 * @param openId
	 * @return 学生打卡信息
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getRankdetails")
	public PageData getRankdetails() throws Exception {
		PageData pageData = this.getPageData();
		return weixiplusGetInfo2.getRankdetails(pageData);
		
	}

}
