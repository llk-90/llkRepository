package com.fh.entity;

public class Chart {
	private String create_date;   //时间 ，横轴
	private String equ_data;      //数据，纵轴
	private String equ_id;        //设备id
	private String info_id;       //参数id
	private String equ_parameter; //参数种类
	private String equ_threshold; //高阈值
	private String low_threshold; //低阈值
	private String equ_plan;      //预案
	private String cycle;         //记录周期
	private String area_kid;       //垮id
	private String[] maxs;        //高于预警的坐标
	
	
	
	
	public String getLow_threshold() {
		return low_threshold;
	}
	public void setLow_threshold(String low_threshold) {
		this.low_threshold = low_threshold;
	}
	public String[] getMaxs() {
		return maxs;
	}
	public void setMaxs(String[] maxs) {
		this.maxs = maxs;
	}
	public String getEqu_threshold() {
		return equ_threshold;
	}
	public void setEqu_threshold(String equ_threshold) {
		this.equ_threshold = equ_threshold;
	}
	public String getEqu_plan() {
		return equ_plan;
	}
	public void setEqu_plan(String equ_plan) {
		this.equ_plan = equ_plan;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getArea_kid() {
		return area_kid;
	}
	public void setArea_kid(String area_kid) {
		this.area_kid = area_kid;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getEqu_data() {
		return equ_data;
	}
	public void setEqu_data(String equ_data) {
		this.equ_data = equ_data;
	}
	public String getEqu_id() {
		return equ_id;
	}
	public void setEqu_id(String equ_id) {
		this.equ_id = equ_id;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	public String getEqu_parameter() {
		return equ_parameter;
	}
	public void setEqu_parameter(String equ_parameter) {
		this.equ_parameter = equ_parameter;
	}
	
	
}
