package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;;


@Service("courseService")
public class CourseService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 区域信息
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> reginoList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CourseMapper.reginoList", page);
	}
	
	/*
	* 学校信息
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> schoolList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CourseMapper.schoolList", page);
	}
	
	/*
	* 年级信息
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> jibuList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CourseMapper.jibuList", page);
	}
	
	/*
	* 班级信息
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> classList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CourseMapper.classList", page);
	}
	
	/*
	* 课程信息
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> courseList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CourseMapper.courseList", page);
	}
	
	/**
	 * 根据节次查询课程时间
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCouId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CourseMapper.findByCouId", pd);
	}
	
	/**
	 * 更新课程时间信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editCourse(PageData pd) throws Exception {
		dao.update("CourseMapper.editCourse", pd);
	}
	
	/**
	 * 新增课程时间信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void addCourse(PageData pd) throws Exception {
		dao.update("CourseMapper.addCourse", pd);
	}
}