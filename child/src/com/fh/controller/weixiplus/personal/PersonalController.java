package com.fh.controller.weixiplus.personal;

import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.Personal.PersonalService;
import com.fh.util.PageData;


@RestController
@RequestMapping(value = "/weixiplusPersonal")
public class PersonalController extends BaseController {
	
	@Resource(name = "PlaceService")
	private PersonalService personalService;

	/*
	 * 获取所有城市列表
	 * 
	*/
	
	@RequestMapping(value = "/getCity")
	public List<PageData> getAllCity() throws Exception
	{
		 return personalService.getCity();
	}
	
	/*
	 * 根據城市id查詢區列表
	 * 
	*/
	
	@RequestMapping(value = "/getDistrict")
	public PageData getDistrict(HttpServletRequest request) throws Exception
	{
		PageData pData = this.getPageData();		
		 return personalService.getDistrict(pData);
		 
	}
}
