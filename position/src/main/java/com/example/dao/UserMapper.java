package com.example.dao;

import java.util.ArrayList;

import org.mapstruct.Mapper;

import com.example.entity.UserPositionInfo;

@Mapper
public interface UserMapper {
	
	//添加定位坐标
	public void addUserPosition(UserPositionInfo up);
	
	//查找历史坐标
	public ArrayList<String> getPosition(String openId);
	
	
}
