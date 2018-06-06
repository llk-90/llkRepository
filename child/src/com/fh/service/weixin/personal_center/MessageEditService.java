package com.fh.service.weixin.personal_center;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;



@Service("messageeditService")
public class MessageEditService {
	@Resource(name= "daoSupport")
	private DaoSupport dao;

	/*
	 * 学生和家长信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MessageEditMapper.findInfoList", pd);
	}
	
	/**
     * 更新学生信息
     */
    public void updateInfo(PageData pd) throws Exception {
        dao.findForObject("MessageEditMapper.updateStuInfo", pd);
    }
    
    /**
     * 更新家长手机号
     */
    public void updateParentInfo(PageData pd) throws Exception {
        dao.findForObject("MessageEditMapper.updateParentInfo", pd);
    }
    
    /**
     * 老师信息列表
     */
    @SuppressWarnings("unchecked")
	public List<PageData> infoList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MessageEditMapper.findTeachInfo", pd);
	}
    
    /**
     * 更新老师信息
     */
    public void updateTeachInfo(PageData pd) throws Exception {
        dao.findForObject("MessageEditMapper.updateTeachInfo", pd);
    }
    
    /**
     * 更新老师信息
     */
    public void updateTeacher(PageData pd) throws Exception {
        dao.findForObject("MessageEditMapper.updateTeacher", pd);
    }
    
    
}
