package com.example.llk.controller.cityController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.llk.entity.City;
import com.example.llk.service.cityService.CityService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	private CityService city;
	
	@ApiResponses({
     	@ApiResponse(code = 404, message = "服务器内部异常"),
		})
	@ApiOperation(value="第二个")
	@ApiImplicitParam(name = "name",value="需要输入的内容",required =false,paramType ="path",dataType= "String")
	@RequestMapping("/info/{name}")
	public City first(@PathVariable("name") String name) {
		return city.getCityInfo(name);
	}
}
