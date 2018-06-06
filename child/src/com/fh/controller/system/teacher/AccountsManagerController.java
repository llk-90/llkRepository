package com.fh.controller.system.teacher;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.teacher.ChargehandManagerService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.mail.SimpleMailSender;

/**
 * 教师管理
 */
@Controller
@RequestMapping(value = "/accountsManager")
public class AccountsManagerController extends BaseController {

	String menuUrl = "accountsManager/list.do"; // 菜单地址(权限用)
	@Resource(name = "chargehandManagerService")
	private ChargehandManagerService chargehandManagerService;
	private static String TYPE="5";//客户经理
	private static String ROLE_CODE="KHJL";//角色
	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID()); // 主键
		pd.put("PASSWORD_OLD", pd.getString("PASSWORD"));
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		pd.put("type", TYPE);//客户经理
		try {
			chargehandManagerService.save(pd);
			SimpleMailSender.sendEmails(Const.SMTP, Const.PORT, Const.EMAIL, Const.PAW, pd.getString("EMAIL"), "掌上关爱客户经理帐号",
					"您的用户名为" + pd.getString("USERNAME") + "，您的密码为" + pd.getString("PASSWORD_OLD") + "。", "1");
		} catch (Exception e) {
		} finally {
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			chargehandManagerService.delete(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 修改基本信息
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		chargehandManagerService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 修改用户名密码
	 */
	@RequestMapping(value = "/editUserName")
	public ModelAndView editUserName() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		chargehandManagerService.editUserName(pd);
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
		PageData pd = this.getPageData();
		pd.put("type", TYPE);//客户经理
		try {
			page.setPd(pd);
			List<PageData> varList = chargehandManagerService.list(page); // 列出chargehandManager列表
			mv.setViewName("system/teacher/accountsManager_list");
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
		PageData pd = this.getPageData();
		pd.put("role_code", ROLE_CODE);
		try {
			List<PageData> roleList = chargehandManagerService.findJSRole(pd);
			mv.setViewName("system/teacher/accountsManager_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
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
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = chargehandManagerService.findByUserId(pd); // 根据Z_ID读取
			mv.setViewName("system/teacher/accountsManager_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String user_ids = pd.getString("user_ids");
			if (null != user_ids && !"".equals(user_ids)) {
				String Arrayuser_ids[] = user_ids.split(",");
                chargehandManagerService.deleteAll(Arrayuser_ids);
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
	 * 校验用户名
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkUsername")
	@ResponseBody
	public Object checkUsername() throws Exception {
		PageData pd = this.getPageData();
		Map<String, String> map = chargehandManagerService.checkUsername(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	
	/************ 下拉列表联动 *****************/
	/**
	 * 根据区域获取学校
	 * 
	 * @throws Exception
	 */
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) chargehandManagerService.schoolList(pd);
	}

	/**
	 * 根据学校获取年级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gradeList")
	@ResponseBody
	public Object gradeList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> gradeList = (List<PageData>) chargehandManagerService.gradeList(pd);
		map.put("gradeList", gradeList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 根据学校获取年级
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classList")
	@ResponseBody
	public Object classList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> classList = (List<PageData>) chargehandManagerService.classList(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**********************************/
}
