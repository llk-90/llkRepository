package com.fh.controller.weixin.personal_center;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixin.message.MessageListService;
import com.fh.service.weixin.personal_center.MessageEditService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.WXgetPicture;

@Controller
@RequestMapping(value= "messageedit")
public class MessageEditController extends BaseController {
	
	@Resource(name= "messageeditService")
	private MessageEditService messageeditService;
	@Resource(name="messageListService")
	private MessageListService messageListService;
    
    /**
	 * 学生信息列表
	 */
	@RequestMapping(value = "/showMessage")
	@ResponseBody
	public Object list() throws Exception {
		PageData pd = this.getPageData();		
		List<PageData> infoList = messageeditService.list(pd);
		for (int i = 0; i < infoList.size(); i++) {
			PageData pds = infoList.get(i);
			String pic = messageListService.getHeadPic(pds,"HEAD_PHOTO");
			pds.put("HEAD_PHOTO", pic);
		 }
		return AppUtil.returnObject(new PageData(), infoList);
	}
	
	/**
	 * 修改学生信息
	 */
	@RequestMapping(value= "/updateStuInfo")
	@ResponseBody
	public Object updateInfo() throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		messageeditService.updateInfo(pd);
		map.put("msg",Const.SUCCESS);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 修改家长信息
	 */
	@RequestMapping(value= "/updateParentInfo")
	@ResponseBody
	public Object updateParentInfo(HttpServletRequest request) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		PageData pd = this.getPageData();
		pd.put("user_id", user_id);
		String picName = get32UUID();
		String filepatha ="/headPic/"+ picName;
		String filepaths =Tools.readTxtFile(Const.FILENAME)+"/headPic/";
		File file=new File(filepaths);
		if(!file.exists()&&!file.isDirectory()){
			file.mkdirs();
		}
		String path =filepaths + picName;
    	pd.put("head_photo", filepatha+".png");
    	//获取服务器图片
    	WXgetPicture.saveImageToDisk(pd.getString("url"), pd.getString("picid"), path, request);
		messageeditService.updateParentInfo(pd);
		map.put("msg",Const.SUCCESS);
		return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 老师信息列表
	 */
	
	@RequestMapping(value = "/showInfo")
	@ResponseBody
	public Object infoList() throws Exception {
		PageData pd = this.getPageData();		
		List<PageData> teachInfo = messageeditService.infoList(pd);
		return AppUtil.returnObject(new PageData(), teachInfo);
	}
	
	/**
	 * 修改老师信息
	 */
	@RequestMapping(value= "/updateTeachInfo")
	@ResponseBody
	public Object updateTeach(HttpServletRequest request) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		String picName = get32UUID();
		String filepatha ="/headPic/"+ picName;
		String filepaths =Tools.readTxtFile(Const.FILENAME)+"/headPic/";
		File file=new File(filepaths);
		if(!file.exists()&&!file.isDirectory()){
			file.mkdirs();
		}
		String path =filepaths + picName;
    	pd.put("head_photo", filepatha+".png");
    	//获取服务器图片
    	WXgetPicture.saveImageToDisk(pd.getString("url"), pd.getString("picid"), path, request);
    	if(Tools.isEmpty(pd.getString("isImg"))){
    		pd.put("head_photo", "");
    	}
		messageeditService.updateTeachInfo(pd);
		map.put("msg",Const.SUCCESS);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 修改老师信息
	 */
	@RequestMapping(value= "/updateTeacher")
	@ResponseBody
	public Object updateTeacher() throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		messageeditService.updateTeacher(pd);
		map.put("msg",Const.SUCCESS);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
}
