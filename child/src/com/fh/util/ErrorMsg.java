package com.fh.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;

@SuppressWarnings("serial")
@Repository("errorMsg")
public class ErrorMsg extends PageData {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public Integer errorCode;
	public String errorMsg;
	
	public Object Success(Integer errCode) throws Exception {
		
		return dao.findForObject("ErrorMsg.getErrMessageByErrCode", errCode);
	}
	

}
