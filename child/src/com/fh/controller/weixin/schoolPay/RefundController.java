package com.fh.controller.weixin.schoolPay;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.util.schoolPayUtil.HttpUtils;
import com.fh.util.schoolPayUtil.XFTPayCommonUtil;
import com.fh.util.schoolPayUtil.XMLUtil;

/**
 * 未完成
 * @author admin
 *
 */
@RestController
@RequestMapping("refund")
public class RefundController extends BaseController {
	
	@RequestMapping("/refundApply")
	@ResponseBody
	public void refundApply() {
		// 校付通子商户号，由校付通提供
		String mch_id = "00000515";
		// 校付通子商户号密钥，由校付通提供
		String mch_key = "202CAB908F911145E0530401A8C0270E";
		// 校付通退款申请订单接口网址，由校付通提供
		String refund_url = "http://ad.xft123.com/refund/gateway";
		//退款金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点 
		String refundFee = "1";
		// 校付通订单号(客户内部订单号):是否查数据库获取待确定
		String payId = "";
		//商户退款订单号 ：待确定
		String refundNo = "";
		//组合请求XML
		String refundxml = applyParXml(refundFee,payId ,refundNo, mch_id, mch_key);
		//发送请求（返回"<xml><message>申请退款成功</message><order_id>1440752889242353</order_id>。。。。。。<xml/>"）
		String xmlR = HttpUtils.post(refund_url, refundxml, HttpUtils.UTF8);
		@SuppressWarnings("unchecked")
		Map<String, String> rm = null;
		try {
			//解析请求返回的xml
			rm = XMLUtil.doXMLParse(xmlR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String getUrl = null;
		// 验证返回结果是否合法
		if (XFTPayCommonUtil.isTenpaySign(rm, mch_key, HttpUtils.UTF8_NAME)) {
			if ("申请退款成功".equals(rm.get("message"))) {

			}
		}

	}
	
	
	
	/**
	 * 构建退款请求xml
	 * 
	 * @param helpBuy
	 */
	private static String applyParXml(String refundFee,String mch_id, String payId,String refundNo ,String mch_key) {

		SortedMap<Object, Object> pm = new TreeMap<Object, Object>();
		pm.put("service", "pay.weixin.jspay");// 接口类型
		pm.put("mch_id", mch_id);// 村付通商户号
		//pm.put("out_trade_no", orderId);// 客户内部订单号
		pm.put("xft_pay_id", payId);// 校付通订单号(客户内部订单号)
		pm.put("out_refund_no", payId);//退款单号
		pm.put("refund_fee", refundFee);// 退款金额
		pm.put("nonce_str", XFTPayCommonUtil.CreateNoncestr());
		String sign = XFTPayCommonUtil.createSign("UTF-8", pm, mch_key);// 生成签名
		pm.put("sign", sign);
		return XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
	}

}
