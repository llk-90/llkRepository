package com.fh.controller.teacher;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.teacher.SchoolOrderService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/schoolOrder")
public class SchoolOrderController extends BaseController {
	String menuUrl = "schoolOrder/list.do"; // 菜单地址(权限用)
	
	@Resource(name="schoolOrderService")
	private SchoolOrderService schoolOrderService;

	/**
	 * 列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView List(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> schoolOrderList = schoolOrderService.schoolOrderList(page);     //订单信息
		mv.addObject("schoolOrderList", schoolOrderList);
		mv.addObject("pd", pd);
		mv.setViewName("teacher/schoolOrder_list");
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
	@SuppressWarnings({ "resource"})
	@RequestMapping(value = "/excel")
	public void excel(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = "";
		try {				
			PageData pd = this.getPageData();
			page.setPd(pd);
			filename = "订单信息";
			page.setShowCount(1000);
			List<PageData> schoolOrderList = schoolOrderService.schoolOrderList(page);			
			HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个webwook
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(filename);// 添加一个sheet
			HSSFRow row = sheet.createRow(0);// 创建第一行
			HSSFCellStyle style = workbook.createCellStyle();// 设置样式
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("用户帐号");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 1);// 创建第一个单元格
			cell.setCellValue("姓名");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 2);// 创建第一个单元格
			cell.setCellValue("卡物理号");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 3);// 创建第一个单元格
			cell.setCellValue("消费额");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 4);// 创建第一个单元格
			cell.setCellValue("流水号");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 5);// 创建第一个单元格
			cell.setCellValue("消费时间");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 6);// 创建第一个单元格					
			for(int i = 0; i < schoolOrderList.size(); i++){				
					//可以导出Excel					
						
				row = sheet.createRow(i + 1);
				cell = row.createCell((short) 0);
				cell.setCellValue(schoolOrderList.get(i).get("AccountNo").toString());// 赋值
				sheet.setColumnWidth(i, 6000);
				cell.setCellStyle(style);// 设置样式
						
				cell = row.createCell((short) 1);
				cell.setCellValue(schoolOrderList.get(i).getString("CustomerName"));// 赋值
				sheet.setColumnWidth(i + 1, 6000);
				cell.setCellStyle(style);// 设置样式
						
				cell = row.createCell((short) 2);
				cell.setCellValue(schoolOrderList.get(i).getString("CardID"));// 赋值
				sheet.setColumnWidth(i + 2, 6000);
				cell.setCellStyle(style);// 设置样式
						
				cell = row.createCell((short) 3);
				cell.setCellValue(schoolOrderList.get(i).get("JE").toString());// 赋值
				sheet.setColumnWidth(i + 3, 6000);
				cell.setCellStyle(style);// 设置样式
						
				cell = row.createCell((short) 4);
				cell.setCellValue(schoolOrderList.get(i).get("LSH").toString());// 赋值
				sheet.setColumnWidth(i + 4, 6000);
				cell.setCellStyle(style);// 设置样式
						
				cell = row.createCell((short) 5);
				cell.setCellValue(schoolOrderList.get(i).get("PayTime").toString());// 赋值
				sheet.setColumnWidth(i + 5, 6000);
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
