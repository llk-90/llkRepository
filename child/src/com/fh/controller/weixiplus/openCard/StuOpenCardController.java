package com.fh.controller.weixiplus.openCard;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.openCard.StuOpenCardService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/**
 * 开卡管理
 */
@Controller
@RequestMapping(value = "/stuOpenCard")
public class StuOpenCardController extends BaseController {

	String menuUrl = "stuOpenCard/list.do"; // 菜单地址(权限用)
	@Resource(name = "stuOpenCardService")
	private StuOpenCardService stuOpenCardService;
	
	/**
	 * 新增
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/save")
	public ModelAndView save() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd2 = new PageData();
		try {
			// xft_user_syn, xft_customer表插入
			if("1".equals(pd.getString("sex"))){
				pd.put("sex", "男");
			} else if("0".equals(pd.getString("sex"))){
				pd.put("sex", "女");
			}
			//找到对应的年级和班级
			pd2 = stuOpenCardService.findGradeClaName(pd);
			//卡号
			Long x = Long.valueOf(pd.getString("IcNo"));
			//16进制的卡号转换
			String cardIDH = x.toHexString(x).toUpperCase();
			pd.put("CardIDH", cardIDH);
	        pd.put("GradeName", pd2.getString("GradeName"));
	        pd.put("ClassName", pd2.getString("ClassName"));
			stuOpenCardService.save(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 修改基本信息
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd2 = new PageData();
		//找到对应的年级和班级
		pd2 = stuOpenCardService.findGradeClaName(pd);
        pd.put("GradeName", pd2.getString("GradeName"));
        pd.put("ClassName", pd2.getString("ClassName"));
		stuOpenCardService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);			
		PageData pd = this.getPageData();
		pd.put("userId", user.getUSER_ID());
		page.setPd(pd);
		try {
			//查询xft_user_syn表学校学生开卡信息
			List<PageData> varList = stuOpenCardService.list(page); 
			mv.setViewName("system/openCard/stuOpenCard_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);			
		PageData pd = this.getPageData();
		PageData pd2 = new PageData();		
		
		pd.put("userId", user.getUSER_ID());
		try {
			//根据登录学校管理员账号找到对应学校名称
			pd = stuOpenCardService.findSchoolName(pd);			
			//在xft_customer取得AccountNo最大值
			pd2 = stuOpenCardService.findMaxAccountNo(pd2);		
			
			//取得AccountNo最大值+1
			Long maxAccountNo = (long) (((Long) pd2.get("AccountNo")).intValue() + 1);
			pd.put("AccNoMax", maxAccountNo);
			
			//根据学校id找对应学校的年级List
			List<PageData> gradeList = stuOpenCardService.findGradeList(pd);
			mv.setViewName("system/openCard/stuOpenCardAdd");
			mv.addObject("pd", pd);
			mv.addObject("gradeList", gradeList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() {
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);			
		PageData pd = new PageData();
		PageData pd2 = new PageData();
		pd = this.getPageData();
		pd.put("userId", user.getUSER_ID());
		try {
			//根据登录学校管理员账号找到对应学校名称
			pd2 = stuOpenCardService.findSchoolName(pd);
			pd = stuOpenCardService.findByStudentId(pd); 
			pd.put("school_id", pd2.get("school_id"));
			pd.put("SchoolName", pd2.getString("SchoolName"));
			//根据学校id找对应学校的年级List
			List<PageData> gradeList = stuOpenCardService.findGradeList(pd);
			mv.setViewName("system/openCard/stuOpenCard_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("gradeList", gradeList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 卡号批量删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String IcNo = pd.getString("IcNo");
			if (null != IcNo && !"".equals(IcNo)) {
				String ArrayIcNo[] = IcNo.split(",");
				stuOpenCardService.deleteAll(ArrayIcNo);
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
	
	/************ 下拉列表联动 *****************/
	/**
	 * 根据学校年级获取班级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classList")
	@ResponseBody
	public Object classList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> classList = (List<PageData>) stuOpenCardService.findClassList(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**********************************/
	
	/**
	 * check卡号是否重复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasIcNo")
	@ResponseBody
	public Object checkIcNo() {
		PageData pd = this.getPageData();
		try {
			pd.put("hasIcNo", stuOpenCardService.checkHasStuNo(pd));
		} catch (Exception e) {
			logger.error(e);
		}
		return AppUtil.returnObject(new PageData(), pd);
	}
	
	/**
	 * 跳转开卡导入页面
	 * @return
	 */
	@RequestMapping(value = "/importStu")
	public ModelAndView importStu() {
		logBefore(logger, "开卡信息导入页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/openCard/openCardImport");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 导入
	 * 
	 * @param myfiles
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public String readExcel(MultipartFile myfiles, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id=request.getParameter("id");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String msg = stuOpenCardService.readExcel(myfiles,id);
		out.print(msg);
		out.flush();
		return null;
	}
	
	@RequestMapping(value = "/returnMember")
	public ModelAndView returnMember() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 下载模板
	 * @return
	 */
	@RequestMapping(value = "/downExcel")
	public String downExcel(HttpServletResponse response) throws Exception {
		//System.out.println(PathUtil.getClasspath());
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + Const.StuOpenCardFileName,
				Const.StuOpenCardFileName);
		return null;
	}
}
