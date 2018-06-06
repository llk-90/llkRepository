package com.fh.controller.weixin.huodong;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.commodity.CommodityService;
import com.fh.service.weixin.huodong.ActiveService;
import com.fh.util.Const;
import com.fh.util.MapPlus;
import com.fh.util.PageData;
import com.fh.util.wxpay.OrderUtil;
import com.fh.util.wxpay.WXPay;
import com.fh.util.wxpay.WXPrepay;

@Controller
@RequestMapping(value="/activeController")
public class ActiveController extends BaseController {
	
	@Resource(name="activeService")
	private ActiveService activeService; 
	
	@Resource(name = "commodityService")
	private CommodityService ser;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public ModelAndView list(Page page) throws Exception {	
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> list = activeService.list(page);
		mv.setViewName("hjy/active");
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 参加活动判定
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/seActive")
	public Object seActive() throws Exception {
		logBefore(logger, "是否参加活动");
		
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		//是否开通业务判定
		String phoneNum=pd.getString("phone1").substring(7,11);
		pd.put("phoneNum", phoneNum);
		int opeNum = activeService.findSeActive(pd);
		
		if (opeNum != 0){			
			//是否参加活动判定
			int acNum = activeService.findOpActive(pd.getString("phone1"));
				
			if (acNum == 0){
				map.put("result", "success");
			} else {
				map.put("acResult", "fail");
			}								
		} else {
			map.put("opeResult", "fail");
		}
		
		return map;
	}	
	
	/**
	 * 保存活动信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveActive")
	public MapPlus saveActive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logBefore(logger, "保存活动信息");
		MapPlus reault = new MapPlus();
		PageData pd = this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		//String openid = (String)session.getAttribute("user_openid");
		
		//openid = user.getOPENID();
		
		BigDecimal price = new BigDecimal(0.01);
		
			Configure configure = new Configure();
			WXPrepay prePay = new WXPrepay();
			prePay.setAppid(configure.getAppId());// 应用ID
			prePay.setBody("掌上关爱");
			prePay.setPartnerKey(configure.getApiKey());//
			prePay.setMch_id(configure.getMchId());// 商户号
			prePay.setNotify_url(configure.getNotifyURL());// 通知地址
			prePay.setOut_trade_no(OrderUtil.GetOrderNumber(""));// 商户订单号
			prePay.setOpenid(pd.getString("openid"));// 用户openid
				String ip = request.getHeader("x-forwarded-for"); 
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
					ip = request.getHeader("Proxy-Client-IP");
				}            
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {       
					ip = request.getHeader("WL-Proxy-Client-IP");    
				}          
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
					ip = request.getRemoteAddr();          
				}
			ip = ip.split(",")[0];
			prePay.setSpbill_create_ip(ip);// 终端IP
			prePay.setTotal_fee(String.valueOf(price.multiply(new BigDecimal(100)).intValue()));// 总金额:单位分
			prePay.setTrade_type("JSAPI");// 交易类型
			prePay.setUnifiedorder(configure.getPrepayURL());
			// 获取预支付订单号
			String prepayid = prePay.submitXmlGetPrepayId();
			logger.info("获取的预支付订单是：" + prepayid);
			if (prepayid != null && prepayid.length() > 10) {
				// 生成微信支付参数，此处拼接为完整的JSON格式，符合支付调起传入格式
				SortedMap<String, Object> callAppParam = WXPay.createPackageValueObj(//
						configure.getAppId(), //
						configure.getMchId(), //
						configure.getApiKey(), //
						prepayid);
				reault.put("callAppParam", callAppParam);
				reault.put("success", true);
				String TrueName = URLDecoder.decode(pd.getString("stuName"),"utf-8");
				String schName = URLDecoder.decode(pd.getString("stu_school"),"utf-8");
				String claName = URLDecoder.decode(pd.getString("stu_class"),"utf-8");
				String addName = URLDecoder.decode(pd.getString("accept_address"),"utf-8");
				pd.put("stuName", TrueName);
				pd.put("stu_school", schName);
				pd.put("stu_class", claName);
				pd.put("accept_address", addName);
				pd.put("order_num", prePay.getOut_trade_no());
				activeService.saveActive(pd);
			} else {
				reault//
						.addParams("msg", "微信下单失败")//
						.addParams("success", false);
			}
		return reault;
	}
	
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	
}
