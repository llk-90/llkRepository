package com.fh.service.weixin.grades;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("gradeslistService")
public class GradesListService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 留言列表显示
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getgradeslist(PageData pd) throws Exception {	
		return (List<PageData>) dao.findForList("GradesListMapper.detailist", pd);
	}
	
	/**
	 * 成绩排名查询
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> gettotallist(PageData pd) throws Exception {	
		return (List<PageData>) dao.findForList("GradesListMapper.totallist", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getpicilist(PageData pd) throws Exception {	
		return (List<PageData>) dao.findForList("GradesListMapper.picillist", pd);
	}
	
		
	@SuppressWarnings("unchecked")
	public List<PageData> getselectbox(PageData pd)  throws Exception{
		List<PageData> getselectbox = new ArrayList<PageData>() ;
		getselectbox = (List<PageData>)dao.findForList("GradesListMapper.getselectbox", pd);
		return getselectbox;
	}
	
	/**
	 * 单科成绩
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> singleList(PageData pd) throws Exception {
		List<PageData> singleList = new ArrayList<PageData>();
		singleList = (List<PageData>) dao.findForList("GradesListMapper.singleList", pd);
		return singleList;
	}

	/**
	 * 根据家长查询学生班级
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData stuClassName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GradesListMapper.stuClassName", pd);
	}
	/**
	 * 根据家长查询学生班级
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData stuSubjectNotEmpty(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GradesListMapper.stuSubjectNotEmpty", pd);
	}
}
