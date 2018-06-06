package com.fh.service.weixinplus.Personal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;

@Service("PlaceService")
public class PersonalService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("personal.getallcity", pd);
	}
	/*
	 * 获取数据库中所有城市
	 * */
	@SuppressWarnings("unchecked")
	public List<PageData> getCity() throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("personal.getallcity", null);
	}
	
	/*
	 * 根據cityid查詢市轄區id
	 * */
	@SuppressWarnings("unchecked")
	public PageData getDistrict(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> templist = (List<PageData>)dao.findForList("personal.getDistrict", pd);
		List<PageData> returnList = new ArrayList<PageData>();
		for(PageData pg : templist) {
			   PageData temppg = new PageData();
			   temppg.put("city_id", pg.get("TownId"));
			   temppg.put("city", pg.get("TownName"));
			   returnList.add(temppg);
			}
		PageData pg = new PageData();
		pg.put("city_info", returnList);
		pg.put("errorcode", errorMsg.Success(1001));
		return pg;
	}
	

}
