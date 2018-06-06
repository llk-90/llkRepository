package com.fh.service.system.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.controller.system.user.UserController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// ======================================================================================

	/*
	 * 通过id获取数据
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUiId", pd);
	}

	/*
	 * 通过loginname获取数据
	 */
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUId", pd);
	}

	/*
	 * 通过邮箱获取数据
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUE", pd);
	}

	/*
	 * 通过编号获取数据
	 */
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUN", pd);
	}

	/*
	 * 保存用户
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("UserXMapper.saveU", pd);
	}

	/*
	 * 修改用户
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("UserXMapper.editU", pd);
	}

	/*
	 * 修改个人资料
	 */
	public void editInfo(PageData pd) throws Exception {
		dao.update("UserXMapper.editInfo", pd);
	}

	/*
	 * 换皮肤
	 */
	public void setSKIN(PageData pd) throws Exception {
		dao.update("UserXMapper.setSKIN", pd);
	}

	/*
	 * 删除用户
	 */
	public void deleteU(PageData pd) throws Exception {
		dao.delete("UserXMapper.deleteU", pd);
	}

	/*
	 * 批量删除用户
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("UserXMapper.deleteAllU", USER_IDS);
	}

	/*
	 * 用户列表(用户组)
	 */
	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}

	/*
	 * 用户列表(全部)
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.listAllUser", pd);
	}

	/*
	 * 用户列表(供应商用户)
	 */
	public List<PageData> listGPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userGlistPage", page);
	}

	/*
	 * 保存用户IP
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserXMapper.saveIP", pd);
	}

	/*
	 * 登录判断
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.getUserInfo", pd);
	}

	/*
	 * 微信绑定：查询用户
	 */
	public List<PageData> getUserByPhoneAndPwd(PageData pd) throws Exception {
		List<PageData> pageDataList = (List<PageData>) dao.findForList("StudentMapper.findStudentsByPhone", pd);
		PageData pd1 = null;
		if (pageDataList.size() > 0) {
			// 默认取第一条
			pd1 = pageDataList.get(0);
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd1.getString("PHONE"), pd.getString("PASSWORD")).toString());
			pd.put("PHONE", pd1.getString("PHONE"));
		} else {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		}

		return (List<PageData>) dao.findForList("UserXMapper.getUserByPhoneAndPwd", pd);
	}

	/*
	 * 微信绑定：查询用户微信关联
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getOpenByUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getOpenByUser", pd);
	}

	/*
	 * 微信绑定：查询用户微信关联
	 */
	public List<PageData> getUserByOpen(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getUserByOpen", pd);
	}
	public List<PageData> getUserByPhone(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getUserByPhone", pd);
	}

	/*
	 * 微信绑定：添加用户微信关联
	 */
	public void saveUserWx(PageData pd) throws Exception {
		dao.save("UserXMapper.saveUserWx", pd);
		dao.update("UserXMapper.updateUserImg", pd);
	}
	
	/*
	 * 绑定：和教育
	 */
	public void saveUserWxHJY(PageData pd) throws Exception {
		dao.save("UserXMapper.saveUserWx", pd);
	}

	/*
	 * 第三方登录校验
	 */
	public PageData loginCk(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.loginCk", pd);
	}
	
	/*
	 * 第三方登录获取用户信息
	 */
	public PageData loginCk_getParentInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.loginCk_getParentInfo", pd);
	}
	
	/*
	 * 第三方登录获取用户信息
	 */
	public PageData loginCk_getTeacherInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.loginCk_getTeacherInfo", pd);
	}
	
	/*  
	 * 微信绑定：查询用户微信关联
	 */
	public List<PageData> getUserByWx(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getUserByWx", pd);
	}
	
	/*
	 * 微信绑定：查询教师微信关联
	 */
	public List<PageData> getUserByTeaWx(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getUserByTeaWx", pd);
	}
	
	/*
	 * 和教育：查询用户微信关联
	 */
	public List<PageData> getHJYUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.getHJYUser", pd);
	}

	/*
	 * 跟新登录时间
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserXMapper.updateLastLogin", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

	/*
	 * 绑定设备初始化查询Uid
	 */
	public PageData findUidByParent(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findUidByParent", pd);
	}

	/*
	 * 修改用户
	 */
	public void updateUid(PageData pd) throws Exception {
		dao.update("UserXMapper.updateUid", pd);
	}

	/*
	 * 绑定设备初始化查询Uid
	 */
	public PageData findUserTypeByOpenId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findUserTypeByOpenId", pd);
	}

	/*
	 * 校验UID唯一
	 */
	public PageData checkUid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.checkUid", pd);
	}

	/**
	 * 查找临界值
	 * 
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData findLinJie(PageData pageData) throws Exception {
		pageData.put("key", UserController.KEY_LinJie);

		Object obj = dao.findForList("UserMapper.findLinJie", pageData);
		if (obj != null) {
			List<PageData> list = (List<PageData>) obj;
			if (list.size() > 0) {
				return list.get(0);
			}
		}
		// PageData result = new PageData();
		// result.put(key, value)

		return null;
	}

	/**
	 * 查找锁定的用户
	 * 
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findLockUser(PageData pageData) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.findLockUser", pageData);
	}

	/**
	 * 查询被举报但是没有锁定的老师账号
	 * 
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findReportedUser(PageData pageData) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.findReportedUser", pageData);
	}

	/**
	 * 根据家长id查询老师id
	 * 
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData findTeacherByUserId(PageData pageData) throws Exception {
		Object obj = dao.findForList("UserMapper.findTeacherByUserId", pageData);
		if (obj != null) {
			List<PageData> list = (List<PageData>) obj;
			return list.size() > 0 ? list.get(0) : null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public PageData findCountByUserId(PageData pageData) throws Exception {
		Object obj = dao.findForList("UserMapper.findCountByUserId", pageData);
		if (obj != null) {
			List<PageData> list = (List<PageData>) obj;
			return list.size() > 0 ? list.get(0) : null;
		}
		return null;
	}

	/**
	 * 解锁
	 * 
	 * @param pageData
	 * @throws Exception
	 */
	public void unlockUser(PageData pageData) throws Exception {
		dao.update("UserMapper.unlockUser", pageData);
	}

	public void lockUser(PageData pageData) throws Exception {
		dao.update("UserMapper.lockUser", pageData);
	}

	public void updateReportCount(PageData pageData) throws Exception {
		dao.update("UserMapper.updateReportCount", pageData);
	}

	@SuppressWarnings("unchecked")
	public void updateCount(PageData pageData) throws Exception {
		dao.update("UserMapper.updateCount", pageData);
		Object obj = dao.findForList("UserMapper.findCountByUserId", pageData);
		if (obj != null) {
			List<PageData> list = (List<PageData>) obj;
			PageData result = list.size() > 0 ? list.get(0) : null;
			Integer count = new Integer(result.get("count").toString());
			PageData linJie = findLinJie(new PageData());
			Integer val = new Integer(linJie.get("Value").toString());
			if (count >= val) {
				lockUser(pageData);
			}
		}
	}

	public void updateLinJie(PageData pageData) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		pageData.put("key", UserController.KEY_LinJie);
		pageData.put("userId", user.getUSER_ID());

		dao.update("UserMapper.updateLinJie", pageData);
	}
}
