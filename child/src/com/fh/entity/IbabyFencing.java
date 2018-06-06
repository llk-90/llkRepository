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
@XmlRootElement(name = "Fencing")
@XmlType(propOrder = { "fencingId", "fencingName", "fencingType", "desc", "alarmType", "noticeMan"})
public class IbabyFencing {
	

	 @XmlElement(name = "FencingId")  
	 private String fencingId;

	 @XmlElement(name = "FencingName")  
	 private String fencingName;

     @XmlElement(name = "FencingType")  
     private String fencingType;

	 @XmlElement(name = "Desc")  
	 private String desc;

	 @XmlElement(name = "AlarmType")  
	 private String alarmType;
	 
	 @XmlElement(name = "NoticeMan")  
	 private String noticeMan;
	 
	public String getFencingId() {
		return fencingId;
	}
	public void setFencingId(String fencingId) {
		this.fencingId = fencingId;
	}
	public String getFencingName() {
		return fencingName;
	}
	public void setFencingName(String fencingName) {
		this.fencingName = fencingName;
	}
	public String getFencingType() {
		return fencingType;
	}
	public void setFencingType(String fencingType) {
		this.fencingType = fencingType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getNoticeMan() {
		return noticeMan;
	}
	public void setNoticeMan(String noticeMan) {
		this.noticeMan = noticeMan;
	}
    
}