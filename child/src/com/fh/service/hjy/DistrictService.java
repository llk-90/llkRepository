package com.fh.service.hjy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("districtService")
public class DistrictService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DistrictMapper.datalist", pd);
	}

	/*
	 * 区域
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> quyulist(PageData page) throws Exception {
		return (List<PageData>) dao.findForList("DistrictMapper.listQuYu", page);
	}

	/*
	 * 通过userid获取区域（区域经理）
	 */
	public String getZoneIdByUserId(PageData pd) throws Exception {
		return (String) dao.findForObject("DistrictMapper.getZoneIdByUserId", pd);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> column(PageData pd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> columnList = (List<PageData>) dao.findForList("DistrictMapper.findColumnById", pd);
		map.put("columnList", columnList);
		return (HashMap<String, Object>) map;
	}

	public void saveNode(PageData pd) throws Exception {
		dao.save("DistrictMapper.saveNode", pd);
	}

	public void saveKidNode(PageData pd) throws Exception {
		dao.save("DistrictMapper.saveKidNode", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DistrictMapper.findById", pd);
	}

	/*
	 * 通过id获取学生id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("DistrictMapper.listid", arrayid);

	}

	/*
	 * 通过id获取老师id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listteacherid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("DistrictMapper.listteacherid", arrayid);

	}

	/*
	 * 通过id获取区域经理id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listareaid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("DistrictMapper.listareaid", arrayid);

	}
	
	
	/**
	 * 检查节点名称是否存在
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean checkZname(PageData pd) throws Exception {
		Object objs = dao.findForList("DistrictMapper.checkZname", pd);
		return objs != null && ((List<PageData>) objs).size() > 0;
	}

}
