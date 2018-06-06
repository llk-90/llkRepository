package com.fh.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType; 

/** 
 * 爱贝多平台请求模板bean
 * @create      2017-6-7 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IBABY")
@XmlType(propOrder = {"response","param","version","action","checkSum","loginName"})  
public class Ibaby {
	
	//返回数据参数写在这个bean
    @XmlElement(name = "Response")    
    private IbabyResponse response;
    
	//请求数据参数写在这个bean
    @XmlElementWrapper(name = "Request")  
    @XmlElement(name = "Param")  
    private List<IbabyParam> param;
    
    @XmlAttribute  
    private String version;
    
    @XmlAttribute  
    private String action;  
    
    @XmlAttribute  
    private String checkSum;
    
    @XmlAttribute  
    private String loginName;
    
	public IbabyResponse getResponse() {
		return response;
	}

	public void setResponse(IbabyResponse response) {
		this.response = response;
	}

	public List<IbabyParam> getParam() {
		return param;
	}

	public void setParam(List<IbabyParam> param) {
		this.param = param;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
    
}
