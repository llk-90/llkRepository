package com.example.entity;




public class UserPositionInfo {
     private String keyId;//openId
     private String lnglat;//坐標信息
     private String sign_count;// 標記次數
     private String notes;//位置信息的解釋
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getLnglat() {
		return lnglat;
	}
	public void setLnglat(String lnglat) {
		this.lnglat = lnglat;
	}
	public String getSign_count() {
		return sign_count;
	}
	public void setSign_count(String sign_count) {
		this.sign_count = sign_count;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
