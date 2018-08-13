package com.example.llk.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.llk.entity.City;
import com.example.llk.service.CityService;

@Controller
@RequestMapping(value="/city")
public class CityController {
	
	@Resource
	private CityService cityService;
	
	@RequestMapping(value="/get")
	@ResponseBody
	public City get(int id){
		City  city = cityService.findById(id);
		return city;
	}

}
