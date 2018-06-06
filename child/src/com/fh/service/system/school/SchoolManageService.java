package com.fh.service.system.school;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

@Service("schoolManageService")
public class SchoolManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取学校信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllSchool(Page page) throws Exception {
		return (List<PageData>) dao.findForList("SchoolMapper.listAllSchlistPage", page);

	}

	/**
	 * 获取区县
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getTown(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SchoolMapper.getTown", pd);
	}

	
	/**
	 *添加学校
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void saveCity(PageData pd) throws Exception {
		PageData p = new PageData();
		p= (PageData) dao.findForObject("SchoolMapper.getCityAndTown", pd);
		int num= 0;
		int  strSchId = 0;
	    pd.put("StrSchId", "999%");
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) dao.findForList("SchoolMapper.gethoutaiAddSchId", pd);
			if(list.size()>0){
			num = Integer.parseInt(list.get(0));
			num++;
			strSchId = num;
			}else{
				num++;
				strSchId= Integer.parseInt("111"+num);
			}
		  p.put("schoolId", strSchId);
		  p.put("schoolName", pd.get("schoolName"));
		p.put("modifyTime", DateUtil.getTime());
		 dao.save("SchoolMapper.saveSch", p);
	}
	
	
	
	/**
	 * 批量删除
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public Object delete(String[] array) throws Exception {
		return dao.delete("SchoolMapper.deleteSch", array);
	}
	
}
