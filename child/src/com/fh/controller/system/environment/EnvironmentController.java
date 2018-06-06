package com.fh.controller.system.environment;

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
import com.fh.entity.Chart;
import com.fh.entity.Page;
import com.fh.service.system.environment.Environmentservice;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/**
 * 类名称：RegionController 创建人： ZKK 创建时间：2016-04-18
 */
@Controller
@RequestMapping(value = "/environment")
public class EnvironmentController extends BaseController {
	String menuUrl = "environment/list.do"; // 菜单地址(权限用)
	@Resource(name = "environmentservice")
	private Environmentservice environmentservice;

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
			environmentservice.save(pd);
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
	 * 去设置页面
	 */
	@RequestMapping(value = "/toSetUp")
	public ModelAndView toSetUp(Page page) {
		logBefore(logger, "去设置regioncontroller页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData kqwd = new PageData();
		PageData kqsd = new PageData();
		PageData trsf = new PageData();
		PageData gzd = new PageData();
		PageData trwd = new PageData();
		PageData co2nd = new PageData();
		try {
			String name = environmentservice.getName(pd.getString("id"));
			kqwd = environmentservice.getLimit(pd.getString("id"),"0");
			kqsd = environmentservice.getLimit(pd.getString("id"),"1");
			trsf = environmentservice.getLimit(pd.getString("id"),"2");
			gzd = environmentservice.getLimit(pd.getString("id"),"3");
			trwd = environmentservice.getLimit(pd.getString("id"),"4");
			co2nd = environmentservice.getLimit(pd.getString("id"),"5");
			if(kqwd != null && kqsd != null && trsf != null && gzd !=null
					&& trwd != null && co2nd != null){
				mv.addObject("msg", "edit");
			}else{
				mv.addObject("msg", "new");
			}
			mv.setViewName("system/environment/environmentSetUp");
			mv.addObject("area_kid", pd.getString("id"));
			mv.addObject("name", name);
			mv.addObject("kqwd", kqwd);
			mv.addObject("kqsd", kqsd);
			mv.addObject("trsf", trsf);
			mv.addObject("gzd", gzd);
			mv.addObject("trwd", trwd);
			mv.addObject("co2nd", co2nd);
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
			varList = environmentservice.quyulist(pd);
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
			varList = environmentservice.quyulist(pd);
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
			environmentservice.edit(pd);
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
				environmentservice.deleteparent(pd);
				environmentservice.delNode(pd);
			}else if(level.equals("0")){
				String parid="";
				pd.put("parent_id", pd.getString("zd_id"));
				List<PageData> list=environmentservice.quyulist(pd);
				if(list.size()>0){
					for(PageData pds:list){
						parid +=pds.get("zd_id").toString() + ",";
					}
				}
				if(!parid.isEmpty()){
					String [] parids=parid.split(","); 
					environmentservice.deleteAll(parids);
				}
				environmentservice.deleteparent(pd);
				environmentservice.delNode(pd);
			}else{
				environmentservice.delNode(pd);
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
	public ModelAndView listb() {
		logBefore(logger, "跳转到环境参数页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/environment/environment");
		return mv;
	}
	/**
	 * 列出日、月、年中数据
	 * @return
	 */
	@RequestMapping(value = "/listData")
	@ResponseBody
	public List<Chart> listData() {
		logBefore(logger, "列表环境参数页面");
		PageData pd = new PageData();
		List<Chart> list = new ArrayList<Chart>();
		try {
			pd = this.getPageData();
			list = environmentservice.listData(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return list;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/L")
	@ResponseBody
	public List<PageData> listdata() throws Exception {
		logBefore(logger, "区域设置regioncontroller");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String names = pd.getString("name");
			pd.put("name", names);
			varList = environmentservice.list(pd);
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
			varList = environmentservice.quyulist(pd);
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
		Map<String, Object> map = (HashMap<String, Object>) environmentservice.column(pd);
		// 返回check结果
		return AppUtil.returnObject(pd, map);
	}
	
	@RequestMapping(value="/saveSetUp")
	public ModelAndView saveSetUp() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		String msg = null;
		if("edit".equals(pd.getString("action"))){
			msg = environmentservice.saveSetUp(pd);
		}else if("new".equals(pd.getString("action"))){
			msg = environmentservice.newSetUp(pd);
		}
		mv.addObject("msg", msg);
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="savePlan")
	@ResponseBody
	public String savePlan() throws Exception{
		PageData pd = this.getPageData();
		String rs = environmentservice.savePlan(pd);
		return rs;
	}

}
