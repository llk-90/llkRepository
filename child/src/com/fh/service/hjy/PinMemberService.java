package com.fh.service.hjy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import com.fh.util.cmcc.OpenService;
import com.fh.util.cmcc.Response;
import com.fh.util.PageData;
import com.fh.util.mail.MailSenderInfo;
import com.fh.util.mail.SimpleMailSender;

@Service("pinMemberService")
public class PinMemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 导入
	 * 
	 * @param myfiles
	 * @return
	 * @throws IOException
	 */
	public String readExcel(MultipartFile myfiles, String id) throws IOException {
		InputStream in = myfiles.getInputStream();
		ImportExcel ie = new ImportExcel(
				in
				, new RectIdx(0, 1, null, null)
				, new StuAdapter(id));
		return ie.getErrMsgs();
	}

	
	/**
	 * 验证验证码 
	 * @return 
	 */
	public String VerifyCode(String cid, String code, String sid, String packageId) throws Exception{
		String requestXML=
				"<MSG_BODY>"
				+ "<Captcha>" +code+ "</Captcha>"
				+ "<CityId>" +cid+ "</CityId>"
				+ "<StudentId>" +sid+ "</StudentId>"
				+ "<PackageId>" +packageId+ "</PackageId>"
				+ "</MSG_BODY>";
		String msgType = "PUT_CAPTCHA_ORDER";
		Response response = OpenService.openService(requestXML, msgType);
	    String responseXML=response.getBody();
	    System.out.println(responseXML);
//		Document doc = (Document) DocumentHelper.parseText(response.getBody());
//		Element element = doc.getRootElement();
		String result= response.getResult();
		String desc = response.getDesc();
		System.out.println("请求返回结果:"+ result + ";" + desc);
		return  result ;
	}
	
	private class StuAdapter extends DataAdapter {
		@SuppressWarnings("unused")
		private Object phone;
		private String id;

		public StuAdapter(String id) {
			this.id = id;
		}

		@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
		@Override
		public String setData(ArrayList<HashMap<String, Object>> result) {
			String msg = "";
			if (result.size() > 0) {
				// 取得所有参数
				List<PageData> excelPdList = new ArrayList<PageData>();
				for (HashMap<String, Object> p : result) {
					PageData pd = new PageData();
					pd.put("schoolId", id);// 学校id
					pd.put("CityName", p.get("CityName"));// 城市
					pd.put("TownName", p.get("TownName"));// 区域
					pd.put("SchoolName", p.get("SchoolName"));// 学校
					pd.put("ClassName", p.get("ClassName"));// 班级名称
					pd.put("hjy_s_name", p.get("hjy_s_name"));// 学生姓名
					pd.put("hjy_p_phone", p.get("hjy_p_phone").toString().substring(7,11));// 电话号码1
					pd.put("packageId", p.get("packageId"));// 营销Id
					pd.put("hjy_pin_code", p.get("hjy_pin_code"));// 验证码
					try {
						String hjy_s_id = (String) dao.findForObject("PinMemberMapper.findStuId", pd);
						pd.put("hjy_s_id", hjy_s_id);// 学生id
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (p.get("hjy_p_phone") == null || "".equals(p.get("hjy_p_phone"))) {
						continue;
					}
					excelPdList.add(pd);
				}
//				for (PageData p : excelPdList) {
//					// 非空校验
//					HashMap<String, String> column_name = new HashMap<String, String>() {
//						{
//							put("CityName", "城市");
//							put("TownName", "区域");
//							put("hjy_s_id", "学号");
//							put("ClassName", "班级名称");
//							put("hjy_s_name", " 学生姓名");
//							put("hjy_p_phone", "家长电话");
//							put("hjy_pin_code", "验证码");
//						}
//					};
//					Iterator iter = column_name.entrySet().iterator();
//					// 非空校验
//					while (iter.hasNext()) {
//						Entry entry = (Entry) iter.next();
//						Object s = p.get(entry.getKey());
//						if (s == null || "".equals(s) || "null".equals(s)) {
//							msg = "\"" + entry.getValue() + "\"不能为空";
//							return msg;
//						}
//					}
//
//					List<PageData> z_zone_list = new ArrayList<PageData>();
//					try {
//						z_zone_list = (List<PageData>) dao.findForList("PinMemberMapper.check_cityNm", p);
//						boolean checkResult = false;
//						// 判断城市是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_school_name = z_zone_list.get(i);
//							if (list_school_name.get("CityName").equals(p.get("CityName"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "城市\"" + p.getString("CityName") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//						// 判断区域是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_entry_year = z_zone_list.get(i);
//							if (list_entry_year.get("TownName").equals(p.get("TownName"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "区域\"" + p.getString("TownName") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//						// 判断班级名称是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_zone = z_zone_list.get(i);
//							if (list_zone.get("ClassName").equals(p.get("ClassName"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "班级名称\"" + p.getString("ClassName") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//						
//						// 判断学生学号是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_zone = z_zone_list.get(i);
//							if (list_zone.get("StudentId").equals(p.get("hjy_s_id"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "学生ID\"" + p.getString("hjy_s_id") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//						
//						// 判断学生姓名是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_zone = z_zone_list.get(i);
//							if (list_zone.get("hjy_s_name").equals(p.get("hjy_s_name"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "学生姓名\"" + p.getString("hjy_s_name") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//						
//						// 判断家长电话是否存在
//						for (int i = 0; i < z_zone_list.size(); i++) {
//							PageData list_zone = z_zone_list.get(i);
//							if (list_zone.get("hjy_p_phone").equals(p.get("hjy_p_phone"))) {
//								checkResult = true;
//								break;
//							}
//						}
//						if (!checkResult) {
//							msg = "家长电话\"" + p.getString("hjy_p_phone") + "\"不存在";
//							return msg;
//						}
//						checkResult = false;
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					// 学生姓名长度
//					if (p.getString("hjy_s_name").length() > 10) {
//						msg = "姓名\"" + p.getString("hjy_s_name") + "\"过长";
//						return msg;
//					}
//					// 手机号码格式
//					Pattern pattern3 = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
//					Matcher m4 = pattern3.matcher(p.getString("hjy_p_phone"));
//					if (!m4.matches()) {
//						msg = "家长电话\"" + p.getString("hjy_p_phone") + "\"格式不正确";
//						return msg;
//					}
//				}
				if ("".equals(msg)) {
					try {
						// 更新订阅状态
						for (PageData p : excelPdList) {
								dao.update("PinMemberMapper.update_status", p);
								dao.save("PinMemberMapper.save_pin_code", p);
								VerifyCode(p.get("CityName").toString(),
										p.get("hjy_pin_code").toString(),
										p.get("hjy_s_id").toString(),
										p.get("hjy_firm_name").toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					msg = Const.SUCCESS;
				}
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
					kv.setKey("CityName").setValue(String.valueOf(obj));
				} else {
					kv.setKey("CityName").setValue(null);
				}
				break;
			case 1:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("TownName").setValue(String.valueOf(obj));
				} else {
					kv.setKey("TownName").setValue(null);
				}
				break;		
			case 2:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("SchoolName").setValue(String.valueOf(obj));
				} else {
					kv.setKey("SchoolName").setValue(null);
				}
				break;
			case 3:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("ClassName").setValue(String.valueOf(obj));
				} else {
					kv.setKey("ClassName").setValue(null);
				}
				break;
			case 4:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("hjy_s_name").setValue(String.valueOf(obj));
				} else {
					kv.setKey("hjy_s_name").setValue(null);
				}
				break;
			case 5:
				obj = getCellValue(cell);
				if (obj != null) {
					if (obj instanceof Double) {
						Double d = (Double) obj;
						kv.setKey("hjy_p_phone").setValue(phone = String.valueOf(d.longValue()));
					} else {
						kv.setKey("hjy_p_phone").setValue(phone = String.valueOf(obj));
					}
				} else {
					kv.setKey("hjy_p_phone").setValue(null);
				}
				break;
			case 6:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("packageId").setValue(String.valueOf(obj));
				} else {
					kv.setKey("packageId").setValue(null);
				}
				break;
			case 7:
				obj = getCellValue(cell);
				if (obj != null) {
					kv.setKey("hjy_pin_code").setValue(String.valueOf(obj));
				} else {
					kv.setKey("hjy_pin_code").setValue(null);
				}
				break;
			default:
				break;
			}
			return kv;
		}
	}

	/**
	 * 查找学生Id
	 */
	public String findStuId(PageData pd) throws Exception {
		return (String) dao.findForObject("PinMassSendMapper.findStuId", pd);
	}
}
