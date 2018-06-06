package com.fh.service.system.region;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.LlkDaoSupport;
import com.fh.util.PageData;

@Service("regionservice")  
public class Regionservice {
	
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RegionMapper.datalist", pd);
	}

	/*
	 * 区域
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> quyulist(PageData page) throws Exception {
		return (List<PageData>) dao.findForList("RegionMapper.listQuYu", page);
	}

	/*
	 * 通过userid获取区域（区域经理）
	 */
	public String getZoneIdByUserId(PageData pd) throws Exception {
		return (String) dao.findForObject("RegionMapper.getZoneIdByUserId", pd);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> column(PageData pd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> columnList = (List<PageData>) dao.findForList("RegionMapper.findColumnById", pd);
		map.put("columnList", columnList);
		return (HashMap<String, Object>) map;
	}

	public void saveNode(PageData pd) throws Exception {
		dao.save("RegionMapper.saveNode", pd);
	}

	public void saveKidNode(PageData pd) throws Exception {
		dao.save("RegionMapper.saveKidNode", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("RegionMapper.edit", pd);
	}

	/*
	 * 删除一级节点
	 */
	public void deleteparent(PageData pd) throws Exception {
		dao.delete("RegionMapper.deleteparent", pd);
	}

	/*
	 * 删除子节点
	 */
	public void delNode(String[] zid) throws Exception {
		dao.delete("RegionMapper.delete", zid);
	}

	/*
	 * 删除子节点
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("RegionMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 删除班级学生
	 */
	public void deletequ(String[] ArrayS_id) throws Exception {
		dao.delete("RegionMapper.deletequ", ArrayS_id);
	}

	/*
	 * 删除班级学生
	 */
	public void deleteUS(String[] ArrayS_id) throws Exception {
		dao.delete("RegionMapper.deleteUS", ArrayS_id);
	}

	/*
	 * 添加一级节点保存
	 */
	public void save(PageData pd) throws Exception {
		dao.save("RegionMapper.save", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RegionMapper.findById", pd);
	}

	/*
	 * 通过id获取学生id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("RegionMapper.listid", arrayid);

	}

	/*
	 * 通过id获取老师id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listteacherid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("RegionMapper.listteacherid", arrayid);

	}

	/*
	 * 通过id获取区域经理id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listareaid(String[] arrayid) throws Exception {

		return (List<String>) dao.findForList("RegionMapper.listareaid", arrayid);

	}

	@SuppressWarnings("unchecked")
	public boolean deleteZone(PageData pd) throws Exception {
		boolean result = true;
		PageData zone = (PageData) dao.findForObject("RegionMapper.findById", pd);
		pd.put("z_type", zone.get("z_type"));
		Object objs = dao.findForList("RegionMapper.findStuByZoneId", pd);
		if (objs != null) {
			List<PageData> list = (List<PageData>) objs;
			if (list.size() > 0) {
				result = false;
			} else {
				Object zIds = dao.findForList("RegionMapper.datalist", pd);
				pd.put("zIds", zIds);
				// 删除选中的节点以及子节点
				dao.delete("RegionMapper.delByZoneId", pd);
			}
		}
		return result;
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
		Object objs = dao.findForList("RegionMapper.checkZname", pd);
		return objs != null && ((List<PageData>) objs).size() > 0;
	}

}
