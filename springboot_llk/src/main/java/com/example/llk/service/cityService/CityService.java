package com.example.llk.service.cityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.llk.dao.CityMapper;
import com.example.llk.entity.City;

@Service
public class CityService {
	
	
	@Autowired
	private CityMapper cityMapper;
	
	public City getCityInfo(String name) {
		return cityMapper.getCityInfo(name);
	}

}
