package com.fh.service.weixinplus.weixiplusCommon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.weixin.xft.XftRechargeService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@Service("weixiplusGetInfo")
public class WeixiplusGetInfo {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	@Resource(name = "xftRechargeService")
	private XftRechargeService xftRechargeService;

	/**
	 * 根据openid获取学生信息
	 * 
	 * @param openId
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public PageData getAtteinfo(PageData pg) throws Exception {

		List<PageData> userids = new ArrayList<PageData>();
		// 用户的考勤次数信息
		List<PageData> useratteinfo = new ArrayList<PageData>();
		// 获取用户绑定的userid
		userids = (List<PageData>) dao.findForList("WeixiplusCommon.selectStuUserid", pg);
		// System.out.println("userids:" + userids);
		String querydate = pg.getString("querydate");
		// 返回值用pd
		PageData pData = new PageData();
		if (userids.size() == 0) {
			// 如果没有绑定过多的话
			pData.put(StringDefault.errorcode, errorMsg.Success(1002));
		} else {
			pData.put(StringDefault.errorcode, errorMsg.Success(0));
			List<PageData> infolist = new ArrayList<>();
			List<PageData> attelist = new ArrayList<>();
			PageData temppd = userids.get(0);
			PageData res = new PageData();
			res.put("avert", temppd.getString("icon"));
			res.put("ParUserName", temppd.getString("ParUserName"));
			res.put("StuName", temppd.getString("UserName"));
			res.put("ClassName", temppd.getString("ClassName"));
			res.put("icno", temppd.getString("IcNo"));
			res.put("lateCount", "0");
			res.put("leaveCount", "0");
			useratteinfo = (List<PageData>) dao.findForList("WeixiplusCommon.selectatteinfo", pg);
			if (useratteinfo.size() > 0) {
				// 迟到，早退次数统计
				int lateCount = 0;
				int leaveCount = 0;
				for (PageData p : useratteinfo) {
					if (p.getString("type").equals("0")) {
						lateCount++;
					} else if (p.getString("type").equals("1")) {
						leaveCount++;
					} else if (p.getString("type").equals("2")) {
						lateCount++;
						leaveCount++;
					}
				}
				// System.out.println(useratteinfo);
				res.put("lateCount", String.valueOf(lateCount));
				res.put("leaveCount", String.valueOf(leaveCount));
			}
			infolist.add(res);

			if (attelist.size() != 0) {
				int date = Calendar.getInstance().get(Calendar.DATE);
				String month = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());

				int j = useratteinfo.size();
				for (int i = date; i > 0; i--) {
					PageData pd = new PageData();
					if (i > Integer.parseInt(
							useratteinfo.get(useratteinfo.size() - j).get("modify_time").toString().substring(8, 10))) {
						pd.put("date", i);
						pd.put("signin", "-—:-—");
						pd.put("signout", "-—:-—");
						pd.put("type", "3");
						pd.put("rank", "");
						attelist.add(pd);
					} else if (i == Integer.parseInt(
							useratteinfo.get(useratteinfo.size() - j).get("modify_time").toString().substring(8, 10))) {
						if (useratteinfo.get(useratteinfo.size() - j).get("signin_time").toString() != ""
								&& useratteinfo.get(useratteinfo.size() - j).get("signout_time").toString() != "") {
							pd.put("date", i);
							pd.put("signin", useratteinfo.get(useratteinfo.size() - j).get("signin_time").toString()
									.substring(11, 16));
							pd.put("signout", useratteinfo.get(useratteinfo.size() - j).get("signout_time").toString()
									.substring(11, 16));
							pd.put("type", useratteinfo.get(useratteinfo.size() - j).getString("type"));
							List<PageData> icnolist = new ArrayList<>();
							icnolist = (List<PageData>) dao.findForList("WeixiplusCommon.selecticnobyopenid", pg);
							String IcNo = icnolist.get(0).getString("IcNo");
							/*
							 * if(IcNo != null){ System.out.println("IcNo="+ IcNo); }
							 */
							pg.put("querydate", month + "-" + i);
							List<PageData> signinlist = new ArrayList<>();
							signinlist = (List<PageData>) dao.findForList("WeixiplusCommon.selectsignintimerank", pg);
							// System.out.println("listsize=" + signinlist.size());
							for (int n = 0; n < signinlist.size(); n++) {
								// System.out.println("icno="+signinlist.get(n).getString("IcNo"));
								if (signinlist.get(n).getString("IcNo").equals(IcNo)) {
									// System.out.println(1111);
									pd.put("rank", n + 1);
									break;
								}
							}
							attelist.add(pd);
							j--;
						} else if (useratteinfo.get(useratteinfo.size() - j).get("signin_time").toString() == "") {
							pd.put("date", i);
							pd.put("signin", "-—:-—");
							pd.put("signout", useratteinfo.get(useratteinfo.size() - j).get("signout_time").toString()
									.substring(11, 16));
							pd.put("type", "3");
							pd.put("rank", "");
							attelist.add(pd);
							j--;
						} else if (useratteinfo.get(useratteinfo.size() - j).get("signout_time").toString() == "") {
							pd.put("date", i);
							pd.put("signin", useratteinfo.get(useratteinfo.size() - j).get("signin_time").toString()
									.substring(11, 16));
							pd.put("signout", "-—:-—");
							pd.put("type", "3");
							List<PageData> icnolist = new ArrayList<>();
							icnolist = (List<PageData>) dao.findForList("WeixiplusCommon.selecticnobyopenid", pg);
							String IcNo = icnolist.get(0).getString("IcNo");
							pg.put("querydate", month + "-" + i);
							List<PageData> signinlist = new ArrayList<>();
							signinlist = (List<PageData>) dao.findForList("WeixiplusCommon.selectsignintimerank", pg);
							for (int n = 0; n <= signinlist.size(); n++) {
								if (signinlist.get(n).getString("IcNo").equals(IcNo)) {
									pd.put("rank", n + 1);
									break;
								}
							}
							attelist.add(pd);
							j--;
						}
					}
				}

				String now = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime()) + "-" + querydate;
				pg.put("querydate", now);
				List<PageData> classsigninrank = new ArrayList<>();
				List<PageData> classsigninranklist = new ArrayList<>();
				classsigninrank = (List<PageData>) dao.findForList("WeixiplusCommon.selectsignintimerank", pg);

				for (int n = 0; n < classsigninrank.size(); n++) {

					String IcNo = classsigninrank.get(n).getString("IcNo");
					PageData p = new PageData();
					p.put("IcNo", IcNo);
					List<PageData> namelist = new ArrayList<>();
					namelist = (List<PageData>) dao.findForList("WeixiplusCommon.selectstuinfo", p);
					String name = namelist.get(0).getString("UserName");
					PageData pp = new PageData();
					pp.put("name", name);
					pp.put("rank", n + 1);
					pp.put("signin", classsigninrank.get(n).get("signin_time").toString().substring(11, 16));
					classsigninranklist.add(pp);
				}
				pData.put("classsigninrank", classsigninranklist);

				pData.put("attelist", attelist);
			} else {
				pData.put(StringDefault.errorcode, errorMsg.Success(1003));
			}
			pData.put("infolist", infolist);

		}

		return pData;

	}

	/**
	 * 根据获取TarBar代码
	 * 
	 * @param
	 * @return Html信息
	 * @throws Exception
	 * @throws IOException
	 */
	public PageData getHtmlTarBar() throws Exception {
		PageData pageData = new PageData();
		pageData.put("htmlStr", readFile("C:\\Users\\Administrator\\Desktop\\TARBAR.txt"));
		return pageData;
	}

	/**
	 * @param 文件地址
	 * @return 文本文字
	 * 
	 * @throws Exception
	 */
	public String readFile(String fileName) {
		String fileContent = "";
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
				BufferedReader reader = new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null) {
					fileContent += line;
				}
				read.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public PageData getStuInfo(PageData pg) throws Exception {

		List<PageData> userids = new ArrayList<PageData>();
		List<PageData> userinfs = new ArrayList<PageData>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String itemNo = "";
		// 获取用户绑定的userid
		userids = (List<PageData>) dao.findForList("WeixiplusCommon.selectStuUserid", pg);
		// System.out.println("userids:" + userids);
		// 返回值用pd
		PageData pData = new PageData();
		if (userids.size() == 0) {
			// 如果没有绑定过多的话
			pData.put(StringDefault.errorcode, errorMsg.Success(1002));
		} else {
			pData.put(StringDefault.errorcode, errorMsg.Success(0));
			List<PageData> infolist = new ArrayList<>();
			PageData temppd = userids.get(0);
			PageData res = new PageData();
			res.put("avert", temppd.getString("icon"));
			res.put("ParUserName", temppd.getString("ParUserName"));
			res.put("StuName", temppd.getString("UserName"));
			res.put("ClassName", temppd.getString("ClassName"));
			res.put("icno", temppd.getString("IcNo"));
			res.put("StudentId", temppd.getString("StudentId"));
			// temppd.put("IcNo", Long.parseLong(temppd.getString("IcNo")));
			if (temppd.getString("IcNo") != null && !temppd.getString("IcNo").equals("")) {
				PageData customerInfo = xftRechargeService
						.queryXftCustomer(Long.toHexString(Long.parseLong(temppd.getString("IcNo"))));
				if (customerInfo != null && customerInfo.size() > 0) {
					String accountNo = customerInfo.get("AccountNo").toString();
					itemNo = customerInfo.get("ItemNo").toString();
					temppd.put("accountNo", accountNo);
					temppd.put("schoolid", session.getAttribute("schoolId"));
					userinfs = (List<PageData>) dao.findForList("WeixiplusCommon.selectStuOprInf", temppd);
				}
			}
			if (userinfs.size() > 0) {
				res.put("balance", (Double.parseDouble(userinfs.get(0).get("LeftJE").toString()) / 100.0) + "");
			}
			if (itemNo != null && itemNo.equals("17")) {
				res.put("balance", "挂失中");
			} else if (itemNo != null && itemNo.equals("19")) {
				res.put("balance", "已停用");
			} else if (itemNo != null && itemNo.equals("14")) {
				res.put("balance", "已销户");
			}
			infolist.add(res);
			pData.put("infolist", infolist);

		}

		return pData;
	}

}
