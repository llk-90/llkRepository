package com.fh.service.weixin.leave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.system.configure.ConfigureController;
import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.controller.weixin.TemplateData;
import com.fh.controller.weixin.WxTemplate;
import com.fh.controller.weixin.WxTest;
import com.fh.controller.weixin.WxUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("leaveAppService")
public class LeaveAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveAppMapper.list", pd);
	}

	public String findSchool(String user_id) throws Exception {
		return (String) dao.findForObject("LeaveAppMapper.findSchool", user_id);
	}

	public void approval(PageData pd) throws Exception {
		dao.update("LeaveAppMapper.approval", pd);
	}

	public void sendTemp(HttpServletRequest request, HttpServletResponse response, PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		List<PageData> list = new ArrayList<PageData>();
		String user_id = "";
		if(user != null){
			user_id = user.getUSER_ID();
		}
		Configure configure = new Configure();
		/*pd = (PageData) dao.findForObject("LeaveAppMapper.findLeaveById", pd);*/
		list = (List<PageData>) dao.findForList("LeaveAppMapper.findLeaveById", pd);
		for(PageData p :list){
			String leaveTime = "";
			if(p.getString("l_start_time") != null && p.getString("l_end_time") != null && p.get("l_leave_type").toString() != null){
				if(p.getString("l_start_time").equals(p.getString("l_end_time"))){
					if(p.get("l_leave_type").toString().equals("1")){
						leaveTime = p.getString("l_start_time") + "上午";
					}else if (p.get("l_leave_type").toString().equals("-1")) {
						leaveTime = p.getString("l_start_time") + "下午";
					}else if (p.get("l_leave_type").toString().equals("0")) {
						leaveTime = p.getString("l_start_time") + "全天";
					}
				}else{
					leaveTime = p.getString("l_start_time") + "到" + p.getString("l_end_time");
				}
			}
			String state = "";
			if(p.get("l_state").toString().equals("-1")){
				state = "驳回";
			}else if (p.get("l_state").toString().equals("1")) {
				state = "批准";
			}
			String openid = p.getString("uw_open_id");
			String username = (String) dao.findForObject("LeaveAppMapper.findUserNameById", user_id);
			WxTest wxTest = new WxTest();
			try {
				WxTemplate temp = new WxTemplate();
				// 点击消息转到的url
				/*temp.setUrl("http://www.baidu.com");*/
				// 家长的openid
				temp.setTouser(openid);
				// 标题颜色
				temp.setTopcolor("#000000");
				// 模板id
				temp.setTemplate_id("gOdvLr73PgqhAKGyOsHBQQxhXNXI7JjtRfKREXp_xrM");

				// 参数设置
				Map<String, TemplateData> m = new HashMap<String, TemplateData>();
				TemplateData first = new TemplateData();
				first.setColor("#000000");
				first.setValue("您好，"+username+"教师已回复您的请假申请");
				m.put("first", first);
				TemplateData keyword1 = new TemplateData();
				keyword1.setColor("#000000");
				keyword1.setValue(p.getString("s_name"));
				m.put("keyword1", keyword1);
				TemplateData keyword2 = new TemplateData();
				keyword2.setColor("#000000");
				keyword2.setValue(leaveTime);
				m.put("keyword2", keyword2);
				TemplateData keyword3 = new TemplateData();
				keyword3.setColor("#000000");
				keyword3.setValue(state);
				m.put("keyword3", keyword3);
				TemplateData keyword4 = new TemplateData();
				keyword4.setColor("#000000");
				keyword4.setValue(username);
				m.put("keyword4", keyword4);
				/*TemplateData remark = new TemplateData();
				remark.setColor("#000000");
				remark.setValue("查看明细内容请点击“详细”");
				m.put("remark", remark);*/
				temp.setData(m);
				// 开始发送
				wxTest.sendTemp(configure.getAppId(), configure.getAppSecret(), temp,  request);

				// 发送结果
				// 经测试，成功
			} catch (Exception e) {
			}
		}
		
	}

	public List<PageData> listNotice(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveAppMapper.listNotice", pd);
	}

	public String findSchoolByParent(String user_id) throws Exception {
		return (String) dao.findForObject("LeaveAppMapper.findSchoolByParent", user_id);
	}

	public String findUseridByOpenid(String openid) throws Exception {
		return (String) dao.findForObject("LeaveAppMapper.findUseridByOpenid", openid);
	}
}
