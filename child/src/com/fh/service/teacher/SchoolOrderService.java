package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("schoolOrderService")
public class SchoolOrderService {

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
	public List<PageData> schoolOrderList(Page page) throws Exception {		 
		return (List<PageData>) dao.findForList("SchoolOrderMapper.findlistPage", page);
	}	
}