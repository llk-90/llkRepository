package com.fh.controller.weixiplus.attendLog;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.FileDownload;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/attendLog")
public class attendLogController extends BaseController {
	String menuUrl = "attendLog/list.do"; // 菜单地址(权限用)
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		ModelAndView mv = this.getModelAndView();	
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			mv.setViewName("system/attendLog/attendLoglist");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 下载txt
	 * @return
	 */
	@RequestMapping(value = "/downtxt")
	public String downExcel(HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		String attTime = pd.getString("time");
		//System.out.println(attTime);
		try {
			FileDownload.fileDownload(response, "D:/logs/" + "log","log");		
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}
}
