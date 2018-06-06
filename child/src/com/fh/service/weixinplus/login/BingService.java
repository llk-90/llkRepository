package com.fh.service.weixinplus.login;

import java.io.IOException;
import java.util.ArrayList;
//import java.sql.Date;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@Service("BingService")
public class BingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	private static Logger logger = LoggerFactory.getLogger(BingService.class);

	/**
	 * 根据输页面上输入信息进行学省查询
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	/*public PageData getBingInfo(PageData pd) throws Exception {

		// 返回值
		PageData pageData = new PageData();
		// 验证手机号是否正确
		if (pd.getString("phone_num") == null || pd.getString("phone_num").length() != 11
				|| pd.getString(StringDefault.name) == null || pd.getString(StringDefault.name).length() == 0) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(4001));
			return pageData;
		}
		// 检查验证码是否正确
		pd.put("type", 0);
		int code = checkVerifyCode(pd);
		if (code != 0) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(code));
			return pageData;
		}
		// 手机号前三位
		String homenum = pd.getString("phone_num").substring(0, 3);
		// 手机号后四位
		String endnum = pd.getString("phone_num").substring(7, 11);
		pd.put("phone_num", homenum + "****" + endnum);
		List<PageData> infolist = new ArrayList<PageData>();
		// 如果有IC卡号的话直接查询IC卡号
		if (pd.get("ic_num") != null && pd.getString("ic_num").trim().length() != 0
				&& !"null".equals(pd.getString("ic_num").trim())
				&& !"undefined".equals(pd.getString("ic_num").trim())) {
			infolist = (List<PageData>) dao.findForList("LoginBing.selecctStuInfo_ic", pd);
		} else {
			infolist = (List<PageData>) dao.findForList("LoginBing.selecctStuInfo", pd);
		}
		if (infolist.size() != 0) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));

			pageData.put(pd.get("ic_num"), infolist.get(0).get("IcNo"));
			pageData.put("name", infolist.get(0).get("UserName"));
			pageData.put("stuID", infolist.get(0).get("StudentId"));
			pageData.put("parentId", infolist.get(0).get("ParentId"));
			pageData.put("parUserName", infolist.get(0).get("ParUserName"));
		} else {
			pageData.put(StringDefault.errorcode, errorMsg.Success(1001));
		}

		return pageData;
	}*/
	
	public PageData getBingInfo(PageData pd) throws Exception {

	// 返回值
	PageData info = new PageData();  
	info= (PageData) dao.findForObject("LoginBing.getStuAndParInfo", pd);
	pd.put("type", 0);
	int code = checkVerifyCode(pd);   
		//学生表有数据的情况下才插入，qry_stuparent_info中无数据时绑定
		if(info !=null && info.get("StudentId") !=null) {
			//验证验证码
			if(code == 0) {
				info.put(StringDefault.errorcode, errorMsg.Success(0));
			}else {
				info = new PageData();   
				info.put(StringDefault.errorcode,"验证码输入错误");
			}
		}else {  
			info = new PageData(); 
			info.put(StringDefault.errorcode, "请联系学校导入基础数据");
		}
	return info;
}  
	

	/**
	 * 根据输页面上输入信息进行绑定
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */
	public PageData saveBingInfo(PageData pd) throws Exception {

		PageData pageData = new PageData();
		try {
			pd.put("u_type", 3);
			dao.save("LoginBing.insertBingInfo", pd);
			//pd.put("stuId", Integer.valueOf(pd.remove("stuId").toString()));
			//dao.save("LoginBing.insertBingInfoToStu", pd);
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));
		} catch (Exception e) {
			e.printStackTrace();
			pageData.put(StringDefault.errorcode, errorMsg.Success(4002));
		}

		return pageData;
	}

	/**
	 * 根据输页面上输入信息进行绑定
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */

	@SuppressWarnings("unchecked")
	public PageData getAllSchool(PageData pd) throws Exception {

		// 学校pg
		PageData pageData = new PageData();
		List<PageData> infolist = new ArrayList<>();
		try {
			if (pd.getString("type").equals("1")) {
				// 如果是获取学校数据
				List<PageData> schools = (List<PageData>) dao.findForList("personal.getAllSch", pd);
				pageData.put("infolist", schools);
				pageData.put(StringDefault.errorcode, errorMsg.Success(0));
			} else {
				// 首先获取所有的省份
				List<PageData> provinces = (List<PageData>) dao.findForList("personal.getallprovince", null);
				for (PageData province : provinces) {
					// 根据不同省份获取不同城市
					List<PageData> citys = (List<PageData>) dao.findForList("personal.getallcity", province);
					for (PageData city : citys) {
						// 根据不同城市获取不同区县
						List<PageData> areas = (List<PageData>) dao.findForList("personal.getDistrict", city);
						city.put("Areas", areas);
					}
					province.put("Citys", citys);
				}

				pageData.put("infolist", provinces);
				pageData.put(StringDefault.errorcode, errorMsg.Success(0));
			}

		} catch (Exception e) {
			// TODO: handle exception

		}
		return pageData;
	}

	/**
	 * 根据学校id查询当前学校的所有class
	 * 
	 * @param schoolid
	 * @return 班级信心
	 * @throws Exception
	 * @throws IOException
	 */
	public PageData getAllClass(PageData pData) throws Exception {
		PageData res = new PageData();
		try {

			List<PageData> classes = (List<PageData>) dao.findForList("personal.getallclass", pData);
			if (classes.size() == 0) {
				res.put("errorcode", errorMsg.Success(1001));
			} else {
				res.put("errorcode", errorMsg.Success(0));
				res.put("classInfo", classes);
			}

		} catch (Exception e) {
			// TODO: handle exception
			res.put("errorcode", errorMsg.Success(4001));
		}
		return res;
	}

	/**
	 * 保存验证码到数据库
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */

	public boolean saveVerifyCode(PageData pd) throws Exception {

		// 删除老老的验证码信息

		try {
			dao.delete("LoginBing.deloldcode", pd);
			pd.put("creat_time", new Date());
			dao.save("LoginBing.saveVerifyCodeInfo", pd);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	/**
	 * 保存验证码到数据库
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */

	public int checkVerifyCode(PageData pd) throws Exception {

		try {
			List<PageData> info = (List<PageData>) dao.findForList("LoginBing.getVerifyCodeInfo", pd);
			// System.out.println(info);
			PageData pageData = info.get(0);
			if (pd.getString(StringDefault.openId).equals(pageData.getString(StringDefault.openId))
					&& pd.getString(StringDefault.phone).equals(pageData.getString(StringDefault.phone))
					&& pd.getString("verifycode").equals(pageData.getString("verifycode"))) {

				// System.out.println(pageData.get("creat_time"));
				long nowsatmp = new Date().getTime();
				Date dbtime = (Date) pageData.get("creat_time");
				long dbstamp = (long) dbtime.getTime();
				if ((nowsatmp - dbstamp) <= 600000) {
					return 0;
				} else {
					return 3002;
				}

			} else {

				return 3002;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return 4001;
		}
		// 删除老老的验证码信息
		// return 0;

	}

	/**
	 * 获取验证码
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getVerifyCode(PageData pd) throws Exception {
		String verifycode = null;
		try {

			List<PageData> info = (List<PageData>) dao.findForList("LoginBing.getVerifyCodeInfo", pd);
			if (info.size() >= 1) {
				for (PageData p : info) {
					verifycode = (String) p.get("verifycode");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifycode;
	}
	
	/**
	 * 获取学生信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getstuInfo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("LoginBing.getstuInfo", pd);
	}
	

}
