package com.fh.service.system.personalinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("resetpswdService")
public class ResetPswdService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	* 检查密码是否正确
	*/
	public Map<String,String> selectPass(PageData pd)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		String oldpass = pd.getString("old_password"); //手输入旧密码
		if(oldpass!=""||oldpass!=null){
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String USERNAME = user.getUSERNAME();
			String passwd = new SimpleHash("SHA-1", USERNAME, oldpass).toString();	
			String oldpassword=user.getPASSWORD();     //获取旧密码
			if(oldpassword.equals(passwd)){
				errInfo="success";
			}else{
				errInfo="false";
			}
			map.put("result", errInfo);
		}
		return map;
	}
	
	/*
	* 修改密码
	*/
	public void editPass(PageData pd)throws Exception{
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String USERNAME=user.getUSERNAME();
		String newpass = pd.getString("new_password");
		String passwd = new SimpleHash("SHA-1", USERNAME, newpass).toString();
		pd.put("passwd",passwd);
		pd.put("USERNAME", USERNAME);
		dao.update("ResetPswdMapper.edit", pd);
	}

	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ResetPswdMapper.edit", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ResetPswdMapper.findById", pd);
	}	
}

