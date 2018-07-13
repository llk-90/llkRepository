package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.entity.UserPositionInfo;

@Mapper
public interface UserMapper {

	// 添加定位坐标
	public void addUserInfo(UserPositionInfo up);

	// 查找历史坐标
	public List<UserPositionInfo> getPosition(String openId);

	// 编辑
	public void updateInfo(UserPositionInfo up);

	// 查询拜访信息
	public List<UserPositionInfo> fingBaifangYuyue(int pageCount);

	// 查询客户具体信息
	public UserPositionInfo findBaifangYuyueDetail(int id);

}
