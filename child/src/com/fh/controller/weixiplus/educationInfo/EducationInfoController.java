package com.fh.controller.weixiplus.educationInfo;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.educationInfo.EducationInfoService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Utils;

import net.sf.json.JSONObject;


/**
 * 
 * @author phf
 *
 */
@RestController
@RequestMapping(value="/weixiplusEducationInfo")
public class EducationInfoController extends BaseController {
	@Autowired
	private EducationInfoService service;
	
	/**
	 * 添加banner图片	
	 */
	@RequestMapping(value="/addBanner")
	public JSONObject addBanner(@RequestParam CommonsMultipartFile[] picture,String moveUrl){
		JSONObject js=new JSONObject();
		PageData pd=new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		pd.put("schoolId", session.getAttribute("schoolId"));
		pd.put("move_url", moveUrl);
		for(MultipartFile file:picture){
			String img = Utils.saveFile(file);
			pd.put("imageUrl",img );
			try {
				service.addBannerPicture(pd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return js;
	}
	
	/**
	 * 添加资讯信息
	 */
	@RequestMapping(value = "/addInfo")
	public JSONObject addInfo(Integer openId,String title,String content,Integer isTop,@RequestParam CommonsMultipartFile picture,@RequestParam CommonsMultipartFile[] image){
		JSONObject js=new JSONObject();
		PageData pd=new PageData();
		PageData pdImage=new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		pd.put("schoolId", session.getAttribute("schoolId"));
		try{
			if(title==null||openId==null||content==null){
				return js;
			}
			pd.put("title", title);
			pd.put("content", content);
			pd.put("is_top", isTop);
			service.addInfo(pd);
			Integer infoId=service.findInfoId();
			pdImage.put("infoId", infoId);
			MultipartFile file = picture;
			String img = Utils.saveFile(file);
			pdImage.put("imageUrl",img );
			service.addInfoPicture(pdImage);
			for(MultipartFile file1:image){
				String img1 = Utils.saveFile(file1);
				pdImage.put("imageUrl",img1 );
				service.addInfoImage(pdImage);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 点击首页展示banner轮播图
	 */
	@RequestMapping(value = "/getBanner")
	public PageData getBanner(){
		PageData pd = this.getPageData();
		return service.getBanner(pd);
	}
	
	/** 
	 * 点击首页展示教育资讯列表
	 */
	@RequestMapping(value = "/getInfoList")
	public PageData educationInfoList(String openId) throws Exception {				
		PageData pd = this.getPageData();
		return service.findEducationInfoList(pd);
	}
	
	/**
	 * 点击某一资讯获取该资讯详情
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getInfoById")
	public PageData findEducationInfoById(String openId,String InfoId) throws Exception{
		JSONObject js=new JSONObject();
		PageData pd=this.getPageData();
		pd.put("openId", openId);
		pd.put("InfoId", InfoId);
		return service.findInfoById(pd);
	}
	
	/**
	 * 申请自己学校的Html
	 * @param openId
	 * @param InfoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/applyPath")
	public PageData applyPath() throws Exception{
		//获取当前用户的User信息
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
	   return service.applyHtmlPath(user);
	}
	
}
