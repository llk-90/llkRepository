package com.fh.controller.system.zone;

import java.util.ArrayList;
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
import com.fh.entity.system.User;
import com.fh.service.system.tools.StudentManageService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/**
 * 类名称：EnvironmentManageController 创建人：ZY 创建时间：2016年6月24日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/ZoneM")
public class ZoneManagerController extends BaseController {
	String menuUrl = "ZoneM/list.do"; // 菜单地址(权限用)

	@Resource(name = "studentManageService")
	private StudentManageService studentManageService;

	/**
	 * 学生列表
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
		List<PageData> varList = studentManageService.Eqlist(page);
		mv.setViewName("system/zone/zoneManager");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "/environmentImp")
	public ModelAndView scoresImp(){
		logBefore(logger, "成绩导入页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/tools/studentImport");
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
		User user = (User) this.getRequest().getSession().getAttribute("sessionUser");
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("create_user", user.getUSERNAME());
		String kname = pd.getString("equ_id");
		if (!kname.isEmpty()) {
			String[] kid = kname.split(",");
			for (int i = 0; i < kid.length; i++) {
				pd.put("zd_id", kid[i]);
				studentManageService.saveE(pd);
			}
		}
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
	public ModelAndView edits() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		User user = (User) this.getRequest().getSession().getAttribute("sessionUser");
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("create_user", user.getUSERNAME());
		studentManageService.editE(pd);
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
			String equ_id = pd.getString("equ_id");
			if (!equ_id.isEmpty()) {
				String Arrayequ_id[] = equ_id.split(",");
				studentManageService.deleteAllE(Arrayequ_id);
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

}
