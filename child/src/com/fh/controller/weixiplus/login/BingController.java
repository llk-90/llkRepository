package com.fh.controller.weixiplus.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.hjy.HeEduSubscriptService;
import com.fh.service.weixinplus.login.BingService;
import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;
import com.fh.util.Const;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;
import com.fh.util.model.StringDefault;

@RestController
@RequestMapping(value = "/weixiplusBing")
public class BingController extends BaseController {

	Date dNow = new Date(); // 当前时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
	String defaultEndDate = sdf.format(dNow); // 格式化当前时间

	@Resource(name = "BingService")
	private BingService bingService;

	@Resource(name = "errorMsg")   
	private ErrorMsg errorMsg;

	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;
	
	@Resource(name = "heEduSubscriptService")
	private HeEduSubscriptService heEduSubscriptService;

	/**
	 * 根据页面信息，查询学生
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/bingAccountSelect")
	public PageData doBingSelect() throws Exception {

		PageData pageData = this.getPageData();
		pageData.put("creat_time", dNow);
		return bingService.getBingInfo(pageData);

	}

	/**
	 * 根据进行插入操作
	 * 
	 * @param 页面信息
	 * @return 是否成功
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/bingAccount")
	public PageData doBing() throws Exception {
		PageData pageData = this.getPageData();
		pageData.put("user_Id", this.get32UUID());
		return bingService.saveBingInfo(pageData);
	}

	/**  
	 * 获取所有学校   
	 * 
	 * @param
	 * @return 学校信息
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAllSch")
	public PageData getAllSchool() throws Exception {
		PageData pageData = this.getPageData();
		return bingService.getAllSchool(pageData);
	}

	/**
	 * 获取当前学校的全部class
	 * 
	 * @param
	 * @return 学校信息
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAllClass")
	public PageData getAllClass() throws Exception {
		PageData pageData = this.getPageData();
		return bingService.getAllClass(pageData);
	}

	/**
	 * 获取验证码
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCode")
	public PageData sendSMS() throws Exception {

		PageData pageData = this.getPageData();
		PageData res = new PageData();
		if (pageData.getString(StringDefault.openId) == null
				|| pageData.getString(StringDefault.openId).length() == 0) {
			res.put(StringDefault.errorcode, errorMsg.Success(2001));
		} else if (pageData.getString(StringDefault.phone) == null
				|| pageData.getString(StringDefault.phone).length() != 11) {
			res.put(StringDefault.errorcode, errorMsg.Success(3001));
		} else {
			try {
				String code = SmsUtil.createRandom(true, 6);
				SmsUtil.smsCodeSend(pageData.getString(StringDefault.phone),
						"验证码：" + code + StringDefault.verifyCodeMsg);
				pageData.put("type", 0);
				pageData.put("creat_time", dNow);
				pageData.put("verifycode", code);
				if (bingService.saveVerifyCode(pageData)) {
					res.put(StringDefault.errorcode, errorMsg.Success(0));
				} else {
					res.put(StringDefault.errorcode, errorMsg.Success(1002));
				}
			} catch (Exception e) {
				// TODO: handle exception
				res.put(StringDefault.errorcode, errorMsg.Success(4002));
			}
		}
		return res;
	}
	
	/*//下发验证码
    @ResponseBody
   	@RequestMapping(value = "/sendSMS")
   	public Object sendSMS(){
    	
    	    packageId
			phoneNum
			cityId 
			StudentName 
    	 
   		logBefore(logger, "下发验证码");
   		Map<String, Object> map = new HashMap<>();
   		PageData pd = this.getPageData();
   	    PageData studentInfo;
   	     String result=null;
		try {
			studentInfo = bingService.getstuInfo(pd);
			String cityid=studentInfo.getString("CityId");
			Object studentId=studentInfo.get("StudentId");
			Object ParentId=studentInfo.get("ParentId");
			String packageId=(String) studentInfo.get("marketingId"); 
			String OrderStaus="2";
			pd.put("OrderStaus",OrderStaus);
			String OrderWay="1";
			pd.put("OrderWay",OrderWay);
			result=  heEduSubscriptService.sendSMS(ParentId,cityid,studentId,packageId);		
			map.put("cityId", cityid);
			map.put("packageId", packageId);
			map.put("studentId", studentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
   	    map.put("SMSresult", result);
   		return map;
   	}
	
  //验证验证码
    @ResponseBody
   	@RequestMapping(value = "/VerifyCode")
   	public Object VerifyCode() throws Exception {  
   		logBefore(logger, "验证验证码");
   		PageData pd = this.getPageData();
   		Map<String, Object> map = new HashMap<>();
	   	String result=  heEduSubscriptService.VerifyCode(pd.getString("verifycode"),pd.getString("cityId"),pd.getString("studentId"),pd.getString("packageId"));	
	   	if(result.equals("200")){
	   			System.out.println("开通成功");
	   		}
	   		map.put("Coderesult", result);
   		return map;
   	}
*/

}
