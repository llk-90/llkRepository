package com.fh.controller.weixiplus.homepage;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.homepage.HomePageService;
import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;


@RestController
@RequestMapping(value = "/weixiplusHomepage")
public class HomePageController extends BaseController {

	
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;
	
	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;
	
	@Resource(name = "homepageservice")
	private HomePageService homepageservice;
	
	@RequestMapping(value = "/gethomepage")
	public PageData getHomePage() throws Exception
	{
		PageData res = new PageData();
		try {
			
			PageData pData = this.getPageData();
			//System.out.println(pData.getString(StringDefault.openId));
			//判断openId是否为空
			if (pData.getString(StringDefault.openId)==null) {
				res.put(StringDefault.errorcode,errorMsg.Success(4001));
				return res;
			}
			//判断openId是否有效
			if (checkValueService.OpenidCheck(pData.getString(StringDefault.openId))) {
				res.put(StringDefault.errorcode,errorMsg.Success(0));
				return res;
			} else {
				//判断openId无效情况下
				res.put(StringDefault.errorcode,errorMsg.Success(2001));
				return res;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		res.put(StringDefault.errorcode,errorMsg.Success(4002));
		return res;
	}
	
	/**
	 * 獲取首頁信息
	 */
	@RequestMapping(value = "/gethomepageInfo")
	public PageData getHomePageContent() throws Exception {
			PageData pData = this.getPageData();
			return homepageservice.getHomePageInfo(pData);
	}
}
