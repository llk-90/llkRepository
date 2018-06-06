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
import com.fh.service.system.teacher.AreaManagerService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.mail.SimpleMailSender;

/**
 * 教师管理
 */
@Controller
@RequestMapping(value = "/areaManager")
public class AreaManagerController extends BaseController {

	String menuUrl = "areaManager/list.do"; // 菜单地址(权限用)
	@Resource(name = "areaManagerService")
	private AreaManagerService areaManagerService;

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
		try {
			areaManagerService.save(pd);
			// 区域表添加区域
			areaManagerService.saveArea(pd);
			// 添加关联
			areaManagerService.saveRelation(pd);
			SimpleMailSender.sendEmails(Const.SMTP, Const.PORT, Const.EMAIL, Const.PAW, pd.getString("EMAIL"), "掌上关爱区域经理帐号",
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
			areaManagerService.delete(pd);
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
		areaManagerService.edit(pd);
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
		areaManagerService.editUserName(pd);
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
		try {
			page.setPd(pd);
			List<PageData> varList = areaManagerService.list(page); // 列出areaManager列表
			mv.setViewName("system/teacher/areaManager_list");
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
		try {
			List<PageData> roleList = areaManagerService.findJSRole(pd);
			mv.setViewName("system/teacher/areaManager_add");
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
		PageData pd = this.getPageData();
		try {
			List<PageData> roleList = areaManagerService.findJSRole(pd);
			pd = areaManagerService.findByZId(pd); // 根据Z_ID读取
			mv.setViewName("system/teacher/areaManager_edit");
			mv.addObject("msg", "edit");
			mv.addObject("roleList", roleList);
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
			String z_ids = pd.getString("z_ids");
			String user_ids = pd.getString("user_ids");
			if (null != z_ids && !"".equals(z_ids)) {
				String Arrayz_ids[] = z_ids.split(",");
				String Arrayuser_ids[] = user_ids.split(",");
				// 校验ID是否允许删除
				String msg = "";
				List<PageData> pList = areaManagerService.checkDel(Arrayz_ids);
				for (PageData p : pList) {
					msg = msg + p.getString("z_name") + " ";
				}
				if ("".equals(msg)) {
					areaManagerService.deleteAll(Arrayuser_ids, Arrayz_ids);
				}
				map.put("msg", msg);
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

	/*
	 * 导出到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("姓名"); // 1
			titles.add("密码"); // 2
			titles.add("23"); // 3
			titles.add("q"); // 4
			titles.add("sa "); // 5
			titles.add("as "); // 6
			titles.add("qw"); // 7
			titles.add("qwe"); // 8
			dataMap.put("titles", titles);
			List<PageData> varOList = areaManagerService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("USERNAME")); // 1
				vpd.put("var2", varOList.get(i).getString("PASSWORD")); // 2
				vpd.put("var3", varOList.get(i).getString("NAME")); // 3
				vpd.put("var4", varOList.get(i).getString("ROLE_ID")); // 4
				vpd.put("var5", varOList.get(i).getString("PHONE")); // 5
				vpd.put("var6", varOList.get(i).getString("EMAIL")); // 6
				vpd.put("var7", varOList.get(i).getString("BZ")); // 7
				vpd.put("var8", varOList.get(i).getString("CREATE_TIME")); // 8
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
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
		Map<String, String> map = areaManagerService.checkUsername(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 校验区域
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkArea")
	@ResponseBody
	public Object checkArea() throws Exception {
		PageData pd = this.getPageData();
		Map<String, String> map = areaManagerService.checkArea(pd);
		return AppUtil.returnObject(new PageData(), map);
	}

	/************ 下拉列表联动 *****************/
	/**
	 * 根据区域获取学校
	 * 
	 * @throws Exception
	 */
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) areaManagerService.schoolList(pd);
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
		List<PageData> gradeList = (List<PageData>) areaManagerService.gradeList(pd);
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
		List<PageData> classList = (List<PageData>) areaManagerService.classList(pd);
		map.put("classList", classList);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**********************************/
}
