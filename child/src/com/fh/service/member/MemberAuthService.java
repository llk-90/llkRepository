package com.fh.service.member;

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
import com.fh.util.PageData;

@Service("memberAuthService")
public class MemberAuthService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("create_user", user_id);
		dao.save("MemberAuthMapper.addAuth", pd);
	}

	/**
	 * 更新
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("create_user", user_id);
		dao.update("MemberAuthMapper.editAuth", pd);
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData getAuthById(String id) throws Exception {
		return (PageData) dao.findForObject("MemberAuthMapper.findById", id);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> authList(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberAuthMapper.findAuthlistPage", page);
		return list;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		dao.delete("MemberAuthMapper.delete", id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("MemberAuthMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> authListApp(PageData pageData) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberAuthMapper.findAuthListHasFreeApp", pageData);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PageData> authListNoFreeApp(PageData pageData) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberAuthMapper.findAuthListApp", pageData);
		return list;
	}

	/**
	 * 
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> fileInfoListApp(PageData pageData) throws Exception {
		return (List<PageData>) dao.findForList("MemberAuthMapper.fileInfoListApp", pageData);
	}

	/**
	 * 查询是否有已发布此权限的课件
	 */
	public int findIsPublish(String id) throws Exception {
		return (int) dao.findForObject("MemberAuthMapper.findIsPublish", id);
	}

	/**
	 * 根据id查找名字
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData getNameById(String id) throws Exception {
		return (PageData) dao.findForObject("MemberAuthMapper.findNameById", id);
	}
	
	/**
	 * check权限名
	 * @param c_name
	 * @return
	 * @throws Exception
	 */
	public int findByName(PageData pd) throws Exception {
		return (int) dao.findForObject("MemberAuthMapper.findByName", pd);
	}


}
