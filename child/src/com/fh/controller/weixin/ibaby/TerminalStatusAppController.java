package com.fh.controller.weixin.ibaby;

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
import com.fh.entity.IbabyPosition;
import com.fh.entity.system.User;
import com.fh.service.weixin.leave.LeaveAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.service.weixin.ibaby.TerminalStatusAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/terminalStatusApp")
public class TerminalStatusAppController extends BaseController {

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;
	
	@Resource(name = "terminalStatusAppService")
	private TerminalStatusAppService terminalStatusAppService;
	
	@Resource(name = "leaveAppService")
	private LeaveAppService leaveAppService;

	/**
	 * 宝贝定位信息获取处理
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/terminalStatus")
	public Object terminalStatus() throws Exception {
		
		logBefore(logger, "定位信息检索");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
		Map<String, Ibaby> mv = new HashMap<>();
		
		Map<String, Object> map = new HashMap<>();
        Ibaby ibaby = new Ibaby();
		
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);
   if(p != null && p.size()>0){
	   pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
	   pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
	   pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei		
	   // 开启实时接口
	   if (pd.getString("stat").equals("start")){
		   mv = terminalStatusAppService.startMonitor(pd);
		   mv = terminalStatusAppService.updMonitor(pd);
	   } else if(pd.getString("stat").equals("keep")) {
		   mv = terminalStatusAppService.keepMonitor(pd);
	   } else if(pd.getString("stat").equals("exit")) {
		   mv = terminalStatusAppService.exitMonitor(pd);
	   }
	   mv = terminalStatusAppService.positionStatus(pd);
	   
	   ibaby = mv.get("result");
	   
	   String  statCode = ibaby.getResponse().getStatus().getCode();
	   String speed = ibaby.getResponse().getPositionList().get(0).getSpeed();
	   String updateTime = ibaby.getResponse().getPositionList().get(0).getUpdateTime();
	   String isGps = ibaby.getResponse().getPositionList().get(0).getIsGps();
	   
	   String lat = ibaby.getResponse().getPositionList().get(0).getLat();
	   String lng = ibaby.getResponse().getPositionList().get(0).getLng();
	   
	   map.put("statCode", statCode);
	   map.put("speed", speed);
	   map.put("updateTime", updateTime);
	   map.put("isGps", isGps);
	   
	   map.put("lat", lat);
	   map.put("lng", lng);
   }else{
	   map.put("statCode", "100");
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
