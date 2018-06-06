package com.fh.controller.member;

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
import com.fh.service.member.MemberAuthService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping(value = "/member_auth")
public class MemberAuthController extends BaseController {

	String menuUrl = "member_auth/list.do"; // 菜单地址(权限用)

	@Resource(name = "memberAuthService")
	private MemberAuthService memberAuthService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView authList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> authList = memberAuthService.authList(page);
		mv.setViewName("member/member_auth");
		mv.addObject("authList", authList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 打开新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("member/add_member_auth");
		return mv;
	}
	
	/**
	 * check权限名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkName")
	@ResponseBody
	public Object checkPhone() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		int count = memberAuthService.findByName(pd);
		if(count > 0){
			map.put("msg", "fail");
		}else{
			map.put("msg", "success");
		}
		return map;
	}

	/**
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAuth")
	public ModelAndView add() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int ct = Integer.parseInt(pd.getString("c_time"))*24;
		pd.put("c_time", ct);
		memberAuthService.add(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 打开编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData auth = memberAuthService.getAuthById(id);
		mv.addObject("auth", auth);
		mv.setViewName("member/edit_member_auth");
		return mv;
	}

	/**
	 * 更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAuth")
	public ModelAndView update() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int ct = Integer.parseInt(pd.getString("c_time"))*24;
		pd.put("c_time", ct);
		memberAuthService.update(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 是否可以删除
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/isDelete")
	@ResponseBody
	public Object isdelete(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, String> map = new HashMap<String,String>();
		int conut = memberAuthService.findIsPublish(id);
		if(conut > 0){
			map.put("msg", "fail");
			PageData pd = memberAuthService.getNameById(id);
			map.put("name", pd.getString("c_name"));	
		}else{
			map.put("msg", "success");
		}
		return map;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		memberAuthService.delete(id);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	

	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			String DATA_NAMES = pd.getString("DATA_NAMES");
			String names = "";
			List<String> nameList = new ArrayList<String>();
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				String ArrayDATA_NAMES[] = DATA_NAMES.split(",");
				for(int i=0; i< ArrayDATA_IDS.length;i++){
					if (memberAuthService.findIsPublish(ArrayDATA_IDS[i]) > 0) {
						nameList.add(ArrayDATA_NAMES[i]);
					}
				}
				if (nameList.size() == 0) {
					memberAuthService.deleteAll(ArrayDATA_IDS);
				} else {
					map.put("msg", "fail");
					map.put("nameList", nameList);
					return AppUtil.returnObject(pd, map);
				}
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

}
