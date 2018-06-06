package com.fh.controller.weixin.teacher;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.teacher.TeacherService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

@RestController
@RequestMapping("/teacher")
public class TeacherAppController extends BaseController {

	@Resource(name = "teacherService")
	TeacherService ser;

	@RequestMapping("/contacts")
	@ResponseBody
	public Object stuParentContactsList() {
		List<PageData> list_stu_parent = new ArrayList<PageData>();
		PageData pd = this.getPageData();
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		
//		String severAddr = getRequest().getScheme()+"://"+ getRequest().getServerName();
//		String url = severAddr + "/child/WxTest/getOpenid.do?redUrl=";
//		String redUrl = severAddr + "/palmcare/message/messageContent.html?lm_send_user_id=";
//		
//		redUrl = redUrl + user.getUSER_ID();
//		redUrl = URLEncoder.encode(redUrl);
//		url = url + redUrl;
		String messageUrl = "../message/messageContent.html?user_id=" + user.getUSER_ID();
		pd.put("messageUrl", messageUrl);
		
		pd.put("USER_ID", user.getUSER_ID());
		try {
			list_stu_parent = ser.contacts(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), list_stu_parent);
	}
}
