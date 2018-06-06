package com.fh.controller.hjy;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.hjy.HeEduSubscriptService;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * @author 860114021
 * @data 20171019
 *02
 */
@Controller
@RequestMapping(value = "/heEduSubscriptInfo")
public class HeEduSubscriptController extends BaseController {
	
	Date dNow = new Date(); // 当前时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
	String defaultEndDate = sdf.format(dNow); // 格式化当前时间
	
	@Resource(name = "heEduSubscriptService")
	private HeEduSubscriptService heEduSubscriptService;
	
	@ResponseBody
	@RequestMapping(value = "/selectCity2")
	public Object selectCity() throws Exception {
		PageData pd=this.getPageData();
		PageData InitInfo = heEduSubscriptService.selectCity(pd);
		return InitInfo;
	}   
	
	@ResponseBody  
	@RequestMapping(value = "/selectphonenum")
	public Object selectphonenum() throws Exception {
		PageData pd=this.getPageData();
		PageData InitInfo = heEduSubscriptService.selectphonenum(pd);
		return InitInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectfirmname")
	public Object selectfirmname() throws Exception {
		PageData pd=this.getPageData();
		PageData InitInfo = heEduSubscriptService.selectfirmname(pd);
		return InitInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectUsername")
	public Object selectUsername() throws Exception {
		PageData pd=this.getPageData();
		PageData result = heEduSubscriptService.selectUsername(pd);
		return result;
	}

    @ResponseBody
	@RequestMapping(value = "/InitInfo")
	//加载业务种类
	public Object InitInfo() throws Exception {
		logBefore(logger, "获取信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<>();
		//加载业务信息
		List<PageData> InitInfo = heEduSubscriptService.selectYewu(pd);
		if(InitInfo !=null && InitInfo.size() >0){			
		 
			map.put("result", "success");
			map.put("initInfo", InitInfo);
		} else{
			map.put("result", "empty");
		}
		return map;
	}
    
	//  加载详细业务信息
    //1 根据Yewuid到hjy_firmoverview表中去查详细业务信息
    //2 根据对应的cityId查出对应的城市名
    @ResponseBody
	@RequestMapping(value = "/detailedInfo")
	public Object DetailedInfo() throws Exception {
		logBefore(logger, "获取业务信息");
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		//根据业务id在数据库内查询详细业务信息
		 List<PageData> YewuInfo = heEduSubscriptService.Yewulist(pd);
		 //根据CityId去查询城市名称
		 String CityInfo=heEduSubscriptService.findCity(pd); 
		 System.out.println("8888"+CityInfo);
		 if(YewuInfo !=null && YewuInfo.size() >0){			
			PageData pdInfo = YewuInfo.get(0);
			pd.put("CityInfo", CityInfo);		 
			pd.put("YewuName", pdInfo.getString("firmName"));
			pd.put("Area", pdInfo.getString("area"));  	
			pd.put("School", pdInfo.getString("school"));
			pd.put("Picture", pdInfo.getString("pictureURL"));
			pd.put("Describe", pdInfo.getString("descript"));
			map.put("result", "success");
			map.put("YewuInfo", pd);
		} else{
			map.put("result", "empty");
		}
		return map;
	}

    //下发验证码
    @ResponseBody
   	@RequestMapping(value = "/sendSMS")
   	public Object sendSMS() throws Exception {
    	/*
    	    packageId
			phoneNum
			cityId 
			StudentName 
    	 */
   		logBefore(logger, "下发验证码");
   		Map<String, Object> map = new HashMap<>();
   		PageData pd = this.getPageData();
   		//获取页面手机号码
   		String phone=pd.getString("phoneNum");
   		pd.put("phone", phone);
   		String phoneNum=phone.substring(7,11);
   		String cityId=pd.getString("cityId");
   		String StudentName=pd.getString("StudentName");
   		pd.put("stuName", StudentName);
   		pd.put("phoneNum", phoneNum);
   		System.out.println(pd);
   	    List<PageData> studentInfo=heEduSubscriptService.selectStuInfo(pd);
   	    PageData studentinfo=studentInfo.get(0);
     	Object studentId=studentinfo.get("StudentId");
        Object ParentId=studentinfo.get("ParentId");
   		String packageId=pd.getString("packageId");
   		String OrderStaus="2";
   		pd.put("OrderStaus",OrderStaus);
   		String OrderWay="1";
   		pd.put("OrderWay",OrderWay);
   	    String result=  heEduSubscriptService.sendSMS(ParentId,cityId,studentId,packageId);		
   		if(result.equals("200")){
	   		String Orderstaus=	heEduSubscriptService.SelectOrderStaus(pd);
	   		if(Orderstaus==null||Orderstaus==""){
	   			try {
	   				int num=(int)(Math.random()*10000);
	   				String s = String.valueOf(num);
	   				String str1 = "9999";
	   				String str2 = str1 + s ;
	   			    int a = Integer.parseInt(str2);
	   				pd.put("id", a);
	   				pd.put("time", defaultEndDate);
	   				heEduSubscriptService.AddInfo(pd);
	   			} catch (NumberFormatException e) {
	   			    e.printStackTrace();
	   			    System.out.println(1);
	   			}
	   		}else{
	   			heEduSubscriptService.UpdateOrderStaus(pd);
	   		}
   		}
   	    map.put("SMSresult", result);
   		
   		return map;
   	}
    
    //验证验证码
    @ResponseBody
   	@RequestMapping(value = "/VerifyCode")
   	public Object VerifyCode() throws Exception {
   		logBefore(logger, "验证验证码");
   		Subject currentUser = SecurityUtils.getSubject();
   		Session session = currentUser.getSession();
   		User user = (User) session.getAttribute(Const.SESSION_USER);
   		Calendar calendar = Calendar.getInstance();
   		Map<String, Object> map = new HashMap<>();
   		PageData pd = this.getPageData();
   		//获取页面手机号码
   		String cityId=pd.getString("cityId");
   		String code=pd.getString("code");
   		String packageId=pd.getString("packageId");
   		String phone=pd.getString("phoneNum");
   		String phoneNum=phone.substring(7,11);
   		String StudentName=pd.getString("StudentName");
   		String OrderStaus="3";
   		pd.put("OrderStaus",OrderStaus);
   		pd.put("stuName", StudentName);
   		pd.put("phone", phone);
   		pd.put("phoneNum", phoneNum);
   		String studentId=heEduSubscriptService.selectStuid(pd);
		try {
			int num=(int)(Math.random()*10000);
			String s = String.valueOf(num);
			String str1 = "9999";
			String str2 = str1 + s ;
		    int a = Integer.parseInt(str2);
			pd.put("id", a);
	   		pd.put("Phonenum", phone);
	   		pd.put("time", defaultEndDate);
	   		String result=  heEduSubscriptService.VerifyCode(code,cityId,studentId,packageId);	
	   		if(result.equals("200")){
	   			System.out.println("开通成功");
	   			heEduSubscriptService.UpdateOrderStaus(pd);
	   		}
	   		map.put("Coderesult", result);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
   		return map;
   	}
 
	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

}
