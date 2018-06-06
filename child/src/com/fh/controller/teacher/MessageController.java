package com.fh.controller.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.service.teacher.MessageService;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/messageController")
public class MessageController extends BaseController {
	String menuUrl = "messageController/list.do"; // 菜单地址(权限用)
	@Resource(name = "messageService")
	private MessageService messageService;
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "留言列表页面");
		// 从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("lm_receive_user_id", userId);
		page.setPd(pd);
		List<PageData> varList = messageService.messageList(page);// 留言信息列表
		mv.addObject("varList", varList);
		mv.setViewName("teacher/message_list");
		mv.addObject("pd", pd);
		return mv;
	}

	/***
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listAll")
	public ModelAndView listAll() {
		logBefore(logger, "回复留言页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("teacher/allStu");
		return mv;
	}

	/**
	 * 去回复页面
	 */
	@RequestMapping(value = "/goReply")
	public ModelAndView goReply(Page page) throws Exception {
		logBefore(logger, "去回复页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID(); // 老师userId
		String sendId = pd.getString("sendId"); // 发送者Id
		String receiveId = pd.getString("receiveId"); // 接收者Id
		if (sendId.equals(userId)) {
			pd.put("sendId", receiveId);
			pd.put("receiveId", userId);
		} else {
			pd.put("sendId", sendId);
			pd.put("receiveId", userId);
		}
		page.setPd(pd);
		// 留言信息姓名
		List<PageData> varListName = messageService.findByIdName(page);
		// 留言信息列表
		List<PageData> varList = messageService.findByIdList(page);
		mv.addObject("varList", varList);
		mv.addObject("varListName", varListName);
		mv.setViewName("teacher/message_reply");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "saveReply")
	public ModelAndView saveReply() throws Exception {
		logBefore(logger, "回复内容保存");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("save_result");
		PageData pd = this.getPageData();
		String lm_content = pd.getString("lm_content");
		messageService.edit(pd);
		boolean hasWord = false;// 是否有敏感字符
		String afterTitle = DFAWordFilter.filter(lm_content);
		if (!afterTitle.equals(lm_content)) {
			hasWord = true;
		}
		pd.put("lm_content", afterTitle);
		messageService.saveReply(pd);
		if (hasWord) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			PageData pageData = new PageData();
			pageData.put("userId", user.getUSER_ID());
			userService.updateCount(pageData);
			PageData count = userService.findCountByUserId(pageData);
			pageData.clear();
			PageData linJie = userService.findLinJie(pageData);
			if (count != null && linJie != null) {
				if (new Integer(count.get("count").toString()) >= new Integer(linJie.get("Value").toString())) {
					mv.setViewName("redirect:/logout");
				}
			}
		}
		return mv;
	}

}
