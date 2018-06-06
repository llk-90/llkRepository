package com.fh.service.weixin.ibaby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Ibaby;
import com.fh.util.IBabyUtil;
import com.fh.util.PageData;

@Service("weilanService")
public class WeilanService {
	
	@Resource(name = "daoSupport")  
	private DaoSupport dao ;
	
	/*
	 * 保存围栏信息(调接口)
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Ibaby> weilanMsgInsertOrUpdate(PageData pd,String addOrUpdate) throws Exception {
	   Map<String, Object> map = getPageData(pd);
	   
	   //请求参数设置
		List<String> keyname = new ArrayList<String>();
		keyname=(List<String>) map.get("keyname");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue=(List<String>) map.get("keyvalue");
		String terminalImei = (String)map.get("terminalImei");
		String loginName = (String)map.get("loginName");
		String password = (String)map.get("password");
		String fencingId = (String)map.get("fencingId");
		
		if("添加".equals(addOrUpdate)){
			String action = "SaveGeoFencing";
			//设备imei
			keyname.add("terminalImei");
			keyvalue.add(terminalImei);
			//String xmlString = IBabyUtil.getXmlInfo((String)map.get("loginName"),action,(String)map.get("password"),keyname,keyvalue);
			String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
			return IBabyUtil.getDeviceInfo(xmlString);
		}
		if("修改".equalsIgnoreCase(addOrUpdate)){
			//围栏id
			keyname.add("fencingId");
			keyvalue.add(fencingId);
			String action = "UpdateGeoFencing";
			//String xmlString = IBabyUtil.getXmlInfo((String)map.get("loginName"),action,(String)map.get("password"),keyname,keyvalue);
			String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
			return IBabyUtil.getDeviceInfo(xmlString);
		}
		else{
		return null;	
		}
	}
	
	/*
	 * 删除围栏信息（根据围栏id）
	 */
	public Map<String,Ibaby> deleteweilanMsg(PageData pd) throws Exception{
		
		 Map<String, Object> map = getPageData(pd);
		 String loginName = (String) map.get("loginName");
		 String password = (String) map.get("password");
		String action = "DeleteGeoFencingById";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("fencingId");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add((String) map.get("terminalImei"));
		keyvalue.add((String) map.get("fencingId"));
		
		  String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	
	//获取所有电子围栏的信息
	public Map<String, Ibaby> findWeilanName(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "QueryGeoFencingByTerminalId";
		
		//添加请求参数
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	//根据接口查询围栏详细
	public Map<String, Ibaby> findWeilanDetail(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String fencingId = pd.getString("fencingId");
		
		String action = "GetGeoFencingById";
		List<String> keyname = new ArrayList<String>();
		keyname.add("fencingId");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(fencingId);
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	//围栏信息同步到设备
	public Map<String, Ibaby> SyncAlarmArea(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "SyncAlarmArea";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
		String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
		
		return IBabyUtil.getDeviceInfo(xmlString);
	}
	
     
	public static Map<String, Object> getPageData(PageData pd){
		
		Map<String, Object> map = new HashMap<>();
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		String fencingId = pd.getString("fencingId");
		String fencingName = pd.getString("fencingName");
		String fencingType = pd.getString("fencingType");
		String desc = pd.getString("desc");
		String noticeMan = pd.getString("noticeMan");
		String alarmType = pd.getString("alarmType");
		//设置键请求数据
		List<String> keyname = new ArrayList<String>();
		keyname.add("fencingName");//围栏名称
		keyname.add("fencingType");//固定给2
		keyname.add("desc");//经纬度和半径
		keyname.add("noticeMan");//是否短信通知家长一,1表示通知,0不通知
		keyname.add("alarmType");//值采取按位与的方式,&1的结果为1则表示监控进入围栏,&2的结果为2则表示监控离开围栏,比如如果值为3,则表示进入和离开围栏都进行监控
		//设置
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(fencingName);
		keyvalue.add(fencingType);
		keyvalue.add(desc);
		keyvalue.add(noticeMan);
		keyvalue.add(alarmType);
		
		map.put("keyname", keyname);
		map.put("keyvalue", keyvalue);
		map.put("loginName", loginName);
		map.put("password", password);
		map.put("terminalImei", terminalImei);
		map.put("fencingId", fencingId);
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
