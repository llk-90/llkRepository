package com.fh.service.system.tools;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("toolService")
public class ToolService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<PageData> Eqlist(Page page)throws Exception{
		return (List<PageData>) dao.findForList("EquipmentMapper.Eqlist", page);
	}
	
	/*
	* 保存
	*/
	public void saveE(PageData pd)throws Exception{
		dao.save("EquipmentMapper.saveEq", pd);
		dao.save("EquipmentMapper.saveAreaEq", pd);
	}
	
	/*
	* 编辑
	*/
	public void editE(PageData pd)throws Exception{
		dao.save("EquipmentMapper.editE", pd);
		dao.save("EquipmentMapper.editEq", pd);
	}
}
