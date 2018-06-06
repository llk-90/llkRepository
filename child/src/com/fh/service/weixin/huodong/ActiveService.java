package com.fh.service.weixin.huodong;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("activeService")
public class ActiveService {

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
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActiveMapper.findlistPage", page);
	}
	
	/**
	 * 是否开通业务
	 * 
	 * @return
	 * @throws Exception
	 */
	public int findSeActive(PageData pd) throws Exception {
		return (int) dao.findForObject("ActiveMapper.findSeActive", pd);
	}
	
	/**
	 * 是否参加活动
	 * 
	 * @return
	 * @throws Exception
	 */
	public int findOpActive(String pd) throws Exception {
		return (int) dao.findForObject("ActiveMapper.findOpActive", pd);
	}
	
	/**
	 * 保存活动信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object saveActive(PageData pd) throws Exception {
		return dao.save("ActiveMapper.InsertActive", pd);
	}
}
