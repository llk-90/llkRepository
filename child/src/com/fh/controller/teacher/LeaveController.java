package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.LeaveService;
import com.fh.service.weixin.leave.LeaveAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

import oracle.net.aso.l;

@Controller
@RequestMapping(value = "/leaveController")
public class LeaveController extends BaseController {
	String menuUrl = "leaveController/list.do"; // 菜单地址(权限用)
	
	@Resource(name="leaveService")
	private LeaveService leaveService;
	@Resource(name="leaveAppService")
	private LeaveAppService leaveAppService;

	/**
	 * 列表
	 * @throws Exception 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "学生考勤管理页面");
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = this.getPageData();
		pd.put("user_id", user.getUSER_ID());
		page.setPd(pd);
		List<PageData> leaveList = leaveService.datalistPage(page);
		mv.addObject("leaveList", leaveList);
		mv.setViewName("teacher/leave_list");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value="/approval")
	public ModelAndView approval(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		leaveService.approval(pd);
		leaveAppService.sendTemp(request, response, pd);
		mv.setViewName("forward://leaveController/list.do");
		return mv;
	}
}
