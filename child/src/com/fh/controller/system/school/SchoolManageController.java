package com.fh.controller.system.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.city.CityManageService;
import com.fh.service.system.school.SchoolManageService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "schoolManage")
public class SchoolManageController extends BaseController {
	String menuUrl = "schoolManage/scholList.do"; // 菜单地址(权限用)

	@Resource(name = "schoolManageService")
	private SchoolManageService schoolService;

	/**
	 * 學校列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/scholList")
	public ModelAndView scholList(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 获取登陆用户ID
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		List<PageData> scholList = new ArrayList<>();
		page.setPd(pd);
		if(pd.get("schoolName")!="" && pd.get("schoolName") !=null){
			  pd.put("schName", "%"+pd.get("schoolName")+"%");
		}
		try {
			scholList = schoolService.listAllSchool(page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		mv.addObject("scholList", scholList);
		mv.setViewName("system/school/schoolList");
		mv.addObject("pd", pd);
		return mv;
	}

	
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping(value = "/toSchAddView")
	public ModelAndView toEditAddView(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> TownList = null;
		try{
			TownList =schoolService.getTown(pd);
			mv.addObject("TownList", TownList);
			mv.setViewName("system/school/schAdd");
			mv.addObject("pd", pd);
		} catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 添加学校
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		schoolService.saveCity(pd);
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
			String schId = pd.getString("schId");
			if (null != schId && !"".equals(schId)) {
				String ArrayschId[] = schId.split(",");
				schoolService.delete(ArrayschId);
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
