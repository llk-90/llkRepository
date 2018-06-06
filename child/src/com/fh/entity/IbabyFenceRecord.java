package com.fh.entity;

import java.util.Date;

public class IbabyFenceRecord {
	//学生ID
	private String studentId;
	//记录数
	private int recordCount;
	//记录日期
	private Date  recordDate;

	public String getStudentId() {
		return studentId;   
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
}
