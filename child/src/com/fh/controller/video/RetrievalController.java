package com.fh.controller.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.region.Regionservice;
import com.fh.service.video.Retrievalservice;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/**
 * 类名称：RegionController 创建人： ZKK 创建时间：2016-04-18
 */
@Controller
@RequestMapping(value = "/retrievalController")
public class RetrievalController extends BaseController {
	String menuUrl = "retrievalController/list.do"; // 菜单地址(权限用)
	@Resource(name = "retrievalservice")
	private Retrievalservice retrievalservice;

	/**
	 * 新增
	 */
	@RequestMapping(value = "/saves")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String qyname = pd.getString("qyname");
			if (qyname.isEmpty()) {
				pd.put("parent_id", "0");
			} else {
				String types = pd.getString("types");
				if (types.equals("2")) {
					pd.put("parent_id", pd.getString("qyname"));
				} else {
					pd.put("parent_id", pd.getString("unit_number"));
				}
			}
			retrievalservice.save(pd);
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
	public ModelAndView toAdd(Page page) {
		logBefore(logger, "去新增regioncontroller页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/region/region_edit");
			mv.addObject("msg", "saves");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增二级页面
	 */
	@RequestMapping(value = "/toAddtw")
	public ModelAndView toAddtw(Page page) {
		logBefore(logger, "去新增regioncontroller页面");
		List<PageData> varList = new ArrayList<PageData>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd.put("parent_id", "0");
			varList = retrievalservice.quyulist(pd);
			pd = this.getPageData();
			mv.setViewName("system/region/region_edittw");
			mv.addObject("msg", "saves");
			mv.addObject("varList", varList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增三级页面
	 */
	@RequestMapping(value = "/toAddth")
	public ModelAndView toAddth(Page page) {
		logBefore(logger, "去新增regioncontroller页面");
		List<PageData> varList = new ArrayList<PageData>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd.put("parent_id", "0");
			varList = retrievalservice.quyulist(pd);
			pd = this.getPageData();
			mv.setViewName("system/region/region_editth");
			mv.addObject("msg", "saves");
			mv.addObject("varList", varList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edits")
	@ResponseBody
	public String edit() throws Exception {
		logBefore(logger, "修改regioncontroller");
		String msg = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			retrievalservice.edit(pd);
			msg = "success";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "errow";
		}
		return msg;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete() throws Exception {
		logBefore(logger, "删除regioncontroller");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String level = pd.getString("level");
			if (level.equals("1")) {
				retrievalservice.deleteparent(pd);
				retrievalservice.delNode(pd);
			}else if(level.equals("0")){
				String parid="";
				pd.put("parent_id", pd.getString("zd_id"));
				List<PageData> list=retrievalservice.quyulist(pd);
				if(list.size()>0){
					for(PageData pds:list){
						parid +=pds.get("zd_id").toString() + ",";
					}
				}
				if(!parid.isEmpty()){
					String [] parids=parid.split(","); 
					retrievalservice.deleteAll(parids);
				}
				retrievalservice.deleteparent(pd);
				retrievalservice.delNode(pd);
			}else{
				retrievalservice.delNode(pd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("forward:/regioncontroller/list.do");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		logBefore(logger, "区域设置regioncontroller");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			mv.setViewName("video/retrieval");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表
	 */
	@RequestMapping(value = "/playBack")
	public ModelAndView playBack() {
		logBefore(logger, "区域设置regioncontroller");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			mv.setViewName("video/retrievalPlayback");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/listdata")
	@ResponseBody
	public List<PageData> listdata() throws Exception {
		logBefore(logger, "区域设置regioncontroller");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String names = pd.getString("name");
			pd.put("name", names);
			varList = retrievalservice.list(pd);
			// mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return varList;
	}

	/**
	 * 区域
	 */
	@RequestMapping(value = "/listQuYu")
	@ResponseBody
	public List<PageData> listQuYu() throws Exception {
		logBefore(logger, "区域设置regioncontroller");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			varList = retrievalservice.quyulist(pd);
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
		logBefore(logger, "区域设置regioncontroller");
		PageData pd = this.getPageData();
		// 上报限制check
		Map<String, Object> map = (HashMap<String, Object>) retrievalservice.column(pd);
		// 返回check结果
		return AppUtil.returnObject(pd, map);
	}

}
