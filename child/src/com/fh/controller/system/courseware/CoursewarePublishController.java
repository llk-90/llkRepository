package com.fh.controller.system.courseware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.CoursewarePublish;
import com.fh.service.system.courseware.CoursewarePublishService;
import com.fh.util.AppUtil;
import com.fh.util.MapPlus;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/coursewareP")
public class CoursewarePublishController extends BaseController {

	String menuUrl = "coursewareP/coursewareList.do"; // 菜单地址(权限用)
	@Resource(name = "coursewarePublishService")
	private CoursewarePublishService coursewarePublishService;

	/**
	 * 课件发布列表
	 */
	@RequestMapping(value = "/coursewareList")
	public ModelAndView coursewareList(Page page) throws Exception {
		List<PageData> list = new ArrayList<PageData>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> powerslist = coursewarePublishService.powersList(pd);
		list = coursewarePublishService.findAuthList(page);
		mv.setViewName("system/courseware/coursewarePublishList");
		mv.addObject("pd", pd);
		mv.addObject("subjects", subjects);
		mv.addObject("grade", grade);
		mv.addObject("powerslist", powerslist);
		mv.addObject("list", list);
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAddC")
	public ModelAndView goAddC() throws Exception {
		List<PageData> list = new ArrayList<PageData>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			list = coursewarePublishService.powersList(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/courseware/coursewareAdd");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("subjects", subjects);
		mv.addObject("grade", grade);
		return mv;
	}

	/**
	 * 去编辑页面
	 */
	@RequestMapping(value = "/goEditC")
	public ModelAndView goEditC() throws Exception {
		List<PageData> list = new ArrayList<PageData>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			list = coursewarePublishService.powersList(pd);
			pd = coursewarePublishService.findObject(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/courseware/coursewareEdit");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("subjects", subjects);
		mv.addObject("grade", grade);
		mv.addObject("msg", "edits");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 编辑保存
	 */
	@RequestMapping(value = "/edits")
	public ModelAndView edits() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			coursewarePublishService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 课件发布
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(CoursewarePublish csw) throws Exception {
		ModelAndView mv = this.getModelAndView();
		coursewarePublishService.save(csw);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除课件
	 */
	@RequestMapping(value = "/deletes")
	public ModelAndView deletes(@RequestParam("c_id") String c_id) {
		ModelAndView mv = this.getModelAndView();
		try {
			coursewarePublishService.delete(c_id);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll(@RequestParam("C_IDS") String C_IDS) {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String cidS = C_IDS;
			if (null != cidS && !"".equals(cidS)) {
				String cid[] = cidS.split(",");
				coursewarePublishService.deleteAll(cid);
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

	// 科目
	public static final MapPlus subjects = //
	new MapPlus(1, "语文")//
			.addParams(2, "数学")//
			.addParams(3, "英语")//
			.addParams(4, "政治")//
			.addParams(5, "物理")//
			.addParams(6, "化学")//
			.addParams(7, "地理")//
			.addParams(8, "历史")//
			.addParams(9, "生物")//
			.addParams(10, "美术")//
			.addParams(11, "音乐")//
			.addParams(12, "体育")//
			.addParams(13, "德育考核")//
			.addParams(14, "劳动技术")//
			.addParams(15, "计算机")//
			.addParams(16, "物理实验")//
			.addParams(17, "化学实验")//
			.addParams(18, "生物实验");
	// 年级
	public static final MapPlus grade = //
	new MapPlus(1, "一年级")//
			.addParams(2, "二年级")//
			.addParams(3, "三年级")//
			.addParams(4, "四年级")//
			.addParams(5, "五年级")//
			.addParams(6, "六年级")//
			.addParams(7, "初一")//
			.addParams(8, "初二")//
			.addParams(9, "初三")//
			.addParams(10, "高一")//
			.addParams(11, "高二")//
			.addParams(12, "高三");

}
