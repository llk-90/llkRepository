package com.fh.service.weixin.personal_center;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("wxUserService")
public class WxUserService {
	
	 @Resource(name = "daoSupport")
	 private DaoSupport dao;
	 
	 /**
     * 查询密码
     */
    public PageData findPwd(PageData pd) throws Exception {
        return (PageData) dao.findForObject("WxUserMapper.findPwd", pd);
    }

    /**
     * 修改密码
     */

    public void updatePwd(PageData pd) throws Exception {
        dao.findForObject("WxUserMapper.updatePwd", pd);
    }
    
    /**
     * 查找家长信息
     */
    public PageData findInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WxUserMapper.findUserInfo", pd);
	}
    /**
     * 查询未读留言数量
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findInfoCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WxUserMapper.findInfoCount", pd);
	}
    
    /**
     * 查找管理员电话
     */
    public PageData findAd() throws Exception {
		return (PageData) dao.findForObject("WxUserMapper.findAd", null);
	}
    

}
