package com.fh.service.weixin.schoolPay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("schoolPayOrderService")
public class SchoolPayOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("schoolPayMapper.InsertOrder", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return null;
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return null;
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return null;
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		
	}
	
	/*
	* 匹配关键词
	*/
	public PageData findByKw(PageData pd)throws Exception{
		return null;
	}
}

