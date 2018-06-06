package com.fh.entity;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType; 

/** 
 * @create      2017-6-7 
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "TelBook")
@XmlType(propOrder = { "telId", "userName", "index", "ringId", "iconId", "telNumber"})
public class IbabyTelBook {
	
	//本条记录的id
	 @XmlElement(name = "TelId")  
	 private String telId;
    //姓名
	 @XmlElement(name = "UserName")  
	 private String userName;
    //序号
	 @XmlElement(name = "Index")  
	 private String index;
	//电话
     @XmlElement(name = "TelNumber")  
     private String telNumber;
    //铃声，数字，根据机型取值范围不同
	 @XmlElement(name = "RingId")  
	 private String ringId;
	//头像id
     @XmlElement(name = "IconId")  
	 private String iconId;
     
     
	public String getTelId() {
		return telId;
	}
	public void setTelId(String telId) {
		this.telId = telId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getRingId() {
		return ringId;
	}
	public void setRingId(String ringId) {
		this.ringId = ringId;
	}
	public String getIconId() {
		return iconId;
	}
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
}