package com.fh.service.system.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.LlkDaoSupport;
import com.fh.util.PageData;

@Service("studentRegionservice")  
public class StudentRegionservice {
	
	@Resource(name = "llkDaoSupport")
	private LlkDaoSupport llkdao;

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) llkdao.findForList("RegionMapper.datalist", pd);
	}

	
	/*
	 * 通过userid获取区域（区域经理）
	 */
	public String getZoneIdByUserId(PageData pd) throws Exception {
		return (String) llkdao.findForObject("RegionMapper.getZoneIdByUserId", pd);
	}


	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		llkdao.update("RegionMapper.edit", pd);
	}

	/*
	 * 删除一级节点
	 */
	public void deleteparent(PageData pd) throws Exception {
		llkdao.delete("RegionMapper.deleteparent", pd);
	}

	/*
	 * 删除子节点
	 */
	public void delNode(String[] zid) throws Exception {
		llkdao.delete("RegionMapper.delete", zid);
	}

	/*
	 * 删除子节点
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		llkdao.delete("RegionMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 删除班级学生
	 */
	public void deletequ(String[] ArrayS_id) throws Exception {
		llkdao.delete("RegionMapper.deletequ", ArrayS_id);
	}

	/*
	 * 删除班级学生
	 */
	public void deleteUS(String[] ArrayS_id) throws Exception {
		llkdao.delete("RegionMapper.deleteUS", ArrayS_id);
	}

	/*
	 * 添加一级节点保存
	 */
	public void save(PageData pd) throws Exception {
		llkdao.save("RegionMapper.save", pd);
	}

	/*
	 * 获取节点名称
	 */
	public String getZoneName(PageData pd) throws Exception {
		return (String) llkdao.findForObject("RegionMapper.getZoneName",pd);
	}

	
	
	
	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) llkdao.findForObject("RegionMapper.findById", pd);
	}

	/*
	 * 通过id获取学生id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listid(String[] arrayid) throws Exception {

		return (List<String>) llkdao.findForList("RegionMapper.listid", arrayid);

	}

	/*
	 * 通过id获取老师id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listteacherid(String[] arrayid) throws Exception {

		return (List<String>) llkdao.findForList("RegionMapper.listteacherid", arrayid);

	}

	/*
	 * 通过id获取区域经理id
	 */
	@SuppressWarnings("unchecked")
	public List<String> listareaid(String[] arrayid) throws Exception {

		return (List<String>) llkdao.findForList("RegionMapper.listareaid", arrayid);

	}

	@SuppressWarnings("unchecked")
	public boolean deleteZone(PageData pd) throws Exception {
		boolean result = true;
		PageData zone = (PageData) llkdao.findForObject("RegionMapper.findById", pd);
		pd.put("z_type", zone.get("z_type"));
		Object objs = llkdao.findForList("RegionMapper.findStuByZoneId", pd);
		if (objs != null) {
			List<PageData> list = (List<PageData>) objs;
			if (list.size() > 0) {
				result = false;
			} else {
				Object zIds = llkdao.findForList("RegionMapper.datalist", pd);
				pd.put("zIds", zIds);
				// 删除选中的节点以及子节点
				llkdao.delete("RegionMapper.delByZoneId", pd);
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
		Object objs = llkdao.findForList("RegionMapper.checkZname", pd);
		return objs != null && ((List<PageData>) objs).size() > 0;
	}

}
