package com.fh.service.weixinplus.unbunding;

//import java.sql.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@Service("UnBingService")
public class UnBingService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name ="errorMsg")
	private ErrorMsg errorMsg;
	
	
	/**
	 * 解绑
	 * @param pd
	 * @return
	 * @throws Exception
	 *//*
	public PageData unBingUserInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		try {
			pageData = (PageData) dao.findForObject("UnBinging.finduserId", pd);
			int resultNum = Integer.parseInt(dao.findForObject("UnBinging.getBingStuInfo", pageData).toString());
			if(resultNum==1){
				//一对一
				dao.delete("UnBinging.deloldBingUserInfo_tstu", pageData);
				pd.put("uw_user_id", pageData.get("uw_user_id"));
				dao.delete("UnBinging.deloldBingUserInfo_tweixin", pd);
				pageData.put(StringDefault.errorcode, errorMsg.Success(0));
			}else{
			    // 一对多
				pd.put("uw_user_id", pageData.get("uw_user_id"));
				dao.delete("UnBinging.deloldBingStuInfo", pd);
				pageData.put(StringDefault.errorcode, errorMsg.Success(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageData.put(StringDefault.errorcode, errorMsg.Success(1002));
		}
		return pageData;   
	}*/
	
	public PageData unBingUserInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();  
		try {      
			pageData = (PageData) dao.findForObject("UnBinging.findLatestData", pd);
			int result = (int) dao.delete("UnBinging.deleteByLatestOpenId", pageData);  
			System.out.println(result);
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));
		}catch (Exception e) {
			e.printStackTrace();
			pageData.put(StringDefault.errorcode, errorMsg.Success(1002));
		}
		return pageData;
	}
}
