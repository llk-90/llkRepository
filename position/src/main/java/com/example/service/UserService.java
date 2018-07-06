package com.example.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserMapper;
import com.example.entity.UserPositionInfo;



@Service
public class UserService {
	
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 添加定位坐标
	 * @param up
	 */
	public void addPosition(UserPositionInfo up) {
		userMapper.addUserPosition(up);
	}
	
	/**
	 * 查找历史坐标
	 * @param openId
	 * @return
	 */
	public ArrayList<String> getPosition(String openId) {
		return  userMapper.getPosition(openId);
	}
	
	
	
	
	
 
}
