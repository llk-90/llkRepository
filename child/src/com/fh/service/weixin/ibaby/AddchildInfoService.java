package com.fh.service.weixin.ibaby;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;


@Service("addchildInfoService")
public class AddchildInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AddchildInfoMapper.list", pd);
	}
	
	public String findStuid(PageData pd) throws Exception {
		return (String) dao.findForObject("AddchildInfoMapper.findStuid", pd);
	}
	
	public String findNotCall(String pd) throws Exception {
		return (String) dao.findForObject("AddchildInfoMapper.findNotCall", pd);
	}
	
	public String findUseridByOpenid(String openid) throws Exception {
		Object userid = dao.findForObject("AddchildInfoMapper.findUseridByOpenid", openid);
		return (userid== null)? "":(String)userid;
	}
	
	public void addChild(PageData pd) throws Exception {
		dao.save("AddchildInfoMapper.addChild", pd);
	}

	public void updChild(PageData pd) throws Exception {
		dao.save("AddchildInfoMapper.updChild", pd);
	}
	public void updateChild(PageData pd) throws Exception {
		dao.save("AddchildInfoMapper.updateChild", pd);
	}
	
	
	
}

