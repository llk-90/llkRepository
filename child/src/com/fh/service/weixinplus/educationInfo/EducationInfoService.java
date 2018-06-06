package com.fh.service.weixinplus.educationInfo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;

import net.sf.json.JSONObject;

/**
 * 
 * @author phf
 *
 */
@Service("educationInfoService")
public class EducationInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
	private static final String DetaiJs = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\educationDetil.js";// 定义空格回车换行符
	private static final String listJs = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\educational.js";// 定义空格回车换行符
	private static final String listHtml = "D:\\weixin\\palmcare\\2thiBady\\html\\jiaozilangfang\\schoolDoc\\educational.html";// 定义空格回车换行符
	private static final String JsRoot = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\";
	private static final String HtmlRoot = "D:\\weixin\\palmcare\\2thiBady\\html\\jiaozilangfang\\schoolDoc\\";
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	public void addBannerPicture(PageData pd) throws Exception {
		dao.save("EducationInfoMapper.addBannerPicture", pd);
	}

	public void addInfo(PageData pd) throws Exception {
		dao.save("EducationInfoMapper.addInfo", pd);
	}

	public Integer findInfoId() throws Exception {
		return (Integer) dao.findForObject("EducationInfoMapper.findInfoId", null);
	}

	public void addInfoPicture(PageData pd) throws Exception {
		dao.save("EducationInfoMapper.addInfoPicture", pd);
	}

	public void addInfoImage(PageData pd) throws Exception {
		dao.save("EducationInfoMapper.addInfoImage", pd);
	}

	/**
	 * 点击首页展示banner轮播图
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public PageData getBanner(PageData pd) {
		List<PageData> bannerList = null;
		PageData pageData = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		PageData temppd = new PageData();
		try {
			temppd.put("schoolId", dao.findForObject("ClassMomentMapper.getSchoolIdByOpenId", temppd).toString());
			bannerList = (List<PageData>) dao.findForList("EducationInfoMapper.findBannerImage", temppd);
			if (bannerList.size() == 0) {
				pageData.put("errorcode", errorMsg.Success(1001));
			} else {
				pageData.put("errorcode", errorMsg.Success(0));
				pageData.put("infolist", bannerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageData;
	}

	/**
	 * 点击首页展示教育咨询列表
	 */
	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;

	@SuppressWarnings("unchecked")
	public PageData findEducationInfoList(PageData pd) throws Exception {
		System.out.println("pd:" + pd);
		PageData pageData = new PageData();
		int pageCount = Integer.valueOf(pd.getString("pageCount")).intValue();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		PageData temppd = new PageData();
		if (session.getAttribute("schoolId") == null) {
			temppd.put("schoolId", 0);
		} else {
			temppd.put("schoolId", session.getAttribute("schoolId"));
		}
		List<PageData> IcNo = (List<PageData>) dao.findForList("EducationInfoMapper.findIcNo", pd);
		if (IcNo == null) {
			pageData.put("errorcode", errorMsg.Success(1001));
		} else {
			pageData.put("IcNo", IcNo.get(0).getString("IcNo"));
			pageData.put("StudentId", IcNo.get(0).getString("StudentId"));
			// System.out.println("IcNo.get(0):"+IcNo.get(0).getString("IcNo"));
		}
		temppd.put("pageCount", pageCount);
		// if (checkValueService.OpenidCheck(pd.getString("openId"))) {
		try {
			List<PageData> educationInfoList = (List<PageData>) dao
					.findForList("EducationInfoMapper.findEducationInfoList", temppd);
			if (educationInfoList.size() == 0) {
				pageData.put("errorcode", errorMsg.Success(1001));
			} else {
				for (int i = 0; i < educationInfoList.size(); i++) {
					PageData educationInfo = educationInfoList.get(i);
					educationInfo.put("content", getTextFromHtml(educationInfo.get("content").toString()));

				}
				pageData.put("errorcode", errorMsg.Success(0));
				pageData.put("educationInfoList", educationInfoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageData;
	}

	@SuppressWarnings("unchecked")
	public PageData findInfoById(PageData pd) throws Exception {
		PageData pageData = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		pd.put("schoolId", session.getAttribute("schoolId"));
		// System.out.println(session.getAttribute("schoolId"));
		PageData educationInfo = (PageData) dao.findForObject("EducationInfoMapper.findEducationInfoById", pd);
		if (educationInfo.size() == 0) {
			pageData.put("errorcode", errorMsg.Success(1001));
		} else {
			pageData.put("errorcode", errorMsg.Success(0));
			pageData.put("educationInfo", educationInfo);
		}
		return pageData;
	}

	@SuppressWarnings("unchecked")
	public List<String> findInfoPictures(Integer InfoId) throws Exception {
		return (List<String>) dao.findForList("EducationInfoMapper.findEducationInfoPictures", InfoId);
	}

	/**
	 * @param htmlStr
	 * @return 删除Html标签
	 */
	public static String delHTMLTag(String htmlStr) {
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		if (htmlStr.length() > 41) {
			htmlStr = htmlStr.substring(0, 40);
		}
		return htmlStr;
	}

	/**
	 * @param User
	 * @return 学校教子良方Html地址 申请当前的学校的Html
	 * @throws Exception
	 */
	public PageData applyHtmlPath(User user) throws Exception {

		PageData apply = new PageData();
		PageData schoolInfo = (PageData) dao.findForObject("EducationInfoMapper.findSchoolInfo", user.getUSER_ID());
		PageData res = new PageData();
		if (schoolInfo.size() == 0) {
			res.put("errorcode", errorMsg.Success(1001));
			return res;
		}
		if (schoolInfo.get("path_string") != null) {
			// 如果以前申请过
			apply.put("school_id", schoolInfo.get("school_id").toString());
			apply.put("school_name", schoolInfo.get("z_name").toString());
			apply.put("path_string", schoolInfo.get("path_string").toString());
			apply.put("apply_user_id", user.getUSER_ID());
			dao.save("EducationInfoMapper.updateSchooUrlInfo", apply);
		} else {
			// 如果以前没有申请过
			apply.put("school_id", schoolInfo.get("school_id").toString());
			apply.put("school_name", schoolInfo.get("z_name").toString());
			apply.put("path_string", "rytrytr");
			apply.put("apply_user_id", user.getUSER_ID());
			dao.save("EducationInfoMapper.saveSchooUrlInfo", apply);
		}

		// 修改教子良方列表js文件
		writeFile(JsRoot, schoolInfo.get("school_id").toString(), listJs);
		// 修改教子良方详情js文件
		writeFile(JsRoot, schoolInfo.get("school_id").toString(), DetaiJs);
		// 返回教子良方列表Html文件
		res.put("url_path", writeFile(HtmlRoot, schoolInfo.get("school_id").toString(), listHtml));
		return res;
	}

	/**
	 * @param 文件地址
	 * @return 文本文字
	 * 
	 * @throws Exception
	 */
	public String readFile(String fileName, String schoolId) {
		String fileContent = "";
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
				BufferedReader reader = new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null) {
					fileContent += line;
				}
				read.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileContent = fileContent.replace("#{target}", schoolId);
		return fileContent;
	}

	/**
	 * @param 文件地址
	 * @return
	 * 
	 * @throws Exception
	 */
	public String writeFile(String fileName, String schoolId, String path) {
		try {
			File f = new File(fileName + schoolId + ".html");
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(readFile(path, schoolId));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://www.guanai100.cn/palmcare/2thiBady/html/jiaozilangfang/schoolDoc/" + schoolId + ".html";
	}
}
