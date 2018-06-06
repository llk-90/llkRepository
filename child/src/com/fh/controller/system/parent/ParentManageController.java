package com.fh.controller.system.parent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.parent.ParentService;
import com.fh.service.system.xft.UserRecAndConHistoryService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;


@RestController
@RequestMapping("/parentManage")
public class ParentManageController extends BaseController{
	
	@Resource(name = "parentService")
	private ParentService parentService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView getParentList(Page page){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		List<PageData> pList = new ArrayList<>();
		page.setPd(pd);
		try {
			pList= parentService.getParentInfo(page);
			mv.setViewName("system/parent/parentManageList");
		    mv.addObject("pList", pList);
		    mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取数据异常！！！");
		}
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/parEditView")
	public ModelAndView parEditView() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			pd= parentService.getParInfoById(pd);
			mv.setViewName("system/parent/parentEdit");
			mv.addObject("msg", "edits");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 修改绑定信息
	 */
	@RequestMapping(value = "/edits")
	public ModelAndView pareEdit(){
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			parentService.updatePar(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 校验手机号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkStuName")
	@ResponseBody
	public Object checkStuName() throws Exception {
		PageData pd = this.getPageData();
		Map<String, String> map = parentService.checkStuName(pd);
		return AppUtil.returnObject(new PageData(), map);
	}
}
