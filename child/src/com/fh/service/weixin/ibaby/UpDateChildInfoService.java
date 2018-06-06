package com.fh.service.weixin.ibaby;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;


@Service("updatechildInfoService")
public class UpDateChildInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public PageData findchild(String stu_id) throws Exception {
		return (PageData) dao.findForObject("UpDateChildInfoMapper.findchild", stu_id);
	}
	
	public void updeChild(PageData pd) throws Exception {
		dao.update("UpDateChildInfoMapper.updeChild", pd);
	}
}

