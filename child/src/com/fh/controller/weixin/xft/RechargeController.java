package com.fh.controller.weixin.xft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.weixin.xft.XftRechargeService;
import com.fh.util.Const;
import com.fh.util.GetBodyStr;
import com.fh.util.PageData;
import com.fh.util.SecretUtils;
import com.fh.util.schoolPayUtil.HttpUtils;
import com.fh.util.schoolPayUtil.XFTPayCommonUtil;
import com.fh.util.schoolPayUtil.XMLUtil;

import net.sf.json.JSONObject;

/**
 * @author admin
 *
 */
@RestController
@RequestMapping("/xft")
public class RechargeController extends BaseController {
	
	@Resource(name = "xftRechargeService")
	private XftRechargeService xftRechargeService;

	/**
	 * 校卡充值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/recharge")
	@ResponseBody
	public  Map<String, String> createPayOrder() {
		
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
		// 校付通子商户订单号，第三方内部订单号要唯一
		StringBuilder id = new StringBuilder();
		id.append(System.currentTimeMillis());
		String out_trade_no = id.toString();
		
		// 校付通子商户订单金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点
		String num = pd.get("money").toString();
		int money =(new BigDecimal(100).multiply(new BigDecimal(num))).intValue();
		String fee = String.valueOf(money);
		//fee = "100";//测试用 1元整
		String body = "账户：充值:"+num+"元";// 校付通子商户订单描述
		//学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + session.getAttribute("schoolId") + ".txt";	
		//读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord); 
		 //按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		 //校付通子商户号
		String mchId = strXft[5].replaceAll("\\r\\n", "");
		 //校付通子商户号密钥
		String mchKey = strXft[7].replaceAll("\\r\\n", "");
		 //校付通申请订单接口网址
		String orderUrl = strXft[1].replaceAll("\\r\\n", "");
		 //校付通支付界面接口网址
		String payUrl = strXft[3].replaceAll("\\r\\n", "");
		
		//校付通申请订单接口网址，由校付通提供
				//String orderUrl = "http://cs.xft123.com/xftpay/gateway";
				//String order_url="http://ad.xft123.com/xftorder/create";
				
				// 校付通支付界面接口网址，由校付通提供
				//String payUrl = "http://cs.xft123.com/xftpay/showpay";
				//String pay_url="http://ad.xft123.com/xftorder/show";
				
				// 校付通子商户号，由校付通提供
				//String mchId = "00000515";
				
				// 校付通子商户号密钥，由校付通提供
				//String mchKey = "202CAB908F911145E0530401A8C0270E";
		
		String xmlP = applyParXml(out_trade_no, fee, body, mchId, mchKey, pd.getString("openId"));
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
		Map<String,String> m = new HashMap<String,String>();
		// 验证返回结果是否合法
		if (XFTPayCommonUtil.isTenpaySign(rm, mchKey, HttpUtils.UTF8_NAME)) {
			if ("申请支付成功".equals(rm.get("message"))) {
				int l_fee = Integer.parseInt(rm.get("total_fee"));
				if(l_fee < 100){
					l_fee = money;
				}
				//int total_fee = l_fee/100;
				//保存订单信息
				pd.put("open_id",pd.getString("openId"));//
				pd.put("IcNo",pd.getString("IcNo"));//
				pd.put("schoolId",session.getAttribute("schoolId"));//
				pd.put("out_trade_no",out_trade_no);//第三方自定义唯一订单号
				pd.put("order_id",rm.get("order_id"));//商户订单编号
				pd.put("token_id",rm.get("token_id"));//校付通订单编号
				pd.put("total_fee",l_fee);//金额(分)
				pd.put("status",rm.get("status"));//状态
				pd.put("mch_id",mchId);//校付通子商户号
				pd.put("order_time",new Date());//订单生成时间
				pd.put("checkOutMoney",0);//订单生成时间
				
				// 生成支付链接
				SortedMap<Object, Object> rs = new TreeMap<Object, Object>();
				// 获取统一下单返回结果中的token_id
				rs.put("token_id", rm.get("token_id"));
				// 生成支付签名
				String sign = XFTPayCommonUtil.createSign("UTF-8", rs,mchKey);// 生成签名
				// 拼装支付链接
				getUrl = payUrl + "?token_id=" + rm.get("token_id") + "&sign=" + sign;
				// 注意：此处输出校付通支付界面URL，可以直接java跳转或者前台 javascript跳转
				System.out.println(getUrl);
				m.put("url", "");
				m.put("resultSucCode",Const.RESULTFAILCODE);
				m.put("resultSucMsg",Const.RESULTERRMSG);
				try {
					//将支付信息同步保存到圈存传输表中
					if (pd.getString("IcNo")!=null && !pd.getString("IcNo").equals("")) {
						System.out.println("wode:"+Long.toHexString(Long.parseLong(pd.getString("IcNo"))));
						PageData customerInfo = xftRechargeService.queryXftCustomer(Long.toHexString(Long.parseLong(pd.getString("IcNo"))));
						if(customerInfo !=null && customerInfo.size()>0) {
							String accountNo=customerInfo.get("AccountNo").toString();
							if (accountNo!=null && !accountNo.equals("")) {
								pd.put("accountNo", accountNo);
								m.put("resultSucCode",Const.RESULTSCUCODE);
								m.put("resultSucMsg",Const.RESULTSUCMSG);
								xftRechargeService.save(pd);
								m.put("url", getUrl);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			m.put("resultFailMsg",Const.RESULTFALIMSG);
			m.put("resultFailCode",Const.RESULTFAILCODE);
		}
		return m;
	}
	
	/**
	 * 鑫源系统请求查询圈存充值金额
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/queryRechargeMon")
	@ResponseBody
	public Object queryRechargeMon() throws UnsupportedEncodingException{
		PageData pd = this.getPageData();
		String str = null;
		//学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + pd.getString("schoolid") + ".txt";		
		// 读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord); 
		// 按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		//学校密码
		String mypwd = strXft[9].replaceAll("\\r\\n", "");
		//学校id
		String schoolid = strXft[11].replaceAll("\\r\\n", "");	
		
		if(SecretUtils.isValid(new StringBuilder(schoolid).append(mypwd).toString(),pd.getString("signature"))){
			try {
				pd.put("schoolId", schoolid);
				str = xftRechargeService.queryRechargeAll(pd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str.getBytes("utf-8");
	}
	
	/**
	 * 鑫源系统更新圈存存储记录
	 * @throws Exception 
	 */
	@RequestMapping("/updateTerminal")
	public void updateTerminal(HttpServletRequest request) throws Exception{
		PageData pd = this.getPageData();
		JSONObject  json= GetBodyStr.getBodyStr(request);
		
		String key = null;
		String value = null;
		@SuppressWarnings("unchecked")
		Iterator<String> sIterator = json.keys();  
		while(sIterator.hasNext()){ 
		    // 获得key  
		    key = sIterator.next();  
		    value = json.getString(key);
			pd.put("DljSeq", key);
			pd.put("cardId", value);
			// 更新记录
			xftRechargeService.updateTerminal(pd);	
		}

	}
	
