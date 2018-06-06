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


@Service("weilanRecordService")
public class WeilanRecordService{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao ;
	
	//获取围栏记录
		public Map<String, Ibaby> findWeilanRecord(PageData pd) throws Exception {    
			
			String loginName = pd.getString("loginName");
			String password = pd.getString("passWord");
			String terminalImei = pd.getString("terminalImei");
			String beginTime = pd.getString("beginTime");
			String endTime = pd.getString("endTime");
			String beginIndex = pd.getString("beginIndex");
			String fetchSize = pd.getString("fetchSize");
			
			String action = "QueryAlarmInfo";
			
			//添加请求参数
			List<String> keyname = new ArrayList<String>();
			keyname.add("terminalImei");
			keyname.add("beginTime");
			keyname.add("endTime");
			keyname.add("beginIndex");
			keyname.add("fetchSize");
			List<String> keyvalue = new ArrayList<String>();
			keyvalue.add(terminalImei);
			keyvalue.add(beginTime);
			keyvalue.add(endTime);
			keyvalue.add(beginIndex);
			keyvalue.add(fetchSize);
		    String xmlString = IBabyUtil.getXmlInfo(loginName,action,password,keyname,keyvalue);
		    		
		    return IBabyUtil.getDeviceInfo(xmlString);
		}

}
