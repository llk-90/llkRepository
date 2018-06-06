package com.fh.controller.system.tools;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.tools.ToolService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.MapDistance;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.TwoDimensionCode;

/** 
 * 类名称：ToolController
 * 创建人：FH 
 * 创建时间：2015年4月4日
 * @version
 */
@Controller
@RequestMapping(value="/tool")
public class ToolController extends BaseController {
	
	@Resource(name = "toolService")
	private ToolService toolService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		toolService.saveE(pd);;
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "修改PropertyFee");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		toolService.editE(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 多级别树页面
	 */
	@RequestMapping(value="/ztree")
	public ModelAndView ztree(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = toolService.Eqlist(page);	
		mv.setViewName("system/tools/ztree");
		mv.addObject("varList", varList );		
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 多级别树跳转新增页面
	 */
	@RequestMapping(value="/ztreeBreakAdd")
	public ModelAndView ztreeBreakAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/ztreeBreakAdd");
		mv.addObject("pd", pd);
		return mv;
	}	
}
