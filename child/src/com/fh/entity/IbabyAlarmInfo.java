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
@XmlRootElement(name = "AlarmInfo")
@XmlType(propOrder = { "lat", "lng", "orgiLat", "orgiLng", "alarmTime", "areaDesc" })
public class IbabyAlarmInfo {
	//纠偏后的经度
    @XmlElement(name = "Lat")  
    private String lat;
    //纠偏后的纬度
    @XmlElement(name = "Lng")  
    private String lng;    
    //原始经度
    @XmlElement(name = "OrgiLat")  
    private String orgiLat;   
    //原始纬度
    @XmlElement(name = "OrgiLng")  
    private String orgiLng;   
    //触发监护时间
    @XmlElement(name = "AlarmTime")  
    private String alarmTime;  
    //触发的围栏信息
    @XmlElement(name = "AreaDesc")  
    private String areaDesc;    

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getOrgiLat() {
		return orgiLat;
	}

	public void setOrgiLat(String orgiLat) {
		this.orgiLat = orgiLat;
	}

	public String getOrgiLng() {
		return orgiLng;
	}

	public void setOrgiLng(String orgiLng) {
		this.orgiLng = orgiLng;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
    
}
