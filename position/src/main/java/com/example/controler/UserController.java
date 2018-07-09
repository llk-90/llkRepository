package com.example.controler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserPositionInfo;
import com.example.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/userPosition")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 上传定位
	 * @param keyId openId
	 * @param lnglat 坐标
	 * @param sign_count 被定位的次数
	 * @param notes 语音输入的内容
	 * @return
	 */
	@ApiResponses({
     	@ApiResponse(code = 404, message = "服务器内部异常"),
		})
	@ApiOperation(value="上传定位")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "keyId",value="openId",required =true,paramType ="query",dataType= "String"),
			@ApiImplicitParam(name = "lnglat",value="坐标",required =true,paramType ="query",dataType= "String"),
			@ApiImplicitParam(name = "sign_count",value="标记次数",required =true,paramType ="query",dataType= "String"),
			@ApiImplicitParam(name = "notes",value="语音内容",required =false,paramType ="query",dataType= "String")})
	@RequestMapping(value="/uploadPosition")
	@ResponseBody
	public Map<Object,Object> uploadPosition(String keyId,String lnglat,String sign_count,String notes,HttpServletRequest res) {
		String url = res.getRequestURI();
		String path = res.getContextPath();
		System.out.println(url);
		System.out.println(path);
		Map<Object,Object> map = new HashMap<>();
		UserPositionInfo user = new UserPositionInfo();
		user.setKeyId(keyId);user.setLnglat(lnglat);user.setNotes(notes);user.setSign_count(sign_count);
		userService.addPosition(user); 
		map.put("code", "200");
		return map;
	}
	
	
	/**
	 * 
	 * @param keyId
	 * @return
	 */
	@ApiOperation(value="上传定位")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keyId",value="openId",required =true,paramType ="query",dataType= "String")})
	@RequestMapping(value="/queryPosition")
	@ResponseBody
	public Map<Object,Object> getPosition(String keyId) {
		Map<Object,Object> map = new HashMap<>();
		ArrayList<String> positions= userService.getPosition(keyId); 
		map.put("positions", positions);
		map.put("code", "200");
		return map;
	}
	
}
