package com.fh.controller.teacher;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.teacher.AttendanceService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/attController")
public class AttendanceController extends BaseController {
	String menuUrl = "attController/list.do"; // 菜单地址(权限用)
	
	@Resource(name="attendanceService")
	private AttendanceService attendanceService;
	
	/***
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, "学生考勤列表页面");
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String t = pd.getString("time_start");
//		String zone_id = attendanceService.findZoneId();
		/**
		 * 
		 */
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		String u_type = user.getU_type();
		
//		pd.put("zone_id", zone_id);
		pd.put("user_id", user_id);
		
		if(Const.QUYU.equals(u_type)){
			pd.put("userIdqy", user_id);
			pd.put("u_type", Const.SCHOOLADMIN);
		}else if(Const.JIAOSHI.equals(u_type)){
			pd.put("userIdtc", user_id);
			pd.put("u_type", Const.SCHOOLADMIN);
		}else if(Const.ZUZHNAG.equals(u_type)){
			pd.put("userIdzz", user_id);
			pd.put("u_type", Const.SCHOOLADMIN);
		}else if(Const.KEHU.equals(u_type)){
			pd.put("userIdkh", user_id);
			pd.put("u_type", Const.SCHOOLADMIN);
		}else if(Const.SCHOOLADMIN.equals(u_type)){
			pd.put("userIdsc", user_id);
			pd.put("u_types", Const.SCHOOLADMIN);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		pd.put("date", sdf.format(date));
		page.setPd(pd);
		List<PageData> list = attendanceService.datalistPage(page);
		PageData head = new PageData();
		head = attendanceService.findhead(pd);
		Calendar cal = Calendar.getInstance();
		if(t!=null && !"".equals(t)){
			cal.setTime(sdf.parse(t));
		}else{
			cal.setTime(date);
		}
	    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	    	list.clear();
	    	head = null;
	    }
		mv.addObject("pd", pd);
		mv.addObject("head", head);
		mv.addObject("list", list);
		mv.setViewName("teacher/attendance_list");
		return mv;
	}
	
	/***
	 * 导出excel
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/excel")
	public void excel(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = "";
		try {
			PageData pd = this.getPageData();
			/*String zone_id = attendanceService.findZoneId();
			pd.put("zone_id", zone_id);*/
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			String user_id = user.getUSER_ID();
			String u_type = user.getU_type();
			
			if(Const.QUYU.equals(u_type)){
				pd.put("userIdqy", user_id);
				pd.put("u_type", Const.SCHOOLADMIN);
			}else if(Const.JIAOSHI.equals(u_type)){
				pd.put("userIdtc", user_id);
				pd.put("u_type", Const.SCHOOLADMIN);
			}else if(Const.ZUZHNAG.equals(u_type)){
				pd.put("userIdzz", user_id);
				pd.put("u_type", Const.SCHOOLADMIN);
			}else if(Const.KEHU.equals(u_type)){
				pd.put("userIdkh", user_id);
				pd.put("u_type", Const.SCHOOLADMIN);
			}else if(Const.SCHOOLADMIN.equals(u_type)){
				pd.put("userIdsc", user_id);
				pd.put("u_types", Const.SCHOOLADMIN);
			}
			
			filename = "打卡记录";
			List<PageData> alllist = (List<PageData>) attendanceService.list(pd);
			HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个webwook
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(filename);// 添加一个sheet
			HSSFRow row = sheet.createRow(0);// 创建第一行
			HSSFCellStyle style = workbook.createCellStyle();// 设置样式
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("学校");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 1);// 创建第一个单元格
			cell.setCellValue("班级");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 2);// 创建第一个单元格
			cell.setCellValue("学号");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 3);// 创建第一个单元格
			cell.setCellValue("学生姓名");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 4);// 创建第一个单元格
			cell.setCellValue("学生性别");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 5);// 创建第一个单元格
			cell.setCellValue("家长姓名");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 6);// 创建第一个单元格
			cell.setCellValue("家长电话");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 7);// 创建第一个单元格
			cell.setCellValue("考勤日期");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 8);// 创建第一个单元格
			cell.setCellValue("入校时间");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 9);// 创建第一个单元格
			cell.setCellValue("退校时间");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 10);// 创建第一个单元格
			cell.setCellValue("状态");// 赋值
			cell.setCellStyle(style);// 设置样式
			for (int i = 0; i < alllist.size(); i++) {
				String sex = "";
				if(alllist.get(i).get("s_sex") != null){
					sex = alllist.get(i).get("s_sex").toString();
					if(sex.equals("1")){
						sex = "男";
					}else{
						sex = "女";
					}
				}
				
				String state = "";
				if(alllist.get(i).getString("state") != null){
					state = alllist.get(i).getString("state");
					if(state.equals("1")){
						state = "正常";
					}else{
						state = "异常";
					}
				}
				
				row = sheet.createRow(i + 1);
				cell = row.createCell((short) 0);
				cell.setCellValue(alllist.get(i).getString("schoolNm"));// 赋值
				sheet.setColumnWidth(i, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 1);
				cell.setCellValue(alllist.get(i).getString("classNm"));// 赋值
				sheet.setColumnWidth(i + 1, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 2);
				cell.setCellValue(alllist.get(i).getString("s_stu_no"));// 赋值
				sheet.setColumnWidth(i + 2, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 3);
				cell.setCellValue(alllist.get(i).getString("s_name"));// 赋值
				sheet.setColumnWidth(i + 3, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 4);
				cell.setCellValue(sex);// 赋值
				sheet.setColumnWidth(i + 4, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 5);
				cell.setCellValue(alllist.get(i).getString("NAME"));// 赋值
				sheet.setColumnWidth(i + 5, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 6);
				cell.setCellValue(alllist.get(i).getString("PHONE"));// 赋值
				sheet.setColumnWidth(i + 6, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 7);
				cell.setCellValue(alllist.get(i).get("ar_date").toString());// 赋值
				sheet.setColumnWidth(i + 7, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 8);
				cell.setCellValue(alllist.get(i).getString("end_time"));// 赋值
				sheet.setColumnWidth(i + 8, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 9);
				cell.setCellValue(alllist.get(i).getString("start_time"));// 赋值
				sheet.setColumnWidth(i + 9, 6000);
				cell.setCellStyle(style);// 设置样式
				
				cell = row.createCell((short) 10);
				cell.setCellValue(state);// 赋值
				sheet.setColumnWidth(i + 10, 6000);
				cell.setCellStyle(style);// 设置样式
			}
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			String execelName = filename + ".xls";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(execelName, "UTF-8"));
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
