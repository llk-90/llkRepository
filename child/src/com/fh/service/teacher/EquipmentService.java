package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;;


@Service("equipmentService")
public class EquipmentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> equipmentList(Page page) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		 String u_type=user.getU_type();
		 PageData pd=page.getPd();
		 if(Const.QUYU.equals(u_type)){
		 pd.put("userIdqy", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.JIAOSHI.equals(u_type)){
		 pd.put("userIdtc", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.ZUZHNAG.equals(u_type)){
		 pd.put("userIdzz", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.KEHU.equals(u_type)){
		 pd.put("userIdkh", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.SCHOOLADMIN.equals(u_type)){
		 pd.put("userIdsc", userId);
		 pd.put("u_types", Const.SCHOOLADMIN);
		 }
		 
		return (List<PageData>) dao.findForList("EquipMapper.findlistPage", page);
	}
	
	/**
	 * 根据学生id查询信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStuId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipMapper.findByStuId", pd);
	}
	
	/**
	 * 更新设备信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editE(PageData pd) throws Exception {
		dao.update("EquipMapper.editEquip", pd);
	}	
	
}