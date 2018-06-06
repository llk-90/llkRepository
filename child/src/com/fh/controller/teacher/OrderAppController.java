package com.fh.controller.teacher;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.teacher.OrderAppService;
import com.fh.util.PageData;
import com.fh.util.wxpay.OrderUtil;

@Controller
@RequestMapping(value = "/orderApp")
public class OrderAppController extends BaseController {
	String menuUrl = "commodity/list.do"; // 菜单地址(权限用)
	
	@Resource(name="orderAppService")
	private OrderAppService orderAppService;

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
		List<PageData> orderAppList = orderAppService.list(page);
		mv.setViewName("teacher/orderApp_list");
		mv.addObject("orderAppList", orderAppList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 发货
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/send")
	public ModelAndView sends(Page page) throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		pd.put("ibaby_commodityStat", "3");
		pd.put("ibaby_commodityDatetime", timestamp);
		orderAppService.updOrderApp(pd);
		List<PageData> orderAppList = orderAppService.list(page);
		mv.setViewName("teacher/orderApp_list");
		mv.addObject("orderAppList", orderAppList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 撤单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancel")
	public ModelAndView cancels(Page page) throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		pd.put("ibaby_commodityStat", "4");
		pd.put("ibaby_commodityDatetime", timestamp);
		orderAppService.updOrderApp(pd);
		List<PageData> orderAppList = orderAppService.list(page);
		mv.setViewName("teacher/orderApp_list");
		mv.addObject("orderAppList", orderAppList);
		mv.addObject("pd", pd);

		return mv;
  }
	
	/**
	 * 去修改订单信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData order = orderAppService.findByorderId(pd);
		mv.addObject("order", order);
		mv.setViewName("teacher/orderApp_edit");
		return mv;
	}	
	
	/**
	 * 编辑订单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits(Page page) throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//调用Service层的editOrderApp()方法;
		orderAppService.editOrderApp(pd);
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
		pd.put("ibaby_order_id", OrderUtil.GetOrderNumber(""));
		pd.put("ibaby_userId", "");
		pd.put("ibaby_nameInfo", "");
		pd.put("ibaby_telInfo", "");
		pd.put("ibaby_addressInfo", "");
		pd.put("ibaby_postCode", "");
		pd.put("ibaby_commodityId", "");
		pd.put("ibaby_commodityNam", "");
		pd.put("ibaby_commodityNum", "");
		pd.put("ibaby_commoditySum", 0.00);
		pd.put("ibaby_commodityStat", "");
		pd.put("ibaby_commodityDatetime", timestamp);
		orderAppService.add(pd);
		List<PageData> orderAppList = orderAppService.list(page);     //新增课程信息	
		mv.setViewName("teacher/orderApp_list");
		mv.addObject("orderAppList", orderAppList);
		mv.addObject("pd", pd);
		return mv;
	}
}
