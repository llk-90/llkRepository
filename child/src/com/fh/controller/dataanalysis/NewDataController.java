package com.fh.controller.dataanalysis;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller; //1
import org.springframework.web.bind.annotation.RequestMapping; //1
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; //1

import com.alibaba.druid.proxy.jdbc.JdbcParameter.TYPE;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.fh.controller.base.BaseController; //1
import com.fh.entity.Chart;
import com.fh.entity.Page;
import com.fh.service.dataanalysis.NewDataService;
import com.fh.service.system.environment.Environmentservice;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping(value = "/newData")
public class NewDataController extends BaseController {
	String menuUrl = "newData/list.do"; // 菜单地址(权限用)

	@Resource(name="newDataService")
	private NewDataService newDataService;
	/**
	 * 列表
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws IOException, ParseException {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("time_start", Tools.getTimeStart());
		pd.put("time_end", Tools.getTime());
		mv.addObject("pd", pd);
		mv.setViewName("dataanalysis/newData_list");
		return mv;
	}

	/**
	 * 列出日、月、年中数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/listData")
	@ResponseBody
	public Map<String, Object> listData() throws Exception {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String,Object>();
		map = newDataService.list(pd);
		return map;
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
			String timeUnit = pd.getString("timeUnit");
			String timevalue = "";
			if(timeUnit.equals(Const.DAY)){
				timevalue = "日";
			}else if (timeUnit.equals(Const.WEEK)) {
				timevalue = "周";
			}else if (timeUnit.equals(Const.MONTH)) {
				timevalue = "月";
			}
			String type = pd.getString("type");
			String typevalue = "";
			if(type.equals("school")){
				typevalue = "学校";
			}else if (type.equals("class")) {
				typevalue = "班级";
			}
			filename = "每" + timevalue + "新增" + typevalue + "数据";
			List<PageData> alllist = (List<PageData>) newDataService.list(pd).get("list");
			HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个webwook
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(filename);// 添加一个sheet
			HSSFRow row = sheet.createRow(0);// 创建第一行
			HSSFCellStyle style = workbook.createCellStyle();// 设置样式
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("日期");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 1);// 创建第一个单元格
			cell.setCellValue("类别");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 2);// 创建第一个单元格
			cell.setCellValue("单位");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 3);// 创建第一个单元格
			cell.setCellValue("数量");// 赋值
			cell.setCellStyle(style);// 设置样式
			for (int i = 0; i < alllist.size(); i++) {
				row = sheet.createRow(i + 1);
				cell = row.createCell((short) 0);
				cell.setCellValue(alllist.get(i).getString("dt"));// 赋值
				sheet.setColumnWidth(i, 6000);
				cell.setCellStyle(style);// 设置样式
				cell = row.createCell((short) 1);
				cell.setCellValue(typevalue);// 赋值
				sheet.setColumnWidth(i + 1, 6000);
				cell.setCellStyle(style);// 设置样式
				cell = row.createCell((short) 2);
				cell.setCellValue(timevalue);// 赋值
				sheet.setColumnWidth(i + 2, 6000);
				cell.setCellStyle(style);// 设置样式
				cell = row.createCell((short) 3);
				cell.setCellValue(alllist.get(i).get("count").toString());// 赋值
				sheet.setColumnWidth(i + 3, 6000);
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
