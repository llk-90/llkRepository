package com.fh.controller.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.service.teacher.AnnouncementService;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/announcementController")
public class AnnouncementController extends BaseController {
	String menuUrl = "announcementController/list.do"; // 菜单地址(权限用)

	@Resource(name = "announcementService")
	private AnnouncementService announcementService;
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "发布公告页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		page.setPd(pd);
		List<PageData> list = announcementService.datalistPage(page);
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/announcement_list");
		return mv;
	}

	/***
	 * 跳转至新增公告页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addAnno")
	public ModelAndView addAnno() {
		logBefore(logger, "新增公告页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("teacher/announcement_add");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(@RequestParam("uploadfile") CommonsMultipartFile file, @RequestParam("title") String title,
			@RequestParam("content") String content, HttpServletRequest request) throws Exception {
		logBefore(logger, "新增公告");
		ModelAndView mv = this.getModelAndView();
		String name = get32UUID();
		String type = "1"; // 默认老师通知
		boolean hasWord = announcementService.add(file, title, type, content, request, name);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		if (hasWord) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData pageData = new PageData();
			pageData.put("userId", user.getUSER_ID());
			PageData count = userService.findCountByUserId(pageData);

			pageData.clear();
			PageData linJie = userService.findLinJie(pageData);
			if (count != null && linJie != null) {
				if (new Integer(count.get("count").toString()) >= new Integer(linJie.get("Value").toString())) {
					mv.setViewName("redirect:/logout");
				}
			}
		}

		return mv;
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public Object check(@RequestParam("title") String title, @RequestParam("content") String content) throws Exception {
		logBefore(logger, "公告列表");
		Map<String, Object> map = new HashMap<>();
		boolean hasWord = false;// 是否有敏感字符
		String afterTitle = DFAWordFilter.filter(title);
		if (!afterTitle.equals(title)) {
			hasWord = true;
		}
		String afterContent = DFAWordFilter.filter(content);
		if (!afterContent.equals(content)) {
			hasWord = true;
		}
		if (hasWord) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData pageData = new PageData();
			pageData.put("userId", user.getUSER_ID());
			PageData count = userService.findCountByUserId(pageData);
			map.put("isAlert", true);
			map.put("count", count.get("count"));
			pageData.clear();
			PageData linJie = userService.findLinJie(pageData);
			map.put("linJie", linJie.get("Value"));
		}
		return map;
	}

	@RequestMapping(value = "/editAnno")
	public ModelAndView editAnno(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = announcementService.getAnno(id);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/announcement_edit");
		return mv;
	}

	@RequestMapping(value = "/saveEdit")
	public ModelAndView saveEdit(@RequestParam("uploadfile") CommonsMultipartFile file,
			@RequestParam("title") String title, @RequestParam("id") String id, @RequestParam("content") String content,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String name = get32UUID();
		String type = "1";
		announcementService.saveEdit(file, title, type, content, request, name, id);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/delAnno")
	public ModelAndView delAnno() throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView();
		String id = pd.getString("id");
		announcementService.delAnno(id);
		mv.setViewName("forward://announcementController/list.do");
		return mv;
	}
}