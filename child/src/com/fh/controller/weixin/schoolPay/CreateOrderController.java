package com.fh.controller.weixin.schoolPay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.weixin.schoolPay.SchoolPayOrderService;
import com.fh.util.PageData;
import com.fh.util.schoolPayUtil.HttpUtils;
import com.fh.util.schoolPayUtil.XFTPayCommonUtil;
import com.fh.util.schoolPayUtil.XMLUtil;
/**
 * 已完成（待测试）
 * @author admin
 *
 */
@RestController
@RequestMapping("/schoolCreateOrder")
public class CreateOrderController extends BaseController {
	
	@Resource(name = "schoolPayOrderService")
	private SchoolPayOrderService schoolPayOrderService;
	
	
	
	@RequestMapping("/createOrder")
	@ResponseBody
	public  Map<String, String> createPayOrder(HttpServletResponse  response) {
		PageData pd = this.getPageData();
		//校付通申请订单接口网址，由校付通提供
		String order_url = "http://cs.xft123.com/xftpay/gateway";
		//String order_url="http://ad.xft123.com/xftorder/create";
		
		// 校付通支付界面接口网址，由校付通提供
		String pay_url = "http://cs.xft123.com/xftpay/showpay";
		//String pay_url="http://ad.xft123.com/xftorder/show";
		
		// 校付通子商户号，由校付通提供
		String mch_id = "00000515";
		
		// 校付通子商户号密钥，由校付通提供
		String mch_key = "202CAB908F911145E0530401A8C0270E";
		
        //// 校付通子商户订单号，第三方内部订单号要唯一
		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String out_trade_no = id.toString();
		
		
		
		// 校付通子商户订单金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点
		//是否是页面传值确定
		String fee = "1";
		String body = "账户：hxh充值:30元";// 校付通子商户订单描述

		String xmlP = applyParXml(out_trade_no, fee, body, mch_id, mch_key);
		
		String xmlR = HttpUtils.post(order_url, xmlP, HttpUtils.UTF8);
		System.out.println("申请结果" + xmlR);
			
		@SuppressWarnings("unchecked")
		Map<String, String> rm = null;
		try {
			rm = XMLUtil.doXMLParse(xmlR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String getUrl = null;
		Map<String,String> m = new HashMap();
		// 验证返回结果是否合法
		if (XFTPayCommonUtil.isTenpaySign(rm, mch_key, HttpUtils.UTF8_NAME)) {
			if ("申请支付成功".equals(rm.get("message"))) {
				// 生成支付链接
				SortedMap<Object, Object> rs = new TreeMap<Object, Object>();
				// 获取统一下单返回结果中的token_id
				rs.put("token_id", rm.get("token_id"));
				// 生成支付签名
				String sign = XFTPayCommonUtil.createSign("UTF-8", rs, mch_key);// 生成签名
				// 拼装支付链接
				getUrl = pay_url + "?token_id=" + rm.get("token_id") + "&sign=" + sign;
				// 注意：此处输出校付通支付界面URL，可以直接java跳转或者前台 javascript跳转
				System.out.println(getUrl);
				m.put("url", getUrl);
				m.put("msg","申请成功");
				pd.put("open_id","465465466464165165");//内部订单号
				pd.put("out_trade_no",out_trade_no);//内部订单号
				pd.put("order_id",rm.get("order_id"));//商户订单编号(返回)
				pd.put("token_id",rm.get("token_id"));//校付通订单编号(返回)
				pd.put("total_fee",fee);//校付通订单编号(返回)
				pd.put("status",12);//内部订单号
				pd.put("mch_id",mch_id);//密匙
				pd.put("order_time",new Date());//订单生成时间
				try {
					schoolPayOrderService.save(pd);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}else{
			m.put("msg","申请失败");
		}
		return m;
	}

	/**
	 * 构建支付请求xml
	 * 
	 * @param helpBuy
	 */
	private static String applyParXml(String out_trade_no, String fee, String body, String mch_id, String mch_key) {
		String notify_url = "http://121.40.129.213/WeiXinTest/xftNotice";// 第三方应用异步调用URL，由第三方应用确定
		String callback_url = "http://121.40.129.213/WeiXinTest/recharge.jsp";// 第三方应用前台支付后返回URL，由第三方应用确定

		// 发送给微信服务器的参数是xml格式的string，微信返回来的信息也是xml格式的string。
		SortedMap<Object, Object> pm = new TreeMap<Object, Object>();
		pm.put("service", "pay.weixin.jspay");// 接口类型
		pm.put("version", "1.0");// 版本号
		pm.put("charset", "UTF-8");// 字符编码
		pm.put("sign_type", "MD5");// 签名加密方式
		pm.put("mch_id", mch_id);// 村付通商户号
		pm.put("out_trade_no", out_trade_no);// 客户内部订单号
		pm.put("body", body);// 商品描述
		pm.put("help_buy", "测试学校-校付通");// 代付识别，如果开启代付需传入相关参数
		pm.put("total_fee", fee);// 支付金额
		// pm.put("user_Id", "oskcxuB_I93PxNoXdYkajzg5OiU4");// open_id
		pm.put("notify_url", notify_url);// 回调地址（采用动力加统一订单系统，到接口完成）
		pm.put("callback_url", callback_url);// 支付完成跳转
		pm.put("nonce_str", XFTPayCommonUtil.CreateNoncestr());
		pm.put("time_expire", "6");
		pm.put("trade_type", "NATIVE");
		pm.put("product_id", "999000");
		String sign = XFTPayCommonUtil.createSign("UTF-8", pm, mch_key);// 生成签名
		pm.put("sign", sign);
		return XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
	}
}
