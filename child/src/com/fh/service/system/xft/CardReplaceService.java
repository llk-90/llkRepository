package com.fh.service.system.xft;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;;


@Service("cardReplaceService")
public class CardReplaceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> cardReplaceList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CardReplaceMapper.findCardlistPage", page);
	}
	
	/**
	 * 根据学生id查询补卡信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStuId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CardReplaceMapper.findCardByStuId", pd);
	}
	
	/**
	 * 更新补卡信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editE(PageData pd) throws Exception {
		dao.update("CardReplaceMapper.editCardMsg", pd);
	}	
}