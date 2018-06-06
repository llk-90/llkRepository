package com.fh.controller.weixin.pay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.entity.system.User;
import com.fh.service.system.commodity.CommodityService;
import com.fh.service.weixin.ibaby.ShopInfoService;
import com.fh.util.Const;
import com.fh.util.MapPlus;
import com.fh.util.PageData;
import com.fh.util.wxpay.OrderUtil;
import com.fh.util.wxpay.WXPay;
import com.fh.util.wxpay.WXPrepay;
import com.fh.util.wxpay.XMLUtil;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController extends BaseController {

	@Resource(name = "commodityService")
	private CommodityService ser;

	@Resource(name = "shopInfoService")
	private ShopInfoService shopInfoService;
	

	@ResponseBody
	@RequestMapping(value = "/ibabypayPlaceOrder")
	public MapPlus ibabypayPlaceOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MapPlus reault = new MapPlus();
		PageData pd = this.getPageData();
		BigDecimal price = shopInfoService.getIbabyPrice(pd);
		if (price == null) {
			return reault//
					.addParams("msg", "数据异常")//
					.addParams("success", false);
		}
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String openid = null;
		
		Date date =new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		if (user != null && (openid = user.getOPENID()) != null) {
			Configure configure = new Configure();
			WXPrepay prePay = new WXPrepay();
			prePay.setAppid(configure.getAppId());// 应用ID
			prePay.setBody("掌上关爱");
			prePay.setPartnerKey(configure.getApiKey());//
			prePay.setMch_id(configure.getMchId());// 商户号
			prePay.setNotify_url(configure.getNotifyURL());// 通知地址
			prePay.setOut_trade_no(OrderUtil.GetOrderNumber(""));// 商户订单号
			prePay.setOpenid(openid);// 用户openid
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
			prePay.setTotal_fee(String.valueOf(price.multiply(new BigDecimal(100)).multiply(new BigDecimal(pd.getString("CommodityNum"))).intValue()));// 总金额:单位分
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
				// pd.clear();
				String commondnam = shopInfoService.getIbabyComNam(pd);
				pd.put("commodity_nam", commondnam);
				pd.put("co_order_num", prePay.getOut_trade_no());
				pd.put("co_user_id", user.getUSER_ID());
				pd.put("co_money", price.multiply(new BigDecimal(pd.getString("CommodityNum"))).intValue());
				pd.put("co_pay_state", 2);
				pd.put("dateTime", timestamp);
				ser.addIbabyOrder(pd);
			} else {
				reault//
						.addParams("msg", "微信下单失败")//
						.addParams("success", false);
			}
		} else {
			reault//
					.addParams("msg", "会话异常")//
					.addParams("success", false);
		}
		return reault;
	}
	
	/**
	 * 微信下单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wxpayPlaceOrder")
	@ResponseBody
	public MapPlus wxpayPlaceOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MapPlus reault = new MapPlus();
		PageData pd = this.getPageData();
		BigDecimal price = ser.findPrice(pd);
		if (price == null) {
			return reault//
					.addParams("msg", "数据异常")//
					.addParams("success", false);
		}
		// Integer commodity_id = (Integer) pd.get("commodity_id");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String openid = null;
		if (user != null && (openid = user.getOPENID()) != null) {
			Configure configure = new Configure();
			//
			WXPrepay prePay = new WXPrepay();
			prePay.setAppid(configure.getAppId());// 应用ID
			prePay.setBody("掌上关爱");
			prePay.setPartnerKey(configure.getApiKey());//
			prePay.setMch_id(configure.getMchId());// 商户号
			prePay.setNotify_url(configure.getNotifyURL());// 通知地址
			prePay.setOut_trade_no(OrderUtil.GetOrderNumber(""));// 商户订单号
			prePay.setOpenid(openid);// 用户openid
			prePay.setSpbill_create_ip(request.getRemoteAddr());// 终端IP
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
				// pd.clear();
				pd.put("co_order_num", prePay.getOut_trade_no());
				pd.put("co_money", price);
				pd.put("co_user_id", user.getUSER_ID());
				pd.put("co_commodity_id", pd.get("commodity_id"));
				pd.put("co_pay_state", 2);
				ser.order(pd);
			} else {
				reault//
						.addParams("msg", "微信下单失败")//
						.addParams("success", false);
			}
		} else {
			reault//
					.addParams("msg", "会话异常")//
					.addParams("success", false);
		}
		return reault;
	}

	@RequestMapping(value = "/wxpay_notify")
	public void wxpay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println(getPageData());
		try {
			PrintWriter out = response.getWriter();
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String result = new String(outSteam.toByteArray(), "utf-8");
			Map<String, String> map = null;
			try {
				map = XMLUtil.doXMLParse(result);
			} catch (JDOMException e) {
				e.printStackTrace();
			}
			String noticeStr = null;
			if (!"SUCCESS".equals(map.get("return_code"))) {
				logger.error("微信支付通信失败");
				return;
			}
			PageData pd = new PageData();
			String out_trade_no = map.get("out_trade_no");// 订单号
			pd.put("out_trade_no", out_trade_no);
			if (!ser.isResulted(pd)) {
				if ("SUCCESS".equals(map.get("result_code"))) {
					noticeStr = setXML("SUCCESS", "OK");
					pd.put("co_pay_state", 1);
				} else if ("FAIL".equals(map.get("result_code"))) {
					pd.put("co_pay_state", -1);
				} else {
					pd.put("co_pay_state", 0);
				}
				ser.updateOrder(pd);
				ser.updateIbabyOrder(pd);
			} else {
				noticeStr = setXML("FAIL", "");
			}
			out.print(new ByteArrayInputStream(noticeStr.getBytes(Charset.forName("UTF-8"))));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
}