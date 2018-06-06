package com.fh.service.member;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.dao.LlkDaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.ImportExcel;
import com.fh.util.ImportExcel.DataAdapter;
import com.fh.util.ImportExcel.KeyValue;
import com.fh.util.ImportExcel.RectIdx;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.fh.util.mail.MailSenderInfo;
import com.fh.util.mail.SimpleMailSender;

@Service("memberService")
public class MemberService {

	@Resource(name = "llkDaoSupport")
	private LlkDaoSupport llkdao;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列表
	 * 
	 * @param page  
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> memberList(Page page) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		 String u_type=user.getU_type();
		 PageData pd=page.getPd();
		 if(Const.QUYU.equals(u_type)){
		 pd.put("userIdqy", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.JIAOSHI.equals(u_type)){
		 pd.put("userIdtc", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.ZUZHNAG.equals(u_type)){
		 pd.put("userIdzz", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.KEHU.equals(u_type)){
		 pd.put("userIdkh", userId);
		 pd.put("u_type", Const.SCHOOLADMIN);
		 }else if(Const.SCHOOLADMIN.equals(u_type)){
		 pd.put("userIdsc", userId);
		 pd.put("u_types", Const.SCHOOLADMIN);
		 }
		return (List<PageData>) dao.findForList("MemberMapper.findlistPage", page);
	}

	/**
	 * 班级信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MemberMapper.findById", pd);
	}

	/**
	 * 更新班级信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception {
		dao.findForObject("MemberMapper.updateClass", pd);
	}

	/**
	 * 查询班级是否存在
	 * 
	 * @return
	 * @throws Exception
	 */
	public int findIsExist(PageData pd) throws Exception {
		return (int) dao.findForObject("MemberMapper.findIsExist", pd);
	}

	/**
	 * 保存学生信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveE(PageData pd) throws Exception {
		pd.put("s_id", "");
		dao.save("MemberMapper.saveStu", pd);
	}

	/**
	 * 保存家长信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveUser(PageData pd) throws Exception {
		dao.save("MemberMapper.saveParent", pd);
	}

	/**
	 * 保存学生家长关联信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveUS(PageData pd) throws Exception {
		dao.save("MemberMapper.stuParent", pd);
	}

	/**
	 * 根据学生id查询信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStuId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MemberMapper.findByStuId", pd);
	}

	/**
	 * 校验手机号
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkPhone(PageData pd) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String info = "";
		PageData pdf = (PageData) dao.findForObject("MemberMapper.checkPhone", pd);
		if (pdf != null) {
			if (!(pd.getString("USER_ID").equals(pdf.getString("USER_ID")))) {
				info = "exist";
			}
		}
		map.put("result", info);
		return map;
	}

	/**
	 * 更新学生信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editE(PageData pd) throws Exception {
		dao.update("MemberMapper.editStu", pd);
	}

	/**
	 * 更新家长信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editUser(PageData pd) throws Exception {
		dao.update("MemberMapper.editUser", pd);
	}

	/**
	 * 删除学生信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delStu(PageData pd) throws Exception {
		dao.delete("MemberMapper.delStu", pd);
	}

	/**
	 * 删除关联信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delUS(PageData pd) throws Exception {
		dao.delete("MemberMapper.delUS", pd);
	}

	/**
	 * 删除家长信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delUser(PageData pd) throws Exception {
		dao.delete("MemberMapper.delUser", pd);
	}

	/**
	 * 删除微信关联表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delUX(PageData pd) throws Exception {
		dao.delete("MemberMapper.delUX", pd);
	}

	/**
	 * 删除订单关联表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delCO(PageData pd) throws Exception {
		dao.delete("MemberMapper.delCO", pd);
	}

	/**
	 * 删除会员权限关联表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delCU(PageData pd) throws Exception {
		dao.delete("MemberMapper.delCU", pd);
	}

	/**
	 * 导入
	 * 
	 * @param myfiles
	 * @return
	 * @throws IOException
	 */
	public String readExcel(MultipartFile myfiles, String id) throws IOException {
		InputStream in = myfiles.getInputStream();
		ImportExcel ie = new ImportExcel(//
				in//
				, new RectIdx(0, 1, null, null)//
				, new StuAdapter(id));
		return ie.getErrMsgs();
	}

	private class StuAdapter extends DataAdapter {
		private Object phone;
		private String id;

		public StuAdapter(String id) {
			this.id = id;
		}

		public void sendMail(PageData pd) throws Exception {
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost("smtp.qq.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			mailInfo.setUserName("1473749555@qq.com");
			mailInfo.setPassword("dafen110");// 您的邮箱密码
			mailInfo.setFromAddress("1473749555@qq.com");
			mailInfo.setToAddress(pd.getString("parent_email"));
			mailInfo.setSubject("掌上关爱账号密码通知");
			mailInfo.setContent("您的账号是:" + pd.getString("parent_phone") + "，密码是:" + pd.getString("matePwd"));
			// 这个类主要来发送邮件

			SimpleMailSender sms = new SimpleMailSender();
			sms.sendTextMail(mailInfo);// 发送文体格式
		}

		@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
		@Override
		public String setData(ArrayList<HashMap<String, Object>> result) {
			String msg = "";
			if (result.size() > 0) {
				/**
				 * var0 :学校名称 var1 : 入学年份; var2 :班级名称 var3 : 电话号码1 var4 :初始密码
		  		 * var5 :学生姓名;var6：性别; var7 :住址; var8 :学号; var9 : 电话号码2 var10
				 * :密码2 var11 : 电话号码3 var12 :密码3 var13 :CardId var14 :订阅状态 var15
				 * :开通日期 var16 :到期时间
				 */
				// 取得所有参数
				List<PageData> excelPdList = new ArrayList<PageData>();
				for (HashMap<String, Object> p : result) {
					PageData pd = new PageData();
					pd.put("USER_ID", UuidUtil.get32UUID()); // 主键
					pd.put("s_phone1", p.get("s_phone1"));// 电话号码1
					pd.put("PASSWORD_UNENCRYPTED", p.get("matePwd"));
					pd.put("s_password1", p.get("s_password1"));// 初始密码
					pd.put("schoolId", id);// 学校id
					pd.put("s_phone2", p.get("s_phone2"));// 电话号码2
					pd.put("s_password2", p.get("s_password2"));// 密码2
					pd.put("s_phone3", p.get("s_phone3"));// 电话号码3
					pd.put("s_password3", p.get("s_password3"));// 密码3
					pd.put("s_stu_no", p.get("s_stu_no"));// 学号
					pd.put("s_name", p.get("s_name"));// 学生姓名
					pd.put("s_addr", p.get("s_addr"));// 住址
					pd.put("s_sex", p.get("s_sex"));// 性别
					pd.put("s_zone_id", p.get("s_zone_id"));// 班级名称
					pd.put("school_name", p.get("school_name"));// 学校名称
					pd.put("s_entry_year", p.get("s_entry_year"));// 入学年份
					pd.put("s_cardId", p.get("stu_cardId"));// CardId
					pd.put("s_status", p.get("s_status"));// 订阅状态
					pd.put("parent_name", p.get("parent_name"));// 家长姓名
					if (p.get("s_phone1") == null || "".equals(p.get("s_phone1"))) {
						continue;
					}   
					SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
					if (p.get("s_start_time") != null) {

						String start_time = p.get("s_start_time").toString();
						try {
							Date date = sdf1.parse(start_time);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String sDate = sdf.format(date);
							pd.put("s_start_time", sDate);// 开通日期
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (p.get("s_end_time") != null) {
						String end_time = p.get("s_end_time").toString();
						try {
							Date date = sdf1.parse(end_time);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String eDate = sdf.format(date);
							pd.put("s_end_time", eDate);// 到期时间
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					excelPdList.add(pd);
				}
				/********** 校验start *********/
				for (PageData p : excelPdList) {
					// 非空校验
					HashMap<String, String> column_name = new HashMap<String, String>() {
						{
							put("s_name", " 学生姓名");
							put("parent_name", "家长姓名");
							// put("s_addr", "住址");
							put("s_sex", "性别");
							put("s_name", "姓名");
							put("s_phone1", "手机号1");
							put("PASSWORD_UNENCRYPTED", "密码");
							//put("s_cardId", "CardId");
							put("s_zone_id", "班级名称");
							//put("s_status", "订阅状态");

						}
					};
					Iterator iter = column_name.entrySet().iterator();
					// 非空校验
					while (iter.hasNext()) {
						Entry entry = (Entry) iter.next();
						Object s = p.get(entry.getKey());
						if (s == null || "".equals(s) || "null".equals(s)) {
							msg = "\"" + entry.getValue() + "\"不能为空";
							return msg;
						}
					}

					// // 学号格式、长度
					// Pattern pattern1 = Pattern.compile("[0-9A-Za-z]*");
					// if(!"".equals(p.getString("stu_no"))){
					// Matcher m1 = pattern1.matcher(p.getString("stu_no"));
					// if (!m1.matches() || p.getString("stu_no").length() > 16)
					// {
					// msg = "学号\"" + p.getString("stu_no") + "\"格式不正确";
					// return msg;
					// }
					// // 学号重复
					// int rowNum;
					// try {
					// rowNum = (int)
					// dao.findForObject("MemberMapper.check_stuNo", p);
					// if (rowNum > 0) {
					// msg = "学号\"" + p.getString("stu_no") + "\"已存在";
					// return msg;
					// }
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					//
					// }

					List<PageData> z_zone_list = new ArrayList<PageData>();
					try {
					/*	z_zone_list = (List<PageData>) dao.findForList("MemberMapper.check_zoneNm", p);
						boolean checkResult = false;
						// 判断学校名称是否存在
						for (int i = 0; i < z_zone_list.size(); i++) {
							PageData list_school_name = z_zone_list.get(i);
							if (list_school_name.get("z_name").equals(p.get("school_name"))) {
								checkResult = true;
								break;
							}
						}
						if (!checkResult) {
							msg = "学校名称\"" + p.getString("school_name") + "\"不存在";
							return msg;
						}
						checkResult = false;*/
						
						List<PageData> psL=(List<PageData>)llkdao.findForList("MemberMapper.check_zoneNm", p);
						boolean checkResult = false;
						for (int i = 0; i < psL.size(); i++) {
							PageData ps = psL.get(i);
							if (ps.get("z_name").equals(p.get("school_name"))) {
								checkResult = true;
								break;
							}
						}
						if (!checkResult) {
							msg = "学校名称\"" + p.getString("school_name") + "\"不存在";
							return msg;
						}
						checkResult = false;
						
						int g_id =0;
						List<PageData> pgs=(List<PageData>) llkdao.findForList("MemberMapper.check_class_grade", p.get("schoolId"));
						for (int i = 0; i < pgs.size(); i++) {
							PageData list_entry_year = pgs.get(i);
							if (list_entry_year.get("z_name").equals(p.get("s_entry_year"))) {
								g_id =  (int) list_entry_year.get("z_id");  //年份ID
								checkResult = true;
								break;
							}
						}
						if (!checkResult) {
							msg = "入学年份\"" + p.getString("s_entry_year") + "\"不存在";
							return msg;
						}
						checkResult = false;
						
						// 判断班级名称是否存在
						List<PageData> pcs = (List<PageData>) llkdao.findForList("MemberMapper.check_class_grade", String.valueOf(g_id));
						for (int i = 0; i < pcs.size(); i++) {
							PageData list_zone = pcs.get(i);
							if (list_zone.get("z_name").equals(p.get("s_zone_id"))) {
									// 获取班级id
									p.put("classId", list_zone.get("z_id"));
									checkResult = true;
									break;
							}
						}
						if (!checkResult) {
							msg = "班级名称\"" + p.getString("s_zone_id") + "\"不存在";
							return msg;
						}
						checkResult = false;

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
					// 学生姓名长度
					if (p.getString("s_name").length() > 10) {
						msg = "姓名\"" + p.getString("s_name") + "\"过长";
						return msg;
					}
					// 住址长度
					// if (p.getString("s_addr").length() > 32) {
					// msg = "住址\"" + p.getString("s_addr") + "\"过长";
					// return msg;
					// }
					// 订阅状态格式（开通或未开通）
					
					/*if (!("1".equals(p.get("s_status").toString()) || "0".equals(p.get("s_status").toString()))) {
						msg = "学生\"" + p.getString("s_status") + "\"订阅状态不正确";
						return msg;
					}*/
					Pattern pattern2 = Pattern.compile(
							"(^[1-2][0-9][0-9][0-9][-](([0][1,3,5,7,8])|([1][0,2]))[-](([0][0-9])|([1-2][0-9])|([3][0-1]))$)|(^[1-2][0-9][0-9][0-9][-](([0][4,6,9])|([1][1]))[-](([0][0-9])|([1-2][0-9])|([3][0]))$)");
					// 开通日期格式
					/*if (!Tools.isEmpty(p.getString("s_start_time"))) {

						Matcher m2 = pattern2.matcher(p.getString("s_start_time"));
						if (!m2.matches() || p.getString("s_start_time").length() > 10) {
							msg = "开通日期\"" + p.getString("s_start_time") + "\"格式不正确";
							return msg;
						}

					}*/

					// 到期时间格式
				/*	if (!Tools.isEmpty(p.getString("s_end_time"))) {
						Matcher m3 = pattern2.matcher(p.getString("s_end_time"));
						if (!m3.matches() || p.getString("s_end_time").length() > 10) {
							msg = "到期时间\"" + p.getString("s_end_time") + "\"格式不正确";
							return msg;
						}
					}*/
					
					// 性别格式（男或女）
					if (!("1".equals(p.get("s_sex").toString()) || "0".equals(p.get("s_sex").toString()))) {
						msg = "学生\"" + p.getString("s_name") + "\"性别不正确";
						return msg;
					}

					// 手机号码格式
					Pattern pattern3 = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
					Matcher m4 = pattern3.matcher(p.getString("s_phone1"));
					if (!m4.matches()) {
						msg = "手机号1\"" + p.getString("s_phone1") + "\"格式不正确";
						return msg;
					}
					// 手机号码2
					if (!Tools.isEmpty(p.getString("s_phone2"))) {

						// 手机号码格式
						Matcher m5 = pattern3.matcher(p.getString("s_phone2").toString());
						if (!m5.matches()) {
							msg = "手机号2\"" + p.getString("s_phone2").toString() + "\"格式不正确";
							return msg;
						}
					}
					// 手机号码3
					if (!Tools.isEmpty(p.getString("s_phone3"))) {

						// 手机号码格式
						Matcher m5 = pattern3.matcher(p.getString("s_phone3"));
						if (!m5.matches()) {
							msg = "手机号3\"" + p.getString("s_phone3") + "\"格式不正确";
							return msg;
						}

					}

					// 密码格式
					Pattern pattern4 = Pattern.compile("[0-9A-Za-z]*");
					Matcher m6 = pattern4.matcher(p.getString("PASSWORD_UNENCRYPTED"));
					if (!m6.matches() || p.getString("PASSWORD_UNENCRYPTED").length() < 6
							|| p.getString("PASSWORD_UNENCRYPTED").length() > 16) {
						msg = "密码\"" + p.getString("PASSWORD_UNENCRYPTED") + "\"格式不正确";
						return msg;
					}
					// // 邮箱格式
					// Pattern pattern5 = Pattern.compile(
					// "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
					// Matcher m5 = pattern5.matcher(p.getString("EMAIL"));
					// if (!m5.matches()) {
					// msg = "邮箱\"" + p.getString("EMAIL") + "\"格式不正确";
					// return msg;
					// }
				}
				if ("".equals(msg)) {
					// Subject currentUser = SecurityUtils.getSubject();
					// Session session = currentUser.getSession();
					// User user = (User)
					// session.getAttribute(Const.SESSION_USER);
					// 用户id
					// String areaUser_id = user.getUSER_ID();
					// 用户班级id
					// String classId = "";
					try {
						// classId = (String)
						// dao.findForObject("MemberMapper.check_className",
						// areaUser_id);
						// 添加
						for (PageData p : excelPdList) {
							p.put("schoolId", id);
							/*int rowNum2 = (int) llkdao.findForObject("MemberMapper.check_Phone",
									p.getString("s_phone1").toString());
						   // int rowNum3 = (int) dao.findForObject("MemberMapper.check_CardId", p.getString("s_cardId").toString());
							if(rowNum3>0){
								msg = "学生【"+ p.getString("s_name")+"】的CardId:\"" + p.getString("s_cardId") + "\"已存在";
								return msg;
							}
							if (rowNum2 > 0) {
								// 修改学生信息
								llkdao.update("MemberMapper.update_parent", p);
								// 修改家长 信息
								llkdao.update("MemberMapper.update_student", p);
							} else {
								// 添加学生
								llkdao.save("MemberMapper.save_student", p);
								Object stuObj = p.get("s_id");
								if (stuObj == null) {
									msg = "【学生数据异常】";
									return msg;
								}
								// 添加家长
								llkdao.save("MemberMapper.save_parent", p);
								// 添加学生家长关联
								llkdao.save("MemberMapper.saveRelation", p);
								// sendMail(p);// 发送邮件
							}*/
							// 添加学生
							llkdao.save("MemberMapper.save_student", p);
							Object stuObj = p.get("s_id");
							if (stuObj == null) {
								msg = "【学生数据异常】";
								return msg;
							}
							// 添加家长
							llkdao.save("MemberMapper.save_parent", p);
							// 添加学生家长关联
							llkdao.save("MemberMapper.saveRelation", p);
							// sendMail(p);// 发送邮件
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					msg = Const.SUCCESS;
				}
			}
			return msg;
			/*
			 * PageData pd = new PageData(); String errorMsg = "";
			 * 
			 * try { Subject currentUser = SecurityUtils.getSubject(); Session
			 * session = currentUser.getSession(); Object obj =
			 * session.getAttribute(Const.SESSION_USER); if (obj == null) {
			 * errorMsg = "【会话异常】"; return errorMsg; } User user = (User) obj;
			 * pd.put("USER_ID", user.getUSER_ID()); Object teacherObj =
			 * dao.findForObject("MemberMapper.getTeacherZoneIdById", pd); if
			 * (teacherObj == null) { errorMsg = "【教师数据异常】"; return errorMsg; }
			 * PageData teacherData = (PageData) teacherObj; pd.put("z_id",
			 * teacherData.get("Z_ID"));// 老师的班级 List<PageData> stuNos =
			 * (List<PageData>)
			 * dao.findForList("MemberMapper.getStuByTeaZoneId", pd);
			 * HashSet<String> stuNoSet = new HashSet<>(); HashSet<String>
			 * phoneSet = new HashSet<>(); stuNos.stream().forEach(it -> { if
			 * (StringUtils.isNotBlank(it.getString("stu_no"))) {
			 * stuNoSet.add(it.getString("stu_no")); } if
			 * (StringUtils.isNotBlank(it.getString("phone"))) {
			 * phoneSet.add(it.getString("phone")); } }); for (HashMap<String,
			 * Object> item : result) { if
			 * (stuNoSet.contains(item.get("s_stu_no"))) { errorMsg += "【学号:" +
			 * item.get("s_stu_no") + "重复，未被导入】"; continue; } if
			 * (phoneSet.contains(item.get("parent_phone"))) { errorMsg +=
			 * "【手机号:" + item.get("parent_phone") + "重复，未被导入】"; continue; } if
			 * (item.keySet().size() != 12) { continue; } pd.clear();
			 * pd.put("s_zone_id", teacherData.get("Z_ID")); pd.putAll(item);
			 * dao.save("MemberMapper.saveStu", pd); Object stuObj =
			 * pd.get("s_id"); if (stuObj == null) { errorMsg = "【学生数据异常】";
			 * return errorMsg; } pd.put("u_type", 3); pd.put("USER_ID",
			 * UuidUtil.get32UUID()); dao.save("MemberMapper.saveParent", pd);
			 * Object parentId = pd.getString("USER_ID"); if (parentId == null)
			 * { errorMsg = "【家长数据异常】"; return errorMsg; }
			 * dao.save("MemberMapper.stuParent", pd); // sendMail(pd);// 发送邮件 }
			 * if (errorMsg.length() <= 0) { errorMsg = "导入成功"; } } catch
			 * (Exception e) { e.printStackTrace(); } return errorMsg;
			 */
		}

		@Override
		public KeyValue getColKeyValue(int colIdx, HSSFCell cell) {
			KeyValue kv = new KeyValue();
			Object obj = null;
			switch (colIdx) {
			case 0:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("school_name").setValue(String.valueOf(obj));
				} else {
					kv.setKey("school_name").setValue(null);
				}
				break;
			case 1:
				obj = getCellValue(cell);
				if (obj != null) {
					String s_entre_year = String.valueOf(obj).replace(".0", "");
					kv.setKey("s_entry_year").setValue(s_entre_year);
				} else {
					kv.setKey("s_entry_year").setValue(null);
				}
				break;
			case 2:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("s_zone_id").setValue(String.valueOf(obj));
				} else {
					kv.setKey("s_zone_id").setValue(null);
				}
				break;
			case 3:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						kv.setKey("s_phone1").setValue(phone = String.valueOf(d.longValue()));
					} else {
						kv.setKey("s_phone1").setValue(phone = String.valueOf(obj));
					}
				} else {
					kv.setKey("s_phone1").setValue(null);
				}
				break;
			case 4:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						String p = String.valueOf(d.longValue());
						kv.Tag = new KeyValue("matePwd", p);
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password1")//
								.setValue(pwd);
					} else {
						String p = String.valueOf(obj);
						kv.Tag = new KeyValue("matePwd", p);
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password1")//
								.setValue(pwd);
					}
				} else {
					kv.setKey("s_password1").setValue(null);
				}
				break;
			case 5:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("parent_name").setValue(String.valueOf(obj));
				} else {
					kv.setKey("parent_name").setValue(null);
				}
				break;
			case 6:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("s_name").setValue(String.valueOf(obj));
				} else {
					kv.setKey("s_name").setValue(null);
				}
				break;

			case 7:
				obj = getCellValue(cell);
				if (obj != null) {
					Object v = String.valueOf(obj);
					if (v != null) {
						Object sex = null;
						if ("男".equals(v)) {
							sex = 1;
						} else {
							sex = 0;
						}
						kv.setKey("s_sex").setValue(sex);
					}
				} else {
					kv.setKey("s_sex").setValue(null);
				}
				break;

			case 8:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("s_addr").setValue(String.valueOf(obj));
				}
				break;
			case 9:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("s_stu_no").setValue(String.valueOf(obj));
				} else {
					kv.setKey("s_stu_no").setValue(null);
				}
				break;
			case 10:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						kv.setKey("s_phone2").setValue(String.valueOf(d.longValue()));
					} else {
						kv.setKey("s_phone2").setValue(String.valueOf(obj));
					}
				} else {
					kv.setKey("s_phone2").setValue(null);
				}
				break;
			case 11:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						String p = String.valueOf(d.longValue());
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password2")//
								.setValue(pwd);
					} else {
						String p = String.valueOf(obj);
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password2")//
								.setValue(pwd);
					}
				} else {
					kv.setKey("s_password2").setValue(null);
				}
				break;
			case 12:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						kv.setKey("s_phone3").setValue(String.valueOf(d.longValue()));
					} else {
						kv.setKey("s_phone3").setValue(String.valueOf(obj));
					}
				} else {
					kv.setKey("s_phone3").setValue(null);
				}
				break;
			case 13:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						String p = String.valueOf(d.longValue());
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password3")//
								.setValue(pwd);
					} else {
						String p = String.valueOf(obj);
						String pwd = new SimpleHash("SHA-1", phone, p).toString();
						kv.setKey("s_password3")//
								.setValue(pwd);
					}
				} else {
					kv.setKey("s_password3").setValue(null);
				}
				break;
			case 14:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("stu_cardId").setValue(String.valueOf(obj));
				} else {
					kv.setKey("stu_cardId").setValue(null);
				}
				break;

			case 15:
				obj = getCellValue(cell);
				if (obj != null) {
					Object v = String.valueOf(obj);
					if (v != null) {
						Object sex = null;
						if ("开通".equals(v)) {
							sex = 1;
						} else {
							sex = 0;
						}
						kv.setKey("s_status").setValue(sex);
					}
				} else {
					kv.setKey("s_status").setValue(null);
				}
				break;
			case 16:
				if (cell != null) {
					kv.setKey("s_start_time").setValue(cell.getDateCellValue());
				} else {
					kv.setKey("s_start_time").setValue(null);
				}
				break;
			case 17:
				if (cell != null) {
					kv.setKey("s_end_time").setValue(cell.getDateCellValue());
				} else {
					kv.setKey("s_end_time").setValue(null);
				}
				break;
			// case 0:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("s_stu_no").setValue(String.valueOf(obj));
			// }
			// break;
			// case 1:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("s_name").setValue(String.valueOf(obj));
			// }
			// break;
			// case 2:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("s_addr").setValue(String.valueOf(obj));
			// }
			// break;
			// case 3:
			// if (cell != null) {
			// kv.setKey("s_birthday").setValue(cell.getDateCellValue());
			// }
			// break;
			// case 4:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// Object v = String.valueOf(obj);
			// if (v != null) {
			// Object sex = null;
			// if ("男".equals(v)) {
			// sex = 1;
			// } else {
			// sex = 0;
			// }
			// kv.setKey("s_sex").setValue(sex);
			// }
			// }
			// break;
			// case 5:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("parent_name").setValue(String.valueOf(obj));
			// }
			// break;
			// case 6:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// if (obj instanceof Double) {
			// Double d = (Double) obj;
			// kv.setKey("parent_phone").setValue(phone =
			// String.valueOf(d.longValue()));
			// } else {
			// kv.setKey("parent_phone").setValue(phone = String.valueOf(obj));
			// }
			// }
			// break;
			// case 7:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// if (obj instanceof Double) {
			// Double d = (Double) obj;
			// String p = String.valueOf(d.longValue());
			// kv.Tag = new KeyValue("matePwd", p);
			// String pwd = new SimpleHash("SHA-1", phone, p).toString();
			// kv.setKey("parent_password")//
			// .setValue(pwd);
			// } else {
			// String p = String.valueOf(obj);
			// kv.Tag = new KeyValue("matePwd", p);
			// String pwd = new SimpleHash("SHA-1", phone, p).toString();
			// kv.setKey("parent_password")//
			// .setValue(pwd);
			// }
			// }
			// break;
			// case 8:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("s_device_e_id").setValue(String.valueOf(obj));
			// }
			// break;
			// case 9:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("s_device_u_id").setValue(String.valueOf(obj));
			// }
			// break;
			// case 10:
			// obj = getCellValue(cell);
			// if (obj != null) {
			// kv.setKey("parent_email").setValue(String.valueOf(obj));
			// }
			// break;
			default:
				break;
			}
			return kv;
		}
	}

	@SuppressWarnings("unchecked")
	public PageData findClassIdByUserId(PageData pd) throws Exception {
		PageData result = null;
		Object object = dao.findForList("MemberMapper.findClassIdByUserId", pd);
		if (object != null) {
			List<PageData> list = (List<PageData>) object;
			result = list.size() > 0 ? list.get(0) : null;
		}
		return (result);
	}

	/**
	 * 验证手机号是否存在 2016/9/1 yc 添加
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkPhone(String phone) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pdf = new PageData();
		String errInfo = "";
		int count = (int) dao.findForObject("MemberMapper.check_Phone_All", phone);
		if (count == 0) {
			errInfo = "notexist";
		}
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 验证手机号是否存在 2016/9/14 yc 添加
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public int checkTeaPhone(String phone) throws Exception {
		int count = (int) dao.findForObject("MemberMapper.check_TeaPhone", phone);
		return count;
	}
	
	/**
	 * 手机号验证码发送次数
	 * 
	 * @param PHONE
	 * @return pd
	 * @throws Exception
	 */
	public PageData selectIntervalPhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MemberMapper.selectIntervalPhone", pd);
	}
	
	/**
	 * 插入手机号和次数
	 * 
	 * @param PHONE
	 * @throws Exception
	 */
	public void saveIntervalPhone(PageData pd) throws Exception {
		dao.save("MemberMapper.saveIntervalPhone", pd);
	}
	
	/**
	 * 更新验证码发送的次数
	 * 
	 * @param PHONE,COUNT
	 * @throws Exception
	 */
	public void updateIntervalPhone(PageData pd) throws Exception {
		dao.update("MemberMapper.updateIntervalPhone", pd);
	}
}
