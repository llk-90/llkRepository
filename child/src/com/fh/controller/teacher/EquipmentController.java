package com.fh.controller.teacher;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Ibaby;
import com.fh.entity.Page;
import com.fh.service.teacher.EquipmentService;
import com.fh.service.weixin.ibaby.HistoryTrackAppService;
import com.fh.service.weixin.ibaby.TerminalStatusAppService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/equipment")
public class EquipmentController extends BaseController {
	String menuUrl = "equipment/list.do"; // 菜单地址(权限用)
	
	@Resource(name="equipmentService")
	private EquipmentService equipmentService;

	@Resource(name="historyTrackAppService")
	private HistoryTrackAppService historyTrackAppService;
	
	@Resource(name="terminalStatusAppService")
	private TerminalStatusAppService terminalStatusAppService;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listEquip(Page page) throws Exception {		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> equipmentList = equipmentService.equipmentList(page);
		mv.setViewName("teacher/equipment_list");
		mv.addObject("equipmentList", equipmentList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去修改设备信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData equipment = equipmentService.findByStuId(pd);
		mv.addObject("equipment", equipment);
		mv.setViewName("teacher/equipmentEdit");
		return mv;
	}	
	
	/**
	 * 编辑设备信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edits() throws Exception {
		logBefore(logger, "修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		equipmentService.editE(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
			filename = "设备信息";
			page.setShowCount(1000);
			List<PageData> equipmentList = equipmentService.equipmentList(page);			
			HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个webwook
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(filename);// 添加一个sheet
			HSSFRow row = sheet.createRow(0);// 创建第一行
			HSSFCellStyle style = workbook.createCellStyle();// 设置样式
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("所属学校");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 1);// 创建第一个单元格
			cell.setCellValue("所属班级");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 2);// 创建第一个单元格
			cell.setCellValue("学生姓名");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 3);// 创建第一个单元格
			cell.setCellValue("联系方式");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 4);// 创建第一个单元格
			cell.setCellValue("开通业务");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 5);// 创建第一个单元格
			cell.setCellValue("购买设备");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 6);// 创建第一个单元格
			cell.setCellValue("设备状态");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 7);// 创建第一个单元格
			cell.setCellValue("IMEI码");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 8);// 创建第一个单元格
			cell.setCellValue("IC卡号");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 9);// 创建第一个单元格
			cell.setCellValue("IC卡种类");// 赋值
			cell.setCellStyle(style);// 设置样式
			cell = row.createCell((short) 10);// 创建第一个单元格
			cell.setCellValue("最后一次操作时间");// 赋值
			cell.setCellStyle(style);// 设置样式			
			
			for(int i = 0; i < equipmentList.size(); i++){

				//宝贝定位时间
				String babyLocaTime = terminalStatus(equipmentList.get(i).get("s_id").toString());
				
				if(babyLocaTime!=null && babyLocaTime !=""){
					
					//当前时间
					Date now = new Date();
					SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begin=dfs.parse(babyLocaTime);            
					//相差小时数					
					int hours = (int) ((now.getTime() - begin.getTime())/(1000 * 60 * 60));
					//相差24小时可以导出Excel
					if(hours > 24){
						
						String ibaby_equ_openBusiness = "";
						if(equipmentList.get(i).get("ibaby_equ_openBusiness") != null){
							ibaby_equ_openBusiness = equipmentList.get(i).get("ibaby_equ_openBusiness").toString();
						}
						
						String ibaby_equ_buyEquip = "";
						if(equipmentList.get(i).get("ibaby_equ_buyEquip") != null){
							ibaby_equ_buyEquip = equipmentList.get(i).get("ibaby_equ_buyEquip").toString();							
						}
						
						String ibaby_equ_status = "";
						if(equipmentList.get(i).get("ibaby_equ_status") != null){
							ibaby_equ_status = equipmentList.get(i).get("ibaby_equ_status").toString();
						}							
						
						row = sheet.createRow(i + 1);
						cell = row.createCell((short) 0);
						cell.setCellValue(equipmentList.get(i).getString("schoolname"));// 赋值
						sheet.setColumnWidth(i, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 1);
						cell.setCellValue(equipmentList.get(i).getString("className"));// 赋值
						sheet.setColumnWidth(i + 1, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 2);
						cell.setCellValue(equipmentList.get(i).getString("s_name"));// 赋值
						sheet.setColumnWidth(i + 2, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 3);
						cell.setCellValue(equipmentList.get(i).getString("phone"));// 赋值
						sheet.setColumnWidth(i + 3, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 4);
						cell.setCellValue(ibaby_equ_openBusiness);// 赋值
						sheet.setColumnWidth(i + 4, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 5);
						cell.setCellValue(ibaby_equ_buyEquip);// 赋值
						sheet.setColumnWidth(i + 5, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 6);
						cell.setCellValue(ibaby_equ_status);// 赋值
						sheet.setColumnWidth(i + 6, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 7);
						cell.setCellValue(equipmentList.get(i).get("ibaby_imei_code").toString());// 赋值
						sheet.setColumnWidth(i + 7, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 8);
						cell.setCellValue(equipmentList.get(i).getString("ibaby_ic_code"));// 赋值
						sheet.setColumnWidth(i + 8, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 9);
						cell.setCellValue(equipmentList.get(i).getString("ibaby_ic_code_type"));// 赋值
						sheet.setColumnWidth(i + 9, 6000);
						cell.setCellStyle(style);// 设置样式
						
						cell = row.createCell((short) 10);
						cell.setCellValue(babyLocaTime);// 赋值
						sheet.setColumnWidth(i + 10, 6000);
						cell.setCellStyle(style);// 设置样式		

					}
		
				}
						
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
	
	//宝贝定位信息获取处理
	@SuppressWarnings("unused")
	public String terminalStatus(String stuId) throws Exception {

		Map<String, Ibaby> mv = new HashMap<>();		
		Map<String, Object> map = new HashMap<>();
		String updateTime = null;
        Ibaby ibaby = new Ibaby();
		PageData pd = new PageData();
		pd.put("stuId",stuId);
		PageData p = historyTrackAppService.findBabyInfoByStuid(pd);

		pd.put("loginName", p.getString("ibaby_equ_accout"));  // 儿童机账号
		pd.put("passWord", p.getString("ibaby_equ_password")); // 儿童机帐号密码	
		pd.put("terminalImei", p.getString("ibaby_imei_code")); // 设备imei		
        
		mv = terminalStatusAppService.positionStatus(pd);

        ibaby = mv.get("result");

        if(ibaby.getResponse().getPositionList()!=null){
        	updateTime = ibaby.getResponse().getPositionList().get(0).getUpdateTime();       	
        }

		return updateTime;
	}
	
}
