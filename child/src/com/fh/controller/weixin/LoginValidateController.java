package com.fh.controller.weixin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.MapPlus;

@Controller
@RequestMapping(value = "/loginValidate")
public class LoginValidateController extends BaseController {

	/**
	 * 登录验证
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isLogin")
	@ResponseBody
	public Object isLogin() {
		MapPlus result = new MapPlus();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (user == null) {
			result.put("isLogin", false);
			result.put("toURL", "http://" + WxUtil.wx_http + "/WxTest/getOpenid.do?redUrl=http%3A%2F%2F"
					+ WxUtil.wx_http + "%2Fpalmcare%2Flogin.html");
		} else {
			result.put("isLogin", true);
		}
		return result;
	}

}
