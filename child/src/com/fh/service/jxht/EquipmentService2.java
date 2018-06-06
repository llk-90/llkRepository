package com.fh.service.jxht;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("equipmentService2")
public class EquipmentService2 {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 *根据openId查询对应班级的所有学生list
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> selectClassStus(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("EquipMapper2.selectClassStus", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectStuByClass(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("EquipMapper2.selectStuByClass", pd);
	}

	@SuppressWarnings("unchecked")
	public PageData selectClassStus2(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("EquipMapper2.selectClassStus2", pd);
	}
	
	@SuppressWarnings("unchecked")
	public PageData selectTeaInfo(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("EquipMapper2.selectTeaInfo", pd);
	}
 
 
	@SuppressWarnings("unchecked")
	public List<PageData> slectTinfoByclass(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("EquipMapper2.slectTinfoByclass", pd);
	}
	
}
