package com.fh.controller.weixin.ibaby;

import java.sql.Timestamp;
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
import com.fh.entity.Ibaby;
import com.fh.entity.system.User;
import com.fh.service.weixin.ibaby.UpDateChildInfoService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.service.weixin.ibaby.TerminalStatusAppService;

import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/updatechildInfo")
public class UpDateChildInfoController extends BaseController {
	
	@Resource(name = "updatechildInfoService")
	private UpDateChildInfoService updatechildInfoService;

	@ResponseBody
	@RequestMapping(value = "/findBaby")
	public Object babyInfo() throws Exception {
		
		logBefore(logger, "获取当前宝贝信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
		Map<String, Object> map = new HashMap<>();
		
		PageData pd = this.getPageData();
			
		PageData babyInfo = updatechildInfoService.findchild(pd.getString("stuId"));
		
		if (babyInfo != null) {
	        map.put("result", "success");
			map.put("babyInfo", babyInfo);
		// 没有宝贝信息
		} else{
			map.put("result", "empty");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updeChild")
	public Object updeChild() throws Exception {
		
		logBefore(logger, "宝贝信息更新");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		String imgSrc=pd.getString("babyicon");
		String s_name=pd.getString("babyname");
		String sex =pd.getString("sex");
		String equAccount=pd.getString("account");
		String equPassword=pd.getString("password");
		String imeiCode=pd.getString("imei");
		String imeiCodeOld=pd.getString("imeiOld");
		String phoneNum=pd.getString("phone");
		String icCode=pd.getString("cardnumber");
		String icCodeOld=pd.getString("cardnumberOld");
		String icName=pd.getString("cardname");
		String icCodeType=pd.getString("cardtype");
		
		pd.put("s_name", s_name);
		pd.put("equAccount", equAccount);
		pd.put("equPassword", equPassword);
		pd.put("imeiCode", imeiCode);
		pd.put("imeiCodeOld", imeiCodeOld);
		pd.put("sex", sex);
		pd.put("icCode", icCode);
		pd.put("icCodeOld", icCodeOld);
		pd.put("imgSrc", imgSrc);
		pd.put("icName", icName);
		pd.put("icCodeType", icCodeType);
		pd.put("phoneNum", phoneNum);
		pd.put("update_time", timestamp);
		 				
		updatechildInfoService.updeChild(pd);
		map.put("result", "success");

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
