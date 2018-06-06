package com.fh.controller.weixiplus.educationInfo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.educationInfo.EducationInfoForThirdService;
import com.fh.util.PageData;
import com.fh.util.Utils;

import net.sf.json.JSONObject;


@RestController
@RequestMapping(value="/weixiplusEducationInfoForThird")
public class EducationInfoForThirdController extends BaseController {
	@Autowired
	private EducationInfoForThirdService service;
	
	/**
	 * 点击首页展示banner轮播图
	 */
	@RequestMapping(value = "/getBanner")
	public PageData getBanner(){
		PageData pd = this.getPageData();
		return service.getBanner(pd);
	}
	
	/** 
	 * 点击首页展示教育资讯列表
	 */
	@RequestMapping(value = "/getInfoList")
	public PageData educationInfoList() throws Exception {				
		PageData pd = this.getPageData();
		return service.findEducationInfoList(pd);
	}
	
	/**
	 * 点击某一资讯获取该资讯详情
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getInfoById")
	public PageData findEducationInfoById(String openId,String InfoId) throws Exception{
		JSONObject js=new JSONObject();
		PageData pd=this.getPageData();
		pd.put("openId", openId);
		pd.put("InfoId", InfoId);
		return service.findInfoById(pd);
	}
	
}
