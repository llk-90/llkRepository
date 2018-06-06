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

@Service("campusManageAppService")
public class CampusManageAppService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 设置获取参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> campusManageInfo(PageData pd) throws Exception {    
		
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
	public Map<String, Ibaby> sendCampusManageInfo(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String studyForbidden = pd.getString("studyForbidden");
		
		String arrNocPeo1 = pd.getString("arrNocPeo1");
		String arrNocPeo2 = pd.getString("arrNocPeo2");
		String levNocPeo1 = pd.getString("levNocPeo1");
		String levNocPeo2 = pd.getString("levNocPeo2");
		Integer schoolCheckInNotic = (arrNocPeo1.equals("1")?1:0) + (arrNocPeo2.equals("1")?2:0) + (levNocPeo1.equals("1")?4:0) + (levNocPeo2.equals("1")?8:0);
		
		String schoolTimeRange = pd.getString("schoolTimeRange");
		String schoolHomeTimeRange = pd.getString("schoolHomeTimeRange");
		
		String action = "UpdateIbabyExtTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("studyForbidden");
		keyname.add("schoolCheckInNotice");
		keyname.add("schoolTimeRange");
		keyname.add("schoolHomeTimeRange");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(studyForbidden);
		keyvalue.add(String.valueOf(schoolCheckInNotic));
		keyvalue.add(schoolTimeRange);
		keyvalue.add(schoolHomeTimeRange);
		
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
	public Map<String, Ibaby> syncCampusManageInfo(PageData pd) throws Exception {    
		
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
