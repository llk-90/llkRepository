package com.fh.service.system.personalinfo;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("usereditService")
public class UserEditService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	* 修改密码
	*/
	public void editUserinfo(PageData pd)throws Exception{
		dao.update("UserEditMapper.edit", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserEditMapper.findById", pd);
	}
}
