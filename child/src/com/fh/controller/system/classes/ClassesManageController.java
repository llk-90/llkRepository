package com.fh.controller.system.classes;

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
import com.fh.service.system.classes.ClassesManageService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "classesManage")
public class ClassesManageController extends BaseController {
	String menuUrl = "classesManage/classesList.do"; // 菜单地址(权限用)

	@Resource(name = "classesManageService")
	private ClassesManageService classService;

	/**
	 * 城市列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classesList")
	public ModelAndView scholList(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> classList = new ArrayList<>();
		page.setPd(pd);
		// 获取登陆用户ID
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		try {
			classList = classService.listAllClass(page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		mv.addObject("classList", classList);
		mv.setViewName("system/classes/classList");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toclassAddView")
	public ModelAndView toEditAddView() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 获取登陆用户ID
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		mv.setViewName("system/classes/classAdd");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		classService.saveClass(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")

	@ResponseBody
	public Map<String, Object> deleteAll() {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			String cId = pd.getString("cId");
			if (null != cId && !"".equals(cId)) {
				String ArraycId[] = cId.split(",");
				classService.delete(ArraycId);
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
