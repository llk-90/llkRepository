package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.MicroCampusBannerService;
import com.fh.util.Const;
import com.fh.util.PageData;

@RestController
@RequestMapping(value = "/microCampusBanner")
public class MicroCampusBannerController extends BaseController {
	String menuUrl = "microCampusBanner/list.do"; // 菜单地址(权限用)
	
	@Resource(name="microCampusBannerService")
	private MicroCampusBannerService microCampusBannerService;
	
	/**
	 * 展示微校园轮播图列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView List(Page page) throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=microCampusBannerService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		page.setPd(pd);
		List<PageData> bannerList=microCampusBannerService.bannerList(page);
		mv.setViewName("teacher/microCampusBanner_list");
		mv.addObject("bannerList", bannerList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 添加轮播图
	 * @param picture
	 * @param moveUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addBanner")
	@ResponseBody
	public ModelAndView addBanner(@RequestParam CommonsMultipartFile[] picture,String moveUrl) throws Exception{
		ModelAndView mv=new ModelAndView(); 
		PageData pd=new PageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=microCampusBannerService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		pd.put("move_url", moveUrl);
		for(MultipartFile file:picture){
			try {
				mv = microCampusBannerService.addBannerPicture(pd,file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mv;
		
	}
	
	/**
	 * 删除轮播图
	 * @param banner_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteBanner")
	public ModelAndView deleteBanner(Integer banner_id) throws Exception{
		PageData pd=new PageData();
		pd.put("banner_id", banner_id);
		microCampusBannerService.delete(pd);
		
		return new ModelAndView("forward:list.do", null);
	}
	
	/**
	 * 编辑迁移地址
	 * @param banner_id
	 * @param moveUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editMoveUrl")
	public ModelAndView editMoveUrl(Integer banner_id,String moveUrl) throws Exception{
		PageData pd=new PageData();
		pd.put("banner_id", banner_id);
		pd.put("moveUrl", moveUrl);
		microCampusBannerService.editMoveUrl(pd);
		return new ModelAndView("forward:list.do", null);
	}
	
}
