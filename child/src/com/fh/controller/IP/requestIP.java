package com.fh.controller.IP;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Controller
public class requestIP extends BaseController{
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@RequestMapping(value = "/requestIP")
	public String list(String mac,int flag) throws Exception {
		PageData pd = new PageData();
		pd.put("mac", mac);
		pd.put("flag", flag);
		String ip = (String)dao.findForObject("NewDataMapper.selectIP",pd);
		return ip;
	}
}
