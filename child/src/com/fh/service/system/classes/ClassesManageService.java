package com.fh.service.system.classes;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

@Service("classesManageService")
public class ClassesManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取城市信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllClass(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ClassesMapper.listAllClasseslistPage", page);

	}


	/**
	 *添加班級
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void saveClass(PageData pd) throws Exception {
		PageData p = new PageData();
		p = (PageData) dao.findForObject("ClassesMapper.getSchIdBuUserId", pd);
		int num= 0;
		int  gradeId = 0;
	    pd.put("gradeId", "2233%");
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) dao.findForList("ClassesMapper.gethoutaiAdd", pd);
			if(list.size()>0){
			num = Integer.parseInt(list.get(0));
			num++;
			gradeId = num;
			}else{
				num++;
				gradeId= Integer.parseInt("2233"+num);
			}
		p.put("gradeId", gradeId);
		p.put("gradeName", pd.get("gradeName"));
		gradeId++;
		p.put("classId", gradeId);
		p.put("className",pd.get("className"));
		gradeId++;
		p.put("classType", gradeId);
		p.put("modifyTime", DateUtil.getTime());
		dao.save("ClassesMapper.saveClass", p);
	}


	
	/**
	 * 刪除
	 * @param str
	 * @param obj
	 * @throws Exception
	 */
	public void delete(String[] str) throws Exception {
		 dao.delete("ClassesMapper.delete", str);
	}

	
}
