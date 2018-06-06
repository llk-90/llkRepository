package com.fh.service.order;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("orderService")
public class OrderService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public List<PageData> orderList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findOrderlistPage", page);
		 
	}
	
	/**
	 * 所有的会员权限
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> nameList() throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findAllAuthName", null);
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData getOrderById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findById", pd);
	}
	
	/**
	 * 线下支付-更新
	 */
	public void update(PageData pd) throws Exception {
		dao.update("OrderMapper.updatePayState", pd);
	}
	
	/**
	 * 线下支付-新增
	 */
	public void add(PageData pd) throws Exception {
		dao.save("OrderMapper.offLinePay", pd);
	}
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrderMapper.deleteAll", ArrayDATA_IDS);
	}
	

}
