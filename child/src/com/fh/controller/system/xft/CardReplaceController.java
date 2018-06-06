package com.fh.controller.system.xft;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.xft.CardReplaceService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/replaceCard")
public class CardReplaceController extends BaseController {
	String menuUrl = "replaceCard/list.do"; // 菜单地址(权限用)
	
	@Resource(name="cardReplaceService")
	private CardReplaceService cardReplaceService;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listReplaceCard(Page page) throws Exception {		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> cardReplaceList = cardReplaceService.cardReplaceList(page);
		mv.setViewName("system/xft/replaceCard_list");
		mv.addObject("cardReplaceList", cardReplaceList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去修改补卡信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData cardReplace = cardReplaceService.findByStuId(pd);
		mv.addObject("cardReplace", cardReplace);
		mv.setViewName("system/xft/cardReplaceEdit");
		return mv;
	}	
	
	/**
	 * 编辑补卡信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		cardReplaceService.editE(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
