package com.fh.controller.system.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.tools.StudentManageService;
import com.fh.service.system.tools.StudentRegionservice;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 类名称：EnvironmentManageController 创建人：ZY 创建时间：2016年6月24日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/StudentM")
public class StudentManageController extends BaseController {
	String menuUrl = "StudentM/list.do"; // 菜单地址(权限用)

	@Resource(name = "studentManageService")
	private StudentManageService studentManageService;
	
	@Resource(name = "studentRegionservice")
	private StudentRegionservice studentRegionservice;
	

	/**
	 * 学生管理页面
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView ztree(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		mv.setViewName("system/tools/ztree");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 学生列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stlist")
	public ModelAndView stlist(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = studentManageService.Eqlist(page);
		mv.setViewName("system/tools/StudentList");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
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
		PageData pd = new PageData();
		pd = this.getPageData();
		// String schoolName = pd.getString("s_zone_id");
		// pd.put("s_zone_id", schoolName);
		studentManageService.saveE(pd);
		pd.put("USER_ID", this.get32UUID());
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		pd.put("password2", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("password2")).toString());
		pd.put("password3", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("password3")).toString());
		studentManageService.saveUser(pd);
		studentManageService.saveUS(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 编辑学生信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edits")
	public ModelAndView editsReg() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("yes".equals(pd.getString("editPass"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD")).toString());
		} else {
			pd.put("PASSWORD", "");
		}

		studentManageService.editE(pd);
		studentManageService.editUser(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 跳转学生编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipmentEdit")
	public ModelAndView equipmentEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = studentManageService.findByEiId(pd);
		mv.setViewName("system/tools/StudentEdit");
		mv.addObject("msg", "edits");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 跳转学生新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipmentAdd")
	public ModelAndView equipmentAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/StudentAdd");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 学生批量删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String s_id = pd.getString("s_id");
			String userid = pd.getString("USER_ID");
			if (null != s_id && !"".equals(s_id)) {
				String ArrayA_ID[] = s_id.split(",");
				String ArrayUser_ID[] = userid.split(",");
				studentManageService.deleteLeave(ArrayA_ID);
				studentManageService.deleteAll(ArrayUser_ID);
				studentManageService.deleteAllEQ(ArrayA_ID);
				studentManageService.deleteAllE(ArrayA_ID);
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
	 * 校验手机号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPhone")
	@ResponseBody
	public Object checkPhone() throws Exception {
		PageData pd = this.getPageData();
		Map<String, String> map = studentManageService.checkPhone(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * check学号是否重复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasStuNo")
	@ResponseBody
	public Object checkStuNo() {
		PageData pd = this.getPageData();
		try {
			pd.put("hasStuNo", studentManageService.checkHasStuNo(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), pd);
	}
	
	
	
	/**
	 * 节点的删除，添加，修改
	 */
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/listdata")
	@ResponseBody
	public List<PageData> listdata() throws Exception {
		logBefore(logger, "regioncontroller");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		try {
			String z_id = studentRegionservice.getZoneIdByUserId(pd);
			pd.put("z_id", z_id);
			varList = studentRegionservice.list(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return varList;
	}
	
	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd() {
		logBefore(logger, "去新增regioncontroller页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Integer level = Integer.parseInt(pd.get("level").toString()) + 1;
			pd.put("zone_type", level);
			if (level.equals(2)) {
				pd.put("zone_type_name", "学校");
			} else if (level.equals(3)) {
				pd.put("zone_type_name", "入学年份");
			} else if (level.equals(4)) {
				pd.put("zone_type_name", "班级");
			}
			mv.setViewName("system/region/region_add");
			mv.addObject("pd", pd);
			mv.addObject("msg", "saves");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去编辑页面
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit() {
		logBefore(logger, "去新增regioncontroller页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Integer level = Integer.parseInt(pd.get("z_type").toString());
			pd.put("zone_type", level);
			if (level.equals(2)) {
				pd.put("zone_type_name", "学校");
			} else if (level.equals(3)) {
				pd.put("zone_type_name", "入学年份");
			} else if (level.equals(4)) {
				pd.put("zone_type_name", "班级");
			}
			String name = studentRegionservice.getZoneName(pd);
			pd.put("name", name);
			mv.setViewName("system/region/region_edit");
			mv.addObject("pd", pd);   
			mv.addObject("msg", "editsReg");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteZone")
	@ResponseBody
	public Object deleteZone() {
		PageData pd = this.getPageData();
		try {
			pd.put("success", studentRegionservice.deleteZone(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return pd;
	}
	
	/**
	 * check学号是否重复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasZname")
	@ResponseBody
	public Object checkZname() {
		PageData pd = this.getPageData();
		try {
			pd.put("hasStuNo", studentRegionservice.checkZname(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), pd);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/editsReg")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "编辑");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			studentRegionservice.edit(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/saves")
	public ModelAndView saveReg() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			pd = this.getPageData();
			pd.put("USER_ID", user.getUSER_ID());
			studentRegionservice.save(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
}
