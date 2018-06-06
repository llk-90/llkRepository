package com.fh.service.system.city;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("cityManageService")
public class CityManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取城市信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllCity(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CityMapper.listAllCitylistPage", page);

	}

	/**
	 * 获取省份
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getProvince() throws Exception {
		return (List<PageData>) dao.findForList("CityMapper.getProvince", null);
	}

	/**
	 *添加城市
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object saveCity(PageData pd) throws Exception {
		return dao.save("CityMapper.saveCity", pd);
	}

	
	
	public Object delete(String[] array) throws Exception {
		return dao.delete("CityMapper.deleteCity", array);
	}
	
}
