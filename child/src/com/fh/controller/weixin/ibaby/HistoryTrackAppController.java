package com.fh.controller.weixin.ibaby;

import java.io.Console;
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
import com.fh.entity.IbabyTrack;
import com.fh.entity.system.User;
import com.fh.service.weixin.leave.LeaveAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/historyTrackApp")
public class HistoryTrackAppController extends BaseController {

	//String menuUrl = "historyTrackApp/quickTrack.do"; // 菜单地址(权限用)

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;
	
	@Resource(name = "leaveAppService")
	private LeaveAppService leaveAppService;

	/**
	 * 学生定位轨迹获取处理
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/detailTrack")
	public Object detailTrack() throws Exception {
		System.out.println("111");
		logBefore(logger, "定位轨迹检索");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
//		String openid = pd.getString("openid");
		
		Map<String, Ibaby> mv = new HashMap<>();
		
		Map<String, Object> map = new HashMap<>();
        Ibaby ibaby = new Ibaby();
		
//		String user_id = leaveAppService.findUseridByOpenid(openid);
//		pd.put("user_id", user_id);

		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);
	
		if(p != null && p.size()>0){
			pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei			 
	        String startTime00 = pd.getString("startTime").concat(":00");
	        String endTime00 = pd.getString("endTime").concat(":00");
	        pd.put("startTime", startTime00);
	        pd.put("endTime", endTime00);
	        
//	      pd.put("loginName", "866076020235506"); 
//	      pd.put("passWord", "123456"); // 儿童机帐号密码	
//			pd.put("terminalImei", "866076020235506"); // 设备imei
//			pd.put("startTime", "2017-04-28 00:00:00");
//			pd.put("endTime", "2017-04-28 23:59:00");
			
			mv = historyTrackAppService.detailTrack(pd);
	          System.out.println(mv);
	        ibaby = mv.get("result");
	        String  statCode = ibaby.getResponse().getStatus().getCode();
//	        System.out.println(ibaby.getResponse());
//	        System.out.println(ibaby.getResponse().getPagination());
	        List<IbabyTrack>  trackList = ibaby.getResponse().getPagination().getTrackList();
//	        List<IbabyPosition>  trackList = ibaby.getResponse().getPositionList();
	        String totalSize = ibaby.getResponse().getPagination().getTotalSize();
			map.put("statCode", statCode);
			map.put("totalSize", totalSize);
	 		map.put("trackList", trackList);
			
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
