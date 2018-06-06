package com.fh.service.weixinplus.weixiplusCommon;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("CheckValueService")
public class CheckValueService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public boolean OpenidCheck(String openid) throws Exception {
		
		
		@SuppressWarnings("unchecked")
		List<PageData> list=(List<PageData>) dao.findForList("EducationInfoMapper.checkOpenId", openid);
		if(list.size()!=0){
			return true;
		}
		else{
			return false;
		}
	}
}
