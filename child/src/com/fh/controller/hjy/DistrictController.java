package com.fh.controller.hjy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.hjy.DistrictService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 类名称：DistrictController 
 */
@Controller
@RequestMapping(value = "/districtController")
public class DistrictController extends BaseController {
	String menuUrl = "districtController/list.do"; // 菜单地址(权限用)
	@Resource(name = "districtService")
	private DistrictService districtService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/listdata")
	@ResponseBody
	public List<PageData> listdata() throws Exception {
		logBefore(logger, "districtController");
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		try {
			String z_id = districtService.getZoneIdByUserId(pd);
			pd.put("z_id", z_id);
			varList = districtService.list(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return varList;
	}

//	/**
//	 * 农场设置
//	 */
//	@RequestMapping(value = "/listQuYu")
//	@ResponseBody
//	public List<PageData> listQuYu() throws Exception {
//		logBefore(logger, "农场设置districtController");
//		List<PageData> varList = new ArrayList<PageData>();
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			varList = districtService.quyulist(pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return varList;
//	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/column")
	@ResponseBody
	public Object column(Page page, HttpServletResponse response) throws Exception {
		logBefore(logger, "districtController");
		PageData pd = this.getPageData();
		// 上报限制check
		Map<String, Object> map = districtService.column(pd);
		// 返回check结果
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * check学号是否重复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasZname")
	@ResponseBody
	public Object checkZname() {
		PageData pd = this.getPageData();
		try {
			pd.put("hasStuNo", districtService.checkZname(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), pd);
	}
	
}
