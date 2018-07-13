package com.example.entity;

import java.util.Date;

public class UserPositionInfo {
	 private int id;
	 private String icon; // 图片地址icon 
     private String keyId;//openId
     private String lng;//经度
     private String lat;//纬度
     private String visitsCount;// 拜访次數
     private String cusName;//位置信息的解釋
     private String address;//位置信息的解釋
     private String baifangTime;//位置信息的解釋
     private String phone;//联系方式
     private String notes;//位置信息的解釋
     private Date createTime;//数据创建时间

     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getKeyId() {
		return keyId;
	}
	
	
	public String getVisitsCount() {
		return visitsCount;
	}
	public void setVisitsCount(String visitsCount) {
		this.visitsCount = visitsCount;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getBaifangTime() {
		return baifangTime;
	}
	public void setBaifangTime(String baifangTime) {
		this.baifangTime = baifangTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getLng() {
		return lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
