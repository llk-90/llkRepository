package com.fh.service.weixinplus.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.service.weixinplus.educationInfo.EducationInfoService;
import com.fh.util.PageData;

@Service("homepageservice")
public class HomePageService {

	@Resource(name = "educationInfoService")
	private EducationInfoService service;
	
	public PageData getHomePageInfo(PageData pData) throws Exception {
		//首先获取教子良方信息
		pData.put("pageCount", "0");
		return service.findEducationInfoList(pData);
		
	}
	
}
