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
import com.fh.service.teacher.NoticeManageService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@RestController
@RequestMapping(value = "/noticeManage")
public class NoticeManageController extends BaseController {
	String menuUrl = "noticeManage/list.do"; // 菜单地址(权限用)
	
	@Resource(name="noticeManageService")
	private NoticeManageService noticeManageService;
	
	/**
	 * 展示通知列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/noticeList")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String schoolId=noticeManageService.findSchoolId(userId);	
		
		pd.put("schoolId", schoolId);
		page.setPd(pd);
		List<PageData> noticeList=noticeManageService.findNoticeList(page);
		if(noticeList != null){
			for(PageData pageDate : noticeList){
				String str = pageDate.getString("picture");
				if(str != null && !"".equals(str)){
					String[] picList = str.trim().split(";");
					pageDate.put("picList", picList);
				}	
			}
		}
		mv.setViewName("teacher/notice_list");
		mv.addObject("noticeList", noticeList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 根据id删除通知
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteInfo")
	public Object deleteInfo() throws Exception{
		
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String id = pd.getString("id");
			if (null != id && !"".equals(id)) {
				String ArrayId[] = id.split(",");
				noticeManageService.deleteAll(ArrayId);
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
	
	/**
	 * 前往新通知编辑页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newInfo")
	public ModelAndView newInfo() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("teacher/newNoticeInfo");
		return mv;
	}
	
	/**
	 * 新通知提交
	 * @param schoolId
	 * @param userId
	 * @param author
	 * @param title
	 * @param content
	 * @param Type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newInfoSubmit")
	public ModelAndView newInfoSubmit(String title,String content,Integer Type,@RequestParam ("picture")MultipartFile[] picture) throws Exception{
		logBefore(logger, "新增");
		ModelAndView mv=this.getModelAndView();
		PageData pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String name=user.getNAME();
		
		String userId = user.getUSER_ID();
		String schoolId=noticeManageService.findSchoolId(userId);	
		pd.put("schoolId", schoolId);
		pd.put("userId", userId);
		pd.put("author", name);
		pd.put("title", title);
		pd.put("content", content.substring(12));
		pd.put("Type", Type);
		try {
			noticeManageService.newNotice(pd,picture);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		
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
}
