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

@Service("studentHealthAppService")
public class StudentHealthAppService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取监护人设置信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> setstudentHealth(PageData pd) throws Exception {    
		
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
	public Map<String, Ibaby> sendStudentHealthInfo(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String isReject = pd.getString("isReject");
		String isRejectMes = pd.getString("isRejectMes");
		String disallowTel = pd.getString("disallowTel");
		int comunicationFireWall = (isReject.equals("1")?1:0) + (isRejectMes.equals("1")?2:0) + (disallowTel.equals("1")?4:0); 
		String allowFamilyListen = pd.getString("allowFamilyListen");
		String noManGetCallSet = pd.getString("noManGetCallSet");
		String lowPowerNoticeFamily = pd.getString("lowPowerNoticeFamily");
		String turnOffNeedPwd = pd.getString("turnOffNeedPwd");
		String getCallMode = pd.getString("getCallMode");
		String timePerCall = pd.getString("timePerCall");
		String lostRobNotice = pd.getString("lostRobNotice");
		
		String action = "UpdateIbabyExtTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("comunicationFireWall");
		keyname.add("allowFamilyListen");
		keyname.add("noManGetCallSet");
		keyname.add("lowPowerNoticeFamily");
		keyname.add("turnOffNeedPwd");
		keyname.add("getCallMode");
		keyname.add("timePerCall");
		keyname.add("lostRobNotice");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(String.valueOf(comunicationFireWall));
		keyvalue.add(allowFamilyListen);
		keyvalue.add(noManGetCallSet);
		keyvalue.add(lowPowerNoticeFamily);
		keyvalue.add(turnOffNeedPwd);
		keyvalue.add(getCallMode);
		keyvalue.add(timePerCall);
		keyvalue.add(lostRobNotice);
		
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
	public Map<String, Ibaby> syncStudentHealthInfo(PageData pd) throws Exception {    
		
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
