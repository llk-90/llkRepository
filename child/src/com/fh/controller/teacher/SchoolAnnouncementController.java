package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.AnnouncementService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/schoolAnnouncementController")
public class SchoolAnnouncementController extends BaseController {
	String menuUrl = "schoolAnnouncementController/list.do"; // 菜单地址(权限用)

	@Resource(name = "announcementService")
	private AnnouncementService announcementService;
	
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
		List<PageData> list = announcementService.schooldatalistPage(page);
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/schoolannouncement_list");
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
		mv.setViewName("teacher/schoolannouncement_add");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(@RequestParam("uploadfile") CommonsMultipartFile file, @RequestParam("title") String title,
			 @RequestParam("content") String content, HttpServletRequest request)
					throws Exception {
		logBefore(logger, "新增公告");
		ModelAndView mv = this.getModelAndView();
		String name = get32UUID();  
		String type = "2";  //默认老师通知
		announcementService.schooladd(file,title,type,content,request,name);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/editAnno")
	public ModelAndView editAnno(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = announcementService.getAnno(id);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/schoolannouncement_edit");
		return mv;
	}

	@RequestMapping(value = "/saveEdit")
	public ModelAndView saveEdit(@RequestParam("uploadfile") CommonsMultipartFile file, @RequestParam("title") String title,
			@RequestParam("id") String id, @RequestParam("content") String content, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String name = get32UUID();
		String type = "2";
		announcementService.saveEdit(file,title,type,content,request,name,id);
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
		mv.setViewName("forward://schoolAnnouncementController/list.do");
		return mv;
	}
}