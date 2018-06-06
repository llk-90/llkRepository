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
import com.fh.service.system.environment.Environmentservice;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.AppuserService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.MapPlus;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.mail.SimpleMailSender;

/**
 * 类名称：AppuserController 创建人：FH 创建时间：2014年6月28日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/appuser")
public class AppuserController extends BaseController {

	String menuUrl = "appuser/listUsers.do"; // 菜单地址(权限用)
	@Resource(name = "appuserService")
	private AppuserService appuserService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "environmentservice")
	private Environmentservice environmentservice;
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 保存用户
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
		pd.put("PARENT_ID", Const.ROLEFARM);

		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		if (null == appuserService.findByUId(pd)) {
			appuserService.saveU(pd);
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
			if (appuserService.findByName(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData p = appuserService.findByUE(pd);
			if (p != null) {
				if (pd.containsKey("user_id")) {
					if (p.getString("USER_ID").equals(pd.getString("user_id"))) {
						errInfo = "success";
					} else {
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
	 * 判断手机号是否存在
	 */
	@RequestMapping(value = "/hasPH")
	@ResponseBody
	public Object hasPH() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData p = appuserService.findByPH(pd);
			if (p != null) {
				if (pd.containsKey("user_id")) {
					if (p.getString("USER_ID").equals(pd.getString("user_id"))) {
						errInfo = "success";
					} else {
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
	 * 去修改用户页面
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 顶部修改个人资料
		String fx = pd.getString("fx");
		if ("head".equals(fx)) {
			mv.addObject("fx", "head");
		} else {
			mv.addObject("fx", "user");
		}
		List<Role> roleList = roleService.listAllapERRoles(); // 列出所有二级角色
		List<PageData> listfarm = environmentservice.listfarm(page);
		pd = appuserService.findByUiId(pd); // 根据ID读取
		mv.setViewName("system/user/appuser_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("listfarm", listfarm);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 保存修改用户
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", Const.ROLEFARM);
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		appuserService.editU(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Role> roleList;
		roleList = roleService.listAllapERRoles(); // 列出所有二级角色
		List<PageData> listfarm = environmentservice.listfarm(page); // 列出用户列表
		mv.addObject("listfarm", listfarm);
		mv.setViewName("system/user/appuser_add");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 去导入页面
	 */
	@RequestMapping(value = "/toImport")
	public ModelAndView toImport() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("system/user/appuser_import");
			mv.addObject("msg", "saveU");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 确认发送邮件
	 */
	@RequestMapping(value = "/SendEmail")
	@ResponseBody
	public Object SendEmail() {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "ok"; // 发送状态
		int count = 0; // 统计发送成功条数
		int zcount = 0; // 理论条数
		List<PageData> pdList = new ArrayList<PageData>();
		String toEMAIL = pd.getString("EMAIL"); // 对方邮箱
		String TITLE = pd.getString("TITLE"); // 标题
		String CONTENT = pd.getString("CONTENT"); // 内容
		String TYPE = pd.getString("TYPE"); // 类型
		// String isAll = pd.getString("isAll"); // 是否发送给全体成员 yes or no
		try {
			if (!toEMAIL.isEmpty()) {
				String[] emails = toEMAIL.split(";");
				zcount = emails.length;
				for (int i = 0; i < emails.length; i++) {
					if (Tools.checkEmail(emails[i])) {// 邮箱格式不对就跳过
						SimpleMailSender.sendEmails(Const.SMTP, Const.PORT, Const.EMAIL, Const.PAW, emails[i], TITLE,
								CONTENT, TYPE);
						count++;
					} else {
						continue;
					}
				}
				msg = "ok";
			}
		} catch (Exception e) {
			msg = "error";
			logger.error(e.toString(), e);
		}
		pd.put("msg", msg);
		pd.put("count", count); // 成功数
		pd.put("ecount", zcount - count); // 失败数
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去页面
	 */
	@RequestMapping(value = "/goSendSms")
	public ModelAndView goSendSms() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("system/head/send_sms");
			mv.addObject("msg", "saveU");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 显示用户列表
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", Const.ROLEFARM);

		page.setPd(pd);
		List<PageData> userList = appuserService.listPdPageUser(page); // 列出用户列表
		List<Role> roleList = roleService.listAllapERRoles(); // 列出所有会员二级角色
		List<PageData> listfarm = environmentservice.listfarm(page); // 列出用户列表
		mv.setViewName("system/user/appuser_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("listfarm", listfarm);
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
			appuserService.deleteU(pd);
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
				appuserService.deleteAllU(ArrayUSER_IDS);
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

	@RequestMapping(value = "/updateReportCount")
	@ResponseBody
	public Object updateReportCount() {
		MapPlus result = new MapPlus();
		try {
			PageData pd = this.getPageData();
			if (pd.get("userId") == null) {
				Subject currentUser = SecurityUtils.getSubject();
				Session session = currentUser.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				pd.put("userId", user.getUSER_ID());
				PageData tid = userService.findTeacherByUserId(pd);
				pd.put("userId", tid.get("userId"));
			}
			userService.updateReportCount(pd);
			result.addParams("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.addParams("success", false);
		}
		return result;
	}

	// ===================================================================================================

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
