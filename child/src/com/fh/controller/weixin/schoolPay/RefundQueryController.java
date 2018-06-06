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
@RequestMapping("refundQuery")
public class RefundQueryController extends BaseController {
	
	@RequestMapping("/query")
	@ResponseBody
	public void refundQuery() {
		String mch_id = "00000515";// 校付通子商户号，由校付通提供
		String mch_key = "202CAB908F911145E0530401A8C0270E";// 校付通子商户号密钥，由校付通提供
		String refund_url = "http://cs.xft123.com/refund/gateway";// 校付通退款查询接口网址，由校付通提供

		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String orderId = id.toString();// 校付通子商户订单号，第三方内部订单号要唯一
		String refundFee = "100";// 校付通子商户订单金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点
		String refundId = "";
		String refundNo = "";
		String refundxml = applyParXml(refundNo, mch_id, mch_key,refundId);
		String xmlR = HttpUtils.post(refund_url, refundxml, HttpUtils.UTF8);
		@SuppressWarnings("unchecked")
		Map<String, String> rm = null;
		try {
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
	 * 构建退款查询请求xml
	 * 
	 * @param helpBuy
	 */
	private static String applyParXml(String mch_id,String refundNo , String refundId,String mch_key) {

		SortedMap<Object, Object> pm = new TreeMap<Object, Object>();
		pm.put("service", "xftwx.refund.query");// 接口类型
		pm.put("mch_id", mch_id);// 村付通商户号
		pm.put("out_refund_no", refundNo);//商户退款单号
		pm.put("xft_refund_id", refundId);//校付通退款单号
		pm.put("nonce_str", XFTPayCommonUtil.CreateNoncestr());
		String sign = XFTPayCommonUtil.createSign("UTF-8", pm, mch_key);// 生成签名
		pm.put("sign", sign);
		return XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
	}

}
