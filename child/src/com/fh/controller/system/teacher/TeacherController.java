package com.fh.controller.system.teacher;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.fh.service.system.teacher.TeacherService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.mail.SimpleMailSender;

/**
 * 教师管理
 */
@Controller
@RequestMapping(value = "/teacher")
public class TeacherController extends BaseController {

	String menuUrl = "teacher/list.do"; // 菜单地址(权限用)
	@Resource(name = "teacherService")
	private TeacherService teacherService;

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID()); // 主键
		pd.put("PASSWORD_OLD", pd.getString("PASSWORD"));
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		try {
			teacherService.save(pd);
			// 添加关联
			teacherService.saveRelation(pd);
			SimpleMailSender.sendEmails(Const.SMTP, Const.PORT, Const.EMAIL, Const.PAW, pd.getString("EMAIL"),
					"掌上关爱教师帐号", "您的用户名为" + pd.getString("PHONE") + "，您的密码为" + pd.getString("PASSWORD_OLD") + "。", "1");
		} catch (Exception e) {
		} finally {
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			teacherService.delete(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		teacherService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 修改手机号密码
	 */
	@RequestMapping(value = "/editUserPhone")
	public ModelAndView editUserPhone() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		teacherService.editUserPhone(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		try {
//			  2016/9/5 YC 修改  直接使用创建人查询学校
			PageData p = teacherService.getZoneIdByUserId(pd);
			pd.put("z_id", p.get("Z_ID"));
			// 获取下拉列表
			List<PageData> schoolList = schoolList(pd);
			mv.addObject("schoolList", schoolList);
			page.setPd(pd);
			List<PageData> varList = teacherService.list(page); // 列出Teacher列表
			mv.setViewName("system/teacher/teacher_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pse = new PageData();
		pse.put("USER_ID", user.getUSER_ID());
		try {
//			  2016/9/5 YC 修改  直接使用创建人查询学校
			//PageData p = teacherService.getZoneIdByUserId(pse);
			// 获取下拉列表
			List<PageData> schoolList = schoolList(pse);
			mv.addObject("schoolList", schoolList);

			List<PageData> roleList = teacherService.findJSRole(pd);
			mv.setViewName("system/teacher/teacher_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pse = new PageData();
		pse.put("USER_ID", user.getUSER_ID());
		try {
//			  2016/9/5 YC 修改  直接使用创建人查询学校
			//PageData p = teacherService.getZoneIdByUserId(pse);
			// 获取下拉列表
			List<PageData> schoolList = schoolList(pse);
			mv.addObject("schoolList", schoolList);

			pd = teacherService.findById(pd); // 根据ID读取
			mv.setViewName("system/teacher/teacher_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				teacherService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去导入页面
	 */
	@RequestMapping(value = "/toImport")
	public ModelAndView toImport() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("system/teacher/teacher_import");
			mv.addObject("msg", "saveU");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	@ResponseBody
	public String readExcel(MultipartFile myfiles, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String firstMsg = teacherService.readExcel(myfiles);
		out.print(firstMsg);
		out.flush();
		return null;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public String downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + Const.TEACHERFILENAME,
				Const.TEACHERFILENAME);
		return null;
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
		Map<String, String> map = teacherService.checkPhone(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	/************ 下拉列表联动 *****************/
	/**
	 * 根据区域获取学校
	 * 
	 * @throws Exception
	 */
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) teacherService.schoolList(pd);
	}

	/**
	 * 根据学校获取年级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gradeList")
	@ResponseBody
	public Object gradeList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> gradeList = (List<PageData>) teacherService.gradeList(pd);
		map.put("gradeList", gradeList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 根据学校获取未绑定年级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classList")
	@ResponseBody
	public Object classList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> classList = (List<PageData>) teacherService.classList(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 根据学校获所有年级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classListAll")
	@ResponseBody
	public Object classListAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> classList = (List<PageData>) teacherService.classListAll(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 根据学校获取未绑定年级及当前班级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classListAndNow")
	@ResponseBody
	public Object classListAndNow() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> classList = (List<PageData>) teacherService.classListAndNow(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**********************************/
}
