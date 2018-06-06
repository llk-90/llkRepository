package com.fh.controller.weixiplus.weixiplusCommon;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;

@Repository("CheckValue")
public class CheckValue {

	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;
		
	public boolean isVaildOpenid(String openid) throws Exception {
		return checkValueService.OpenidCheck(openid);
	}
}
