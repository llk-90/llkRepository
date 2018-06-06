package com.fh.controller.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.member.MemberService;
import com.fh.service.system.tools.StudentManageService;
import com.fh.service.system.user.UserService;
import com.fh.service.weixinplus.login.BingService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.SendSmsMsg;
import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/WxTest")
public class WxTest extends BaseController {

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "studentManageService")
	private StudentManageService studentManageService;
	@Resource(name = "memberService")
	private MemberService memberService;
	@Resource(name= "BingService")
	private BingService bingService;
	
	

	/**
	 * 和教育绑定微信
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/loginCk", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object loginCk(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		if (pd == null) {
			map.put("result", "402");
		} else if (pd.get("t") == null || pd.get("t") == "") {
			map.put("result", "402");
		} else if (pd.get("nm") == null || pd.get("nm") == "") {
			map.put("result", "402");
		} else if (pd.get("pw") == null || pd.get("pw") == "") {
			map.put("result", "402");
		} else if (pd.get("u_type") == null || pd.get("u_type") == "") {
			map.put("result", "402");
		} else {
			String t = pd.get("t").toString();// 请求时的时间戳。
			String nm = pd.get("nm").toString();// 用户名字符串(手机号)。
			String pw = pd.get("pw").toString();// 密码（未加密）。
			String u_type = pd.get("u_type").toString();// 1是家长；2是老师。
			PageData pck = new PageData();
			pck.put("PHONE", nm);
			pck.put("PASSWORD", (new SimpleHash("SHA-1", nm, pw)).toString());
			if ("1".equals(u_type)) {// 家长
				pck.put("U_TYPE", "3");
				PageData p3 = userService.loginCk(pck);
				// 校验用户名密码
				if (p3 != null && p3.get("USER_ID") != null) {// 正确
					map.put("result", "400");
					map.put("u_name", p3.get("NAME").toString());
					// 取得学校、年级、班级
					PageData parent = userService.loginCk_getParentInfo(p3);
					if (parent.get("class") != null && parent.get("class") != "") {
						map.put("class", parent.get("class").toString());
					} else {
						map.put("class", "");
					}
					if (parent.get("grade") != null && parent.get("grade") != "") {
						map.put("grade", parent.get("grade").toString());
					} else {
						map.put("grade", "");
					}
					if (parent.get("school") != null && parent.get("school") != "") {
						map.put("school", parent.get("school").toString());
					} else {
						map.put("school", "");
					}
				} else {
					map.put("result", "401");
				}
			} else if ("2".equals(u_type)) {// 老师
				pck.put("U_TYPE", "2");
				PageData p2 = userService.loginCk(pck);
				// 校验用户名密码
				if (p2 != null && p2.get("USER_ID") != null) {// 正确
					map.put("result", "400");
					map.put("u_name", p2.get("NAME").toString());
					// 取得学校、年级、班级
					PageData teacher = userService.loginCk_getTeacherInfo(p2);
					if (teacher.get("class") != null && teacher.get("class") != "") {
						map.put("class", teacher.get("class").toString());
					} else {
						map.put("class", "");
					}
					if (teacher.get("grade") != null && teacher.get("grade") != "") {
						map.put("grade", teacher.get("grade").toString());
					} else {
						map.put("grade", "");
					}
					if (teacher.get("school") != null && teacher.get("school") != "") {
						map.put("school", teacher.get("school").toString());
					} else {
						map.put("school", "");
					}
				} else {
					map.put("result", "401");
				}
			} else {
				map.put("result", "402");
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 微信号绑定用户
	 */
	@RequestMapping(value = "/binding")
	@ResponseBody
	public Object binding(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		String s = pd.getString("OPENID");
		String[] s1 = s.split("hkY5sn");
		pd.put("OPENID", s1[0]);
		
		byte[] newPwdV = Base64.decodeBase64(s1[1].getBytes());
		String newPwdVStr = new String(newPwdV, "UTF-8");
		newPwdVStr = newPwdVStr.substring(0,newPwdVStr.length()-6);
		newPwdVStr = new String(Base64.decodeBase64(newPwdVStr.getBytes()), "UTF-8");
		pd.put("PASSWORD", newPwdVStr);
		
		String code = pd.getString("CODE");
		//图片验证码
//		String imgcode = pd.getString("IMGCODE");
	/*	Subject sub = SecurityUtils.getSubject();
		Session session = sub.getSession();
		String session_code = session.getAttribute("verifyCode").toString();*/
		pd.put("openId", pd.get("OPENID"));
		pd.put("type", 0);
		pd.put("phone_num", pd.get("PHONE"));
		String session_code = String.valueOf(bingService.getVerifyCode(pd));
//		String session_img_code = session.getAttribute(Const.SESSION_SECURITY_CODE).toString();
		// 根据phone和pass查询用户
		List<PageData> pUserByPhoneAndPwdList = userService.getUserByPhoneAndPwd(pd);
		//PageData pUser = userService.getUserByPhoneAndPwd(pd);
		if (pUserByPhoneAndPwdList.size() == 0) {
			// 手机号或密码错误
			map.put("result", "error1");
		} else if(!session_code.equals(code)){
			// 手机验证码错误
			map.put("result", "error4");
//		}else if(!session_img_code.equalsIgnoreCase(imgcode)){
//			// 图片验证码错误
//			map.put("result", "error5");
		}else {
			
			//pd.put("USER_ID", pUser.getString("USER_ID"));
			//PageData pBinding = userService.getUserByOpen(pd);
			List<PageData> pBindingList = userService.getUserByOpen(pd);
			if (pBindingList.size() == 0) {
				List<PageData> pUserByPhoneList = userService.getUserByPhone(pd);
				for (int j =0; j< pUserByPhoneList.size(); j++) {
					PageData pUser = pUserByPhoneList.get(j);
					if ("2".equals(pUser.get("U_TYPE").toString())) {
						List<PageData> pBinding2 = userService.getOpenByUser(pUser);
						if (pBinding2.size() > 0) {
							// 该教师已被其他微信号绑定
							map.put("result", "error3");
							return AppUtil.returnObject(new PageData(), map);
						}
					}
					
				}
				
				/*// 添加关联
				Map<String, Object> userInfo = WxUtil.getUserInfo(pd.getString("OPENID"), request);
				pd.put("head_photo", userInfo.get("headimgurl").toString());*/
				for (int j =0; j< pUserByPhoneList.size(); j++) {
					PageData pUser = pUserByPhoneList.get(j);
					pd.put("USER_ID", pUser.getString("USER_ID"));
					pd.put("U_TYPE", '7');
					userService.saveUserWx(pd);
				}
				// 绑定成功
				map.put("u_type", pUserByPhoneAndPwdList.get(0).get("U_TYPE").toString());
				map.put("uw_open_id", pd.getString("OPENID"));
				map.put("result", "success");
				
			} else {
				// 该微信号已绑定其他用户
				map.put("result", "error2");
			}
		}
				
//		// 根据phone和pass查询用户
//		PageData pUser = userService.getUserByPhoneAndPwd(pd);
//		if (pUser == null || "".equals(pUser.getString("USER_ID"))) {
//			// 手机号或密码错误
//			map.put("result", "error1");
//		} else {
//			pd.put("USER_ID", pUser.getString("USER_ID"));
//			PageData pBinding = userService.getUserByOpen(pd);
//			if (pBinding == null || "".equals(pBinding.getString("uw_open_id"))) {
//				List<PageData> pBinding2 = userService.getOpenByUser(pd);
//				if ("2".equals(pUser.get("U_TYPE").toString()) && pBinding2.size() > 0) {
//					// 该教师已被其他微信号绑定
//					map.put("result", "error3");
//				} else {
//					// 添加关联
//					Map<String, Object> userInfo = WxUtil.getUserInfo(pd.getString("OPENID"), request);
//					pd.put("head_photo", userInfo.get("headimgurl").toString());
//					userService.saveUserWx(pd);
//					// 绑定成功
//					map.put("u_type", pUser.get("U_TYPE").toString());
//					map.put("uw_open_id", pd.getString("OPENID"));
//					map.put("result", "success");
//				}
//			} else {
//				// 该微信号已绑定其他用户
//				map.put("result", "error2");
//			}
//		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**  
	 * 微信号绑定用户校验  
	 */
	@RequestMapping(value = "/bindingCheck")
	@ResponseBody
	public Object bindingCheck() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		List<PageData> pBinding = userService.getUserByOpen(pd);
		if (pBinding.size() == 0) {
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 和教育登录
	 */
	@RequestMapping(value = "/HJYLogin", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void HJYLogin( HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();// {Token=741aa2f61bd3338b957a72f831409827}
		if (pd != null && pd.get("Token") != null && pd.get("Token") != "") {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String openid= "";
			if (session.getAttribute("user_openid") != null) {
				openid = (String)session.getAttribute("user_openid");
				pd.put("OPENID", openid);
				pd.put("USER_ID", "HJYYK9999");
				//检查openid是否已存在
				List<PageData> pBinding = userService.getUserByOpen(pd);
				if(pBinding.size() == 0){
					userService.saveUserWxHJY(pd);//添加用户微信关联表，和教育用户userid统一为HJYYK9999
				}
				User user = (User) session.getAttribute(Const.SESSION_USER);
				if (user == null) {
					user = new User();
				}
				user.setOPENID(openid);
				user.setUSER_ID("HJYYK9999");
				session.setAttribute(Const.SESSION_USER, user);
				response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/login.html?openid=" + openid+"&target=HJY");
			}
		}

	}

	/**
	 * 微信号绑定用户获取学生信息 2016/9/1 yc 添加
	 */
	@RequestMapping(value = "/bindingGetStudent", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object bindingGetStudent() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		// 检查用户名是否存在
		int count = memberService.checkTeaPhone(pd.getString("PHONE").toString());
		// 不存在则返回提示不存在
		if (count > 0) {
			map.put("result", "success");
			map.put("userType", "2");// 老师
		} else {
			// 学生信息
			PageData pBinding = studentManageService.findStudentsByPhone(pd);
			if (pBinding != null && !"".equals(pBinding.getString("s_name"))) {
				map.put("result", "success");
				// 学生姓名
				map.put("sName", pBinding.getString("s_name"));
				// 班级名称
				map.put("zName", pBinding.getString("z_name"));
			} else {
				map.put("result", "error");
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 注册 2016/9/1 yc 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/regsendsms")
	@ResponseBody
	public Object regSendSms() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		// 检查用户名是否存在
		map = memberService.checkPhone(pd.getString("PHONE").toString());
		// 不存在则返回提示不存在
		if (map.get("result") != null && map.get("result").length() > 0) {
			return AppUtil.returnObject(new PageData(), map);
		}
		
		// 生成手机验证码
		String verifyCode = SendSmsMsg.createRandom(true, 6);
		//↓↓↓↓修改王吉凯17.4.11↓↓↓↓
		boolean sendStruts = false;
		//判断是否是第一次
		PageData pageData = memberService.selectIntervalPhone(pd);
		if(null == pageData){
			pageData = new PageData();
			sendStruts = SendSmsMsg.smsCodeSend(pd.getString("PHONE"), MessageFormat.format(SendSmsMsg.verifyCodeMsg, verifyCode));
			//修改不会向前端发送验证码,发送success的标示
			if(sendStruts){
				//发送成功
				map.put("result", "success");
				pageData.put("PHONE", pd.getString("PHONE"));
				pageData.put("COUNT", 1);
				memberService.saveIntervalPhone(pageData);
			}
		}else {
			int count = (int)pageData.get("count");
			if(count>=10){
				map.put("result", "验证码已超过当日次数！");
				return AppUtil.returnObject(new PageData(), map);
			}else {
				//数据库有值，且不大于10次限制,则，count加一
				sendStruts = SendSmsMsg.smsCodeSend(pd.getString("PHONE"), MessageFormat.format(SendSmsMsg.verifyCodeMsg, verifyCode));
				//修改不会向前端发送验证码,发送success的标示
				if(sendStruts){
					//发送成功
					map.put("result", "success");
					pageData.put("PHONE", pd.getString("PHONE"));
					pageData.put("COUNT", ++count);
					memberService.updateIntervalPhone(pageData);
				}
			}
		}
		//phoneAndCount.put(pd.getString("PHONE"), value)

		/*Subject sub = SecurityUtils.getSubject();
		Session session = sub.getSession();
		session.setAttribute("verifyCode", verifyCode);*/
		pd.put("type" , 0);
		pd.put("creat_time", new Date());
		pd.put("verifycode", verifyCode);
		pd.put("phone_num", pd.get("PHONE"));
		bingService.saveVerifyCode(pd);
		
		//↑↑↑↑修改王吉凯17.4.11↑↑↑↑
		return AppUtil.returnObject(new PageData(), map);
	}
	
//	/**
//	 * 非校验获取openid转到相应页面（参数redUrl：转码之后的回调页面，得到openid转到页面）
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getOpenidNoCheck")
//	public void getOpenidNoCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		String openid = "";
//		
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//		if (user == null) {
//			user = new User();
//		}
//		
//		PageData pd = this.getPageData();
//		
//		String redUrl = "";
//		if (pd.getString("redUrl") != null) {
//			redUrl = java.net.URLDecoder.decode(pd.getString("redUrl"), "UTF-8");
//		}
//		
//		if (user.getOPENID() == null || "".equals(user.getOPENID())) {
//			// 获取openid绑定
//			if (pd.getString("code") == null || pd.getString("code") == "") {
//				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxUtil.wx_appid
//						+ "&redirect_uri=http%3A%2F%2F" + WxUtil.wx_http
//						+ "%2FWxTest%2FgetOpenidNoCheck&response_type=code&scope=snsapi_userinfo&state=" + redUrl
//						+ "#wechat_redirect");
//				return;
//			} else {
//				String code = pd.getString("code");
//				String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxUtil.wx_appid + "&secret="
//						+ WxUtil.wx_appsecret + "&code=" + code + "&grant_type=authorization_code";
//				String json = WxUtil.loadJson(url);
//				Map<String, Object> mapTicket = WxUtil.parseJSON2Map(json);
//				if (mapTicket.get("openid") != null) {
//					openid = mapTicket.get("openid").toString();
//				}
//				redUrl = pd.getString("state");
//			}
//			user.setOPENID(openid);
//		} else {
//			openid = user.getOPENID();
//		}
//		if (!"".equals(openid)) {
//			pd.put("OPENID", openid);
//			session.setAttribute("user_openid", openid);
//			session.setAttribute(Const.SESSION_USER, user);
//		}
//	}
	
	
	
	
	
	/**
	 * 获取openid转到相应页面（参数redUrl：转码之后的回调页面，得到openid转到页面）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOpenid")
	public void getOpenid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getRequest().getSession().removeAttribute("sessionUser");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String openid = "";
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (user == null) {
			user = new User();
		}
		PageData pd = this.getPageData();
		String redUrl = "";
		if (pd.getString("redUrl") != null) {
			redUrl = java.net.URLDecoder.decode(pd.getString("redUrl"), "UTF-8");
		}
		if (user.getOPENID() == null || "".equals(user.getOPENID())) {
			// 获取openid绑定
			if (pd.getString("code") == null || pd.getString("code") == "") {
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxUtil.wx_appid
						+ "&redirect_uri=http%3A%2F%2F" + WxUtil.wx_http
						+ "%2FWxTest%2FgetOpenid&response_type=code&scope=snsapi_userinfo&state=" + redUrl
						+ "#wechat_redirect");
				return;
			} else {
				String code = pd.getString("code");
				String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxUtil.wx_appid + "&secret="
						+ WxUtil.wx_appsecret + "&code=" + code + "&grant_type=authorization_code";
				String json = WxUtil.loadJson(url);
				Map<String, Object> mapTicket = WxUtil.parseJSON2Map(json);
				if (mapTicket.get("openid") != null) {
					openid = mapTicket.get("openid").toString();
				}
				redUrl = pd.getString("state");
			}
			user.setOPENID(openid);
		} else {
			openid = user.getOPENID();
		}

		if(redUrl.matches(".*/((Palm_treasure.html)).*")) {
			response.sendRedirect(redUrl + "?openid=" + openid);
			return;
		}
		if(redUrl.matches(".*/((login.html)).*")) {
			response.sendRedirect(redUrl + "?openid=" + openid);
			return;
		}
		if(redUrl.matches(".*/((zhanzongbao_login.html)).*")) {
			response.sendRedirect(redUrl + "?openid=" + openid);
			return;
		}
		if (!"".equals(openid)) {
			pd.put("OPENID", openid);
			session.setAttribute("user_openid", openid);
			
			List<PageData> pl = userService.getUserByWx(pd);
			if (pl.size() == 0) {
				// 查询出来的是null或者USER_ID是空的
				response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/2thiBady/html/login/BingPage.html?openid=" + openid);
			} else {
				for (int i = 0; i < pl.size(); i++) {
					PageData p = pl.get(i);
					if (p.get("isLock").equals(1) && redUrl.matches(Const.HTML_TEACHER) && "2".equals(p.get("U_TYPE").toString())) {
						// 已锁定
						response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/role/noRole_lock.html");
					}
					if (p.get("isLock").equals(1) && redUrl.matches(Const.HTML_PARENT) && "3".equals(p.get("U_TYPE").toString())) {
						// 已锁定
						response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/role/noRole_lock.html");
					}
				}

				boolean checkFlg = false;
				// 正常查询出数据,即有用户
				for (int i = 0; i < pl.size(); i++) {
					PageData p = pl.get(i);
					user.setUSER_ID(p.getString("USER_ID"));
					session.setAttribute(Const.SESSION_USER, user);
					session.setAttribute("schoolId", p.get("schoolId"));
					if (redUrl.contains("?")) {
						redUrl = redUrl + "&openid=" + openid;
					} else {
						redUrl = redUrl + "?openid=" + openid;
					}
					response.sendRedirect(redUrl);
					break;
					// 无权限页面
//					if (redUrl.matches(Const.HTML_TEACHER) && "2".equals(p.get("U_TYPE").toString())) {
//						response.sendRedirect(redUrl);
//						checkFlg = true;
//						break;
//					} else if (redUrl.matches(Const.HTML_PARENT) && "3".equals(p.get("U_TYPE").toString())) {
//						response.sendRedirect(redUrl);
//						checkFlg = true;
//						break;
//					} 
				}
					
//					if (checkFlg == false) {
//						// 无权限页面
//						String noRoleUrl = "http://" + WxUtil.wx_http + "/palmcare/role/noRole_menu.html";
//						response.sendRedirect(noRoleUrl);
//					}
			}
		} 
	}
	
	/**
	 * 获取TeacherOpenid转到相应页面（参数redUrl：转码之后的回调页面，得到TeacherOpenid转到页面）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTeacherOpenid")
	public void getTeacherOpenid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getRequest().getSession().removeAttribute("sessionUser");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String openid = "";
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (user == null) {
			user = new User();
		}
		PageData pd = this.getPageData();
		String redUrl = "";
		if (pd.getString("redUrl") != null) {
			redUrl = java.net.URLDecoder.decode(pd.getString("redUrl"), "UTF-8");
		}
		if (user.getOPENID() == null || "".equals(user.getOPENID())) {
			// 获取openid绑定
			if (pd.getString("code") == null || pd.getString("code") == "") {
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxUtil.wx_teacher_appid
						+ "&redirect_uri=http%3A%2F%2F" + WxUtil.wx_http
						+ "%2FWxTest%2FgetTeacherOpenid&response_type=code&scope=snsapi_userinfo&state=" + redUrl
						+ "#wechat_redirect");
				return;
			} else {
				String code = pd.getString("code");
				String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxUtil.wx_teacher_appid + "&secret="
						+ WxUtil.wx_teacher_appsecret + "&code=" + code + "&grant_type=authorization_code";
				String json = WxUtil.loadJson(url);
				Map<String, Object> mapTicket = WxUtil.parseJSON2Map(json);
				if (mapTicket.get("openid") != null) {
					openid = mapTicket.get("openid").toString();
				}
				redUrl = pd.getString("state");
			}
			user.setOPENID(openid);
		} else {
			openid = user.getOPENID();
		}

		if (!"".equals(openid)) {
			pd.put("OPENID", openid);
			session.setAttribute("user_openid", openid);
			
			List<PageData> pl = userService.getUserByTeaWx(pd);
			if (pl.size() == 0) {
				// 查询出来的是null或者USER_ID是空的
				response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/2thiBady/html/teacher/TeacherBingPage.html?openid=" + openid);
			} else {
				for (int i = 0; i < pl.size(); i++) {
					PageData p = pl.get(i);
					if (p.get("isLock").equals(1) && redUrl.matches(Const.HTML_TEACHER) && "2".equals(p.get("U_TYPE").toString())) {
						// 已锁定
						response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/role/noRole_lock.html");
					}
					if (p.get("isLock").equals(1) && redUrl.matches(Const.HTML_PARENT) && "3".equals(p.get("U_TYPE").toString())) {
						// 已锁定
						response.sendRedirect("http://" + WxUtil.wx_http + "/palmcare/role/noRole_lock.html");
					}
				}

				// 正常查询出数据,即有用户
				for (int i = 0; i < pl.size(); i++) {
					PageData p = pl.get(i);
					user.setUSER_ID(p.getString("USER_ID"));
					session.setAttribute(Const.SESSION_USER, user);
					session.setAttribute("schoolId", p.get("schoolId"));
					session.setAttribute("classID", p.get("classID"));
					if (redUrl.contains("?")) {
						redUrl = redUrl + "&openid=" + openid;
					} else {
						redUrl = redUrl + "?openid=" + openid;
					}
					response.sendRedirect(redUrl);
					break;
				}
			}
		} 
	}

	/**
	 * 测试（给家长发送模板消息)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sendTempTest")
	public void sendTempTest(HttpServletRequest request, HttpServletResponse response) {
		PageData pd = this.getPageData();
		String openid = pd.getString("openid");
		// 测试帐号新建测试模板。填写模板标题、模板内容
		// 模板标题：哈哈哈
		// 模板内容：我是{{aidi.DATA}}
		// 生成模板id：lTSkmf-__p7J5UfY_KH3EBLvfByOLZa2z5GXSUQGDQw
		try {
			WxTemplate temp = new WxTemplate();
			// 点击消息转到的url
			temp.setUrl("http://www.baidu.com");
			// 家长的openid
			temp.setTouser(openid);
			// 标题颜色
			temp.setTopcolor("#000000");
			// 模板id
			temp.setTemplate_id("lTSkmf-__p7J5UfY_KH3EBLvfByOLZa2z5GXSUQGDQw");

			// 参数设置
			Map<String, TemplateData> m = new HashMap<String, TemplateData>();
			TemplateData aidi = new TemplateData();
			aidi.setColor("#000000");
			aidi.setValue("奥巴马");
			m.put("aidi", aidi);
			temp.setData(m);
			// 开始发送
			sendTemp(WxUtil.wx_appid, WxUtil.wx_appsecret, temp, request);

			// 发送结果
			/******************************************
			 * 哈哈哈
			 * 
			 * 我是奥巴马
			 ******************************************/
			// 经测试，成功
		} catch (Exception e) {
		}
	}

	/**
	 * 发送模板消息
	 * 
	 * @param appid：公众账号的唯一标识
	 * @param appsecret：公众账号的密钥
	 * @param temp：模板类对象
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(value = "/sendTemp")
	public void sendTemp(String appid, String appsecret, WxTemplate temp, HttpServletRequest request)
			throws IOException, ParseException {
		String access_token = WxUtil.getToken(appid, appsecret, request);
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
		String jsonString = JSONObject.fromObject(temp).toString();
		JSONObject jsonObject = httpRequest(url, "POST", jsonString);
		System.out.println(jsonObject);
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				System.out.println(
						"错误 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg"));
			}
		}
		System.out.println("模板消息发送结果：" + result);
	}

	/**
	 * 
	 * 发起https请求并获取结果
	 *
	 * 
	 * 
	 * @param requestUrl
	 *            请求地址
	 * 
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * 
	 * @param outputStr
	 *            提交的数据
	 * 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * 
	 */

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

		JSONObject jsonObject = null;

		StringBuffer buffer = new StringBuffer();

		try {

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化

			TrustManager[] tm = { new MyX509TrustManager() };

			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象

			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);

			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);

			httpUrlConn.setDoInput(true);

			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）

			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))

				httpUrlConn.connect();

			// 当有数据需要提交时

			if (null != outputStr) {

				OutputStream outputStream = httpUrlConn.getOutputStream();

				// 注意编码格式，防止中文乱码

				outputStream.write(outputStr.getBytes("UTF-8"));

				outputStream.close();

			}

			// 将返回的输入流转换成字符串

			InputStream inputStream = httpUrlConn.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;

			while ((str = bufferedReader.readLine()) != null) {

				buffer.append(str);

			}

			bufferedReader.close();

			inputStreamReader.close();

			// 释放资源

			inputStream.close();

			inputStream = null;

			httpUrlConn.disconnect();

			jsonObject = JSONObject.fromObject(buffer.toString());

		} catch (ConnectException ce) {

			System.out.println("Weixin server connection timed out.");

		} catch (Exception e) {

			System.out.println("https request error:{}" + e);

		}

		return jsonObject;

	}

}

class MyX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
