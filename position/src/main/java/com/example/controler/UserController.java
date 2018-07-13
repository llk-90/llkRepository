package com.example.controler;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * 上传客户信息
	 * @param keyId openId
	 * @param lnglat 坐标
	 * @param sign_count 被定位的次数
	 * @param notes 语音输入的内容
	 * @return
	 */
	@ApiResponses({
     	@ApiResponse(code = 404, message = "服务器内部异常"),
		})
	@ApiOperation(value="上传客户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "keyId",value="openId",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "lng",value="经度",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "lat",value="纬度",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "visits_count",value="标记次数",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "cus_name",value="客户姓名",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "address",value="客户地址",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "date",value="预约时间",required =true,paramType ="body",dataType= "String"),
			@ApiImplicitParam(name = "phone",value="联系方式",required =true,paramType ="body",dataType= "String")
	})
	@RequestMapping(value="/uploadPosition")
	@ResponseBody
	public Map<Object,Object> uploadPosition(@RequestBody UserPositionInfo user) {
	Map<Object,Object> map = new HashMap<>();
		user.setCreateTime(new Date());
		try {
			userService.addPosition(user);
			map.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "201");
		} 
		return map;
	}
	
	
	/**
	 * 
	 * @param keyId
	 * @return
	 */
	@ApiOperation(value="获取拜访客户记录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keyId",value="openId",required =true,paramType ="query",dataType= "String")})
	@RequestMapping(value="/queryPosition")
	@ResponseBody
	public Map<Object,Object> getPosition(String keyId) {
		Map<Object,Object> map = new HashMap<>();
		
		List<UserPositionInfo> positions = null;
		try {
			positions = userService.getPosition(keyId);
			map.put("code", "200");
			map.put("positions", positions);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "201");
		} 
		return map;
	}
	
	
	/**
	 * 
	 * @param lng
	 * @param lat
	 * @param notes
	 * @return
	 */
	@ApiOperation(value="编辑客户备注")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "lng",value="经度",required =true,paramType ="body",dataType= "String"),
		@ApiImplicitParam(name = "lat",value="纬度",required =true,paramType ="body",dataType= "String"),
		@ApiImplicitParam(name = "notes",value="备注",required =true,paramType ="body",dataType= "String"),
		@ApiImplicitParam(name = "visits_count",value="拜访次数",required =true,paramType ="body",dataType= "String")
	})
	@RequestMapping(value="/updateInfo")
	@ResponseBody
	public Map<Object,Object> updateUserInfo(@RequestBody UserPositionInfo user) {
		Map<Object,Object> map = new HashMap<>();
		try {
			userService.updateInfo(user);
			map.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "201");
		}; 
		return map;
	}
	
	
	
	
	/**
	 * 微信用，查询拜访信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fingBaifangYuyue")
	public Map<Object,Object> fingBaifangYuyue(int pageCount) throws Exception {
		
		int newPageCount = pageCount*3;
		Map<Object,Object> map = new HashMap<>();
		List<UserPositionInfo> cusInfoList= userService.fingBaifangYuyue(newPageCount);
		map.put("infoList", cusInfoList);
		map.put("errcode", 0);
		
		return map;
	}
	
	/**
	 * 获取客户具体信息
	 * @return 用户所有信息
	 * @throws IOException 
	 */
	@RequestMapping(value = "/findBaifangYuyueDetail")
	public UserPositionInfo findBaifangYuyueDetail(int id) throws Exception {
		UserPositionInfo userPositionInfo = userService.findBaifangYuyueDetail(id);
		return userPositionInfo;		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
