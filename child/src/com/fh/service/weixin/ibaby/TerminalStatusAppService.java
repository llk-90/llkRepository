package com.fh.service.weixin.ibaby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Ibaby;
import com.fh.util.IBabyUtil;
import com.fh.util.PageData;

@Service("terminalStatusAppService")
public class TerminalStatusAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 设置开启实时定位参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> startMonitor(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "StartMonitor";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置修改实时定位参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> updMonitor(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "MonitorIntervalSet";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("interval");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add("5");
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置保持实时定位参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	public Map<String, Ibaby> keepMonitor(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "KeepAliveMonitor";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置断开实时定位参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	public Map<String, Ibaby> exitMonitor(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "ExitMonitor";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置获取定位信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	public Map<String, Ibaby> positionStatus(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "GetTerminalStatus";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置获取定位信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	public Map<String, Ibaby> addressInfo(PageData pd) throws Exception {  
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		
		String lat = pd.getString("lat");
		String lng = pd.getString("lng");
		String orgiLat = pd.getString("orgiLat");
		String orgiLng = pd.getString("orgiLng");
		String mcc = pd.getString("mcc");
		String isGps = pd.getString("isGps");
		
		String action = "GetGeoReverse";
		List<String> keyname = new ArrayList<String>();
		keyname.add("lat");
		keyname.add("lng");
		keyname.add("orgiLat");
		keyname.add("orgiLng");
		keyname.add("mcc");
		keyname.add("isGps");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(lat);
		keyvalue.add(lng);
		keyvalue.add(orgiLat);
		keyvalue.add(orgiLng);
		keyvalue.add(mcc);
		keyvalue.add(isGps);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
}
