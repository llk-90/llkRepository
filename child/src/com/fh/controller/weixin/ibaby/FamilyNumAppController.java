package com.fh.controller.weixin.ibaby;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import com.fh.entity.IbabyTelBook;
import com.fh.service.weixin.ibaby.FamilyNumAppService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/familyNumApp")
public class FamilyNumAppController extends BaseController {
	 	
	@Resource(name="familyNumAppService")
	private FamilyNumAppService familyNumAppService;

	@Resource(name = "historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;	

	@ResponseBody
	@RequestMapping(value = "/familyNumInfo")
	public Object findFamilyInfoApp() throws Exception {
		
		logBefore(logger, "获取联络号信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
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
		    mv = familyNumAppService.findFamilyInfo(pd);
		    ibaby = mv.get("result");
		    String statCode = ibaby.getResponse().getStatus().getCode();
		    if (statCode.equals("200")){
				//紧急求助电话接口方法	    
				String  sos1 = ibaby.getResponse().getTerminalExt().getSos1();
				String  sos2 = ibaby.getResponse().getTerminalExt().getSos2();
				String  sos3 = ibaby.getResponse().getTerminalExt().getSos3();
				String  sos4 = ibaby.getResponse().getTerminalExt().getSos4();		
				//监护人电话接口方法
				String  guardian1 = ibaby.getResponse().getTerminalExt().getGuardian1();		
				String  guardian2 = ibaby.getResponse().getTerminalExt().getGuardian2();		
			    
				map.put("sos1", sos1);
			    map.put("sos2", sos2);
			    map.put("sos3", sos3);
			    map.put("sos4", sos4);
			    map.put("guardian1", guardian1);
			    map.put("guardian2", guardian2);
			    map.put("statCode", statCode);
			}
		    
		    mv = familyNumAppService.findBookListInfo(pd);
		    ibaby = mv.get("result");
		    statCode = ibaby.getResponse().getStatus().getCode();
		    if (statCode.equals("200")){
				//获取亲情号的接口   		
		    	List<IbabyTelBook>  telBookList = ibaby.getResponse().getTelBookList();
		    	//根据index重新排序
		        Collections.sort(telBookList, new MapComparator());
		    	map.put("telBookList", telBookList);
		    	map.put("statCode", statCode);
			}
		} else{
			map.put("statCode", "100");
		}
		
		return map;
	}
	
	static class MapComparator implements Comparator<IbabyTelBook> {
		@Override
		public int compare(IbabyTelBook o1, IbabyTelBook o2) {
			// TODO Auto-generated method stub
			String b1 = o1.getIndex();
			String b2 = o2.getIndex();
			if (b1 != null) {
				return b1.compareTo(b2);
			}
			return 0;
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/sendFamilyNumInfo")
	public Object sendFamilyInfoApp() throws Exception {
		
		logBefore(logger, "更新联络号信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
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
		    mv = familyNumAppService.sendFamilyInfoApp(pd);
		    ibaby = mv.get("result");
		    String statCode = ibaby.getResponse().getStatus().getCode();
			map.put("statCode", statCode);
			
//		    mv = familyNumAppService.findBookListInfo(pd);
//		    ibaby = mv.get("result");
//		    statCode = ibaby.getResponse().getStatus().getCode();
//		    List<String> rtnTelId = new ArrayList<String>();
//		    if (statCode.equals("200")){
//				//获取亲情号的接口   		
//		    	List<IbabyTelBook>  telBookList = ibaby.getResponse().getTelBookList();
//		    	 for(IbabyTelBook s : telBookList){
//		    		 rtnTelId.add(s.getTelId());
//		    	 }
//		    	
//			}
//		    List<String> TelId = new ArrayList<String>();
//		    List<String> UserName = new ArrayList<String>();
//		    List<String> TelNumber = new ArrayList<String>();
			map.put("result", "success");
			//获取电话簿信息(亲情号码4个)
			for(int i=0;i<4;i++){
				String name = "familynum["+i+"][UserName]";
				String telnum = "familynum["+i+"][TelNumber]";
				String telid = "familynum["+i+"][TelId]";
//				if (pd.getString(name)!=null) {UserName.add(pd.getString(name));}
//				if (pd.getString(telnum)!=null) {TelNumber.add(pd.getString(telnum));}
//				if (pd.getString(telid)!=null) {TelId.add(pd.getString(telid));}
				pd.put("telId", pd.getString(telid));
				pd.put("userName", pd.getString(name));
				pd.put("telNumber", pd.getString(telnum));
				pd.put("ringId", "1");
				if (pd.getString(telid)==null || pd.getString(telid).equals("")) {
					mv = familyNumAppService.addBookListInfo(pd);
				} else {
					mv = familyNumAppService.UpdateTelBookListInfo(pd);
				}
			    ibaby = mv.get("result");
			    statCode = ibaby.getResponse().getStatus().getCode();
			    map.put("statCode", statCode);
			    if (!statCode.equals("200")){map.put("result", "error");}
			}
			
//			for(int i=0;i<TelId.size();i++){
//			
//				if(TelId.get(i).equals("Add")){
//					pd.put("userName", UserName.get(i));
//					pd.put("telNumber", TelNumber.get(i));
//					pd.put("ringId", "1");
//					mv = familyNumAppService.addBookListInfo(pd);
//				}
//				else if(!TelId.get(i).equals("Add") && !TelId.get(i).equals("")){
//					pd.put("telId", TelId.get(i));
//					pd.put("userName", UserName.get(i));
//					pd.put("telNumber", TelNumber.get(i));
//					pd.put("ringId", "1");
//					mv = familyNumAppService.UpdateTelBookListInfo(pd);
//				}
	//	
//			    ibaby = mv.get("result");
//			    statCode = ibaby.getResponse().getStatus().getCode();
//			    map.put("statCode", statCode);
//			    if (!statCode.equals("200")){map.put("result", "error");}
//			}
//			for(String rtnTelstr: rtnTelId){
//				if(!TelId.contains(rtnTelstr)){
//					pd.put("telId", rtnTelstr);
//					mv = familyNumAppService.DeleteTelBookListInfo(pd);
//				    ibaby = mv.get("result");
//				    statCode = ibaby.getResponse().getStatus().getCode();
//				    map.put("statCode", statCode);
//					if (!statCode.equals("200")){map.put("result", "error");}
//				}
//			}
			pd.put("index", "1"); // 同步index
	        if (map.get("result").equals("success")){
	    	    mv = familyNumAppService.syncFamilyInfoApp(pd);
	    	    ibaby = mv.get("result");
	            statCode = ibaby.getResponse().getStatus().getCode();
	    		map.put("result", statCode);
	        } else {
	        	map.put("statCode", "error");
	        }
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
