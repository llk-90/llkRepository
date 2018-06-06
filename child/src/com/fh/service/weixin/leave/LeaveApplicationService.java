package com.fh.service.weixin.leave;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("leaveappService")
public class LeaveApplicationService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 保存申请结果
	 */
	public void leavaInsert(PageData pd) throws Exception{
		dao.save("LeaveApplicationMapper.save", pd);
	}
	
	/*
	 * 已经请了假的右上角标红
	 */
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveApplicationMapper.listTime", pd);
	}
	/*
	 * 根据openid读取学生信息
	 */
	public PageData findByOpenid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LeaveApplicationMapper.findByOpenId", pd);
	}
	/*
	 * 查找老师openid
	 */
	
	public PageData findTeacherByOpenid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LeaveApplicationMapper.findTeacherByOpenid", pd);
	}
	
	/**
	 * 已经请了假的不能再次请假
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getCheckList(PageData pd) throws Exception {	
		return (List<PageData>) dao.findForList("LeaveApplicationMapper.getCheckList", pd);
	}
		
}
