package com.fh.controller.weixin.attendanceapp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.dao.DAO;
import com.fh.service.weixin.attendanceapp.AttendanceAppService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/attendanceApp")
public class AttendanceAppController extends BaseController {
	
	@Resource(name="attendanceAppService")
	private AttendanceAppService attendanceAppService; 
	
	@RequestMapping(value="/list")
	@ResponseBody
	public List<PageData> list() throws Exception{
		List<PageData> list = new ArrayList<>();
		PageData pd = this.getPageData();
		list = attendanceAppService.list(pd);
		return list;
	}
	
	@RequestMapping(value="/listAll")
	@ResponseBody
	public List<PageData> listAll() throws Exception{
		List<PageData> list = new ArrayList<PageData>();
		PageData pd = this.getPageData();
		list = attendanceAppService.listAll(pd);
		return list;
	}
}
