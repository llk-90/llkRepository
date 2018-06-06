package com.fh.controller.member;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.fh.service.member.MemberService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	String menuUrl = "member/list.do"; // 菜单地址(权限用)
	@Resource(name = "memberService")
	private MemberService memberService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/list")
	public ModelAndView listMember(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> memberList = memberService.memberList(page);
		mv.setViewName("member/member_list");
		mv.addObject("memberList", memberList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 打开新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd() throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		PageData pd = this.getPageData();
		pd.put("userId", userId);
		pd = memberService.findClassIdByUserId(pd);
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("member/StudentAdd");
		mv.addObject("pd", pd);
		mv.addObject("msg", "save");
		return mv;
	}

	/**
	 * 新增学生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		memberService.saveE(pd);
		pd.put("USER_ID", this.get32UUID());
		pd.put("parent_password",
		new SimpleHash("SHA-1", pd.getString("parent_phone"), pd.getString("parent_password")).toString());
		memberService.saveUser(pd);
		memberService.saveUS(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 校验手机号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPhone")
	@ResponseBody
	public Object checkPhone() throws Exception {
		PageData pd = this.getPageData();
		Map<String, String> map = memberService.checkPhone(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 去修改班级信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		PageData pd = new PageData();
		pd.put("userId", userId);
		PageData classInfo = memberService.findById(pd);
		mv.addObject("classInfo", classInfo);
		mv.setViewName("member/update_stu");
		return mv;
	}

	/**
	 * 更新班级信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClass")
	public ModelAndView update() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		memberService.update(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 更新班级信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectClass")
	@ResponseBody
	public Object selectClass() throws Exception {
		PageData pd = this.getPageData();
		int count = memberService.findIsExist(pd);
		if (count > 0) {
			pd.put("msg", "fail");
		} else {
			pd.put("msg", "ok");
		}
		return pd;
	}

	/**
	 * 去修改学生信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData stuInfo = memberService.findByStuId(pd);
		mv.addObject("stuInfo", stuInfo);
		mv.setViewName("member/StudentEdit");
		return mv;
	}

	/**
	 * 编辑学生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if("yes".equals(pd.getString("editPass"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		}else{
			pd.put("PASSWORD", "");
		}
		memberService.editE(pd);
		memberService.editUser(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		memberService.delCO(pd);
		memberService.delCU(pd);
		memberService.delUX(pd);
		memberService.delUser(pd);
		memberService.delUS(pd);
		memberService.delStu(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 跳转学生导入页面
	 * 2016/8/31 yc  修改  (pd传到画面)
	 * @return
	 */
	@RequestMapping(value = "/importStu")
	public ModelAndView importStu() {
		logBefore(logger, "学生信息导入页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("member/studentImport");
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
		String msg = memberService.readExcel(myfiles,id);
		out.print(msg);
		out.flush();
		return null;
	}
			
	@RequestMapping(value = "/returnMember")
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

}
