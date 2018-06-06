package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;
import com.fusioncharts.exporter.generators.PDFGenerator;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;;


@Service("leaveService")
public class LeaveService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public List<PageData> datalistPage(Page page) throws Exception{
		List<PageData> list = (List<PageData>) dao.findForList("LeaveMapper.datalistPage", page);
		return list;
	}
	
	public void approval(PageData pd) throws Exception{
		dao.update("LeaveMapper.aproval", pd);
	}
}