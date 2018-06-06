package com.fh.controller.weixiplus.regist;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.regist.RegistService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@RestController
@RequestMapping(value = "/weixiplusRegist")
public class ResistController extends BaseController {

	@Resource(name = "RegistService")
	private RegistService registService;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	/**
	 * 获取补卡历史记录
	 * 
	 * @return 返回用户信息列表
	 **/
	@RequestMapping(value = "registList")
	public PageData getRegistHistory() throws Exception {

		PageData pageData = new PageData();
		try {

			return registService.getRegistHistory(this.getPageData());

		} catch (Exception e) {
			// TODO: handle exception
			pageData.put(StringDefault.errorcode, errorMsg.Success(4001));
			return pageData;

		}

	}

	/**
	 * 获取提交补卡信息
	 * 
	 * @return 返回是否成功
	 **/
	@RequestMapping(value = "/submit")
	public PageData submit(@RequestParam CommonsMultipartFile[] picture, String IcNo, String openId) throws Exception {
		PageData pd = this.getPageData();
		try {
			return registService.submitCardInfo(pd, null);
		} catch (Exception e) {
			e.printStackTrace();
			PageData res = new PageData();
			res.put(StringDefault.errorcode, errorMsg.Success(0));
			return res;
		}

	}

}
