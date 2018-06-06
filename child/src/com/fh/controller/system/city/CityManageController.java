package com.fh.controller.system.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.city.CityManageService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="cityManage")
public class CityManageController extends BaseController{
	String menuUrl = "schoolManage/scholList.do"; // 菜单地址(权限用)
	
	@Resource(name="cityManageService")
	private  CityManageService cityService;
  
	
	/**
	 * 城市列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cityList")
	public ModelAndView scholList(Page page){
		
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> cityList =new ArrayList<>();
		page.setPd(pd);
		try{
			cityList= cityService.listAllCity(page);
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		mv.addObject("cityList", cityList);
		mv.setViewName("system/city/cityList");
		mv.addObject("pd", pd);
		return mv;
	}

	
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping(value = "/toEditAddView")
	public ModelAndView toEditAddView(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> provinceList = null;
		try{
			provinceList =cityService.getProvince();
			mv.addObject("provinceList", provinceList);
			mv.setViewName("system/city/cityAdd");
			mv.addObject("pd", pd);
		} catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	

	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		cityService.saveCity(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Map<String,Object> deleteAll() {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			String cityId = pd.getString("cityId");
			if (null != cityId && !"".equals(cityId)) {
				String ArraycityId[] = cityId.split(",");
				cityService.delete(ArraycityId);
				map.put("msg", "ok");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return map;
	}
	
}
