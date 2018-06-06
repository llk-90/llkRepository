package com.fh.service.system.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("chargehandManagerService")
public class ChargehandManagerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		pd.put("parent_id",getUserId());
		dao.save("ChargehandManagerMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ChargehandManagerMapper.deleteRelation", pd);
		dao.delete("ChargehandManagerMapper.deleteRole", pd);
		dao.delete("ChargehandManagerMapper.delete", pd);
	}

	/*
	 * 修改基本信息
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ChargehandManagerMapper.edit", pd);
		dao.update("ChargehandManagerMapper.editZone", pd);
	}

	/*
	 * 修改用户名密码
	 */
	public void editUserName(PageData pd) throws Exception {
		dao.update("ChargehandManagerMapper.editUserName", pd);
	}

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {

		page.getPd().put("parent_id",getUserId());
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ChargehandManagerMapper.findByUserId", pd);
	}

	/*
	 * 通过userid获取区域（区域经理）
	 */
	public PageData getZoneIdByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ChargehandManagerMapper.getZoneIdByUserId", pd);
	}

	/*
	 * 通过userid获取区域（教师）
	 */
	public PageData getZoneIdByAreaManager(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ChargehandManagerMapper.getZoneIdByAreaManager", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findJSRole(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.findJSRole", pd);
	}

	/*
	 * 批量删除
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> checkDel(String[] ArrayDATA_IDS) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.checkDel", ArrayDATA_IDS);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] user_ids) throws Exception {
		dao.delete("ChargehandManagerMapper.deleteAll", user_ids);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.schoolList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> gradeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.gradeList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> classList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.classList", pd);
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

		PageData p = (PageData) dao.findForObject("ChargehandManagerMapper.checkUsername", pd);
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

		PageData p = (PageData) dao.findForObject("ChargehandManagerMapper.checkArea", pd);
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
	/**
	 * 获取用户ID
	 * 2016/9/1 yc  添加
	 * @return
	 */
	private String getUserId(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user =(User)session.getAttribute(Const.SESSION_USER);
		if(user ==null || "".equals(user.getUSER_ID())){
			return null;
		}
		return user.getUSER_ID();
	}
	/**
	 * 
	 * 通过用户名获取获取学校数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findSchoolList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ChargehandManagerMapper.findSchoolList", pd);
	}
	
}
