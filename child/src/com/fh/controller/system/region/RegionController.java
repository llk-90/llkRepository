package com.fh.controller.system.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.region.Regionservice;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 类名称：RegionController 创建人： ZKK 创建时间：2016-04-18
 */
@Controller
@RequestMapping(value = "/regioncontroller")
public class RegionController extends BaseController {
	String menuUrl = "regioncontroller/list.do"; // 菜单地址(权限用)
	@Resource(name = "regionservice")
	private Regionservice regionservice;

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
			String z_id = regionservice.getZoneIdByUserId(pd);
			pd.put("z_id", z_id);
			varList = regionservice.list(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return varList;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/saves")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		try {
			pd = this.getPageData();
			pd.put("USER_ID", user.getUSER_ID());
			regionservice.save(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
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
			mv.setViewName("system/region/region_edit");
			mv.addObject("pd", pd);
			mv.addObject("msg", "edits");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edits")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "编辑");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			regionservice.edit(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
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
			pd.put("success", regionservice.deleteZone(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return pd;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deletes")
	@ResponseBody
	public Object delete() throws Exception {
		logBefore(logger, "删除节点regioncontroller");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		// 删除班级id
		List<String> listbanjiid = new ArrayList<String>();
		List<String> listteacher = new ArrayList<String>();
		List<String> listarea = new ArrayList<String>();

		String banjiids = pd.getString("banji");
		String schoolid = pd.getString("schoolid");
		String areaid = pd.getString("areaid");
		String nianji = pd.getString("nianji");
		if (!areaid.isEmpty()) {// 判断区域
			String[] arid = areaid.split(",");
			listarea = regionservice.listareaid(arid);// 获取区域经理
			if (listarea.isEmpty()) {
				regionservice.delNode(arid);
				pd.put("success", "ok");
			} else {
				pd.put("error", "error");
			}
		}
		if (!banjiids.isEmpty()) {
			String[] banjiid = banjiids.split(",");
			listbanjiid = regionservice.listid(banjiid);// 获取学生
			listteacher = regionservice.listteacherid(banjiid);// 获取老师
			if (listbanjiid.isEmpty() && listteacher.isEmpty()) {
				// 按级别删除数据，最底层开始
				regionservice.delNode(banjiid);
				pd.put("success", "ok");
			} else {
				pd.put("error", "error");
			}
		}
		if (!nianji.isEmpty()) {// 判断年级
			String[] njid = nianji.split(",");
			regionservice.delNode(njid);
			pd.put("success", "ok");
		}
		if (!schoolid.isEmpty()) {// 判断学校
			String[] shid = schoolid.split(",");
			regionservice.delNode(shid);
			pd.put("success", "ok");
		}
		map.put("list", pd);
		// mv.setViewName("forward:/StudentM/list.do");
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 农场设置
	 */
	@RequestMapping(value = "/listQuYu")
	@ResponseBody
	public List<PageData> listQuYu() throws Exception {
		logBefore(logger, "农场设置regioncontroller");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			varList = regionservice.quyulist(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return varList;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/column")
	@ResponseBody
	public Object column(Page page, HttpServletResponse response) throws Exception {
		logBefore(logger, "regioncontroller");
		PageData pd = this.getPageData();
		// 上报限制check
		Map<String, Object> map = regionservice.column(pd);
		// 返回check结果
		return AppUtil.returnObject(pd, map);
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
			pd.put("hasStuNo", regionservice.checkZname(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), pd);
	}
	
}
