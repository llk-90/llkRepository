package com.fh.controller.weixin.notice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserService;
import com.fh.service.weixin.leave.LeaveAppService;
import com.fh.service.weixin.notice.NoticeAppService;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.WXgetPicture;

@Controller
@RequestMapping(value = "/noticeApp")
public class NoticeAppController extends BaseController {

	String menuUrl = "noticeApp/list.do"; // 菜单地址(权限用)

	@Resource(name = "noticeAppService")
	private NoticeAppService noticeAppService;
	@Resource(name = "leaveAppService")
	private LeaveAppService leaveAppService;
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list() throws Exception {
		logBefore(logger, "公告列表");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<>();
		String user_id = leaveAppService.findUseridByOpenid(user.getUSER_ID());
//		String user_id = leaveAppService.findUseridByOpenid(pd.getString("openid"));
		pd.put("user_id", user_id); // 正式替换为user_id
		// pd.put("user_id", "702ebc80dcf5480287db18c7f7f5601c");
		List<PageData> result = noticeAppService.list(pd);
		for (PageData p : result) {
			String pic = p.getString("n_pic_url");
			if (null != pic && !pic.isEmpty()) {
				pic = "/files" + pic;
			} else {
				pic = "";
			}
			p.put("n_pic_url", pic);
		}
		String school = noticeAppService.findSchool(pd.getString("user_id"));
		PageData classPd = noticeAppService.findClass(pd.getString("user_id"));
		map.put("list", result);
		map.put("school", school);
		map.put("className", classPd.getString("z_name"));
		return map;
	}

	@RequestMapping(value = "/addNotice")
	@ResponseBody
	public Object addNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logBefore(logger, "手机端发布公告");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String openid = user.getOPENID();
		// String openid = "oCBdZwzDvXL8IN4TaN7R2SKjJZiY";
		PageData pd = this.getPageData();
		String result = "";
		Map<String, Object> map = new HashMap<>();
		String picName = get32UUID();

		String filepatha = "/uploadFiles/annoPic/" + picName;
		String filepaths = Tools.readTxtFile(Const.FILENAME) + "/uploadFiles/annoPic/";
		File file = new File(filepaths);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String path = filepaths + picName;
		pd.put("n_pic_url", filepatha + ".png");
		// 获取服务器图片
		WXgetPicture.saveImageToDisk(pd.getString("url"), pd.getString("picid"), path, request);
		pd.put("n_zone_id", noticeAppService.getZoneByOpenid(openid));
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 HH:mm");
		String time = format.format(date);
		pd.put("create_time", date);
		pd.put("notice_time", time);
		boolean hasWord = false;// 是否有敏感字符
		String title = pd.getString("title");
		String afterTitle = DFAWordFilter.filter(title);
		if (!afterTitle.equals(title)) {
			hasWord = true;
		}
		pd.put("title", afterTitle);
		String content = pd.getString("content");
		String afterContent = DFAWordFilter.filter(content);
		if (afterContent.equals(content)) {
			hasWord = true;
		}
		pd.put("content", afterContent);
		if (hasWord) {
			PageData pageData = new PageData();
			pageData.put("userId", user.getUSER_ID());
			userService.updateCount(pageData);
			PageData count = userService.findCountByUserId(pageData);
			map.put("isAlert", true);
			map.put("count", count.get("count"));
			pageData.clear();
			PageData linJie = userService.findLinJie(pageData);
			map.put("linJie", linJie.get("Value"));
		}
		noticeAppService.addNotice(pd);
		noticeAppService.sendTemp(request, response, pd);
		result = "success";
		map.put("result", result);
		return map;
	}
}
