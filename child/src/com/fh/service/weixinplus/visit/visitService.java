package com.fh.service.weixinplus.visit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.service.weixinplus.weixiplusCommon.CheckValueService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;

import net.sf.json.JSONObject;
/**
 * 
 * @author roger
 *
 */
@Service("visitService")
public class visitService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String DetaiJs = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\educationDetil.js";//定义空格回车换行符
    private static final String listJs = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\educational.js";//定义空格回车换行符
    private static final String listHtml = "D:\\weixin\\palmcare\\2thiBady\\html\\jiaozilangfang\\schoolDoc\\educational.html";//定义空格回车换行符
    private static final String JsRoot = "D:\\weixin\\palmcare\\2thiBady\\js\\app\\jiaozilangfang\\SchoolDoc\\";
    private static final String HtmlRoot = "D:\\weixin\\palmcare\\2thiBady\\html\\jiaozilangfang\\schoolDoc\\";
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	@SuppressWarnings("unchecked")
	public PageData findInfoList(PageData pd) throws Exception {
		PageData pageData = new PageData();
		try{
				PageData parInfo = (PageData)dao.findForObject("visitMapper.findParInfo", pd);
				if (parInfo==null || parInfo.size()==0) {
					pageData.put("errorcode", errorMsg.Success(1001));
				}else{
					pageData.put("errorcode", errorMsg.Success(0));
					pageData.put("parInfo",parInfo);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		PageData list=  (PageData) dao.findForObject("visitMapper.getTeaInfo", pd);
		pageData.put("teaInfo", list);
		return pageData;
	}
	
	@SuppressWarnings("unchecked")
	public PageData getHandleInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		List list=  (List) dao.findForList("visitMapper.getHandleInfo", pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date startDate = (Date)((PageData) list.get(i)).get("start_date");
			  Date endDate = (Date) ((PageData) list.get(i)).get("end_date");
			  SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
			  ((PageData) list.get(i)).put("start_date", sdf.format(startDate));
			  ((PageData) list.get(i)).put("end_date", sdf.format(endDate));
			}
		pageData.put("HandleInfo", list);
		return pageData;
	}
	
	public PageData getNoHandleLeaveInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		List list=  (List) dao.findForList("visitMapper.getNoHandleLeaveInfo", pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date startDate = (Date)((PageData) list.get(i)).get("start_date");
			  Date endDate = (Date) ((PageData) list.get(i)).get("end_date");
			  SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
			  ((PageData) list.get(i)).put("start_date", sdf.format(startDate));
			  ((PageData) list.get(i)).put("end_date", sdf.format(endDate));
			}
		pageData.put("NoHandleleaveInfo", list);
		return pageData;
	}
	
	@SuppressWarnings("unchecked")
	public PageData getNoHandleInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		List list=  (List) dao.findForList("visitMapper.getNoHandleInfo", pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date startDate = (Date)((PageData) list.get(i)).get("start_date");
			  Date endDate = (Date) ((PageData) list.get(i)).get("end_date");
			  SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
			  ((PageData) list.get(i)).put("start_date", sdf.format(startDate));
			  ((PageData) list.get(i)).put("end_date", sdf.format(endDate));
			}
		pageData.put("NoHandleInfo", list);
		return pageData;
	}
	
	@SuppressWarnings("unchecked")
	public PageData getHandleLeaveInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		List list=  (List) dao.findForList("visitMapper.getHandleLeaveInfo", pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date startDate = (Date)((PageData) list.get(i)).get("start_date");
			  Date endDate = (Date) ((PageData) list.get(i)).get("end_date");
			  SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
			  ((PageData) list.get(i)).put("start_date", sdf.format(startDate));
			  ((PageData) list.get(i)).put("end_date", sdf.format(endDate));
			}
		pageData.put("HandleleaveInfo", list);
		return pageData;
	}
	
	public PageData homeworkList(PageData pd) throws Exception {
		PageData pageData = new PageData();
		List list=  (List) dao.findForList("visitMapper.homeworkList", pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date date = (Date)((PageData) list.get(i)).get("creat_time");
			  SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			  ((PageData) list.get(i)).put("creat_time", sdf.format(date));
			}
		pageData.put("homeworkList", list);
		return pageData;
	}
	
	
	public PageData askHomework(PageData pd) throws Exception {
		PageData pageData = new PageData();
		PageData tea_pd=  (PageData) dao.findForObject("visitMapper.getTeaInfo", pd);
		List list=  (List) dao.findForList("visitMapper.homeworkList2", tea_pd);
		for(int i = 0 ; i < list.size() ; i++) {
			  Date date = (Date)((PageData) list.get(i)).get("creat_time");
			  SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			  ((PageData) list.get(i)).put("creat_time", sdf.format(date));
			}
		
		PageData todayHomework = (PageData) dao.findForObject("visitMapper.todayHomework", tea_pd);
		pageData.put("homeworkList", list);
		pageData.put("todayHomework", todayHomework);
		return pageData;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public PageData addInfo(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		String beginDay = pd.getString("beginDay");
		String endDay = pd.getString("endDay");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sdf.parse(beginDay);
		Date endDate = sdf.parse(endDay);
		pd.put("beginDay", beginDate);
		pd.put("endDate", endDay);
		try {
			dao.findForObject("visitMapper.addInfo", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	public PageData leaveInfoAdd(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		String beginDay = pd.getString("beginDay");
		String endDay = pd.getString("endDay");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sdf.parse(beginDay);
		Date endDate = sdf.parse(endDay);
		pd.put("beginDay", beginDate);
		pd.put("endDay", endDate);
		try {
			dao.findForObject("visitMapper.leaveInfoAdd", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	public PageData subHomework(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try {
			dao.findForObject("visitMapper.subHomework", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	@SuppressWarnings("unchecked")
	public PageData recordInfo(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		
		try{
			//學生姓名
			PageData stuInfo = (PageData)dao.findForObject("visitMapper.findParInfo", pd);
			//教师姓名
			PageData teaInfo = (PageData)dao.findForObject("visitMapper.getTeaInfo", pd);
			//拜访状态 老师回复内容 
			PageData recordInfo = (PageData)dao.findForObject("visitMapper.findRecordInfo", pd);
			//拜访完成记录
			List<PageData> recordedInfoList = (List<PageData>)dao.findForList("visitMapper.recordListInfo", pd);
//			if (stuInfo.size()==0 || recordInfo.size()==0 || recordedInfoList.size()==0) {
//				pageData.put("errorcode", errorMsg.Success(1001));
//			}else{
				
				for(int i = 0 ; i < recordedInfoList.size() ; i++) {
					  Date startDate = (Date)recordedInfoList.get(i).get("start_date");
					  Date endDate = (Date) recordedInfoList.get(i).get("end_date");
					  SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
					  recordedInfoList.get(i).put("start_date", sdf.format(startDate));
					  recordedInfoList.get(i).put("end_date", sdf.format(endDate));
					}
				
				pageData.put("errorcode", errorMsg.Success(0));
				pageData.put("parInfo",stuInfo);
				pageData.put("teaInfo",teaInfo);
				pageData.put("recordInfo",recordInfo);
				pageData.put("recordedInfoList",recordedInfoList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return pageData;
	}
	
	public PageData leaveApplyInfo(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		
		try{
			//學生姓名
			PageData stuInfo = (PageData)dao.findForObject("visitMapper.findParInfo", pd);
			//教师姓名
			PageData teaInfo = (PageData)dao.findForObject("visitMapper.getTeaInfo", pd);
			//请假状态 老师回复内容 
			PageData recordInfo = (PageData)dao.findForObject("visitMapper.leaveRecordInfo", pd);
			//请假完成记录
			List<PageData> leaveRecordedInfoList = (List<PageData>)dao.findForList("visitMapper.leaveRecordedInfoList", pd);
//			if (stuInfo.size()==0 || recordInfo.size()==0 || recordedInfoList.size()==0) {
//				pageData.put("errorcode", errorMsg.Success(1001));
//			}else{
				
				for(int i = 0 ; i < leaveRecordedInfoList.size() ; i++) {
					  Date startDate = (Date)leaveRecordedInfoList.get(i).get("start_date");
					  Date endDate = (Date) leaveRecordedInfoList.get(i).get("end_date");
					  SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
					  leaveRecordedInfoList.get(i).put("start_date", sdf.format(startDate));
					  leaveRecordedInfoList.get(i).put("end_date", sdf.format(endDate));
					}
				
				pageData.put("errorcode", errorMsg.Success(0));
				pageData.put("parInfo",stuInfo);
				pageData.put("teaInfo",teaInfo);
				pageData.put("recordInfo",recordInfo);
				pageData.put("leaveRecordedInfoList",leaveRecordedInfoList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return pageData;
	}
	
	public PageData judgeType(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try{
			PageData type = (PageData)dao.findForObject("visitMapper.judgeType", pd);
			//判断该openid的最新数据的状态 0为家长 1为老师
			if(type==null){
				pageData.put("flag", "0");
			}else{
				pageData.put("flag", "1");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pageData;
	}
	
	public PageData visitOk(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try{
			dao.findForObject("visitMapper.visitOk", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		}catch(Exception e){
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	
	public PageData leaveOk(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try{
			dao.findForObject("visitMapper.leaveOk", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		}catch(Exception e){
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	
	public PageData visitRefuse(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try{
			dao.findForObject("visitMapper.visitRefuse", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		}catch(Exception e){
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
	public PageData leaveRefuse(PageData pd) throws Exception  {
		PageData pageData = new PageData();
		try{
			dao.findForObject("visitMapper.leaveRefuse", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		}catch(Exception e){
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}
	
//	@SuppressWarnings("unchecked")
//	public PageData addInfo(PageData pd) throws Exception  {
//		PageData pageData = new PageData();
//		String beginDay = pd.getString("beginDay");
//		String endDay = pd.getString("endDay");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date beginDate = sdf.parse(beginDay);
//		Date endDate = sdf.parse(endDay);
//		pd.put("beginDay", beginDate);
//		pd.put("endDate", endDay);
//		try {
//			dao.findForObject("visitMapper.addInfo", pd);
//			pageData.put("errorcode", errorMsg.Success(0));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			pageData.put("errorcode", errorMsg.Success(1001));
//		}
//		return pageData;
//	}
}
