package com.fh.controller.teacher;

import java.io.PrintWriter;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.ScoresImportService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.Jurisdiction;
import com.fh.util.MapPlus;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "/scoresImportController")
public class ScoresImportController extends BaseController {
	String menuUrl = "scoresImportController/list.do"; // 菜单地址(权限用)
	@Resource(name = "scoresImportService")
	private ScoresImportService scoresImportService;

	/**
	 * 成绩列表显示
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "成绩管理页面");
		//从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String userId =user.getUSER_ID();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("userId", userId);
		page.setPd(pd);
		List<PageData> varList = scoresImportService.list(page); // 列出成绩列表
		mv.addObject("varList", varList);
		List<PageData> piciList = scoresImportService.picilist(page); // 列出考试批次
		mv.addObject("piciList", piciList);
		mv.setViewName("teacher/scores");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/*
	 * 打开成绩导入页面
	 */
	@RequestMapping(value = "/scoresImp")
	public ModelAndView scoresImp() {
		logBefore(logger, "成绩导入页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("teacher/scoresImport");
		return mv;
	}

	/**
	 * 从EXCEL导入成绩到数据库
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public String readExcel(MultipartFile myfiles, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String msg = scoresImportService.readExcel(myfiles);
		out.print(msg);
		out.flush();
		return null;
	}

	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/returnScore")
	public ModelAndView returnScore() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public String downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + Const.SCOREFILENAME,
				Const.SCOREFILENAME);
		return null;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, "批量删除School_Controller");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = this.getPageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		map = scoresImportService.deleteAll(pd);
		return AppUtil.returnObject(pd, map);
	}
}
