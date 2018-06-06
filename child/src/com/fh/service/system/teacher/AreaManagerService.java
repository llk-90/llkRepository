package com.fh.service.system.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("areaManagerService")
public class AreaManagerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("AreaManagerMapper.save", pd);
	}

	/*
	 * 新增
	 */
	public void saveArea(PageData pd) throws Exception {
		dao.save("AreaManagerMapper.saveArea", pd);
	}

	/*
	 * 新增关联
	 */
	public void saveRelation(PageData pd) throws Exception {
		dao.save("AreaManagerMapper.saveRelation", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("AreaManagerMapper.deleteRelation", pd);
		dao.delete("AreaManagerMapper.deleteRole", pd);
		dao.delete("AreaManagerMapper.delete", pd);
	}

	/*
	 * 修改基本信息
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AreaManagerMapper.edit", pd);
		dao.update("AreaManagerMapper.editZone", pd);
	}

	/*
	 * 修改用户名密码
	 */
	public void editUserName(PageData pd) throws Exception {
		dao.update("AreaManagerMapper.editUserName", pd);
	}

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByZId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AreaManagerMapper.findByZId", pd);
	}

	/*
	 * 通过userid获取区域（区域经理）
	 */
	public PageData getZoneIdByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AreaManagerMapper.getZoneIdByUserId", pd);
	}

	/*
	 * 通过userid获取区域（教师）
	 */
	public PageData getZoneIdByAreaManager(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AreaManagerMapper.getZoneIdByAreaManager", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findJSRole(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.findJSRole", pd);
	}

	/*
	 * 批量删除
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> checkDel(String[] ArrayDATA_IDS) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.checkDel", ArrayDATA_IDS);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] user_ids, String[] z_ids) throws Exception {
		dao.delete("AreaManagerMapper.deleteRelation", z_ids);
		dao.delete("AreaManagerMapper.deleteZone", z_ids);
		dao.delete("AreaManagerMapper.deleteAll", user_ids);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.schoolList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> gradeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.gradeList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> classList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AreaManagerMapper.classList", pd);
	}

	/**
	 * 校验用户名
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkUsername(PageData pd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "";

		PageData p = (PageData) dao.findForObject("AreaManagerMapper.checkUsername", pd);
		if ("".equals(pd.getString("USERNAME_OLD"))) {
			if (p != null && p.getString("USERNAME") != null) {
				errInfo = "exist";
			}
		} else {
			if (p != null && p.getString("USERNAME") != null
					&& !pd.getString("USERNAME_OLD").equals(p.getString("USERNAME"))) {
				errInfo = "exist";
			}
		}

		map.put("result", errInfo);
		return map;
	}

	/**
	 * 校验区域
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkArea(PageData pd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "";

		PageData p = (PageData) dao.findForObject("AreaManagerMapper.checkArea", pd);
		if (Tools.isEmpty(pd.getString("area_old"))) {
			if (p != null && p.getString("z_name") != null) {
				errInfo = "exist";
			}
		} else {
			if (p != null && p.getString("z_name") != null && !pd.getString("area_old").equals(p.getString("z_name"))) {
				errInfo = "exist";
			}
		}

		map.put("result", errInfo);
		return map;
	}
}
