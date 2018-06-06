package com.fh.controller.weixiplus.microcampus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.microcampus.MicroCampusService;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;

@RestController
@RequestMapping(value="/weixinplusmicrocampus")
public class MicroCampusController extends BaseController {
	
	

	@Resource(name = "microcampusservice")
	private MicroCampusService microCampusService;
	/**
	 * 微信共用，查询全部微校园banner
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectmicrocampusbanner")
	public PageData selectMicroCampusBanner() throws Exception {
		PageData pageData = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		pageData.put("schoolId", session.getAttribute("schoolId"));
	  return microCampusService.selectMicroCampusBanner(pageData);
	}
	
	/**
	 * 微信用，查询微校园信息
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectmicrocampusinfo")
	public PageData selectMicroCampusInfo() throws Exception {
		PageData pageData = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//pageData.put("schoolId", session.getAttribute("schoolId"));
		pageData.put("schoolId", "41670");
		return microCampusService.selectMicroCampusInfo(pageData);
	}
	/**
	 * 微信用，根据id查询单个微校园信息
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/singlemicrocampusinfo")
	public PageData singleMicroCampusInfo() throws Exception {
		PageData pageData = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//pageData.put("schoolId", session.getAttribute("schoolId"));
		pageData.put("schoolId", "41670");
	return	microCampusService.singleMicroCampusInfo(pageData);
	}
	/**
	 * 申请自己学校的Html
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/applyPath")
	public ModelAndView applyPath() throws Exception{
		PageData pd=this.getPageData();
		ModelAndView mv=this.getModelAndView();
		//获取当前用户的User信息
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String userId=user.getUSER_ID();
		pd.put("userId", userId);
		PageData url=microCampusService.applyHtmlPath(pd);
		mv.setViewName("teacher/microCampusUrl");
		mv.addObject("path",  url);
		mv.addObject("pd",  pd);
		return mv;
		
	}
	/**
	 * 微校园，查询文章评论
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectcomment")
	public PageData selectComment() throws Exception {
		PageData pageData = this.getPageData();		
		return microCampusService.selectComment(pageData);
	}
	
	/**
	 * 微校园，插入评论
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savecomment")
	public void savecomment() throws Exception {
		PageData pData = this.getPageData();		
		microCampusService.savecomment(pData);
		
	}
}
