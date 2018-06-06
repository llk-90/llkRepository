package com.example.llk.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/first")
public class UserController {
	@ApiResponses({
     	@ApiResponse(code = 404, message = "服务器内部异常"),
		})
	@ApiOperation(value="第一个")
	@ApiImplicitParam(name = "name",value="需要输入的内容",required =false,paramType ="path",dataType= "String")
	@RequestMapping("/hello/{name}")
	public String first(@PathVariable("name") String name) {
		return name;
	}
}
