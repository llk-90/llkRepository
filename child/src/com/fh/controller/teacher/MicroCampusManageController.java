package com.fh.controller.teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.fh.service.teacher.MicroCampusManageService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@RestController
@RequestMapping(value = "/microCampusManage")
public class MicroCampusManageController extends BaseController {
	String menuUrl = "microCampusManage/list.do"; // 菜单地址(权限用)
	
	@Resource(name="microCampusManageService")
	private MicroCampusManageService microCampusManageService;
	
	/**
	 * 展示资讯列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=microCampusManageService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		page.setPd(pd);
		List<PageData> microCampusList=microCampusManageService.microCampus(page);
		mv.setViewName("teacher/microCampus_list");
		mv.addObject("microCampusList", microCampusList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 根据id删除资讯
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteInfo")
	public Object deleteInfo() throws Exception{
		/*PageData pd=new PageData();
		pd.put("id", id);
		microCampusManageService.delete(pd);*/
		
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String id = pd.getString("id");
			if (null != id && !"".equals(id)) {
				String ArrayId[] = id.split(",");
				microCampusManageService.deleteAll(ArrayId);
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
		
		//return new ModelAndView("forward:list.do", null);
	}
	
	/**
	 * 前往新资讯编辑页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newInfo")
	public ModelAndView newInfo() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("teacher/newMicroCampusInfo");
		return mv;
	}
	
	/**
	 * 新资讯提交
	 * @param title
	 * @param content
	 * @param StateTop
	 * @param InfoState
	 * @param picture
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newInfoSubmit")
	public ModelAndView newInfoSubmit(String title,String content,Integer Type,Integer StateTop,Integer InfoState,MultipartFile picture) throws Exception{
		logBefore(logger, "新增");
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String name=user.getNAME();
		
		String userId = user.getUSER_ID();
		String schoolId=microCampusManageService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		
		
		pd.put("author", name);
		pd.put("title", title);
		pd.put("content", content);
		pd.put("Type", Type);
		pd.put("StateTop", StateTop);
		pd.put("InfoState", InfoState);
		try {
			microCampusManageService.newMicroCampus(pd,picture);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		
		return mv;
	}
	
	/**
	 * 前往编辑页面
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData microCampusInfo = microCampusManageService.findByMicroCampusId(pd);
		mv.addObject("microCampusInfo",microCampusInfo);
		mv.setViewName("teacher/microCampusInfoEdit");
		return mv;
	}
	
	/**
	 * 编辑封面
	 * @param picture
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editIcon")
	@ResponseBody
	public ModelAndView addBanner(@RequestParam CommonsMultipartFile picture,Integer id){
		ModelAndView mv=new ModelAndView(); 
		PageData pd=new PageData();
		pd.put("id", id);
		MultipartFile file=picture;
		try {
			System.out.println("编辑封面");
			mv = microCampusManageService.editIcon(pd,file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return mv;
		
	}
	
	/**
	 * 文件上传
	 * @param uploadPic
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upfile")
	public PageData upfileImg(MultipartFile uploadPic, HttpSession session, HttpServletRequest request)
			throws Exception {
		PageData pd = new PageData();
		pd.put("errno", 0);
		List<String> img = new ArrayList<String>();
		String realName = "";
		if (uploadPic != null) {
			String fileType = uploadPic.getOriginalFilename().substring(uploadPic.getOriginalFilename().indexOf("."));
			// 实际存储文件名
			realName = UuidUtil.get32UUID() + fileType;
			/*
			 * String realPath =
			 * session.getServletContext().getRealPath("/uploadFiles/uploadImgs"); 
			 * File uploadFile = new File(realPath, realName);
			 * uploadPic.transferTo(uploadFile);
			 */
			String realPath = "D:" + "\\weixin\\palmcare\\picture\\";
			File uploadFile = new File(realPath, realName);
			uploadPic.transferTo(uploadFile);
		}
		String str="http://www.guanai100.cn/palmcare/picture/"+realName;
		//String str = "D:" + "\\picture\\" + realName;
		img.add(str);
		pd.put("data", img);

		return pd;
	}

	/**
	 * 编辑提交
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editSubmit")
	public ModelAndView editSubmit() throws Exception{
		logBefore(logger, "修改");
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		microCampusManageService.editMicroCampus(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
}
