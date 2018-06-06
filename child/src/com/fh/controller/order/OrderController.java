package com.fh.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.order.OrderService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController{
	
	String menuUrl = "order/list.do"; //菜单地址(权限用)
	@Resource(name="orderService")
	private OrderService orderService;
	
	/**
	 * 列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> orderList = orderService.orderList(page);
		List<PageData> allAuthName = orderService.nameList();
		mv.setViewName("order/orderList");
		mv.addObject("orderList",orderList);
		mv.addObject("allAuthName",allAuthName);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 查看详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toSee")
	public ModelAndView toSee()throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = this.getPageData();
			PageData orderDetails = orderService.getOrderById(pd);
			mv.addObject("order", orderDetails);
			mv.setViewName("order/offlinePayment");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 线下支付
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/offLinePay")
	public ModelAndView toOffline()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try{
			orderService.update(pd);
			PageData pdf = orderService.getOrderById(pd);
			orderService.add(pdf);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 批量删除
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
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				orderService.deleteAll(ArrayDATA_IDS);
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

	
	
	
	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

}
