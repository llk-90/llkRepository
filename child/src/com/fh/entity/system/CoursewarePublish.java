package com.fh.entity.system;

/**
 * 
 * 类名称：CoursewarePublish.java 类描述：
 * 
 * @author 作者单位： 联系方式： 创建时间：2016年7月27日
 * @version 1.0
 */
public class CoursewarePublish {
	private int c_type;
	private int c_subject;
	private int c_grade;
	private long c_commodity_id;
	private String c_name;
	private String c_detail;

	public CoursewarePublish() {

	}
	public CoursewarePublish(int c_type, int c_subject, int c_grade, long c_commodity_id, String c_name,
			String c_detail) {
		this.c_type = c_type;
		this.c_subject = c_subject;
		this.c_grade = c_grade;
		this.c_commodity_id = c_commodity_id;
		this.c_name = c_name;
		this.c_detail = c_detail;
	}

	public int getC_type() {
		return c_type;
	}

	public void setC_type(int c_type) {
		this.c_type = c_type;
	}

	public int getC_subject() {
		return c_subject;
	}

	public void setC_subject(int c_subject) {
		this.c_subject = c_subject;
	}

	public int getC_grade() {
		return c_grade;
	}

	public void setC_grade(int c_grade) {
		this.c_grade = c_grade;
	}

	public long getC_commodity_id() {
		return c_commodity_id;
	}

	public void setC_commodity_id(long c_commodity_id) {
		this.c_commodity_id = c_commodity_id;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_detail() {
		return c_detail;
	}

	public void setC_detail(String c_detail) {
		this.c_detail = c_detail;
	}

}
