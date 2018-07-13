package com.example.service;

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
	 * 添加客户信息
	 * @param up
	 */
	public void addPosition(UserPositionInfo up) throws Exception{
			userMapper.addUserInfo(up);
	}
	
	/**
	 * 拜访历史
	 * @param openId
	 * @return
	 */
	public List<UserPositionInfo> getPosition(String openId) throws Exception{
		return  userMapper.getPosition(openId);
	}
	
	
	/**
	 * 编辑客户信息
	 * @param openId
	 */
	public void updateInfo(UserPositionInfo up) throws Exception{
		  userMapper.updateInfo(up);
	}
	
	
	
	/**
	 * 查询拜访信息
	 * @param openId
	 * @return
	 */
	public List<UserPositionInfo> fingBaifangYuyue(int pageCount){
		return  userMapper.fingBaifangYuyue(pageCount);
	}
	
	/**
	 * 查询客户具体信息
	 * @param openId
	 * @return
	 */
	public UserPositionInfo findBaifangYuyueDetail(int id) {
		return userMapper.findBaifangYuyueDetail(id);
	}
	
	
	
	
}
