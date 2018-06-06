package com.fh.entity;

import java.util.List;

import com.fh.util.UuidUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author Alex
 * @reeditor Barry
 *
 */
public class Moment {
	private String key;
	private Integer id;
	private Integer uid;
	private String title;
	private String content;
	private String picture;
	private String upvote_id;
	private String create_time;
	private Integer is_delete;
	private List<Comment> list;
	private Integer ClassId;
	private Integer usertype;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCreate_time() {
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
	public void GenerateNewKey() {
		this.key = this.ClassId + "__" + System.currentTimeMillis() + "__" + UuidUtil.get32UUID();
	}

	/**
	 * Turn the entity as json.
	 * 
	 * @return JSONObject
	 */
	public String parseJson() {
		return JSONObject.fromObject(this).toString();
	}

	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

	public Integer getClassId() {
		return ClassId;
	}

	public void setClassId(Integer classId) {
		ClassId = classId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpvote_id() {
		return upvote_id;
	}

	public void setUpvote_id(String upvote_id) {
		this.upvote_id = upvote_id;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "Moment [key=" + key + ", id=" + id + ", uid=" + uid + ", title=" + title + ", content=" + content
				+ ", picture=" + picture + ", upvote_id=" + upvote_id + ", create_time=" + create_time + ", is_delete="
				+ is_delete + ", list=" + list + ", ClassId=" + ClassId + ", usertype=" + usertype + "]";
	}
}
