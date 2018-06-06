package com.fh.entity;

/**
 * 
 * @author Alex
 * @reeditor Barry
 * 
 */
import net.sf.json.JSONObject;

public class Comment {
	private Integer id;
	private Integer mid;
	private Integer uid;
	private Integer reply_id;
	private String content;
	private String create_time;
	private Integer is_delete;
	private Integer ClassId;
	private Integer usertype;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;

	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getReply_id() {
		return reply_id;
	}

	public void setReply_id(Integer reply_id) {
		this.reply_id = reply_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_time(){
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}

	/**
	 * Generate a new key.
	 * 
	 * @return String
	 */
	/*
	 * public String GenerateNewKey() { this.key = this.mid + "__" +
	 * UuidUtil.get32UUID() + "__" + System.currentTimeMillis(); return key; }
	 */

	/**
	 * Turn the entity as json.
	 * 
	 * @return JSONObject
	 */
	public JSONObject parseJson(Comment comment) {
		return JSONObject.fromObject(comment);
	}

	public Integer getClassId() {
		return ClassId;
	}

	public void setClassId(Integer classId) {
		ClassId = classId;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
}
