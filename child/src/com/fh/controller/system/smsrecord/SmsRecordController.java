package com.fh.controller.system.smsrecord;

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
import com.fh.service.system.smsrecord.SmsRecordService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;

/**
 * 类名称：SmsRecordController 创建人：FH 创建时间：2016-02-24
 */
@Controller
@RequestMapping(value = "/smsRecord")
public class SmsRecordController extends BaseController {

	String menuUrl = "smsRecord/list.do"; // 菜单地址(权限用)
	@Resource(name = "SmsRecordService")
	private SmsRecordService smsRecordService;
	private PageData page;

	/**
	 * 列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "列表smsRecord");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = smsRecordService.list(page);
		mv.setViewName("system/smsrecord/smsRecord_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 余额查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/balCheck")
	public ModelAndView balCheck() throws Exception {
		logBefore(logger, "余额查询");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String ye = SmsUtil.smsQueryBalance();
		pd.put("ye", ye);
		mv.setViewName("system/smsrecord/smsRecord_bal");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 详情
	 * 
	 * @return
	 */

	@RequestMapping(value = "/checkInfo")
	public ModelAndView checkInfo() {
		logBefore(logger, "去checkInfo页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> varList = smsRecordService.findById(pd); // 根据ID读取
			mv.setViewName("system/smsrecord/sysRecord_info");
			mv.addObject("pd", pd);
			mv.addObject("varList", varList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				smsRecordService.deleteAll(ArrayDATA_IDS);
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

}
