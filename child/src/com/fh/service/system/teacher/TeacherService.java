package com.fh.service.system.teacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.fh.util.mail.SimpleMailSender;

@Service("teacherService")
public class TeacherService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		pd.put("parent_id",getUserId());
		pd.put("SCHOOL_ID",pd.get("schoolParam"));
		dao.save("TeacherMapper.save", pd);
	}

	/*
	 * 新增关联
	 */
	public void saveRelation(PageData pd) throws Exception {
		dao.save("TeacherMapper.saveRelation", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("TeacherMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("TeacherMapper.edit", pd);
		dao.update("TeacherMapper.editRelation", pd);
	}

	/*
	 * 修改
	 */
	public void editUserPhone(PageData pd) throws Exception {
		dao.update("TeacherMapper.editUserPhone", pd);
	}

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		page.getPd().put("parent_id",getUserId());
		return (List<PageData>) dao.findForList("TeacherMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TeacherMapper.findById", pd);
	}

	/*
	 * 通过userid获取区域（区域经理）
	 */
	public PageData getZoneIdByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TeacherMapper.getZoneIdByUserId", pd);
	}

	/*
	 * 通过userid获取区域（教师）
	 */
	public PageData getZoneIdByTeacher(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TeacherMapper.getZoneIdByTeacher", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findJSRole(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.findJSRole", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("TeacherMapper.deleteRelationWx", ArrayDATA_IDS);
		dao.delete("TeacherMapper.deleteRelation", ArrayDATA_IDS);
		dao.delete("TeacherMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> schoolList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.schoolList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> gradeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.gradeList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> classList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.classList", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> classListAndNow(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.classListAndNow", pd);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> classListAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TeacherMapper.classListAll", pd);
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
		String errInfo = "";

		PageData p = (PageData) dao.findForObject("TeacherMapper.checkTel", pd);
		if ("".equals(pd.getString("phone_old"))) {
			if (p != null && p.getString("PHONE") != null) {
				errInfo = "exist";
			}
		} else {
			if (p != null && p.getString("PHONE") != null && !pd.getString("phone_old").equals(p.getString("PHONE"))) {
				errInfo = "exist";
			}
		}

		map.put("result", errInfo);
		return map;
	}

	/*
	 * 通过老师查找班级-->学生-->家长
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> contacts(PageData pd) throws Exception {
		Map<String, PageData> map = new HashMap<String, PageData>();
		List<PageData> list_stu_parent = new ArrayList<PageData>();
		List<PageData> list = (List<PageData>) dao.findForList("TeacherMapper.contacts", pd);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				PageData stup = list.get(i);
				String s_name = stup.getString("s_name");
				if (map.containsKey(s_name)) {
					PageData pda = map.get(s_name);
					String pname = pda.getString("pname");
					pname += "/" + stup.getString("pname");
					pda.put("pname", pname);
				} else {
					stup.put("messageUrl", pd.get("messageUrl") + "&lm_send_user_id=" + stup.getString("puser_id"));
					map.put(s_name, stup);
				}
			}
			for (Map.Entry<String, PageData> m : map.entrySet()) {
				PageData pstu = m.getValue();
				list_stu_parent.add(pstu);
			}
		}
		return list_stu_parent;

	}

	/*
	 * 导入
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public String readExcel(MultipartFile file) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		// 用户名
		String areaUser_id = user.getUSER_ID();
		String firstMsg = "";

		if (null != file && !file.isEmpty()) {
			try {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
				String fileName = FileUpload.fileUp(file, filePath, "Import_teacher"); // 执行上传
				List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0); // 执行读EXCEL操作,读出的数据导入List
				List<PageData> tempCheck = (List) ObjectExcelRead.readExcel(filePath, fileName, 0, 0, 0); // 执行读EXCEL操作,读出的数据导入List
				if (!"学校名称".equals(tempCheck.get(0).get("var0").toString())) {
					firstMsg = "excel模板不正确";
					return firstMsg;
				}
				/**
				 * var0 :学校名称; var1 :年级; var2 :班级; var3 :教师名称; var4 ：性别; var5
				 * ：手机号码;var6 :邮箱; var7 ：登录密码; var8 ：备注;
				 */
				// 取得所有参数
				List<PageData> excelPdList = new ArrayList<PageData>();
				for (PageData p : listPd) {
					PageData pd = new PageData();
					pd.put("USER_ID", UuidUtil.get32UUID()); // 主键
					pd.put("PHONE", p.getString("var5"));// 手机号
					pd.put("PASSWORD_UNENCRYPTED", p.getString("var7"));
					pd.put("PASSWORD",
							new SimpleHash("SHA-1", pd.getString("PHONE"), pd.getString("PASSWORD_UNENCRYPTED"))
									.toString());// 密码
					pd.put("NAME", p.getString("var3"));// 姓名
					if ("男".equals(p.getString("var4"))) {
						pd.put("SEX", "1");// 性别
					} else if ("女".equals(p.getString("var4"))) {
						pd.put("SEX", "0");// 性别
					} else {
						pd.put("SEX", p.getString("var4"));// 性别
					}
					pd.put("ROLE_ID", "fea087fda14148e0ad38f48356027e61");// 角色
					pd.put("BZ", p.getString("var8"));// 备注
					pd.put("EMAIL", p.getString("var6"));// 密码
					pd.put("schoolName", p.getString("var0"));// 学校名称
					pd.put("gradeName", p.getString("var1"));// 年级名称
					pd.put("className", p.getString("var2"));// 班级名称
					excelPdList.add(pd);
				}
				/********** 校验start *********/
				int i = 1;
				for (PageData p : excelPdList) {
					i++;
					HashMap<String, String> column_name = new HashMap<String, String>() {
						{
							put("schoolName", "学校名称");
							put("gradeName", "年级名称");
							put("className", "班级名称");
							put("NAME", "教师姓名");
							put("SEX", "性别");
							put("PHONE", "手机号");
							put("EMAIL", "邮箱");
							put("PASSWORD_UNENCRYPTED", "登录密码");
							put("BZ", "备注");
						}
					};
					Iterator iter = column_name.entrySet().iterator();
					// 非空校验
					while (iter.hasNext()) {
						Entry entry = (Entry) iter.next();
						if (Tools.isEmpty(p.getString(entry.getKey()))) {
							firstMsg = "第" + i + "行：\"" + entry.getValue() + "\"不能为空";
							return firstMsg;
						}
					}
					// 校验字符串长度
					while (iter.hasNext()) {
						Entry entry = (Entry) iter.next();
						if (!Tools.isEmpty(p.getString(entry.getKey()))) {
							if (p.getString(entry.getKey()).length() > 32) {
								firstMsg = "第" + i + "行：\"" + entry.getValue() + "\"长度不能超过32";
								return firstMsg;
							}
						}
					}
					// 校验学校是否在当前区域下
					p.put("areaUser_id", areaUser_id);
					//2016/9/5 yc  添加条件
					p.put("USER_ID1",areaUser_id);
					PageData pd = (PageData) dao.findForObject("TeacherMapper.getZoneIdByUserId1", p);
					p.put("z_id", pd.get("Z_ID"));					
					PageData p4 = (PageData) dao.findForObject("TeacherMapper.checkSchool", p);
					if (p4 == null || Tools.isEmpty(p4.get("z_id").toString())) {
						firstMsg = "第" + i + "行：学校\"" + p.getString("schoolName") + "\"不在当前区域下";
						return firstMsg;
					}
					p.put("SCHOOL_ID",p4.get("z_id").toString());
					// 校验班级是否存在
					PageData p1 = (PageData) dao.findForObject("TeacherMapper.checkClassId", p);
					if (p1 == null || Tools.isEmpty(p1.get("z_id").toString())) {
						firstMsg = "第" + i + "行：班级\"" + p.getString("className") + "\"不存在";
						return firstMsg;
					} else {
						// 校验班级是否存在班主任
						PageData p2 = (PageData) dao.findForObject("TeacherMapper.checkTeacherId", p1);
						if (p2 != null && !Tools.isEmpty(p2.getString("ut_user_id"))) {
							firstMsg = "第" + i + "行：班级\"" + p.getString("className") + "\"已存在教师";
							return firstMsg;
						}
					}
					// 性别格式（男或女）
					if (!("1".equals(p.get("SEX").toString()) || "0".equals(p.get("SEX").toString()))) {
						firstMsg = "第" + i + "行：性别\"" + p.get("SEX").toString() + "\"错误，只能输入男或女";
						return firstMsg;
					}
					// 校验手机号码格式
					Pattern pattern1 = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
					Matcher m1 = pattern1.matcher(p.getString("PHONE"));
					if (!m1.matches()) {
						firstMsg = "第" + i + "行：手机号\"" + p.getString("PHONE") + "\"格式不正确";
						return firstMsg;
					}
					// 校验手机号重复
					PageData p3 = (PageData) dao.findForObject("TeacherMapper.checkTel", p);
					if (p3 != null && !Tools.isEmpty(p3.getString("USER_ID"))) {
						firstMsg = "第" + i + "行：手机号\"" + p.getString("PHONE") + "\"已存在";
						return firstMsg;
					}
					// 校验邮箱格式
					Pattern pattern2 = Pattern.compile(
							"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
					Matcher m2 = pattern2.matcher(p.getString("EMAIL"));
					if (!m2.matches()) {
						firstMsg = "第" + i + "行：邮箱\"" + p.getString("EMAIL") + "\"格式不正确";
						return firstMsg;
					}
					// 校验密码格式
					Pattern pattern3 = Pattern.compile("[0-9A-Za-z]*");
					Matcher m3 = pattern3.matcher(p.getString("PASSWORD_UNENCRYPTED"));
					if (!m3.matches() || p.getString("PASSWORD_UNENCRYPTED").length() < 6) {
						firstMsg = "第" + i + "行：密码\"" + p.getString("PASSWORD_UNENCRYPTED") + "\"格式不正确";
						return firstMsg;
					}

				}
				/********** 校验END *********/
				if ("".equals(firstMsg)) {
					for (PageData p : excelPdList) {
						p.put("parent_id", areaUser_id);
						dao.save("TeacherMapper.save", p);
						// 根据学校、年级、班级查询班级id
						PageData p1 = (PageData) dao.findForObject("TeacherMapper.checkClassId", p);
						p.put("classParam", p1.get("z_id").toString());// 班级
						dao.save("TeacherMapper.saveRelation", p);
					}
				}
				if ("".equals(firstMsg)) {
					for (PageData p : excelPdList) {
						SimpleMailSender.sendEmails(Const.SMTP, Const.PORT, Const.EMAIL, Const.PAW,
								p.getString("EMAIL"), "掌上关爱教师帐号",
								"您的用户名为" + p.getString("PHONE") + "，您的密码为" + p.getString("PASSWORD_UNENCRYPTED") + "。",
								"1");
					}
				}
			} catch (Exception e) {
				return firstMsg;
			}
		}
		return firstMsg;
	}
	/**
	 * 获取用户ID
	 * 2016/9/5 yc  添加
	 * @return
	 */
	private String getUserId(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user =(User)session.getAttribute(Const.SESSION_USER);
		if(user ==null || "".equals(user.getUSER_ID())){
			return null;
		}
		return user.getUSER_ID();
	}
}
