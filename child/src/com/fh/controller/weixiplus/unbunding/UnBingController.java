package com.fh.controller.weixiplus.unbunding;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.unbunding.UnBindingTeacherService;
import com.fh.service.weixinplus.unbunding.UnBingService;
import com.fh.util.PageData;

@RestController
@RequestMapping(value = "/weixinplusUnBing")
public class UnBingController extends BaseController {

	@Resource(name = "UnBingService")
	private UnBingService unBingService;

	@Resource(name = "UnBindingTeacherService")
	private UnBindingTeacherService unBindingTeacherService;

	/**
	 * 解绑
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/unbingUserInfo")
	public PageData doBingSelect() throws Exception {
		PageData pd = this.getPageData();
		PageData pageData = new PageData();
		pageData = unBindingTeacherService.unBingTeaInfo(pd);
		if (null != pageData || pageData.size() == 0 || pageData.getString("code").equals("0")) {
			pageData = unBingService.unBingUserInfo(pd);
		}
		if (null != pageData || pageData.size() == 0 || pageData.getString("code").equals("0")) {
			getRequest().getSession().removeAttribute("sessionUser");
		}
		return pageData;
	}
}
