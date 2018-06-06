package com.fh.controller.weixin.ibaby;
import java.util.HashMap;
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
import com.fh.service.weixin.ibaby.CampusManageAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/campusManageApp")
public class CampusManageAppController extends BaseController {
	 	
	@Resource(name="campusManageAppService")
	private CampusManageAppService campusManageAppService;

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;	

	@ResponseBody
	@RequestMapping(value = "/campusManageInfo")
	public Object campusManageInfoApp() throws Exception {
		
		logBefore(logger, "获取校园管理信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
    	Map<String, Object> map = new HashMap<>();	
		Ibaby ibaby = new Ibaby();
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);	
				
		if(p != null && p.size()>0){
			pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei	
		    //从openid获取use_id
		    Map<String, Ibaby> mv = new HashMap<>();	
		    mv = campusManageAppService.campusManageInfo(pd);
		    ibaby = mv.get("result");
		    
			//设置上课时间禁用	    
			String  studyForbidden = ibaby.getResponse().getTerminalExt().getStudyForbidden();
			//获取上课时间
			String  schoolTimeRange = ibaby.getResponse().getTerminalExt().getSchoolTimeRange();	
		    //离校通知家长
			String  schoolCheckInNotice = ibaby.getResponse().getTerminalExt().getSchoolCheckInNotice();
			int schoolCheckInNoticeInt = Integer.parseInt(schoolCheckInNotice);
			String arrNoticPoe1 = (schoolCheckInNoticeInt & 0x01)==1?"1":"0"; 
			String arrNoticPoe2 = (schoolCheckInNoticeInt & 0x02)==2?"1":"0"; 
			String levNoticPoe1 = (schoolCheckInNoticeInt & 0x04)==4?"1":"0"; 
			String levNoticPoe2 = (schoolCheckInNoticeInt & 0x08)==8?"1":"0"; 
			//获取上下学时间段
			String  schoolHomeTimeRange = ibaby.getResponse().getTerminalExt().getSchoolHomeTimeRange();
	        String  statCode = ibaby.getResponse().getStatus().getCode();

			map.put("statCode", statCode);
		    //设置上课时间禁用	
			map.put("studyForbidden", studyForbidden);
		    //离校通知家长
		    map.put("arrNoticPoe1", arrNoticPoe1);
		    map.put("arrNoticPoe2", arrNoticPoe2);
		    map.put("levNoticPoe1", levNoticPoe1);
		    map.put("levNoticPoe2", levNoticPoe2);  
		    //map.put("schoolCheckInNotice", schoolCheckInNotice);
			//获取上课时间
		    map.put("schoolTimeRange", schoolTimeRange);
		    //获取上下学时间段
		    map.put("schoolHomeTimeRange", schoolHomeTimeRange);
		}else{
			map.put("statCode", "100");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendCampusManageInfo")
	public Object sendCampusManageInfoApp() throws Exception {
		
		logBefore(logger, "同步更新校园管理信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
		String stuid = pd.getString("stuId");
		pd.put("stuId", stuid);
    	Map<String, Object> map = new HashMap<>();	
		Ibaby ibaby = new Ibaby();
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);

		if(p != null && p.size()>0){
			pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei
			pd.put("index", "2"); // 同步index
		    //保存设置信息
		    Map<String, Ibaby> mv = new HashMap<>();	
		    mv = campusManageAppService.sendCampusManageInfo(pd);
		    ibaby = mv.get("result");
	        String  statCode = ibaby.getResponse().getStatus().getCode();
	        //同步设置到儿童机
	        if (statCode.equals("200")){
	    	    mv = campusManageAppService.syncCampusManageInfo(pd);
	    	    ibaby = mv.get("result");
	            statCode = ibaby.getResponse().getStatus().getCode();
	    		map.put("result", statCode);
	        } else {
	        	map.put("result", "999");
	        }
		}else{
			map.put("result", "100");
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
