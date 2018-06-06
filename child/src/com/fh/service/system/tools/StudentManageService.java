package com.fh.service.system.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.dao.LlkDaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("studentManageService")
public class StudentManageService {

	@Resource(name = "llkDaoSupport")
	private LlkDaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> Eqlist(Page page) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd.put("userId", user.getUSER_ID());
		List<PageData> objs = (List<PageData>) dao.findForList("StudentMapper.findAreaByUserId", pd);
		if (objs != null && objs.size() > 0) {
			List<PageData> areaInfos = (List<PageData>) objs;
			PageData areaInfo = areaInfos.size() > 0 ? areaInfos.get(0) : null;
			page.getPd().put("areaId", areaInfo.get("areaId"));
			page.getPd().put("userId", user.getUSER_ID());
		} else {
			page.getPd().put("areaId", "");
			page.getPd().put("userId", user.getUSER_ID());
		}
		return (List<PageData>) dao.findForList("StudentMapper.listPageEqlist", page);
	}


	/*
	 * 根据手机号查询学生信息   
	 * 2016/8/31 yc  添加
	 */
	public PageData findStudentsByPhone(PageData pd) throws Exception {
		List<PageData> pageDataList= (List<PageData>) dao.findForList("StudentMapper.findStudentsByPhone", pd);
		PageData pd1=null;
		if(pageDataList.size()>0){
			//默认取第一条
			pd1=pageDataList.get(0);
		}
		return pd1;
	}

	
	/*
	 * 保存学生
	 */
	public void saveE(PageData pd) throws Exception {
		pd.put("s_id", "");
		dao.save("StudentMapper.saveEq", pd);
	}

	/*
	 * 保存家长
	 */
	public void saveUser(PageData pd) throws Exception {
		dao.save("StudentMapper.saveUser", pd);
	}

	/*
	 * 保存学生家长
	 */
	public void saveUS(PageData pd) throws Exception {
		dao.save("StudentMapper.saveUS", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByEiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StudentMapper.findByEId", pd);
	}

	/*
	 * 编辑学生表信息
	 */
	public void editE(PageData pd) throws Exception {
		dao.update("StudentMapper.editE", pd);
	}

	/*
	 * 编辑老师
	 */
	public void editUser(PageData pd) throws Exception {
		dao.update("StudentMapper.editUser", pd);
	}

	/*
	 * 编辑老师学生
	 */
	public void editUS(PageData pd) throws Exception {
		dao.update("StudentMapper.editUS", pd);
	}

	/*
	 * 批量删除学生
	 */
	public void deleteAllE(String[] s_id) throws Exception {
		dao.delete("StudentMapper.deleteAllE", s_id);
	}

	/*
	 * 批量删除留言
	 */
	public void deleteLeave(String[] s_id) throws Exception {
		dao.delete("StudentMapper.deleteLeave", s_id);
	}
	
	
	/*
	 * 批量删除学生家长关联表
	 */
	public void deleteAllEQ(String[] s_id) throws Exception {
		dao.delete("StudentMapper.deleteAllEQ", s_id);
	}
	
	/*
	 * 批量删除关联、家长、微信表
	 */
	public void deleteAll(String[] user_id) throws Exception {
		dao.delete("StudentMapper.deleteRelationWx", user_id);
		dao.delete("StudentMapper.deleteRelation", user_id);
		dao.delete("StudentMapper.deleteCommodity", user_id);
		dao.delete("StudentMapper.deleteUserCommodity", user_id);
		dao.delete("StudentMapper.deleteAll", user_id);
	}
	

	/**
	 * 校验手机号
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkPhone(PageData pd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "";

		PageData p = (PageData) dao.findForObject("StudentMapper.checkTel", pd);
		if ("".equals(pd.getString("phone_old"))) {
			if (p != null && p.getString("PHONE") != null) {
				errInfo = "exist";
			}
		} else {
			if (p != null && p.getString("PHONE") != null && !pd.getString("phone_old").equals(p.getString("PHONE"))) {
				errInfo = "exist";
			}
		}
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 检查学号是否存在
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean checkHasStuNo(PageData pd) throws Exception {
		Object objs = dao.findForList("StudentMapper.checkStuNo", pd);
		return objs != null && ((List<PageData>) objs).size() > 0;
	}
}