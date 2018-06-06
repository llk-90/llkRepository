package com.fh.controller.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/videoMessage")
public class VideoMessageController extends BaseController{
	
	/**
	 * 去监控视频信息页面
	 */
	@RequestMapping(value="/list")
	public ModelAndView videoList() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("video/videoMessage");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去监控视频信息新增页面
	 */
	@RequestMapping(value="/addVideoMessage")
	public ModelAndView addVideoMessage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("video/videoMessageAdd");
		mv.addObject("pd", pd);
		return mv;
	}

}
