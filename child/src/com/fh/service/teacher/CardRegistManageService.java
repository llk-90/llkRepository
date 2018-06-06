package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;;


@Service("cardRegistManageService")
public class CardRegistManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public String findSchoolId(String userId) throws Exception{
		return (String) dao.findForObject("CardRegistManageMapper.findSchoolId", userId);
	}
	
	/**
	 * 补卡信息列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> cardRegistlist(Page page) throws Exception{
		return (List<PageData>)dao.findForList("CardRegistManageMapper.findCardRegistlistPage", page);
	}
	
	
	/**
	 * 根据id找到该补卡信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByInfoId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CardRegistManageMapper.findByCardRegistId", pd);
	}
	
	/**
	 * 编辑补卡信息
	 * @param pd
	 * @throws Exception
	 */
	public void editCardRegist(PageData pd) throws Exception{
		dao.update("CardRegistManageMapper.editCardRegist", pd);
	}
	
}