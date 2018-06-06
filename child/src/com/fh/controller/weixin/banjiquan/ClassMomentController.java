package com.fh.controller.weixin.banjiquan;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.controller.base.BaseController;
import com.fh.service.weixin.banjiquan.ClassMomentService;
import com.fh.util.PageData;

import net.sf.json.JSONObject;

@Controller("classMomentController")
@RequestMapping("/classMoment")
public class ClassMomentController extends BaseController {
	@Resource(name = "classMomentService")
	private ClassMomentService classMomentService;

	@RequestMapping("/getMsg")
	@ResponseBody
	public JSONObject getMomentMsg(String classId, Integer pageSize, Integer pageCount, String key, Integer usertype,
			Integer uid) {
		HttpServletRequest request = getRequest();
		if (null == uid || 0 == uid || null == classId) {
			if (null != request.getSession().getAttribute("parentId")) {
				usertype = 1;
				uid = Integer.parseInt(request.getSession().getAttribute("parentId").toString());
				if (null != request.getSession().getAttribute("classId")) {
					classId = request.getSession().getAttribute("classId").toString();
				} else {
					try {
						classId = classMomentService.getParentClass(uid);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		JSONObject js = new JSONObject();
		List<PageData> list;
		try {
			list = classMomentService.getMomentMsg(uid, classId, pageSize, pageCount, key, usertype);
			if (list.size() != 0) {
				js.element("data", list);
			} else {
				js.element("errMsg", "OK");
			}
		} catch (Exception e) {
			js.element("errMsg", "Failed");
			e.printStackTrace();
		}
		return js;

	}

	@RequestMapping("/addMoment")
	@ResponseBody
	public JSONObject addNewMoment(Integer uid, String title, String content, Integer classId, Integer usertype,
			MultipartHttpServletRequest request) {
		JSONObject js = new JSONObject();
		if (null == title || null == content || "".equals(title.trim()) || "".equals(content.trim())) {
			js.element("errMsg", "参数不全");
			return js;
		}
		classMomentService.addNewMoment(uid, title, content, classId, usertype, request);
		js.element("errMsg", "OK");
		return js;
	}

	@RequestMapping("/addMoment2")
	@ResponseBody
	public JSONObject addNewMoment2(Integer id, MultipartHttpServletRequest request) throws Exception {
		JSONObject js = new JSONObject();
		classMomentService.addNewMoment2(id, request);
		js.element("errMsg", "OK");
		return js;
	}

	@RequestMapping("/updateStatus")
	@ResponseBody
	public JSONObject updateStatus(String jsonstr) {
		JSONObject js = new JSONObject();
		/* PageData pd = this.getPageData(); */
		String content = null;
		try {
			content = classMomentService.updateStatus(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		js.element("content", content);
		js.element("errMsg", "OK");
		return js;
	}

	@RequestMapping("/getUserInfo")
	@ResponseBody
	public JSONObject getUserInfo(Integer usertype, Integer uid) {
		JSONObject js = new JSONObject();
		try {
			HttpServletRequest request = getRequest();
			if (null != usertype && null != uid) {
				if (null != request.getSession().getAttribute("parentId")) {
					usertype = 1;
					uid = Integer.parseInt(request.getSession().getAttribute("parentId").toString());
				}
				if (usertype == 1) {
					if (null != uid) {
						js.element("userInfo", classMomentService.getParInfoUseInController(uid));
					}
				} else {
					// teacher user information interface
				}
				js.element("errMsg", "OK");
			} else {
				js.element("errMsg", "Failed");
			}
		} catch (Exception e) {
			js.element("errMsg", "Failed");
			e.printStackTrace();
		}
		return js;
	}

	/**
	 * Getting info by openId
	 * 
	 * @param openid
	 * @return
	 */
	@RequestMapping("/getInfoByOpenId")
	@ResponseBody
	public JSONObject getInfoByOpenId(String openid) {
		JSONObject js = new JSONObject();
		PageData pageData = null;
		try {
			pageData = classMomentService.getInfoByOpenId(openid);
			if (null != pageData) {
				js.element("errMsg", "OK");
				js.element("uid", pageData.get("uid").toString());
				js.element("classId", pageData.get("classId").toString());
			} else {
				pageData = classMomentService.getInfoByTeaOpenId(openid);
				if (null != pageData) {
					js.element("errMsg", "OK");
					js.element("uid", pageData.get("uid").toString());
					js.element("classId", pageData.get("classId").toString());
				} else {
					js.element("errMsg", "Failed");
				}
			}
		} catch (Exception e) {
			js.element("errMsg", "Failed");
			e.printStackTrace();
		}
		return js;
	}
}
