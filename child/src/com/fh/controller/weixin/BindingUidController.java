package com.fh.controller.weixin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 绑定设备
 * 
 * @author 860116018
 *
 */
@Controller
@RequestMapping(value = "/bindingUid")
public class BindingUidController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 获取用户绑定设备
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUid")
	@ResponseBody
	public Object classList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		// 得到Uid
		pd = userService.findUidByParent(pd);
		if (pd == null || pd.getString("s_device_u_id") == null) {
			map.put("Uid", "");
		} else {
			map.put("Uid", pd.getString("s_device_u_id"));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改绑定设备
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUid")
	@ResponseBody
	public Object updateUid() {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		// 得到Uid
		try {
			userService.updateUid(pd);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断用户权限
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUser")
	@ResponseBody
	public Object checkUser() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		// 得到u_type
		PageData p = userService.findUserTypeByOpenId(pd);
		if (p == null || p.get("u_type").toString() == null) {
			map.put("result", "error_empty");
		} else if ("3".equals(p.get("u_type").toString())) {
			map.put("result", "success");
		} else {
			map.put("result", "error_notFamily");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 校验UID唯一
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUid")
	@ResponseBody
	public Object checkUid() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		// 得到u_type
		PageData p = userService.checkUid(pd);
		if (p == null || p.get("s_device_u_id").toString() == null) {
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

}
