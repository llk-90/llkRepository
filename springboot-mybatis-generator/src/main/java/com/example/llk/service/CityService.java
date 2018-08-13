package com.example.llk.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.llk.dao.CityMapper;
import com.example.llk.entity.City;
import com.example.llk.entity.CityExample;

@Service
public class CityService {

	@Resource
	private CityMapper cityMapper;
	
	public City findById(int id){
		CityExample cityExample = new CityExample();
		cityExample.createCriteria().andIdEqualTo(id).andIdIsNull();
		return cityMapper.selectByPrimaryKey(id);
	}
	
	
	
}
