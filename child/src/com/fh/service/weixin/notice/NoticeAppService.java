package com.fh.service.weixin.notice;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.controller.weixin.TemplateData;
import com.fh.controller.weixin.WxTemplate;
import com.fh.controller.weixin.WxTest;
import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;


@Service("noticeAppService")
public class NoticeAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("NoticeAppMapper.list", pd);
	}
	
	public String findSchool(String user_id) throws Exception{
		return (String) dao.findForObject("NoticeAppMapper.findSchool", user_id);
	}
	
	public PageData findClass(String user_id) throws Exception{
		return (PageData) dao.findForObject("NoticeAppMapper.findClass", user_id);
	}
	
	public void addNotice(PageData pd) throws Exception {
		dao.save("NoticeAppMapper.addNotice", pd);
	}
	
	public void sendTemp(HttpServletRequest request, HttpServletResponse response, PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject(); 
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Configure configure = new Configure();
		String time = pd.getString("notice_time");
		String content = DFAWordFilter.filter(pd.getString("content"));
		String teacher_id = user.getUSER_ID();
		PageData classPd = (PageData) dao.findForObject("NoticeAppMapper.findClassAndTeacher", teacher_id);
		String className = "";
		String teacherName = "";
		if(classPd != null){
			className = classPd.getString("z_name");
			teacherName = classPd.getString("name");
		}
		
		List<PageData> parentsList = (List<PageData>) dao.findForList("NoticeAppMapper.listTempUser", teacher_id);
		
		String openid = "";
		WxTest wxTest = new WxTest();
		
		String severAddr = request.getScheme()+"://"+ request.getServerName();
		
		for(PageData p : parentsList){
			openid = p.getString("uw_open_id");

			String url = severAddr + "/WxTest/getOpenid.do?redUrl=";
			String redUrl = severAddr + "/palmcare/notice/notice_list.html?openid=";
			
			redUrl = redUrl + openid;
			redUrl = URLEncoder.encode(redUrl, "utf-8");
			url = url + redUrl;
			
			// 测试帐号新建测试模板。填写模板标题、模板内容
			// 模板标题：哈哈哈
			// 模板内容：我是{{aidi.DATA}}
			// 生成模板id：lTSkmf-__p7J5UfY_KH3EBLvfByOLZa2z5GXSUQGDQw
			try {
				WxTemplate temp = new WxTemplate();
				// 点击消息转到的url
				/*temp.setUrl("http://www.baidu.com");*/
				// 点击消息转到的url
				temp.setUrl(url);
				// 家长的openid
				temp.setTouser(openid);
				// 标题颜色
				temp.setTopcolor("#000000");
				// 模板id
				temp.setTemplate_id("C9PppZ5j_NKPbIFyhwMsh1ceJhLKDoiClpVszKuuX1s");

				// 参数设置
				Map<String, TemplateData> m = new HashMap<String, TemplateData>();
				TemplateData first = new TemplateData();
				first.setColor("#000000");
				first.setValue("您好，亲爱的"+p.getString("s_name")+"家长");
				m.put("first", first);
				TemplateData keyword1 = new TemplateData();
				keyword1.setColor("#000000");
				keyword1.setValue(className);
				m.put("keyword1", keyword1);
				TemplateData keyword2 = new TemplateData();
				keyword2.setColor("#000000");
				keyword2.setValue(teacherName);
				m.put("keyword2", keyword2);
				TemplateData keyword3 = new TemplateData();
				keyword3.setColor("#000000");
				keyword3.setValue(time);
				m.put("keyword3", keyword3);
				TemplateData keyword4 = new TemplateData();
				keyword4.setColor("#000000");
				keyword4.setValue(content);
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
	
	public String getZoneByOpenid(String openid) throws Exception{
		return (String) dao.findForObject("NoticeAppMapper.getZoneByOpenid", openid);
	}
	
}

