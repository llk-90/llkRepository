package com.fh.service.teacher;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.controller.weixin.TemplateData;
import com.fh.controller.weixin.WxTemplate;
import com.fh.controller.weixin.WxTest;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;
import com.fh.util.Tools;;

@Service("announcementService")
public class AnnouncementService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "userService")
	private UserService userService;

	public List<PageData> datalistPage(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AnnouncementMapper.datalistPage", page);
		return list;
	}

	public List<PageData> schooldatalistPage(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AnnouncementMapper.schooldatalistPage", page);
		return list;
	}

	public boolean add(CommonsMultipartFile file, String title, String type, String content, HttpServletRequest request,
			String name) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd.put("type", type);
		pd.put("user_id", user.getUSER_ID());
		String zong_id = (String) dao.findForObject("AnnouncementMapper.findId", pd);
		System.out.println("zone_id ====================================== " + zong_id);
		pd.put("zone_id", zong_id);
		boolean hasWord = false;// 是否有敏感字符
		String afterTitle = DFAWordFilter.filter(title);
		if (!afterTitle.equals(title)) {
			hasWord = true;
		}
		pd.put("title", afterTitle);
		String afterContent = DFAWordFilter.filter(content);
		if (!afterContent.equals(content)) {
			hasWord = true;
		}
		pd.put("content", afterContent);

		// 路径 文件名
		String filepaths = "", filenames = "";
		// 附件接收
		System.out.println("===================start======================");
		if (!file.isEmpty()) {
			System.out.println("===========================empty=======================");
			// 文件后缀名
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			// 实际存储文件名
			String filename = name + fileType;// 取32位UUID作为文件名
			// 实际文件名+后缀
			/* filenames = file.getOriginalFilename(); */
			filepaths = "/uploadFiles/annoPic/" + filename;
			pd.put("n_pic_url", filepaths);
			/*
			 * String path =
			 * request.getSession().getServletContext().getRealPath(filepaths);
			 */
			String path = Tools.readTxtFile(Const.FILENAME) + filepaths;
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				System.out.println("=============================ok===========================");
			} catch (IOException e) {
				System.out.println("==============================" + e);
				e.printStackTrace();

			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 HH:mm");
		Date date = new Date();
		String time = format.format(date);
		pd.put("create_time", date);
		dao.save("AnnouncementMapper.add", pd);
		Configure configure = new Configure();
		String teacher_id = user.getUSER_ID();
		PageData classPd = (PageData) dao.findForObject("NoticeAppMapper.findClassAndTeacher", teacher_id);
		String className = "";
		String teacherName = "";
		if (classPd != null) {
			className = classPd.getString("z_name");
			teacherName = classPd.getString("name");
		}

		List<PageData> parentsList = (List<PageData>) dao.findForList("NoticeAppMapper.listTempUser", teacher_id);

		String openid = "";
		WxTest wxTest = new WxTest();
		
		String severAddr = request.getScheme()+"://"+ request.getServerName();

		for (PageData p : parentsList) {
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
				/* temp.setUrl("http://www.baidu.com"); */
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
				first.setValue("您好，亲爱的" + p.getString("s_name") + "家长");
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
				String afterContent2 = DFAWordFilter.filter(content);
				if (afterContent2.equals(content)) {
					hasWord = true;
				}
				keyword4.setValue(afterContent2);
				m.put("keyword4", keyword4);
				/*
				 * TemplateData remark = new TemplateData();
				 * remark.setColor("#000000"); remark.setValue("查看明细内容请点击“详细”");
				 * m.put("remark", remark);
				 */
				temp.setData(m);
				// 开始发送
				wxTest.sendTemp(configure.getAppId(), configure.getAppSecret(), temp, request);

				// 发送结果
				// 经测试，成功
			} catch (Exception e) {
			}
		}
		if (hasWord) {
			PageData pageData = new PageData();
			pageData.put("userId", user.getUSER_ID());
			userService.updateCount(pageData);
		}
		return hasWord;
	}

	public void schooladd(CommonsMultipartFile file, String title, String type, String content,
			HttpServletRequest request, String name) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd.put("type", type);
		pd.put("user_id", user.getUSER_ID());
		String zong_id = (String) dao.findForObject("AnnouncementMapper.findSchoolId", pd);
		System.out.println("zone_id ====================================== " + zong_id);
		pd.put("zone_id", zong_id);
		pd.put("content", DFAWordFilter.filter(content));
		pd.put("title", DFAWordFilter.filter(title));

		// 路径 文件名
		String filepaths = "", filenames = "";
		// 附件接收
		System.out.println("===================start======================");
		if (!file.isEmpty()) {
			System.out.println("===========================empty=======================");
			// 文件后缀名
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			// 实际存储文件名
			String filename = name + fileType;// 取32位UUID作为文件名
			// 实际文件名+后缀
			/* filenames = file.getOriginalFilename(); */
			filepaths = "/uploadFiles/annoPic/" + filename;
			pd.put("n_pic_url", filepaths);
			/*
			 * String path =
			 * request.getSession().getServletContext().getRealPath(filepaths);
			 */
			String path = Tools.readTxtFile(Const.FILENAME) + filepaths;
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				System.out.println("=============================ok===========================");
			} catch (IOException e) {
				System.out.println("==============================" + e);
				e.printStackTrace();

			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 HH:mm");
		Date date = new Date();
		String time = format.format(date);
		pd.put("create_time", date);
		dao.save("AnnouncementMapper.add", pd);
		Configure configure = new Configure();
		String teacher_id = user.getUSER_ID();
		PageData classPd = (PageData) dao.findForObject("NoticeAppMapper.findSchoolAndTeacher", teacher_id);
		String className = "";
		String teacherName = "";
		if (classPd != null) {
			className = "全部";
			teacherName = classPd.getString("name");
		}

		List<PageData> parentsList = (List<PageData>) dao.findForList("NoticeAppMapper.listSchoolTempUser", teacher_id);

		String openid = "";
		WxTest wxTest = new WxTest();
		
		String severAddr = request.getScheme()+"://"+ request.getServerName();

		for (PageData p : parentsList) {
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
				/* temp.setUrl("http://www.baidu.com"); */
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
				first.setValue("您好，亲爱的" + p.getString("s_name") + "家长");
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
				keyword4.setValue(DFAWordFilter.filter(content));
				m.put("keyword4", keyword4);
				/*
				 * TemplateData remark = new TemplateData();
				 * remark.setColor("#000000"); remark.setValue("查看明细内容请点击“详细”");
				 * m.put("remark", remark);
				 */
				temp.setData(m);
				// 开始发送
				wxTest.sendTemp(configure.getAppId(), configure.getAppSecret(), temp, request);

				// 发送结果
				// 经测试，成功
			} catch (Exception e) {
			}
		}
	}

	public PageData getAnno(String id) throws Exception {
		return (PageData) dao.findForObject("AnnouncementMapper.getAnno", id);
	}

	public void saveEdit(CommonsMultipartFile file, String title, String type, String content,
			HttpServletRequest request, String name, String id) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Date date = new Date();
		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("type", type);
		pd.put("id", id);
		String zong_id = (String) dao.findForObject("AnnouncementMapper.findId", pd);
		pd.put("zone_id", zong_id);
		pd.put("content", DFAWordFilter.filter(content));
		pd.put("create_time", date);
		pd.put("title", DFAWordFilter.filter(title));

		// 路径 文件名
		String filepaths = "";
		String filenames = "";
		// 附件接收
		if (!file.isEmpty()) {
			// 文件后缀名
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			// 实际存储文件名
			String filename = name + fileType;// 取32位UUID作为文件名
			// 实际文件名+后缀
			/* filenames = file.getOriginalFilename(); */
			filepaths = "/uploadFiles/annoPic/" + filename;
			pd.put("n_pic_url", filepaths);
			String path = Tools.readTxtFile(Const.FILENAME) + filepaths;
			/*
			 * String path =
			 * request.getSession().getServletContext().getRealPath(filepaths);
			 */
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.save("AnnouncementMapper.saveEdit", pd);
		Configure configure = new Configure();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 HH:mm");
		String time = format.format(date);
		String teacher_id = user.getUSER_ID();
		PageData classPd = (PageData) dao.findForObject("NoticeAppMapper.findClassAndTeacher", teacher_id);
		String className = "";
		String teacherName = "";
		if (classPd != null) {
			className = classPd.getString("z_name");
			teacherName = classPd.getString("name");
		}

		List<PageData> parentsList = (List<PageData>) dao.findForList("NoticeAppMapper.listTempUser", teacher_id);

		String openid = "";
		WxTest wxTest = new WxTest();
		
		String severAddr = request.getScheme()+"://"+ request.getServerName();

		for (PageData p : parentsList) {
			openid = p.getString("uw_open_id");
			// 测试帐号新建测试模板。填写模板标题、模板内容
			// 模板标题：哈哈哈
			// 模板内容：我是{{aidi.DATA}}
			// 生成模板id：lTSkmf-__p7J5UfY_KH3EBLvfByOLZa2z5GXSUQGDQw
			try {
				WxTemplate temp = new WxTemplate();

				String url = severAddr + "/WxTest/getOpenid.do?redUrl=";
				String redUrl = severAddr + "/palmcare/notice/notice_list.html?openid=";
				
				redUrl = redUrl + openid;
				redUrl = URLEncoder.encode(redUrl, "utf-8");
				url = url + redUrl;
				
				// 点击消息转到的url
				/* temp.setUrl("http://www.baidu.com"); */
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
				first.setValue("您好，亲爱的" + p.getString("s_name") + "家长");
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
				keyword4.setValue(DFAWordFilter.filter(content));
				m.put("keyword4", keyword4);
				/*
				 * TemplateData remark = new TemplateData();
				 * remark.setColor("#000000"); remark.setValue("查看明细内容请点击“详细”");
				 * m.put("remark", remark);
				 */
				temp.setData(m);
				// 开始发送
				wxTest.sendTemp(configure.getAppId(), configure.getAppSecret(), temp, request);

				// 发送结果
				// 经测试，成功
			} catch (Exception e) {
			}
		}
	}

	public void delAnno(String id) throws Exception {
		dao.delete("AnnouncementMapper.delAnno", id);
	}
}