package com.fh.service.weixinplus.openCard;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.ImportExcel;
import com.fh.util.ImportExcel.DataAdapter;
import com.fh.util.ImportExcel.KeyValue;
import com.fh.util.ImportExcel.RectIdx;
import com.fh.util.PageData;
import com.fh.util.mail.MailSenderInfo;
import com.fh.util.mail.SimpleMailSender;

@Service("stuOpenCardService")
public class StuOpenCardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StuOpenCardMapper.datalistPage", page);
	}
	
	/*
	 * 通过userId获取该学校管理员的学校名称
	 */
	public PageData findSchoolName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StuOpenCardMapper.findSchoolName", pd);
	}
	
	/*
	 * //找到对应的年级和班级
	 */
	public PageData findGradeClaName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StuOpenCardMapper.findGradeClaName", pd);
	}
	
	/**
	 * 
	 * 根据学校id找对应学校的年级List
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findGradeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StuOpenCardMapper.findGradeList", pd);
	}
	
	/**
	 * 
	 * 根据年级id找对应的班级List
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findClassList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StuOpenCardMapper.findClassList", pd);
	}

	/*
	 * xft_user_syn, xft_customer表插入
	 */
	public void save(PageData pd) throws Exception {
		//xft_user_syn表插入
		dao.save("StuOpenCardMapper.saveUserSyn", pd);
		//xft_customer表插入
		dao.save("StuOpenCardMapper.saveCustomer", pd);
	}

	/*
	 * 通过StudentId获取数据
	 */
	public PageData findByStudentId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StuOpenCardMapper.findByStudentId", pd);
	}
	
	/*
	 * 修改基本信息
	 */
	public void edit(PageData pd) throws Exception {
		//xft_user_syn表修改
		dao.update("StuOpenCardMapper.editUserSyn", pd);
		//xft_customer表修改
		dao.update("StuOpenCardMapper.editCustomer", pd);
	}
	
	/*
	 * 批量删除开卡号码
	 */
	public void deleteAll(String[] IcNo) throws Exception {
		//xft_user_syn表删除
		dao.delete("StuOpenCardMapper.deleteStuCard", IcNo);
		//xft_customer表删除
		dao.delete("StuOpenCardMapper.deleteStuCustomer", IcNo);
	}
	
	/**
	 * 检查卡号是否存在
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean checkHasStuNo(PageData pd) throws Exception {
		Object objs = dao.findForList("StuOpenCardMapper.checkStuNo", pd);
		return objs != null && ((List<PageData>) objs).size() > 0;
	}	
	
	/*
	 * 在xft_customer取得AccountNo最大值
	 */
	public PageData findMaxAccountNo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StuOpenCardMapper.findMaxAccountNo", pd);
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
		private String id;

		public StuAdapter(String id) {
			this.id = id;
		}

		@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
		@Override
		public String setData(ArrayList<HashMap<String, Object>> result) {
			String msg = "";
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);			
			PageData pdSch = new PageData();
			pdSch.put("userId", user.getUSER_ID());
			try {
				//根据登录学校管理员账号找到对应学校名称
				pdSch = (PageData) dao.findForObject("StuOpenCardMapper.findSchoolName", pdSch);
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
			
			if (result.size() > 0) {
				/**
				 * var0:学生姓名;	var1:性别;	var2:年级 ;	var3:班级;	var4:学生卡号
				 */
				// 取得所有参数
				List<PageData> excelPdList = new ArrayList<PageData>();
				for (HashMap<String, Object> p : result) {
					PageData pd = new PageData();
					// 学生姓名
					pd.put("stuName", p.get("name"));
					// 性别
					pd.put("sex", p.get("user_sex"));
					// 年级
					pd.put("GradeName", p.get("grade"));
					// 班级
					pd.put("ClassName", p.get("class"));
                    // 学生卡号
					pd.put("IcNo", p.get("IcNo"));
					// 学校 school_id
					pd.put("school_id", pdSch.get("school_id"));					
					excelPdList.add(pd);
				}
				/********** 校验start *********/
				int line = 1;
				for (PageData p : excelPdList) {
					line++;
					// 非空校验
					HashMap<String, String> column_name = new HashMap<String, String>() {
						{
							put("stuName", "学生姓名");
							put("sex", "性别");
							put("GradeName", "年级");
							put("ClassName", "班级");
							put("IcNo", "学生卡号");

						}
					};
					Iterator iter = column_name.entrySet().iterator();
					// 非空校验
					while (iter.hasNext()) {
						Entry entry = (Entry) iter.next();
						Object s = p.get(entry.getKey());
						if (s == null || "".equals(s) || "null".equals(s)) {
							msg = "第" + line + "行：\"" + entry.getValue() + "\"不能为空";
							return msg;
						}
					}
					
					// 学生姓名长度
					if (p.getString("stuName").length() > 10) {
						msg = "第" + line + "行：姓名\"" + p.getString("stuName") + "\"过长";
						return msg;
					}

					// 性别格式（男或女）
					if (!("男".equals(p.get("sex")) || "女".equals(p.get("sex")))) {
						msg = "第" + line + "行：学生\"" + p.getString("stuName") + "\"性别不正确";
						return msg;
					}

					List<PageData> stuGradeList = new ArrayList<PageData>();
					try {
						stuGradeList = (List<PageData>) dao.findForList("StuOpenCardMapper.findGradeClassList", p);
						
						boolean checkResult = false;
						// 判断年级名称是否存在
						for (int i = 0; i < stuGradeList.size(); i++) {
							PageData gradeName = stuGradeList.get(i);
							if (gradeName.get("GradeName").equals(p.get("GradeName"))) {
								checkResult = true;
								break;
							}
						}
						if (!checkResult) {
							msg = "第" + line + "行：年级名称\"" + p.getString("GradeName") + "\"不存在";
							return msg;
						}
						checkResult = false;
						
						// 判断班级名称是否存在
						for (int i = 0; i < stuGradeList.size(); i++) {
							PageData className = stuGradeList.get(i);
							if (className.get("ClassName").equals(p.get("ClassName"))) {						
									checkResult = true;
									break;
							}
						}
						if (!checkResult) {
							msg = "第" + line + "行：班级名称\"" + p.getString("ClassName") + "\"不存在";
							return msg;
						}
						checkResult = false;

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// 学生卡号格式
					Pattern pattern3 = Pattern.compile("[0-9]*");
					Matcher m4 = pattern3.matcher(p.getString("IcNo"));
					if (!m4.matches()) {
						msg = "第" + line + "行：学生卡号\"" + p.getString("IcNo") + "\"格式不正确，请输入数字!";
						return msg;
					}
					
					// 学生卡号长度
					if (p.getString("IcNo").length() != 10) {
						msg = "第" + line + "行：卡号\"" + p.getString("IcNo") + "\"位数不正确，请输入10位的卡号!";
						return msg;
					}
				}
				if ("".equals(msg)) {
					try {
						// 添加
						for (PageData p : excelPdList) {
							p.put("schoolId", id);
							//在xft_customer取得AccountNo最大值
							Object pd2 = dao.findForObject("StuOpenCardMapper.findMaxAccountNo", p);
							//取得AccountNo最大值+1
							Long maxAccountNo = (long) (((Long) ((PageData) pd2).get("AccountNo")).intValue() + 1);
							p.put("AccNoMax", maxAccountNo);						
							
							//卡号
							Long x = Long.valueOf(p.getString("IcNo"));
							//16进制的卡号转换
							@SuppressWarnings("static-access")
							String cardIDH = x.toHexString(x).toUpperCase();
							p.put("CardIDH", cardIDH);
							
							// IcNo存在条数
							int rowNum2 = (int) dao.findForObject("StuOpenCardMapper.check_IcNO",
									p.getString("IcNo"));
							
							if (rowNum2 > 0) {
								// 修改开卡信息,更新xft_user_syn表
								dao.update("StuOpenCardMapper.editUserSyn", p);
								// 修改开卡信息,更新xft_customer表
								dao.update("StuOpenCardMapper.editCustomer", p);
							} else {																
								// 添加开卡信息,xft_user_syn表插入
								dao.save("StuOpenCardMapper.saveUserSyn", p);								
								// 添加开卡信息,xft_customer表插入
								dao.save("StuOpenCardMapper.saveCustomer", p);
								Object stuObj = p.get("IcNo");
								if (stuObj == null) {
									msg = "【学生数据异常】";
									return msg;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					msg = Const.SUCCESS;
				}
			} else {
				msg = "请填写数据!";
			}
			return msg;
			
		}

		@Override
		public KeyValue getColKeyValue(int colIdx, HSSFCell cell) {
			KeyValue kv = new KeyValue();
			Object obj = null;
			switch (colIdx) {
			case 0:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("name").setValue(String.valueOf(obj));
				} else {
					kv.setKey("name").setValue(null);
				}
				break;
			case 1:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("user_sex").setValue(String.valueOf(obj));
				} else {
					kv.setKey("user_sex").setValue(null);
				}
				break;
			case 2:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("grade").setValue(String.valueOf(obj));
				} else {
					kv.setKey("grade").setValue(null);
				}
				break;
			case 3:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("class").setValue(String.valueOf(obj));
				} else {
					kv.setKey("class").setValue(null);
				}
				break;
			case 4:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						kv.setKey("IcNo").setValue(String.valueOf(d.longValue()));
					} else {
						kv.setKey("IcNo").setValue(String.valueOf(obj));
					}
				} else {
					kv.setKey("IcNo").setValue(null);
				}
				break;
			default:
				break;
			}
			return kv;
		}
	}
}
