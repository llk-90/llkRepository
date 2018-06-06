package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.BannerManageService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/bannerManage")
public class BannerManageController extends BaseController {
	String menuUrl = "bannerManage/list.do"; // 菜单地址(权限用)
	
	@Resource(name="bannerManageService")
	private BannerManageService bannerManageService;
	
	/**
	 * 教育资讯轮播图列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView List(Page page) throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		page.setPd(pd);
		List<PageData> bannerList=bannerManageService.bannerList(page);
		mv.setViewName("teacher/banner_list");
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
		System.out.println("添加动作！！！");
		ModelAndView mv=new ModelAndView(); 
		PageData pd=new PageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=bannerManageService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		pd.put("move_url", moveUrl);
		for(MultipartFile file:picture){
			//String img = Utils.saveFile(file);
			//String[] fileUrl=img.split("/");
			//pd.put("imageUrl",fileUrl[fileUrl.length-1] );
			try {
				mv = bannerManageService.addBannerPicture(pd,file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mv;
		
	}
	
	/**
	 * 根据id删除轮播图
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteBanner")
	public ModelAndView deleteBanner(Integer id) throws Exception{
		PageData pd=new PageData();
		pd.put("id", id);
		bannerManageService.delete(pd);
		
		return new ModelAndView("forward:list.do", null);
	}
	
	/**
	 * 编辑迁移地址
	 * @param id
	 * @param moveUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editMoveUrl")
	public ModelAndView editMoveUrl(Integer id,String moveUrl) throws Exception{
		PageData pd=new PageData();
		pd.put("id", id);
		pd.put("moveUrl", moveUrl);
		bannerManageService.editMoveUrl(pd);
		return new ModelAndView("forward:list.do", null);
	}
	
}
