package com.fh.entity;

public class IbabyFenceMsg {
	//家长ID
   private String parentId;
   //学生ID
   private String studentId;
   //围栏名称
   private String weilanName;
   //围栏半径 
   private String weilanRadius;
   //进入围栏
   private String intoWeilan;
   //离开围栏
   private String leaveWeilan;
   //东经
   private String eastLongitud;
   //北纬
   private String northLatitud;
   
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getWeilanName() {
		return weilanName;
	}
	public void setWeilanName(String weilanName) {
		this.weilanName = weilanName;
	}
	public String getWeilanRadius() {
		return weilanRadius;
	}
	public void setWeilanRadius(String weilanRadius) {
		this.weilanRadius = weilanRadius;
	}
	
	public String getIntoWeilan() {
		return intoWeilan;
	}
	public void setIntoWeilan(String intoWeilan) {
		this.intoWeilan = intoWeilan;
	}
	public String getLeaveWeilan() {
		return leaveWeilan;
	}
	public void setLeaveWeilan(String leaveWeilan) {
		this.leaveWeilan = leaveWeilan;
	}
	public String getEastLongitud() {
		return eastLongitud;
	}
	public void setEastLongitud(String eastLongitud) {
		this.eastLongitud = eastLongitud;
	}
	public String getNorthLatitud() {
		return northLatitud;
	}
	public void setNorthLatitud(String northLatitud) {
		this.northLatitud = northLatitud;
	}



}
