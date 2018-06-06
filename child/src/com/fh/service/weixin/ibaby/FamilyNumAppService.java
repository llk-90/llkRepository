package com.fh.service.weixin.ibaby;

import java.util.ArrayList;
import java.util.Arrays;
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

@Service("familyNumAppService")
public class FamilyNumAppService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 设置获取联系人信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> findFamilyInfo(PageData pd) throws Exception {    
		
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
	 * 设置获取电话簿信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> findBookListInfo(PageData pd) throws Exception {    
	
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String action = "QueryTelBookListByTerminalId";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);

		String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
		
	    return IBabyUtil.getDeviceInfo(xmlString);
	      
	}
	
	/**
	 * 设置联系人信息参数
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> sendFamilyInfoApp(PageData pd) throws Exception {    
		
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String sos1 = pd.getString("sos1");
		String sos2 = pd.getString("sos2");
		String sos3 = pd.getString("sos3");
		String sos4 = pd.getString("sos4");
		String guardian1 = pd.getString("guardian1");
		String guardian2 = pd.getString("guardian2");
		
		String action = "UpdateIbabyExtTerminal";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("sos1");
		keyname.add("sos2");
		keyname.add("sos3");
		keyname.add("sos4");
		keyname.add("guardian1");
		keyname.add("guardian2");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(sos1);
		keyvalue.add(sos2);
		keyvalue.add(sos3);
		keyvalue.add(sos4);
		keyvalue.add(guardian1);
		keyvalue.add(guardian2);
		
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
	public Map<String, Ibaby> syncFamilyInfoApp(PageData pd) throws Exception {    
		
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
	
	
	/**
	 * 新增电话本
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> addBookListInfo(PageData pd) throws Exception {    
	
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String userName = pd.getString("userName");
		String telNumber = pd.getString("telNumber");
		String ringId = pd.getString("ringId");
		
		String action = "AddTelBook";
		List<String> keyname = new ArrayList<String>();
		keyname.add("terminalImei");
		keyname.add("userName");
		keyname.add("telNumber");
		keyname.add("ringId");
		List<String> keyvalue = new ArrayList<String>();
		keyvalue.add(terminalImei);
		keyvalue.add(userName);
		keyvalue.add(telNumber);
		keyvalue.add(ringId);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 更新电话本
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> UpdateTelBookListInfo(PageData pd) throws Exception {    
	
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String telId = pd.getString("telId");
		String userName = pd.getString("userName");
		String telNumber = pd.getString("telNumber");
		String ringId = pd.getString("ringId");
		
		String action = "UpdateTelBook";
		List<String> keyname = new ArrayList<String>();
		//keyname.add("terminalImei");
		keyname.add("telId");
		keyname.add("userName");
		keyname.add("telNumber");
		keyname.add("ringId");
		List<String> keyvalue = new ArrayList<String>();
		//keyvalue.add(terminalImei);
		keyvalue.add(telId);
		keyvalue.add(userName);
		keyvalue.add(telNumber);
		keyvalue.add(ringId);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
	/**
	 * 删除电话本
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Ibaby> DeleteTelBookListInfo(PageData pd) throws Exception {    
	
		String loginName = pd.getString("loginName");
		String password = pd.getString("passWord");
		String terminalImei = pd.getString("terminalImei");
		
		String telId = pd.getString("telId");
		
		String action = "DeleteTelBook";
		List<String> keyname = new ArrayList<String>();
		//keyname.add("terminalImei");
		keyname.add("telId");
		List<String> keyvalue = new ArrayList<String>();
		//keyvalue.add(terminalImei);
		keyvalue.add(telId);
		
	    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
	    		
	    return IBabyUtil.getDeviceInfo(xmlString);
	}
	
}
	

