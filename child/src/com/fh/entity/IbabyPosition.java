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
@XmlRootElement(name = "Position")
@XmlType(propOrder = { "terminalId", "updateTime", "staySecond", "lat", "lng", "orgiLat", 
		"orgiLng", "lastGpsLat", "lastGpsLng", "lastGpsTime", "speed", "direction", 
		"battery", "address", "isGps", "onlineStatus", "schoolForbidden", "powerOff", 
		"lacId", "cellId", "isLocked", "activeConn", "realTimeMonitor", "step" })
public class IbabyPosition {
	
    @XmlElement(name = "TerminalId")  
    private String terminalId;

    @XmlElement(name = "UpdateTime")  
    private String updateTime;    

    @XmlElement(name = "StaySecond")  
    private String staySecond;
    
    @XmlElement(name = "Lat")  
    private String lat;

    @XmlElement(name = "Lng")  
    private String lng;    

    @XmlElement(name = "OrgiLat")  
    private String orgiLat;   

    @XmlElement(name = "OrgiLng")  
    private String orgiLng;   

    @XmlElement(name = "LastGpsLat")  
    private String lastGpsLat;   

    @XmlElement(name = "LastGpsLng")  
    private String lastGpsLng;   

    @XmlElement(name = "LastGpsTime")  
    private String lastGpsTime;   
    
    @XmlElement(name = "Speed")  
    private String speed;  

    @XmlElement(name = "Direction")  
    private String direction;    

    @XmlElement(name = "Battery")  
    private String battery;    
    
    @XmlElement(name = "Address")  
    private String address;  
    
    @XmlElement(name = "IsGps")  
    private String isGps;       

    @XmlElement(name = "OnlineStatus")  
    private String onlineStatus; 

    @XmlElement(name = "SchoolForbidden")  
    private String schoolForbidden; 

    @XmlElement(name = "PowerOff")  
    private String powerOff;

    @XmlElement(name = "LacId")  
    private String lacId;

    @XmlElement(name = "CellId")  
    private String cellId;

    @XmlElement(name = "IsLocked")  
    private String isLocked;

    @XmlElement(name = "ActiveConn")  
    private String activeConn;

    @XmlElement(name = "RealTimeMonitor")  
    private String realTimeMonitor;

    @XmlElement(name = "Step")  
    private String step;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStaySecond() {
		return staySecond;
	}

	public void setStaySecond(String staySecond) {
		this.staySecond = staySecond;
	}

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

	public String getLastGpsLat() {
		return lastGpsLat;
	}

	public void setLastGpsLat(String lastGpsLat) {
		this.lastGpsLat = lastGpsLat;
	}

	public String getLastGpsLng() {
		return lastGpsLng;
	}

	public void setLastGpsLng(String lastGpsLng) {
		this.lastGpsLng = lastGpsLng;
	}

	public String getLastGpsTime() {
		return lastGpsTime;
	}

	public void setLastGpsTime(String lastGpsTime) {
		this.lastGpsTime = lastGpsTime;
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

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsGps() {
		return isGps;
	}

	public void setIsGps(String isGps) {
		this.isGps = isGps;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getSchoolForbidden() {
		return schoolForbidden;
	}

	public void setSchoolForbidden(String schoolForbidden) {
		this.schoolForbidden = schoolForbidden;
	}

	public String getPowerOff() {
		return powerOff;
	}

	public void setPowerOff(String powerOff) {
		this.powerOff = powerOff;
	}

	public String getLacId() {
		return lacId;
	}

	public void setLacId(String lacId) {
		this.lacId = lacId;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getActiveConn() {
		return activeConn;
	}

	public void setActiveConn(String activeConn) {
		this.activeConn = activeConn;
	}

	public String getRealTimeMonitor() {
		return realTimeMonitor;
	}

	public void setRealTimeMonitor(String realTimeMonitor) {
		this.realTimeMonitor = realTimeMonitor;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
    
}
