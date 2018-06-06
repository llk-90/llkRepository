package com.fh.service.system.tools;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("environmentManageService")
public class EnvironmentManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> Eqlist(Page page) throws Exception {
		return (List<PageData>) dao.findForList("EquipmentMapper.listPageEqlist", page);
	}
	/*
	 * 保存
	 */
	public void saveE(PageData pd) throws Exception {
		dao.save("EquipmentMapper.saveEq", pd);
		this.saveAreaEq(pd);
	}

	/*
	 * 保存
	 */
	public void saveAreaEq(PageData pd) throws Exception {
		dao.save("EquipmentMapper.saveAreaEq", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByEiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipmentMapper.findByEId", pd);
	}

	/*
	 * 编辑设备表信息
	 */
	public void editE(PageData pd) throws Exception {
		dao.update("EquipmentMapper.editE", pd);
	}

	/*
	 * 编辑
	 */
	public void editEq(PageData pd) throws Exception {
		dao.update("EquipmentMapper.editEq", pd);
	}

	/*
	 * 批量删除设备
	 */
	public void deleteAllE(String[] equ_id) throws Exception {
		dao.delete("EquipmentMapper.deleteAllE", equ_id);
	}

	/*
	 * 批量删除农场设备
	 */
	public void deleteAllEQ(String[] area_kid) throws Exception {
		dao.delete("EquipmentMapper.deleteAllEQ", area_kid);
	}

}
