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
import com.fh.service.weixin.ibaby.SystemSettingsAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/systemSettingsApp")
public class SystemSettingsAppController extends BaseController {
	 	
	@Resource(name="systemSettingsAppService")
	private SystemSettingsAppService systemSettingsAppService;

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;	

	@ResponseBody
	@RequestMapping(value = "/systemSettingsInfo")
	public Object systemSettingsInfo() throws Exception {
		
		logBefore(logger, "获取系统设置信息");
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
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备iMei	
			
			// 从open_id获取use_id
			Map<String, Ibaby> mv = new HashMap<>();	
			mv = systemSettingsAppService.systemSettings(pd);
			
			ibaby = mv.get("result");
			String  statCode = ibaby.getResponse().getStatus().getCode();
			if (statCode.equals("200")) {
				// 自动开关机	    
				String  autoTurnOffOn = ibaby.getResponse().getTerminalExt().getAutoTurnOffOn();
				String[] autoTurnOffOnArr = autoTurnOffOn.split("\\|");
				String[] turnOnA = autoTurnOffOnArr[0].split(",");
				String[] turnOffA = autoTurnOffOnArr[1].split(",");
				String[] turnOnB = autoTurnOffOnArr[2].split(",");
				String[] turnOffB = autoTurnOffOnArr[3].split(",");
				
				map.put("autostartavaild", turnOnA[0]);
				map.put("startatime", turnOnA[2]);
				map.put("autoendavaild", turnOffA[0]);
				map.put("endatime", turnOffA[2]);
				
				map.put("autostartbvaild", turnOnB[0]);
				map.put("startbtime", turnOnB[2]);
				map.put("autoendbvaild", turnOffB[0]);
				map.put("endbtime", turnOffB[2]);
				
				// GPS开关
				String  gpsKey = ibaby.getResponse().getTerminalExt().getGpsKey();
				int gpsKeyInt = Integer.parseInt(gpsKey);
				String nightModel = "0";
				if((gpsKeyInt & 0x01)==1){
					nightModel = "0";
				} else if ((gpsKeyInt & 0x02)==2){
					nightModel = "1";
				} else if ((gpsKeyInt & 0x04)==4){
					nightModel = "2";
				}
				String classTime = "0";
				if((gpsKeyInt & 0x08)==8){
					classTime = "0";
				} else if ((gpsKeyInt & 0x10)==16){
					classTime = "1";
				} else if ((gpsKeyInt & 0x20)==32){
					classTime = "2";
				}
				String otherTime = "0";
				if((gpsKeyInt & 0x40)==64){
					otherTime = "0";
				} else if ((gpsKeyInt & 0x80)==128){
					otherTime = "1";
				} else if ((gpsKeyInt & 0x100)==256){
					otherTime = "2";
				}
				map.put("nightModel", nightModel);
				map.put("classTime", classTime);
				map.put("otherTime", otherTime);
				
				map.put("statCode", statCode);
				//修改家长密码(尚未开通)
			}else {
				map.put("result", "fail");
			}
		}else{
			map.put("statCode", "100");
		}
		return map;
	}
 
	@ResponseBody
	@RequestMapping(value = "/sendSystemSettingsInfo")
	public Object sendSystemSettingsInfoApp() throws Exception {
		
		logBefore(logger, "同步更新校园管理信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
    	Map<String, Object> map = new HashMap<>();	
		Ibaby ibaby = new Ibaby();
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);
		if(p !=null && p.size()>0){
			pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei
			
			//保存设置信息
			Map<String, Ibaby> mv = new HashMap<>();	
			mv = systemSettingsAppService.sendSystemSettingsInfo(pd);
			ibaby = mv.get("result");
			String  statCode = ibaby.getResponse().getStatus().getCode();
			
			//同步设置到儿童机
			if (statCode.equals("200")){
				pd.put("index", "3"); // 同步index
				mv = systemSettingsAppService.syncSystemSettingsInfo(pd);
				pd.put("index", "5"); // 同步index
				mv = systemSettingsAppService.syncSystemSettingsInfo(pd);
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
