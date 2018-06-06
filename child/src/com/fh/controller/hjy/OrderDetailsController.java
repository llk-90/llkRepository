package com.fh.controller.hjy;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.hjy.OrderDetailsService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/orderDetails")
public class OrderDetailsController extends BaseController{

String menuUrl = "orderDetails/list.do"; // 菜单地址(权限用)
	
	@Resource(name="orderDetailsService")
	private OrderDetailsService orderDetailsService;


	/**
	 * 订购详细信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView List(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> orderDetailsList = orderDetailsService.orderDetailslist(page);//订购详细列表
		mv.addObject("orderDetailsList", orderDetailsList);
		mv.addObject("pd", pd);
		mv.setViewName("hjy/orderDetails_list");
		return mv;
	}
	
}