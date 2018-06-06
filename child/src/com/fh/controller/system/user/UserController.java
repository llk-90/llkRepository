package com.fh.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.MapPlus;
import com.fh.util.PageData;

/**
 * 类名称：UserController 创建人：FH 创建时间：2014年6月28日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	// 老师用户类型
	public static final int UType_Teacher = 2;
	// 敏感词临界值
	public static final String KEY_LinJie = "KEY_LinJie";

	String menuUrl = "user/listUsers.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 保存系统管理员
	 */
	@RequestMapping(value = "/saveU")
	public ModelAndView saveU(PrintWriter out) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID()); // ID
		pd.put("RIGHTS", ""); // 权限
		pd.put("LAST_LOGIN", ""); // 最后登录时间
		pd.put("IP", ""); // IP
		pd.put("STATUS", "0"); // 状态
		pd.put("SKIN", "default"); // 默认皮肤
		pd.put("PARENT_ID", Const.ROLEADMIN);

		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());

		if (null == userService.findByUId(pd)) {
			userService.saveU(pd);
			mv.addObject("msg", "success");
		} else {
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUId(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 编辑判断邮箱是否存在
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUE(pd) != null) {
				if (pd.containsKey("USER_ID")) {
					PageData pda = userService.findByUE(pd);
					if (!pda.getString("USER_ID").equals(pd.getString("USER_ID"))) {
						errInfo = "error";
					}
				} else {
					errInfo = "error";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value = "/hasN")
	@ResponseBody
	public Object hasN() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUN(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改个人资料
	 */
	@RequestMapping(value = "/editInfo")
	public ModelAndView editInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", Const.ROLEADMIN);
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editInfo(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改个人资料页面
	 */
	@RequestMapping(value = "/goEditInfo")
	public ModelAndView goEditInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userService.findByUiId(pd); // 根据ID读取
		mv.setViewName("system/user/user_editinfo");
		mv.addObject("msg", "editInfo");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 修改系统管理员
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", Const.ROLEADMIN);
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editU(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改系统管理员页面
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "user");
		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
		pd = userService.findByUiId(pd); // 根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);

		return mv;
	}

	/**
	 * 去新增系统管理员页面
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Role> roleList;

		roleList = roleService.listAllERRoles(); // 列出所有二级角色

		mv.setViewName("system/user/userAdd");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);

		return mv;
	}

	/**
	 * 去个人中心修改个人信息页面
	 */
	@RequestMapping(value = "/goUserInfoEdit")
	public ModelAndView goUserInfoEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("system/persionalinfo/userinfo_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 去个人中心修改密码页面
	 */
	@RequestMapping(value = "/goResetpswd")
	public ModelAndView goResetpswd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/persionalinfo/resetpswd");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 显示系统管理员
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", Const.ROLEADMIN);
		String USERNAME = pd.getString("USERNAME");

		if (null != USERNAME && !"".equals(USERNAME)) {
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}

		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");

		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			lastLoginStart = lastLoginStart + " 00:00:00";
			pd.put("lastLoginStart", lastLoginStart);
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			lastLoginEnd = lastLoginEnd + " 00:00:00";
			pd.put("lastLoginEnd", lastLoginEnd);
		}

		page.setPd(pd);
		List<PageData> userList = userService.listPdPageUser(page); // 列出用户列表
		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色

		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 显示用户列表(tab方式)
	 */
	@RequestMapping(value = "/listtabUsers")
	public ModelAndView listtabUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> userList = userService.listAllUser(pd); // 列出用户列表
		mv.setViewName("system/user/user_tb_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/deleteU")
	public void deleteU(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			userService.deleteU(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAllU")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String USER_IDS = pd.getString("USER_IDS");
			if (null != USER_IDS && !"".equals(USER_IDS)) {
				String ArrayUSER_IDS[] = USER_IDS.split(",");
				userService.deleteAllU(ArrayUSER_IDS);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/toLockList")
	public ModelAndView toLockList() {
		ModelAndView mav = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			PageData linJie = userService.findLinJie(pd);
			pd.put("uType", UType_Teacher);
			List<PageData> lockUser = userService.findLockUser(pd);
			List<PageData> reportedUser = userService.findReportedUser(pd);
			mav.addObject("linJie", linJie);
			mav.addObject("lockUser", lockUser);
			mav.addObject("reportedUser", reportedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("system/user/user_lock_list");
		return mav;
	}

	@RequestMapping(value = "/unlockUser")
	@ResponseBody
	public Object unlockUser() {
		MapPlus result = new MapPlus();
		try {
			userService.unlockUser(this.getPageData());
			result.addParams("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.addParams("success", false);
		}
		return result;
	}

	/**
	 * 更新临界值
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateLinJie")
	@ResponseBody
	public Object updateLinJie() {
		MapPlus result = new MapPlus();
		try {
			userService.updateLinJie(this.getPageData());
			result.addParams("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.addParams("success", false);
		}
		return result;
	}

	@RequestMapping(value = "/lockUser")
	@ResponseBody
	public Object lockUser() {
		MapPlus result = new MapPlus();
		try {
			userService.lockUser(this.getPageData());
			result.addParams("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.addParams("success", false);
		}
		return result;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
