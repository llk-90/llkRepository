package com.fh.controller.system.personalinfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.personalinfo.ResetPswdService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/** 
 * 类名称：ResetPswdComtroller
 * 创建人：
 * 创建时间：2016年6月16日
 * @version
 */
@Controller
@RequestMapping(value="/resetpswd")

public class ResetPswdComtroller extends BaseController {
	String menuUrl = "resetpswd/list.do"; //菜单地址(权限用)
	@Resource(name="resetpswdService")
	private ResetPswdService resetpswdService;
	
	/**
	 * 检查密码是否一致
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/check")
	@ResponseBody
	public Object check() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		map = resetpswdService.selectPass(pd);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改ResetPswd");
		PageData pd = new PageData();
		pd = this.getPageData();
		resetpswdService.editPass(pd);
		return new ModelAndView("redirect:/logout");
	}
	
	/**
	 * 修改密码画面显示
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ResetPswd");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		mv.setViewName("system/persionalinfo/resetpswd");
		mv.addObject("pd", pd);
		return mv;
	}
}

