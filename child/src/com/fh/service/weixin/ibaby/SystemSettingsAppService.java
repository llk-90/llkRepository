package com.fh.service.weixin.ibaby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.fh.entity.Ibaby;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;

import com.fh.util.IBabyUtil;
import com.fh.util.PageData;

@Service("systemSettingsAppService")
public class SystemSettingsAppService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取监护人设置信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> systemSettings(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "GetTerminalExt";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}

	/**
	 * 设置同步参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> sendSystemSettingsInfo(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String autostartavaild = pd.getString("autostartavaild");
		String startatime = pd.getString("startatime");
		String autoendavaild = pd.getString("autoendavaild");
		String endatime = pd.getString("endatime");
		String autostartbvaild = pd.getString("autostartbvaild");
		String startbtime = pd.getString("startbtime");
		String autoendbvaild = pd.getString("autoendbvaild");
		String endbtime = pd.getString("endbtime");		
		
		String autoTurnOffOn = autostartavaild + "," + "0" + "," + startatime + "|" + autoendavaild + "," + "1" + "," + endatime + "|" 
								+ autostartbvaild + "," + "0" + "," + startbtime + "|" + autoendbvaild + "," + "1" + "," + endbtime;
		
		String nightModel = pd.getString("nightModel");
		String classTime = pd.getString("classTime");
		String otherTime = pd.getString("otherTime");
		Integer gpsKey = (nightModel.equals("0")?1:0) + (nightModel.equals("1")?2:0) + (nightModel.equals("2")?4:0)
									 + (classTime.equals("0")?8:0) + (classTime.equals("1")?16:0) + (classTime.equals("2")?32:0)
									 + (otherTime.equals("0")?64:0) + (otherTime.equals("1")?128:0) + (otherTime.equals("2")?256:0);

		String action = "UpdateIbabyExtTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("autoTurnOffOn");
		keyname.add("gpsKey");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(autoTurnOffOn);
		keyvalue.add(String.valueOf(gpsKey));

	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 设置同步参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> syncSystemSettingsInfo(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		String index = pd.getString("index");
		
		String action = "SyncTerminalParam";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("index");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(index);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	
}
