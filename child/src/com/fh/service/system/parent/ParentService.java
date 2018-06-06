package com.fh.service.system.parent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("parentService")
public class ParentService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取绑定学生的家长信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getParentInfo(Page page) throws Exception{
		List<PageData> pList = new ArrayList<PageData>();
		pList= (List<PageData>) dao.findForList("ParentMapper.getParentInfolistPage", page);
		return  pList;
	}
	
	/**
	 * 根据id获取信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getParInfoById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ParentMapper.getParInfoById", pd);
	}
	
	/**
	 * 修改信息
	 * @param pd
	 * @throws Exception
	 */
	public void updatePar(PageData pd) throws Exception{
		dao.update("ParentMapper.updatePar", pd);
		dao.update("ParentMapper.updateUserName", pd);
	}
	
	/**
	 * 校验学生姓名
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkStuName(PageData pd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "";

		PageData p = (PageData) dao.findForObject("ParentMapper.checkStuName", pd);
		if ("".equals(pd.getString("stuName_old"))) {
			if (p == null) {
				errInfo = "exist";
			}
		} else {
			if (p == null) {
				errInfo = "exist";
			}
		}
		
		map.put("result", errInfo);
		return map;
	}
}
