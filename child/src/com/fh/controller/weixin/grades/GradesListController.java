package com.fh.controller.weixin.grades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.weixin.grades.GradesListService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping(value = "/gradesList")
public class GradesListController extends BaseController {

	@Resource(name = "gradeslistService")
	private GradesListService gradeslistService;

	/**
	 * 成绩列表显示
	 */
	@RequestMapping(value = "/detailst")
	@ResponseBody
	public Object detailslist() throws Exception {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<>();
		// 从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		pd.put("userId", userId);
		List<PageData> detailslist = gradeslistService.getgradeslist(pd); // 查询成绩
		List<PageData> gettotallist = gradeslistService.gettotallist(pd); // 查询成绩排名
		List<PageData> getpicilist = gradeslistService.getpicilist(pd); // 根据考试批次进行查询
		int a = 0;
		String faile = "undefined";
		if (gettotallist.size() > 0) {
			for (PageData strList : gettotallist) {
				if (detailslist.size() > 0) {
					for (PageData numList : detailslist) {
						for (PageData piciList : getpicilist) {
							String stuNum = numList.getString("number");// 根据家长查询到的手机号
							String stuId = strList.getString("rc_phone");// 成绩排名时查询的手机号		
							String piciId = piciList.getString("rc_phone");// 考试批次查询时查询的手机号			
							String pici = piciList.getString("rc_batch");// 考试批次查询
							String pici1 = pd.getString("pici");
							a++;
							if (Tools.isEmpty(pici1)) {
								if (stuId.equals(stuNum)) {
									map.put("faile", Const.SUCCESS);
									map.put("list", detailslist);
									map.put("sort", a);
									break;
								} else {
									break;
								}
							} else {
								if (piciId.equals(stuNum) && pici.equals(pici1)) {
									map.put("faile", Const.SUCCESS);
									map.put("list", detailslist);
									map.put("sort", a);
									return map;
								} else {
									continue;
								}

							}

						}
					}
				}
			}
		} else {
			map.put("faile", faile);
			map.put("sort", a);
		}
		return map;
	}

	@RequestMapping(value = "/selectbox")
	@ResponseBody
	public Object selectbox(Page page, HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		pd.put("userId", userId);
		List<PageData> selectbox = gradeslistService.getselectbox(pd);
		return AppUtil.returnObject(pd, selectbox);
	}

	/**
	 * 单科成绩
	 */
	@RequestMapping(value = "/singleSubject")
	@ResponseBody
	public Object singleSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		if (pd.getString("subject") == null && "".equals(pd.getString("subject"))) {
			return null;
		}
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		String strDate = "";
		String endDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = sdf.parse(DateUtil.getDay());
		String nowYear = DateUtil.getYear();
		if (nowDate.after(sdf.parse(nowYear + "-09-01"))) {
			strDate = nowYear + "-09-01";
			endDate = DateUtil.getDay();
		} else {
			strDate = (Integer.parseInt(nowYear) - 1) + "-09-01";
			endDate = DateUtil.getDay();
		}
		pd.put("strDate", strDate);
		pd.put("endDate", endDate);
		List<PageData> singleList = gradeslistService.singleList(pd);
		return AppUtil.returnObject(pd, singleList);
	}

	/**
	 * 根据家长查询学生班级
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stuClassName")
	@ResponseBody
	public Object stuClassName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		// 班级
		PageData p1 = gradeslistService.stuClassName(pd);
		if (p1 == null || Tools.isEmpty(p1.getString("className"))) {
			return null;
		}
		map.put("className", p1.getString("className"));
		// 科目
		PageData p2 = gradeslistService.stuSubjectNotEmpty(pd);
		map.put("subject", p2);
		return AppUtil.returnObject(pd, map);
	}

}
