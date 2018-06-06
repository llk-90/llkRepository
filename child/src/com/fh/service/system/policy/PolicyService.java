package com.fh.service.system.policy;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("policyService")
public class PolicyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(ModelAndView mv, PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("PolicyMapper.save", pd);
	}
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PolicyMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void update(PageData pd)throws Exception{
		dao.update("PolicyMapper.update", pd);
	}
	/*
	* 修改状态
	*/
	public void updateState(PageData pd)throws Exception{
		dao.update("PolicyMapper.updateState", pd);
	}
	/*
	* 修改类别
	*/
	public void updateType(PageData pd)throws Exception{
		dao.update("PolicyMapper.updateType", pd);
	}
	/*
	 * 根据ID查一条数据
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PolicyMapper.findById", pd);
	}
	/*
	 * 查公示发布条数
	 */
	public PageData findstate(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PolicyMapper.findstate", pd);
	}
	/*
	*列表
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PolicyMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PolicyMapper.listAll", pd);
	}
	
	/*
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PolicyMapper.deleteAll", ArrayDATA_IDS);
	}	
}

