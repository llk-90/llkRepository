package com.fh.controller.dataanalysis;

import org.springframework.stereotype.Controller; //1
import org.springframework.web.bind.annotation.RequestMapping; //1
import org.springframework.web.servlet.ModelAndView;  //1

import com.fh.controller.base.BaseController;  //1

@Controller
@RequestMapping(value = "/attendanceController")
public class AttendanceAnalysisController extends BaseController {
	String menuUrl = "attendanceController/list.do"; // 菜单地址(权限用)

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		logBefore(logger, "考勤数据分析");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("dataanalysis/attendanceAnalysis");
		return mv;
	}
	
}
