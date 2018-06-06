package com.fh.controller.dataanalysis;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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

import com.fh.controller.base.BaseController; //1
import com.fh.service.dataanalysis.CoursewareAnalysisService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping(value = "/coursewareAnalysisController")
public class CoursewareAnalysisController extends BaseController {
	String menuUrl = "coursewareAnalysisController/list.do"; // 菜单地址(权限用)

	@Resource(name = "coursewareAnalysisService")
	private CoursewareAnalysisService coursewareAnalysisService;

	/**
	 * 列表
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws IOException, ParseException {
		logBefore(logger, "课件内容浏览数据分析");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("time_start", Tools.getTimeStart());
		pd.put("time_end", Tools.getTime());
		mv.setViewName("dataanalysis/coursewareAnalysis");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 获取数据
	 */
	@RequestMapping(value = "/listData")
	@ResponseBody
	public List<PageData> listData() {
		logBefore(logger, "课件内容浏览分析获取数据");
		PageData pd = new PageData();
		List<PageData> list = new ArrayList<PageData>();
		try {
			pd = this.getPageData();
			list = coursewareAnalysisService.listData(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return list;
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
	public void excel(HttpServletResponse response) throws Exception {
		String filename = "";
		try {
			PageData pd = this.getPageData();
			String excel_type = pd.getString("excel_type");
			if (excel_type.equals(Const.TYPE_DAY_CODE)) {
				filename = "课件浏览数据日分析表";
			} else if (excel_type.equals(Const.TYPE_WEEK_CODE)) {
				filename = "课件浏览数据周分析表";
			} else if (excel_type.equals(Const.TYPE_MONTH_CODE)) {
				filename = "课件浏览数据月分析表";
			}
			List<PageData> alllist = coursewareAnalysisService.listData(pd);
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
			cell.setCellValue("浏览量");// 赋值
			cell.setCellStyle(style);// 设置样式
			for (int i = 0; i < alllist.size(); i++) {
				row = sheet.createRow(i + 1);
				cell = row.createCell((short) 0);
				cell.setCellValue(alllist.get(i).getString("dt"));// 赋值
				sheet.setColumnWidth(i, 6000);
				cell.setCellStyle(style);// 设置样式
				cell = row.createCell((short) 1);
				cell.setCellValue(alllist.get(i).getString("type_name"));// 赋值
				sheet.setColumnWidth(i + 1, 6000);
				cell.setCellStyle(style);// 设置样式
				cell = row.createCell((short) 2);
				cell.setCellValue(alllist.get(i).get("count").toString());// 赋值
				sheet.setColumnWidth(i + 2, 6000);
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
