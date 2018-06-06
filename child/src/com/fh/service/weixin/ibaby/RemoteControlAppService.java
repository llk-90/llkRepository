package com.fh.service.weixin.ibaby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.fh.entity.Ibaby;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.IBabyUtil;
import com.fh.util.PageData;

@Service("remoteControlAppService")
public class RemoteControlAppService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * 远程休眠
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> SleepTerminal(PageData pd) throws Exception {    
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "SleepTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 *远程唤醒
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> WakeUpTerminal (PageData pd) throws Exception {    
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "WakeUpTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}

	/**
	 *远程关机
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> PowerOffTerminal  (PageData pd) throws Exception {    
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "PowerOffTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 *恢复出厂
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> ResetTerminal   (PageData pd) throws Exception {    
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei  = pd.getString("terminalImei");
		
		String action = "ResetTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}

	/**
	 *家长监听
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> CallBack(PageData pd) throws Exception {    
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei  = pd.getString("terminalImei");
		String phone   = pd.getString("phone");
		
		String action = "CallBack";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("phone");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(phone);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	
	
}
