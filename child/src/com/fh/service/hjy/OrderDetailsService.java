package com.fh.service.hjy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("orderDetailsService")
public class OrderDetailsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 订购明细列表
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> orderDetailslist(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderDetailsMapper.orderDetailslistPage", page);
	}
	
}