	/**
	 * 保存从鑫源取得的操作信息
	 */
	@RequestMapping("/xyoper")
	@ResponseBody
	public String quangcun(){
		PageData pd = this.getPageData();
		HttpServletRequest request = this.getRequest();
		String resultStr  =null;
		//学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + pd.getString("schoolid") + ".txt";		
		// 读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord); 
		// 按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		//学校密码
		String mypwd = strXft[9].replaceAll("\\r\\n", "");
		//学校id
		String schoolid = strXft[11].replaceAll("\\r\\n", "");	
		if(SecretUtils.isValid(new StringBuilder(schoolid).append(mypwd).toString(),pd.getString("signature"))){
			try {
				JSONObject  json= GetBodyStr.getBodyStr(request);
				pd.putAll(json);
				xftRechargeService.insertxyoper(pd);
				if(pd.get("itemNo").toString().equals("21")){
					pd.put("cardId", Long.parseLong(pd.getString("cardId")));
					xftRechargeService.updatexycustomer(pd);
				}
				resultStr = Const.SIGNATURE_VALID_SUC;
				} catch (Exception e) {
					e.printStackTrace();
				}
        }else{
        	resultStr = Const.SIGNATURE_VALID_FAIL;
        }
		return resultStr;
	}
	
	/**
	 * 保存从鑫源取得的消费记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveConsume")
	@ResponseBody
	public String saveConsume(){
		PageData pd = this.getPageData();
		HttpServletRequest request = this.getRequest();
		String resultStr  =null;
		//学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + pd.getString("schoolid") + ".txt";		
		// 读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord); 
		// 按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		//学校密码
		String mypwd = strXft[9].replaceAll("\\r\\n", "");
		//学校id
		String schoolid = strXft[11].replaceAll("\\r\\n", "");	
		if(SecretUtils.isValid(new StringBuilder(schoolid).append(mypwd).toString(),pd.getString("signature"))){
        	try {
        		pd.putAll(GetBodyStr.getBodyStr(request));
        		xftRechargeService.saveConsume(pd);
        		resultStr = Const.SIGNATURE_VALID_SUC;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }else{
        	resultStr = Const.SIGNATURE_VALID_FAIL;
        }      
		return resultStr;
	}
	
	/**
	 * 鑫源系统请求查询开卡学生信息
	 * @return
	 */
	@RequestMapping("/queryStuInFo")
	@ResponseBody
	public Object queryStuInFo() throws UnsupportedEncodingException{
		PageData pd = this.getPageData();
		String str = null;
		//学校的TXT文件地址
		String schoolWord = Const.XFT_SCHOOL + pd.getString("schoolid") + ".txt";		
		// 读取内容,不同学校不同的账户和id
		String strXftSchool = readToString(schoolWord); 
		// 按照逗号分成数组
		String strXft[] = strXftSchool.split(";");
		//学校密码
		String mypwd = strXft[9].replaceAll("\\r\\n", "");
		//学校id
		String schoolid = strXft[11].replaceAll("\\r\\n", "");	
		
		if(SecretUtils.isValid(new StringBuilder(schoolid).append(mypwd).toString(),pd.getString("signature"))){
			try {
				str = xftRechargeService.selUserDateAll(pd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str.getBytes("utf-8");
	}
	
	/**
	 * 鑫源系统更新开卡学生信息flg
	 * @throws Exception 
	 */
	@RequestMapping("/updUserDataFlg")
	public void updUserDataFlg(HttpServletRequest request) throws Exception{		
		PageData pd = this.getPageData();
		JSONObject  json= GetBodyStr.getBodyStr(request);
		
		String key = null;
		String value = null;
		@SuppressWarnings("unchecked")
		Iterator<String> sIterator = json.keys();  
		while(sIterator.hasNext()){ 
		    // 获得key  
		    key = sIterator.next();  
		    value = json.getString(key);
			pd.put("id", key);
			pd.put("dljId", value);
			// 更新记录
			xftRechargeService.updUserDataFlg(pd);	
		}
	}
	
	
	/**
	 *获取鑫源用户流水帐(包括操作和消费信息)
	 */
	@RequestMapping("/xyoperHis")
	@ResponseBody
	public Map<String , Object> getRunWaterHistory(){
		PageData pd = this.getPageData();
		List<PageData> list = new ArrayList<>();
		Map<String, Object> map =new HashMap<>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		map.put("resultSucMsg", "获取消费记录失败");
		map.put("resultSucCode", "404");
		try {
			if (pd.getString("IcNo")!=null && !pd.getString("IcNo").equals("")) {
				PageData customerInfo = xftRechargeService.queryXftCustomer(Long.toHexString(Long.parseLong(pd.getString("IcNo"))));
				if(customerInfo !=null &&customerInfo.size()>0)  {
					String accountNo=customerInfo.get("AccountNo").toString();
					pd.put("accountNo", accountNo);
					pd.put("schoolid", session.getAttribute("schoolId"));
					list= xftRechargeService.runHistory(pd);
					map.put("userHistory", list);
					map.put("resultSucMsg", "成功");
					map.put("resultSucCode", "200");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 校付通请求支付结果
	 * @return
	 */
	@RequestMapping("/confirmPayment")
	@ResponseBody
	public Map<String, String> getnotifyxml() {

		PageData pd = new PageData();
		Map<String,String> m = new HashMap<String,String>();
		m.put("resultSucCode",Const.RESULTSCUCODE);
		m.put("resultSucMsg",Const.RESULTSUCMSG);
		System.out.println(pd.toString());
		String xmlN = HttpUtils.postData(this.getRequest(), null);// 获取微信提交的数据
		System.out.println(xmlN);
		//String enc = HttpUtils.UTF8_NAME;
		if (null != xmlN && !xmlN.isEmpty()) {
			try {
				@SuppressWarnings("unchecked")
				Map<String, String> nm = XMLUtil.doXMLParse(xmlN);// 解析XML
				if (nm.get("trade_no") != null && !nm.get("trade_no").equals("")) {
					pd.put("out_trade_no",nm.get("trade_no"));
					pd.put("status",nm.get("trade_status"));
					pd.put("order_time", new Date());
					xftRechargeService.edit(pd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	/**
	 * 校付通构建支付请求xml
	 * 
	 * @param helpBuy
	 */
	private static String applyParXml(String out_trade_no, String fee, String body, String mch_id, String mch_key, String openid) {
		String notify_url = "http://www.guanai100.cn/xft/confirmPayment";// 第三方应用异步调用URL，由第三方应用确定
		String callback_url = "http://www.guanai100.cn/palmcare/2thiBady/html/geren/recharge.html?openid="+openid;// 第三方应用前台支付后返回URL，由第三方应用确定

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
	
	/**
	 * 读取TXT所有内容
	 * @param fileName
	 * @return
	 */
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
	
}
