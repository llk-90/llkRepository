package com.fh.controller.weixiplus.teacher;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.teacher.TeacherBingService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@Controller
@RequestMapping(value = "/teacherBing")
public class TeacherBingController extends BaseController {

	@Resource(name = "teacherBingService")
	private TeacherBingService teacherBingService;
	
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;


	/**
	 * 根据进行插入操作
	 * 
	 * @param 页面信息
	 * @return 是否成功
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveTeacherBingInfo")
	@ResponseBody
	public PageData doBing() throws Exception {
		PageData pageData = this.getPageData();
		PageData pagedata = new PageData();
		PageData pdCheck = new PageData();
		pdCheck.put(StringDefault.errorcode, errorMsg.Success(0));
		pagedata = teacherBingService.saveTeacherBingInfo(pageData);
		if (pdCheck.equals(pagedata)) {
			pagedata = teacherBingService.saveQryWeiBing(pageData);
		}
		return pagedata;
	}
}
