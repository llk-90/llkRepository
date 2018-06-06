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
import com.fh.service.weixin.ibaby.StudentHealthAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/studentHealthApp")
public class StudentHealthAppController extends BaseController {
	 	
	@Resource(name="studentHealthAppService")
	private StudentHealthAppService studentHealthAppService;

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;	

	@ResponseBody
	@RequestMapping(value = "/studentHealthInfo")
	public Object studentHealthInfoApp() throws Exception {
		
		logBefore(logger, "获取学生安全健康信息");
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
			
		    //从open_id获取use_id
		    Map<String, Ibaby> mv = new HashMap<>();	
		    mv = studentHealthAppService.setstudentHealth(pd);
		    
		    ibaby = mv.get("result");
		    
	        String  statCode = ibaby.getResponse().getStatus().getCode();
	        if (statCode.equals("200")) {
				//拒绝陌生人来电电话接口方法	    
				String  comunicationFireWall = ibaby.getResponse().getTerminalExt().getComunicationFireWall();
				int schoolCheckInNoticeInt = Integer.parseInt(comunicationFireWall);
				String isReject = (schoolCheckInNoticeInt & 0x01)==1?"1":"0"; //是否拒绝陌生人来电
				String isRejectMes = (schoolCheckInNoticeInt & 0x02)==2?"1":"0"; //是否拒绝陌生人短信
				String disallowTel = (schoolCheckInNoticeInt & 0x04)==4?"1":"0"; //不允许直接输入号码拨打电话
				
				//允许家长进行监听
				String  allowFamilyListen = ibaby.getResponse().getTerminalExt().getAllowFamilyListen();
				//无人接听转为监听
				String  noManGetCallSet = ibaby.getResponse().getTerminalExt().getNoManGetCallSet();
				//低电量通知家长
				String  lowPowerNoticeFamily = ibaby.getResponse().getTerminalExt().getLowPowerNoticeFamily();		
				//监护人密码控制关机
				String  turnOffNeedPwd = ibaby.getResponse().getTerminalExt().getTurnOffNeedPwd();		
			    //更换手机卡时提醒
				String  lostRobNotice = ibaby.getResponse().getTerminalExt().getLostRobNotice();
				//接听电话默认免提
				String  getCallMode = ibaby.getResponse().getTerminalExt().getGetCallMode();	
			    //控制通话时间
				String  timePerCall = ibaby.getResponse().getTerminalExt().getTimePerCall();
				
				
				map.put("isReject", isReject);
				map.put("isRejectMes", isRejectMes);
				map.put("disallowTel", disallowTel);
				
				map.put("lostRobNotice", lostRobNotice);
				map.put("comunicationFireWall", comunicationFireWall);
			    map.put("allowFamilyListen", allowFamilyListen);
			    map.put("noManGetCallSet", noManGetCallSet);
			    map.put("lowPowerNoticeFamily", lowPowerNoticeFamily);
			    map.put("turnOffNeedPwd", turnOffNeedPwd);
			    map.put("getCallMode", getCallMode);
			    map.put("timePerCall", timePerCall);
			    map.put("result", "success");
	        } else {
	        	map.put("result", "fail");
	        }
			map.put("statCode", statCode);
			
		}else{
			map.put("result", "fail");
		}
		
		return map;
	}
 
	@ResponseBody
	@RequestMapping(value = "/sendstudentHealthInfo")
	public Object sendStudentHealthInfoApp() throws Exception {
		
		logBefore(logger, "同步更新校园管理信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
    	Map<String, Object> map = new HashMap<>();	
		Ibaby ibaby = new Ibaby();
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);
		
		//检索结果为null
		if (p.size() == 0) {
			map.put("result", "fail");
			return map;	
		} else {
			pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei
			pd.put("index", "5"); // 同步index
			
		    //保存设置信息
		    Map<String, Ibaby> mv = new HashMap<>();	
		    mv = studentHealthAppService.sendStudentHealthInfo(pd);
		    ibaby = mv.get("result");
	        String  statCode = ibaby.getResponse().getStatus().getCode();
	        //同步设置到儿童机
	        if (statCode.equals("200")){
	    	    mv = studentHealthAppService.syncStudentHealthInfo(pd);
	    	    ibaby = mv.get("result");
	            statCode = ibaby.getResponse().getStatus().getCode();
	    		map.put("result", statCode);
	        } else {
	        	map.put("result", "999");
	        }

			return map;
		}
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
