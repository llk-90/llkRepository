package com.fh.controller.hjy;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.hjy.PinMemberService;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "/pinMember")
public class PinMemberController extends BaseController {

	String menuUrl = "pinMember/list.do"; // 菜单地址(权限用)
	@Resource(name = "pinMemberService")
	private PinMemberService pinMemberService;

	/**
	 * 跳转学生导入页面
	 * 2016/8/31 yc  修改  (pd传到画面)
	 * @return
	 */
	@RequestMapping(value = "/importStu")
	public ModelAndView importStu() {
		logBefore(logger, "验证码信息导入页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("hjy/pinMassImport");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 导入
	 * 
	 * @param myfiles
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public String readExcel(MultipartFile myfiles, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id=request.getParameter("id");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String msg = pinMemberService.readExcel(myfiles,id);
		out.print(msg);
		out.flush();
		return null;
	}
			
	@RequestMapping(value = "/returnPinMember")
	public ModelAndView returnMember() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("save_result");
		return mv;
	}
	
	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */	
	
	/*
	 * 下载模板
	 */
	@RequestMapping(value = "/downExcel")
	public String downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + Const.YanzhmFileName,
				Const.YanzhmFileName);
		return null;
	}

}
