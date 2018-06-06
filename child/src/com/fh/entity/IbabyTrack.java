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
@XmlRootElement(name = "Track")
@XmlType(propOrder = { "lat", "lng", "orgiLat", "orgiLng", "speed", "direction", "isGps", "positionTime", "staySecond" })
public class IbabyTrack {
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
    //速度
    @XmlElement(name = "Speed")  
    private String speed;  
    //方向
    @XmlElement(name = "Direction")  
    private String direction;    
    //是否gps定位，1为gps定位
    @XmlElement(name = "IsGps")  
    private String isGps;       
    //定位时间
    @XmlElement(name = "PositionTime")  
    private String positionTime; 
    //停留时间（秒）
    @XmlElement(name = "StaySecond")  
    private String staySecond;

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

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getIsGps() {
		return isGps;
	}

	public void setIsGps(String isGps) {
		this.isGps = isGps;
	}

	public String getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public String getStaySecond() {
		return staySecond;
	}

	public void setStaySecond(String staySecond) {
		this.staySecond = staySecond;
	}    
    

    
}
