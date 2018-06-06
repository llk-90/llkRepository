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
import com.fh.service.weixin.ibaby.RemoteControlAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/remoteControlApp")
public class RemoteControlAppController extends BaseController {
	 	
	@Resource(name="remoteControlAppService")
	private RemoteControlAppService remoteControlAppService;

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;	
 
	@ResponseBody
	@RequestMapping(value = "/RemoteControl")
	public Object RemoteControlApp() throws Exception {
		
		logBefore(logger, "设置远程休眠");
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
		    //保存设置信息
		    Map<String, Ibaby> mv = new HashMap<>();	
		    String temp= pd.getString ("temp");	    
		    switch(temp){
			    case "1":
			        mv = remoteControlAppService.SleepTerminal(pd);
			        break;
			    case "2":
			    	pd.put("phone","1");
				    mv = remoteControlAppService.CallBack(pd);
				    break;
			    case "3":
			    	pd.put("phone","2");
				    mv = remoteControlAppService.CallBack(pd);
				    break;
			    case "4":
				    mv = remoteControlAppService.PowerOffTerminal(pd);
				    break;
			    case "5":
			    	mv = remoteControlAppService.ResetTerminal(pd);
				    break;
			    case "6":
			    	mv = remoteControlAppService.WakeUpTerminal(pd);
				    break;
			}	    
		    ibaby = mv.get("result");
	        String  statCode = ibaby.getResponse().getStatus().getCode();
	        map.put("statCode", statCode);
	       
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
