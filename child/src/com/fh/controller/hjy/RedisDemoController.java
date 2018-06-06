package com.fh.controller.hjy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.dao.RedisDao;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/** RedisDemo
 * @author FH Q313596790
 * 2016.5.8
 */
@Controller
@RequestMapping(value="/appRedisDemo")
public class RedisDemoController extends BaseController{
	
	@Resource(name = "redisDaoImpl")
	private RedisDao redisDaoImpl;
	
	/**
	 * 接口 http://127.0.0.1:8080/child/appRedisDemo/redisDemo.webapp
	 * 
	 */
	@RequestMapping(value="/redisDemo")
	@ResponseBody
	public Object redis(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
		
//		redisDaoImpl.delete("fh0");											//删除
//		redisDaoImpl.delete("fh");											//删除
//		redisDaoImpl.delete("fh1");											//删除
//		redisDaoImpl.delete("fh2");											//删除
		
		System.out.println(redisDaoImpl.addString("fh0","opopopo"));		//存储字符串
		System.out.println("获取字符串:"+redisDaoImpl.getString("fh0"));			//获取字符串
		
		result += "获取字符串:"+redisDaoImpl.getString("fh0")+",";
		
		Map<String, String> jmap = new HashMap<String, String>();
    	jmap.put("name", "fhadmin");
    	jmap.put("age", "22");
    	jmap.put("qq", "313596790");
		System.out.println(redisDaoImpl.addMap("fh", jmap));				//存储Map
		System.out.println("获取Map:"+redisDaoImpl.getMap("fh"));			//获取Map
		
		result += "获取Map:"+redisDaoImpl.getMap("fh")+",";
		
		List<String> list = new ArrayList<String>();
		list.add("ssss");
		list.add("bbbb");
		list.add("cccc");
		System.out.println(redisDaoImpl.addList("fh1", list));									//存储List
		System.out.println("获取List:"+redisDaoImpl.getList("fh1"));			//获取List		
		
		result += "获取List:"+redisDaoImpl.getList("fh1")+",";
		
		Set<String> set = new HashSet<String>();
		set.add("wwww");
		set.add("eeee");
		set.add("rrrr");
		System.out.println(redisDaoImpl.addSet("fh2", set));									//存储Set
		System.out.println("获取Set:"+redisDaoImpl.getSet("fh2"));			//获取Set
		
		result += "获取Set:"+redisDaoImpl.getSet("fh2")+",";
		
		map.put("result", result);
		
		return AppUtil.returnObject(new PageData(), map);
	}

}
