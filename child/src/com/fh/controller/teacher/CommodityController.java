package com.fh.controller.teacher;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.teacher.CommodityAppService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/commodity")
public class CommodityController extends BaseController {
	String menuUrl = "commodity/list.do"; // 菜单地址(权限用)
	
	@Resource(name="commodityAppService")
	private CommodityAppService commodityAppService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listCommod(Page page) throws Exception {		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> commodityList = commodityAppService.commodityList(page);
		mv.setViewName("teacher/commodity_list");
		mv.addObject("commodityList", commodityList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去修改商品信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData commodity = commodityAppService.findBycommodityId(pd);
		mv.addObject("commodity", commodity);
		mv.setViewName("teacher/commodityEdit");
		return mv;
	}	
	
	/**
	 * 编辑商品信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits(@RequestParam("uploadfile") CommonsMultipartFile file, @RequestParam("commodity_name") String commodityName,@RequestParam("commodity_id") String commodityId,
			@RequestParam("commodity_price") String commodityPrice, @RequestParam("commodity_detail") String commodityDetail,HttpServletRequest request) throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("ibaby_commodity_id", commodityId);
		pd.put("ibaby_commodity_imgurl", "");
		pd.put("ibaby_commodity_name", commodityName);
		pd.put("ibaby_commodity_price", commodityPrice);
		pd.put("ibaby_commodity_detail", commodityDetail);
		String name = get32UUID();
		boolean hasWord = commodityAppService.editE(file, name, request, pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");

		return mv;
	}
	
	/**
	 * 新增课程时间信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Page page = new Page();
		page.setPd(pd);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		pd.put("ibaby_commodity_imgurl", "");
		pd.put("ibaby_commodity_name", "XXX");
		pd.put("ibaby_commodity_price", "0.01");
		pd.put("ibaby_commodity_detail", "XXX");
		pd.put("ibaby_create_time", timestamp);
		pd.put("ibaby_update_time", timestamp);
		String maxId = commodityAppService.findmaxId(pd);
		pd.put("ibaby_commodity_id", maxId);
		commodityAppService.add(pd);
		List<PageData> commodityList = commodityAppService.commodityList(page);     //新增课程信息	
		mv.setViewName("teacher/commodity_list");
		mv.addObject("commodityList", commodityList);
		mv.addObject("pd", pd);
		return mv;
	}
}
