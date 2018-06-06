package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.teacher.CourseService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/course")
public class CourseController extends BaseController {
	String menuUrl = "course/list.do"; // 菜单地址(权限用)
	
	@Resource(name="courseService")
	private CourseService courseService;

	/**
	 * 班级显示
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView List(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> reginoList = courseService.reginoList(page);     //区域信息
		List<PageData> schoolList = courseService.schoolList(page);		//学校信息
		List<PageData> jibuList = courseService.jibuList(page);		    //年级信息
		List<PageData> classList = courseService.classList(page);       //班级信息
		List<PageData> courseList = courseService.courseList(page);     //课程信息	
		mv.addObject("reginoList", reginoList);
		mv.addObject("schoolList", schoolList);
		mv.addObject("jibuList", jibuList);
		mv.addObject("classList", classList);
		mv.addObject("courseList", courseList);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/course_list");
		return mv;
	}
	
	/**
	 * 去修改课程时间
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String reginoType = new String();
		String schoolType = new String();
		String jibuType = new String();
		String classType = new String();

		if(pd.getString("reginoType").equals("")){
			reginoType = "全部";			
		}else{
			reginoType = java.net.URLDecoder.decode(pd.getString("reginoType"),"UTF-8");
		}
	
		if(pd.getString("schoolType").equals("")){			
			schoolType = "全部";
		}else{
			schoolType = java.net.URLDecoder.decode(pd.getString("schoolType"),"UTF-8");
		}
		
		if(pd.getString("jibuType").equals("")){				
			jibuType = "全部";
		}else{
			jibuType = java.net.URLDecoder.decode(pd.getString("jibuType"),"UTF-8");
		}
		
		if(pd.getString("classType").equals("")){		
			classType = "全部";
		}else{
			classType = java.net.URLDecoder.decode(pd.getString("classType"),"UTF-8");
		}
		String donotcall = pd.getString("ibaby_donot_call");
		pd.put("reginoType", reginoType);
		pd.put("schoolType", schoolType);
		pd.put("jibuType", jibuType);
		pd.put("classType", classType);
		pd.put("donotcall", donotcall);
		PageData course = courseService.findByCouId(pd);
		mv.addObject("course", course);
		mv.addObject("reginoType", reginoType);
		mv.addObject("schoolType", schoolType);
		mv.addObject("jibuType", jibuType);
		mv.addObject("classType", classType);
		mv.addObject("donotcall", donotcall);
		mv.setViewName("teacher/courseEdit");
		return mv;
	}
	
	/**
	 * 编辑课程时间信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		courseService.editCourse(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 新增课程时间信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		logBefore(logger, "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Page page = new Page();
		page.setPd(pd);
		String reginoType = java.net.URLDecoder.decode(pd.getString("reginoType"),"UTF-8");
		String schoolType = java.net.URLDecoder.decode(pd.getString("schoolType"),"UTF-8");
		String jibuType = java.net.URLDecoder.decode(pd.getString("jibuType"),"UTF-8");
		String classType = java.net.URLDecoder.decode(pd.getString("classType"),"UTF-8");
		String donotcall = pd.getString("ibaby_donot_call");
		pd.put("reginoType", reginoType);
		pd.put("schoolType", schoolType);
		pd.put("jibuType", jibuType);
		pd.put("classType", classType);
		pd.put("donotcall", donotcall);
		courseService.addCourse(pd);
		List<PageData> courseList = courseService.courseList(page);     //新增课程信息	
		List<PageData> reginoList = courseService.reginoList(page);     //区域信息
		List<PageData> schoolList = courseService.schoolList(page);		//学校信息
		List<PageData> jibuList = courseService.jibuList(page);		    //年级信息
		List<PageData> classList = courseService.classList(page);       //班级信息
		mv.addObject("courseList", courseList);
		mv.addObject("reginoList", reginoList);
		mv.addObject("schoolList", schoolList);
		mv.addObject("jibuList", jibuList);
		mv.addObject("classList", classList);	
		mv.addObject("pd", pd);
		mv.setViewName("teacher/course_list");
		return mv;
	}
	
}
