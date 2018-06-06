package com.fh.controller.system.personalinfo;

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
import com.fh.service.system.personalinfo.UserEditService;
import com.fh.util.Const;
import com.fh.util.PageData;

/** 
 * 类名称：ResetPswdComtroller
 * 创建人：
 * 创建时间：2016年6月16日
 * @version
 */
@Controller
@RequestMapping(value="/useredit")
public class UserEditController extends BaseController{
String menuUrl = "useredit/list.do"; //菜单地址(权限用)
@Resource(name="usereditService")
private UserEditService usereditService;

	/**
	 * 编辑信息画面显示
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表useredit");
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String USER_ID=user.getUSER_ID();
		PageData pd = new PageData();
		pd.put("USER_ID", USER_ID);
		try {
			pd = usereditService.findById(pd);
			mv.setViewName("system/persionalinfo/userinfo_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 编辑信息
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改useredit");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		usereditService.editUserinfo(pd);
		mv.addObject("msg","success");
		mv.setViewName("forward:/useredit/list.do");
		return mv;
	}
}

