package com.fh.service.weixinplus.educationInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



@Service("educationInfoForThirdService")
public class EducationInfoForThirdService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	
	
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	
	/**
	 * 点击首页展示banner轮播图
	 */
	@SuppressWarnings("unchecked")
	public PageData getBanner(PageData pd){
		List<PageData> bannerList = null;
		PageData pageData = new PageData();
		try {
			bannerList = (List<PageData>) dao.findForList("EducationInfoMapperforthird.findBannerImage", pd);
			if (bannerList.size()==0) {
				pageData.put("errorcode",errorMsg.Success(1001));
			} else {
				pageData.put("errorcode",errorMsg.Success(0));
				pageData.put("infolist", bannerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageData;
	}
	
	/** 
	 * 点击首页展示教育咨询列表
	 */
	@Resource(name = "CheckValueService")
	private CheckValueService checkValueService;
	
	@SuppressWarnings("unchecked")
	public PageData findEducationInfoList(PageData pd) throws Exception {
		PageData pageData = new PageData();
		int pageCount = Integer.valueOf(pd.getString("pageCount")).intValue();
//		if (checkValueService.OpenidCheck(pd.getString("openId"))) {
			try{
				List<PageData> educationInfoList =
						(List<PageData>) dao.findForList("EducationInfoMapperforthird.findEducationInfoList", pd);
				if (educationInfoList.size()==0) {
					pageData.put("errorcode", errorMsg.Success(1001));
				} else {
					if (educationInfoList.size()<(pageCount+1)*5) {
						educationInfoList = educationInfoList.subList(pageCount*5,educationInfoList.size()-1);
					} else {
						educationInfoList = educationInfoList.subList(pageCount*5, (pageCount+1)*5);
					}
					
					for (int i = 0; i < educationInfoList.size(); i++) {
						PageData educationInfo = educationInfoList.get(i);
						educationInfo.put("content",getTextFromHtml(educationInfo.get("content").toString()));	
						
					}
					pageData.put("errorcode", errorMsg.Success(0));
					pageData.put("educationInfoList",educationInfoList);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
//		}else{
//			pageData.put("errorcode", errorMsg.Success(2001));
//		}
		
		return pageData;
	}

	@SuppressWarnings("unchecked")
	public PageData findInfoById(PageData pd) throws Exception{
		PageData pageData = new PageData();
		PageData educationInfo =
				(PageData)dao.findForObject("EducationInfoMapperforthird.findEducationInfoById",pd);
		if (educationInfo.size()==0) {
			pageData.put("errorcode", errorMsg.Success(1001));
		} else {
			pageData.put("errorcode", errorMsg.Success(0));
			pageData.put("educationInfo",educationInfo);
		}
		return pageData;
	}
	/**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
   
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
       
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        if (htmlStr.length()>41) {
            htmlStr = htmlStr.substring(0,40);
		}
        return htmlStr;
    }
    /**
     * @param 文件地址
     * @return 文本文字
     * 
     * @throws Exception 
     */
    public String readFile(String fileName)  
    {     
        String fileContent = "";     
        try   
        {       
            File f = new File(fileName);      
            if(f.isFile()&&f.exists())  
            {       
                InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");       
                BufferedReader reader=new BufferedReader(read);       
                String line;       
                while ((line = reader.readLine()) != null)   
                {        
                    fileContent += line;       
                }         
                read.close();      
            }     
        } catch (Exception e)   
        {         
            e.printStackTrace();     
        } 
        fileContent.replace("#{target}", "787878");
        return fileContent;   
    }  
    /**
     * @param 文件地址
     * @return 
     * 
     * @throws Exception 
     */
    public void writeFile(String fileName)   
    {     
        try   
        {      
            File f = new File(fileName);      
            if (!f.exists())   
            {       
                f.createNewFile();      
            }      
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"gbk");      
            BufferedWriter writer=new BufferedWriter(write);          
            writer.write(readFile(""));      
            writer.close();     
        } catch (Exception e)   
        {      
            e.printStackTrace();     
        }  
    }  
}
