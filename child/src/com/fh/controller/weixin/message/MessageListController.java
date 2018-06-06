package com.fh.controller.weixin.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.user.UserService;
import com.fh.service.weixin.message.MessageListService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/messageList")
public class MessageListController extends BaseController {

	@Resource(name = "messageListService")
	private MessageListService messageListService;
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 留言列表显示
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> infoList = messageListService.messageList(pd);
		return AppUtil.returnObject(new PageData(), infoList);
	}

	/**
	 * 留言内容保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save() throws Exception {
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		DFAWordFilter filter = DFAWordFilter.getInstance();
		Object content = pd.get("lm_content");
		String after = filter.replaceSensitiveWord(//
				content == null ? "" : content.toString(), //
				DFAWordFilter.MAX_MATCH_TYPE, "*");
		pd.put("lm_content", after);
		if (!(content == null ? "" : content).equals(after)) {
			PageData pageData = new PageData();
			pageData.put("userId", pd.get("lm_send_user_id"));
			userService.updateCount(pageData);
			PageData count = userService.findCountByUserId(pageData);
			map.put("isAlert", true);
			map.put("count", count.get("count"));
			pageData.clear();
			PageData linJie = userService.findLinJie(pageData);
			map.put("linJie", linJie.get("Value"));
		}
		messageListService.save(pd);
		map.put("result", Const.SUCCESS);
		map.put("filterString", after);
		return map;
	}

	/**
	 * 留言列表显示
	 */
	@RequestMapping(value = "/contentList")
	@ResponseBody
	public Object contentList() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> contentList = messageListService.contentList(pd);
		return AppUtil.returnObject(new PageData(), contentList);
	}

	/**
	 * 查询用户类型
	 */
	@RequestMapping(value = "/getUserid")
	@ResponseBody
	public Object getUserid() throws Exception {
		PageData contentList = new PageData();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		// if(type.equals("1")){//老师
		contentList = messageListService.getUseridte(pd);
		// }else if(type.equals("2")){//家长和老师
		// if(!pd.containsKey("user_id")){
		// User user = (User)
		// SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		// pd.put("user_id", user.getUSER_ID());
		// pd.put("user_id", "e95316b7fe25451ab6dbcf7400b27157");
		// }
		// contentList = messageListService.getUserid(pd);
		// }

		return AppUtil.returnObject(new PageData(), contentList);
	}

	/**
	 * 查询用户
	 */
	@RequestMapping(value = "/getreceiveid")
	@ResponseBody
	public Object getreceiveid() throws Exception {
		PageData pd = this.getPageData();
		PageData contentList = messageListService.getreceiveid(pd);
		return AppUtil.returnObject(new PageData(), contentList);
	}

}
