package com.fh.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.CardRegistManageService;
import com.fh.service.teacher.EduInfoManageService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@RestController
@RequestMapping(value = "/cardRegistManage")
public class CardRegistManageController extends BaseController {
	String menuUrl = "cardRegistManage/list.do"; // 菜单地址(权限用)
	
	@Resource(name="cardRegistManageService")
	private CardRegistManageService cardRegistManageService;
	
	/**
	 * 展示补卡信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=cardRegistManageService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		page.setPd(pd);
		List<PageData> cardRegistlist=cardRegistManageService.cardRegistlist(page);
		mv.setViewName("teacher/cardRegist_list");
		mv.addObject("cardRegistlist", cardRegistlist);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 前往编辑页面
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData cardRegist = cardRegistManageService.findByInfoId(pd);
		mv.addObject("cardRegist",cardRegist);
		mv.setViewName("teacher/cardRegistEdit");
		return mv;
	}
	
	/**
	 * 编辑提交
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editSubmit")
	public ModelAndView editSubmit() throws Exception{
		logBefore(logger, "修改");
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String name=user.getNAME();
		pd.put("name",name);
		
		cardRegistManageService.editCardRegist(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
}
