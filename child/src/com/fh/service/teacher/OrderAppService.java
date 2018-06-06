package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;;


@Service("orderAppService")
public class OrderAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("orderAppMapper.findlistPage", page);
	}
	
	/**
	 * 更新商订单息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updOrderApp(PageData pd) throws Exception {
		dao.update("orderAppMapper.updateOrderApp", pd);
	}
	
	/**
	 * 更改商订单息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editOrderApp(PageData pd) throws Exception {
		dao.update("orderAppMapper.editOrderApp", pd);
	}
	
	/**
	 * 新增订单息息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.update("orderAppMapper.addorder", pd);
	}
	
	
	/**
	 * 根据订单id查询信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByorderId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("orderAppMapper.findByorderId", pd);
	}
	
}