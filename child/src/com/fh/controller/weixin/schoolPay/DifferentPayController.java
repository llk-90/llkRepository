package com.fh.controller.weixin.schoolPay;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;
import com.fh.util.schoolPayUtil.HttpUtils;
import com.fh.util.schoolPayUtil.XFTPayCommonUtil;
import com.fh.util.schoolPayUtil.XMLUtil;


/**
 * 未完成
 * @author admin
 *
 */
@RestController
@RequestMapping("differentPay")
public class DifferentPayController extends BaseController {
	
	@RequestMapping("/createOrder")
	@ResponseBody
	public PageData differentPay() {
		PageData pd = this.getPageData();
		//String order_url = "http://ad.xft123.com/xftpay/getnotifyxml";//异步支付请求地址（）
		String order_url = "http://cs.xft123.com/xftpay/getnotifyxml";//异步支付请求地址（）
		//order_url="http://ad.xft123.com/xftpay/getnotifyxml";//
		String mch_id = "00000515";// 校付通子商户号，由校付通提供
		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String out_trade_no = id.toString();// 校付通子商户订单号，第三方内部订单号要唯一
		
		//构建请求xml
		SortedMap<Object, Object> pm = new TreeMap<Object, Object>();
		pm.put("out_trade_no", out_trade_no);// 客户内部订单号
		pm.put("mch_id", mch_id);// 村付通商户号
		String  differentPayReq = XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
		//开始请求
		String xmlR = HttpUtils.post(order_url, differentPayReq, HttpUtils.UTF8);
		Map<String, String> rm = null;
		try {
			rm = XMLUtil.doXMLParse(xmlR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   pd.put("state", rm.get("trade_status"));
		return pd;
	}
	
}
