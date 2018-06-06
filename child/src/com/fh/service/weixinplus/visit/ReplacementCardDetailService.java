package com.fh.service.weixinplus.visit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.dao.DaoSupport;
import com.fh.util.Const;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.Utils;
import com.fh.util.model.StringDefault;
import com.fh.util.schoolPayUtil.HttpUtils;
import com.fh.util.schoolPayUtil.XFTPayCommonUtil;
import com.fh.util.schoolPayUtil.XMLUtil;

@Service("ReplacementCardDetailService")
public class ReplacementCardDetailService {

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public PageData saveReplacementRecord(PageData pg, MultipartHttpServletRequest multiRequest) throws Exception {
		if (multiRequest.getMultiFileMap() != null) {
			MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
			List<MultipartFile> list = map.get("pictureList");
			String pictureUrl = "";
			for (MultipartFile c : list) {
				if (c.getSize() != 0) {
					pictureUrl = pictureUrl + Utils.saveFile(c) + ";";
				}
			}
			pg.put("url", pictureUrl);
		}
		PageData pageData = new PageData();
		try {
			dao.save("CardReplacement.insertReplacement", pg);
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));
		} catch (Exception e) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(4003));
			e.printStackTrace();
		}
		return pageData;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Map<String, String> goToWeChatPay(PageData pg) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String out_trade_no = id.toString();

		// 校付通子商户订单金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点
		String num = pg.get("money").toString();
		int money = (new BigDecimal(100).multiply(new BigDecimal(num))).intValue();
		String fee = String.valueOf(money);
		// fee = "100";//测试用 1元整
		String body = "账户：充值:" + num + "元";// 校付通子商户订单描述
		// 学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + pg.get("SchoolID").toString() + ".txt";
		// 读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord);
		// 按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		// 校付通子商户号
		String mchId = strXft[5].replaceAll("\\r\\n", "");
		// 校付通子商户号密钥
		String mchKey = strXft[7].replaceAll("\\r\\n", "");
		// 校付通申请订单接口网址
		String orderUrl = strXft[1].replaceAll("\\r\\n", "");
		// 校付通支付界面接口网址
		String payUrl = strXft[3].replaceAll("\\r\\n", "");

		String xmlP = applyParXml(out_trade_no, fee, body, mchId, mchKey, pg.getString("openId"));
		System.out.println(xmlP);
		String xmlR = HttpUtils.post(orderUrl, xmlP, HttpUtils.UTF8);
		System.out.println("申请结果" + xmlR);

		Map<String, String> rm = null;
		try {
			rm = XMLUtil.doXMLParse(xmlR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String getUrl = null;
		Map<String, String> m = new HashMap<String, String>();
		// 验证返回结果是否合法
		if (XFTPayCommonUtil.isTenpaySign(rm, mchKey, HttpUtils.UTF8_NAME)) {
			if ("申请支付成功".equals(rm.get("message"))) {
				int l_fee = Integer.parseInt(rm.get("total_fee"));
				if (l_fee < 100) {
					l_fee = money;
				}
				// int total_fee = l_fee/100;
				// 保存订单信息
				pg.put("open_id", pg.getString("openId"));//
				pg.put("IcNo", pg.getString("IcNo"));//
				pg.put("schoolId", pg.get("SchoolID").toString());//
				pg.put("out_trade_no", out_trade_no);// 第三方自定义唯一订单号
				pg.put("order_id", rm.get("order_id"));// 商户订单编号
				pg.put("token_id", rm.get("token_id"));// 校付通订单编号
				pg.put("total_fee", l_fee);// 金额(分)
				pg.put("status", rm.get("status"));// 状态
				pg.put("mch_id", mchId);// 校付通子商户号
				pg.put("order_time", new Date());// 订单生成时间
				pg.put("checkOutMoney", 0);// 订单生成时间

				// 生成支付链接
				SortedMap<Object, Object> rs = new TreeMap<Object, Object>();
				// 获取统一下单返回结果中的token_id
				rs.put("token_id", rm.get("token_id"));
				// 生成支付签名
				String sign = XFTPayCommonUtil.createSign("UTF-8", rs, mchKey);// 生成签名
				// 拼装支付链接
				getUrl = payUrl + "?token_id=" + rm.get("token_id") + "&sign=" + sign;
				// 注意：此处输出校付通支付界面URL，可以直接java跳转或者前台 javascript跳转
				System.out.println(getUrl);
				m.put("url", "");
				m.put("resultSucCode", Const.RESULTFAILCODE);
				m.put("resultSucMsg", Const.RESULTERRMSG);
				try {
					// 将支付信息同步保存到圈存传输表中
					if (pg.getString("IcNo") != null && !pg.getString("IcNo").equals("")) {
						m.put("resultSucCode", Const.RESULTSCUCODE);
						m.put("resultSucMsg", Const.RESULTSUCMSG);
						dao.save("xftMapper.insertRecharge", pg);
						m.put("url", getUrl);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			m.put("resultFailMsg", Const.RESULTFALIMSG);
			m.put("resultFailCode", Const.RESULTFAILCODE);
		}
		return m;

	}

	private String readToString(String fileName) {
		String filePath = "file:/D:/ProgramFiles/ApacheSoftwareFoundation/Tomcat8/webapps/child/"; // 项目路径
		filePath = filePath.replaceAll("file:/", "");
		filePath = filePath.replaceAll("%20", " ");
		filePath = filePath.trim() + fileName.trim();
		if (filePath.indexOf(":") != 1) {
			filePath = File.separator + filePath;
		}
		String encoding = "utf-8";
		File file = new File(filePath);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	private static String applyParXml(String out_trade_no, String fee, String body, String mch_id, String mch_key,
			String openid) {
		String notify_url = "http://www.guanai100.cn/xft/confirmPayment";// 第三方应用异步调用URL，由第三方应用确定
		String callback_url = "http://www.guanai100.cn/palmcare/2thiBady/html/geren/recharge.html?openid=" + openid;// 第三方应用前台支付后返回URL，由第三方应用确定

		// 发送给微信服务器的参数是xml格式的string，微信返回来的信息也是xml格式的string。
		SortedMap<Object, Object> pm = new TreeMap<Object, Object>();
		pm.put("service", "pay.weixin.jspay");// 接口类型
		pm.put("version", "1.0");// 版本号
		pm.put("charset", "UTF-8");// 字符编码
		pm.put("sign_type", "MD5");// 签名加密方式
		pm.put("mch_id", mch_id);// 村付通商户号
		pm.put("out_trade_no", out_trade_no);// 客户内部订单号
		pm.put("body", body);// 商品描述
		pm.put("help_buy", "该充值用于学校饭堂、小卖部、校医室消费。");// 代付识别，如果开启代付需传入相关参数
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
