package com.fh.controller.weixin.courseware;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.courseware.CoursewarePublishController;
import com.fh.entity.system.User;
import com.fh.service.member.MemberAuthService;
import com.fh.service.system.commodity.CommodityService;
import com.fh.service.system.courseware.CoursewarePublishService;
import com.fh.service.weixin.personal_center.WxUserService;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.MapPlus;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/**
 * 课件
 * 
 * @author 860115007
 *
 */
@Controller
@RequestMapping("/coursewareApp")
public class CoursewareAppController extends BaseController {

	@Resource(name = "coursewarePublishService")
	CoursewarePublishService ser;
	@Resource(name = "memberAuthService")
	MemberAuthService authSer;
	@Resource(name = "commodityService")
	CommodityService commoditySer;
	@Resource(name = "wxUserService")
	private WxUserService wxUserService;

	/**
	 * 课件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list() throws Exception {
		MapPlus result = null;
		try {
			result = new MapPlus("data", ser.list(this.getPageData()));
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	/**
	 * 点击量
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/clickCount")
	@ResponseBody
	public Object clickCount() {
		MapPlus result = new MapPlus();
		try {
			ser.clickCount(this.getPageData());
			result.addParams("success", true);
		} catch (Exception e) {
			logger.error(e);
			result.addParams("success", false);
			result.addParams("err", e);
		}
		return result;
	}

	@RequestMapping("/findById")
	@ResponseBody
	public Object findById() throws Exception {
		MapPlus result = null;
		try {
			result = new MapPlus("data", ser.findById(this.getPageData()));
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 课件类型
	 * 
	 * @return
	 */
	@RequestMapping("/gradetList")
	@ResponseBody
	public Object gradeList() {
		MapPlus result = null;
		try {
			ArrayList<MapPlus> objs = new ArrayList<>();
			CoursewarePublishController.grade.forEach((k, v) -> {
				objs.add(new MapPlus("id", k).addParams("text", v));
			});
			result = new MapPlus("data", objs);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return result;
	}

	/**
	 * 到考勤
	 * 
	 * @param host
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAttendance")
	public ModelAndView toAttendanceCheck(@RequestHeader("Host") String host) throws Exception {
		PageData ad_phone = wxUserService.findAd();
		ModelAndView result = new ModelAndView(
				"redirect:http://" + host + "/palmcare/role/noRole_dingYue.html?phone=" + ad_phone.get("phone"));
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (user != null && user.getOPENID() != null) {
			PageData pd = this.getPageData();
			pd.put("userId", user.getUSER_ID());
			boolean canShow = ser.findStuByUserId(pd);
			if (canShow) {
				result = new ModelAndView(
						"redirect:http://" + host + "/palmcare/datepicker_qingjia/attendanceCheck.html");
			}
		} else {
			result = new ModelAndView("redirect:http://" + host + "/palmcare/role/sessionTimeOut.html");
		}
		return result;
	}

	@RequestMapping("/toDetails")
	public ModelAndView toDownload(@RequestHeader("Host") String host) throws Exception {
		PageData ad_phone = wxUserService.findAd();
		ModelAndView result = new ModelAndView(
				"redirect:http://" + host + "/palmcare/role/noRole_dingYue.html?phone=" + ad_phone.get("phone"));
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null && user.getUSER_ID() != null) {
				PageData pd = this.getPageData();
				pd.put("userId", user.getUSER_ID());
				boolean canShow = ser.findStuByUserId(pd);
				if (canShow) {
					result = new ModelAndView("redirect:http://" + host
							+ "/palmcare/courseware/picture_details.html?id=" + pd.get("coursewareId"));
				}

				// System.out.println("openid:" + user.getOPENID());
				// PageData pd = this.getPageData();
				// if (pd.get("coursewareId") != null) {
				// PageData commoditys = commoditySer.findByCoursewareId(pd);
				// if (commoditys != null) {
				// BigDecimal price = (BigDecimal) commoditys.get("price");
				// if (price.compareTo(new BigDecimal(0.0)) == 0) {
				// 免费
				// PageData info =
				// commoditySer.findFileByCoursewareId(pd);
				// String diskPath = info.getString("disk_path");
				// String diskFilename =
				// info.getString("disk_filename");
				// String fileDownloadUrl = "redirect:http://" +
				// host + "/files/" + diskPath + diskFilename;

				// result = new ModelAndView("redirect:http://" + host
				// + "/palmcare/courseware/picture_details.html?id=" +
				// pd.get("coursewareId"));
				// } else {
				// pd.put("openId", user.getOPENID());
				// List<PageData> infos = authSer.fileInfoListApp(pd);
				// if (infos != null && infos.size() > 0) {
				// PageData info = infos.get(0);
				// String diskPath =
				// info.getString("disk_path");
				// String diskFilename =
				// info.getString("disk_filename");
				// String fileDownloadUrl = "redirect:http://" +
				// host + "/files/" + diskPath
				// + diskFilename;
				// result = new ModelAndView("redirect:http://" + host
				// + "/palmcare/courseware/picture_details.html?id=" +
				// pd.get("coursewareId"));
				// }
				// }
				// }
				// }
			} else {
				result = new ModelAndView("redirect:http://" + host + "/palmcare/role/sessionTimeOut.html");
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return result;
	}

	@RequestMapping(value = "/downExcel")
	public String downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + Const.StudentFileName2,
				Const.StudentFileName2);
		return null;
	}
}
