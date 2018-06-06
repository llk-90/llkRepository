package com.fh.service.video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("retrievalservice")
public class Retrievalservice {
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
	public void edit(PageData pd)throws Exception{
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
	public void delNode(PageData pd) throws Exception {
		dao.delete("RegionMapper.delete", pd);
	}
	
	/*
	 * 删除子节点
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("RegionMapper.deleteAll", ArrayDATA_IDS);
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

}
