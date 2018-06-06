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
import com.fh.service.weixin.ibaby.AddchildInfoService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.service.weixin.ibaby.SystemSettingsAppService;
import com.fh.service.weixin.ibaby.TerminalStatusAppService;
import com.fh.service.weixin.ibaby.UpDateChildInfoService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping(value = "/addchildInfo")
public class AddchildInfoController extends BaseController {
	
	@Resource(name = "addchildInfoService")
	private AddchildInfoService addchildInfoService;
	
	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;
	
	@Resource(name = "terminalStatusAppService")
	private TerminalStatusAppService terminalStatusAppService;
	
	@Resource(name = "updatechildInfoService")
	private UpDateChildInfoService updatechildInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/addChild")
	public Object addChild() throws Exception {
		logBefore(logger, "宝贝信息添加");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime()); 
		String user_id = addchildInfoService.findUseridByOpenid(pd.getString("openid"));
		
		pd.put("user_id", user_id); // 正式替换为user_id
		pd.put("phoneStatus", "0");
		pd.put("create_time", timestamp);
		pd.put("update_time", timestamp);
		String s_id = addchildInfoService.findStuid(pd);
		
		if (s_id != null){
			pd.put("stu_id", s_id);
			PageData babyInfo = updatechildInfoService.findchild(pd.getString("stu_id"));
			if(babyInfo!=null && babyInfo.size()>0){
				map.put("result", "repeat");
			} else {
				addchildInfoService.addChild(pd);
				map.put("stuId", s_id);
				map.put("result", "success");
			}
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/babyInfo")
	public Object babyInfo() throws Exception {
		logBefore(logger, "获取最近宝贝信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Calendar calendar = Calendar.getInstance();
		
		Map<String, Object> map = new HashMap<>();
		Map<String, Ibaby> mv = new HashMap<>();
		Date date = new Date();
        Ibaby ibaby = new Ibaby();
		
		PageData pd = this.getPageData();
		String user_id = addchildInfoService.findUseridByOpenid(pd.getString("openid"));
		
		pd.put("user_id", user_id); // 正式替换为user_id
		
		//宝贝学号
		//用户头像
		//用户名称
		//是否通讯中设备		
		List<PageData> babyInfo = addchildInfoService.list(pd);
		if(babyInfo !=null && babyInfo.size() >0){			
			PageData pdInfo = babyInfo.get(0);
			
			pd.put("loginName", pdInfo.getString("ibaby_equ_accout"));  // 儿童机账号
			pd.put("passWord", pdInfo.getString("ibaby_equ_password")); // 儿童机帐号密码	
			pd.put("terminalImei", pdInfo.getString("ibaby_imei_code")); // 设备imei
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sd.format(date);
	        String startTime = dateString.concat(" 00:00:00");
	        String endTime = dateString.concat(" 23:59:59");
	        pd.put("startTime", startTime);
	        pd.put("endTime", endTime);
	        
	
			//是否在保
	        Object createDateStr = pdInfo.get("ibaby_create_date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date createDate = sdf.parse(createDateStr.toString());
			calendar.setTime(createDate);
			calendar.add(Calendar.YEAR, +1);
			createDate = calendar.getTime();
			if (date.after(createDate)){
				pdInfo.put("isProtect", true);
			} else {
				pdInfo.put("isProtect", false);
			}
//			//轨迹数
//			mv = historyTrackAppService.detailTrack(pd);
//	        ibaby = mv.get("result");
//	        String totalSize = ibaby.getResponse().getPagination().getTotalSize();
//	        pdInfo.put("locationNum", totalSize);
	        
	        //定位方式 
	        //最后通讯时间
			mv = terminalStatusAppService.positionStatus(pd);
	        ibaby = mv.get("result");
	        try {
	        	String updateTime = ibaby.getResponse().getPositionList().get(0).getUpdateTime();
		        String isGps = ibaby.getResponse().getPositionList().get(0).getIsGps();
		        String lat = ibaby.getResponse().getPositionList().get(0).getLat();
		        String lng = ibaby.getResponse().getPositionList().get(0).getLng();
		        String orgiLat = ibaby.getResponse().getPositionList().get(0).getOrgiLat();
		        String orgiLng = ibaby.getResponse().getPositionList().get(0).getOrgiLng();
		        pdInfo.put("locatState", isGps);
		        pdInfo.put("lastConnectTime", updateTime);
		        //联系地址
		        pd.put("lat", lat);
		        pd.put("lng", lng);
		        pd.put("orgiLat", orgiLat);
		        pd.put("orgiLng", orgiLng);
		        pd.put("mcc", "460");
		        pd.put("isGps", isGps);
		        mv = terminalStatusAppService.addressInfo(pd);
		        ibaby = mv.get("result");
		        String address = ibaby.getResponse().getGeoReverse().getAddress();
		        pdInfo.put("connectAds", address);

//		        mv = systemSettingsAppService.systemSettings(pd);
//		        ibaby = mv.get("result");
//		        String  comunicationFireWall = ibaby.getResponse().getTerminalExt().getComunicationFireWall();	    
//				String  recivesState = "";
//				
//		        pdInfo.put("recivesState", recivesState);
		        
		        map.put("result", "success");
				map.put("babyInfo", pdInfo);
	        } catch (Exception e) {
	        	map.put("result", ibaby.getResponse().getStatus().getInfo());
	        }	         	        

		// 没有宝贝信息
		} else{
			map.put("result", "empty");
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public Object list() throws Exception {
		logBefore(logger, "查询所有宝贝");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		String user_id = addchildInfoService.findUseridByOpenid(pd.getString("openid"));
		pd.put("user_id", user_id); // 正式替换为user_id
		List<PageData> result = addchildInfoService.list(pd);
		
		map.put("list", result);
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/switchChild")
	public Object switchChild() throws Exception {
		logBefore(logger, "切换宝贝");
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
		String user_id = addchildInfoService.findUseridByOpenid(pd.getString("openid"));
		pd.put("user_id", user_id); // 正式替换为user_id
		String s_id = addchildInfoService.findStuid(pd);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime()); 
		pd.put("update_time", timestamp);
		Map<String, Object> map = new HashMap<>();
		if (s_id != null){
			pd.put("stu_id", s_id);
			addchildInfoService.updChild(pd);
			map.put("result", "success");
			map.put("stu_id", s_id);
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateChild")
	public Object updateChild(){
		logBefore(logger, "修改绑定信息");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<>();
		try {
			String user_id = addchildInfoService.findUseridByOpenid(pd.getString("openid"));
			pd.put("user_id", user_id); // 正式替换为user_id
			String s_id = addchildInfoService.findStuid(pd);
			pd.put("s_id", s_id);
			pd.put("create_date",new Date());
			pd.put("update_date", new Date());
			addchildInfoService.updateChild(pd);
			map.put("result", "success");
		} catch (Exception e) {
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
