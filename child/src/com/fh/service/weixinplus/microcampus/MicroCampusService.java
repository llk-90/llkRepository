package com.fh.service.weixinplus.microcampus;

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

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.util.DFAWordFilter;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;

@Service("microcampusservice")
public class MicroCampusService {
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String listHtml = "D:\\weixin\\palmcare\\2thiBady\\html\\weixiaoyuan\\schoolDoc\\weixiaoyuan.html";//列表的地址
    private static final String HtmlRoot = "D:\\weixin\\palmcare\\2thiBady\\html\\weixiaoyuan\\schoolDoc\\";//存储地址
    @Resource(name="errorMsg")
	private ErrorMsg errorMsg;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 微信用，查询全部微校园banner
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData selectMicroCampusBanner(PageData pData) throws Exception {
		PageData res = new PageData();
		try {
			List<PageData> bannerList = (List<PageData>) dao.findForList("MicroCampusMapper.selectmicrocampusbanner", pData);		
			if(bannerList.size() == 0) {
				res.put("errcode", errorMsg.Success(1001));
			}
			res.put("errcode", errorMsg.Success(0));
			res.put("bannerList",bannerList);
		} catch (Exception e) {
			// TODO: handle exception
			res.put("errcode", errorMsg.Success(4001));
		}
		return res;
	}
	

	/**
	 * 微信用，查询微校园信息
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData selectMicroCampusInfo(PageData pData) throws Exception {
		PageData res = new PageData();
		try {
			//进行分页，生成最大最小信息数
			int minNum = Integer.valueOf(pData.getString("pageCount")).intValue()*5;
			int page = 5;
			pData.put("minNum",minNum);
			pData.put("page",page);
			int type = Integer.valueOf(pData.getString("type")).intValue();
			pData.put("type", type);
		List<PageData> infoList = (List<PageData>) dao.findForList("MicroCampusMapper.selectmicrocampusinfo", pData);
			
			if (infoList.size()==0&Integer.valueOf(pData.getString("pageCount"))==0) {
				res.put("errcode", errorMsg.Success(1005));
			} else {
				for (int i = 0; i < infoList.size(); i++) {
					PageData temp = infoList.get(i);
					temp.put("content",getTextFromHtml(temp.get("content").toString()));
				}
				res.put("infoList", infoList);
				res.put("errcode", errorMsg.Success(0));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			res.put("errcode", errorMsg.Success(4001));
		}
		return res;
	}
	
	/**
	 * 微信用，根据id查询单个微校园信息
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData singleMicroCampusInfo(PageData pData) throws Exception {
		PageData res = new PageData();
		try {
			List<PageData> infoList = (List<PageData>) dao.findForList("MicroCampusMapper.singlemicrocampusinfo", pData);
			if (infoList.size()==0) {
				res.put("errcode", errorMsg.Success(1005));
			} else {
				res.put("errcode", errorMsg.Success(0));
				res.put("educationInfo", infoList.get(0));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			res.put("errcode", errorMsg.Success(4001));
		}
		return res;
	}
	 /**
     * @param User
     * @return 学校微校园Html地址
     *  申请当前的学校的Html
     * @throws Exception 
     */
    public PageData applyHtmlPath(PageData pd) throws Exception {   
    	
    	PageData apply = new PageData();
    	PageData schoolInfo = (PageData)dao.findForObject("MicroCampusMapper.findSchoolInfo", pd);
    	PageData res = new PageData();
    	if (schoolInfo.size()==0) {
    		res.put("errorcode", errorMsg.Success(1001));
    		return res;
		}
        if (schoolInfo.get("path_string")!=null) {
			//如果以前申请过，直接显示以前申请的信息
        	res.put("errorcode", errorMsg.Success(0));
        	res.put("path", schoolInfo.getString("path_string"));

		} else {
			//如果以前没有申请过
			try {
				apply.put("school_id", schoolInfo.get("school_id").toString());
	        	apply.put("school_name", schoolInfo.get("SchoolName").toString());
	        	apply.put("path_string", "rytrytr");
	        	apply.put("apply_user_id", pd.getString("userId"));
	        	//更改列表地址
	        	String path = writeFile(HtmlRoot, schoolInfo.get("school_id").toString(), listHtml);
	        	apply.put("path_string", path);
	        	System.out.println(path);
				dao.save("MicroCampusMapper.saveSchooUrlInfo", apply);
	        	res.put("path", path);
	        	res.put("errorcode", errorMsg.Success(0));
			} catch (Exception e) {
				// TODO: handle exception
				res.put("errorcode", errorMsg.Success(4001));
			}
			
		}
       
        return res;
    }
    /**
     * @param 文件地址
     * @return 文本文字
     * 
     * @throws Exception 
     */
    public String readFile(String fileName,String schoolId)  
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
        fileContent = fileContent.replace("#{schoolId}", schoolId);
        return fileContent;   
    }  
    /**
     * @param 文件地址
     * @return 
     * 
     * @throws Exception 
     */
    public String writeFile(String fileName,String schoolId,String path)   
    {     
        try   
        {      
            File f = new File(fileName+schoolId+".html");      
            if (!f.exists())   
            {       
                f.createNewFile();      
            }      
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"gbk");      
            BufferedWriter writer=new BufferedWriter(write);
            writer.write(readFile(path,schoolId));      
            writer.close();     
        } catch (Exception e)   
        {      
            e.printStackTrace();     
        } 
        return "http://www.guanai100.cn/palmcare/2thiBady/html/weixiaoyuan/schoolDoc/"+schoolId+".html";
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
        if (htmlStr.length()>121) {
            htmlStr = htmlStr.substring(0,120);
		}
        return htmlStr;
    }
    /**
	 * 微信用，根据文章id查询评论
	 * @param InfoId
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public PageData selectComment(PageData pData) throws Exception {
		PageData res = new PageData();
		try {
			@SuppressWarnings("unchecked")
			List<PageData> infoList = (List<PageData>) dao.findForList("MicroCampusMapper.selectComment", pData);
			if (infoList.size()==0) {
				res.put("errcode", errorMsg.Success(1005));
			} else {
				res.put("errcode", errorMsg.Success(0));
				res.put("commentlist", infoList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			res.put("errcode", errorMsg.Success(4001));
		}
		return res;
	}
	
	/**
	 * 微信用，插入评论
	 * @param InfoId
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public void savecomment(PageData pData) throws Exception {		
		try {	
			DFAWordFilter filter = DFAWordFilter.getInstance();
			Object content = pData.get("content");
			String before = content == null ? "" : content.toString();
			String after = filter.replaceSensitiveWord(//
					content == null ? "" : content.toString(), //
					DFAWordFilter.MAX_MATCH_TYPE, "*");
			after = getTextFromHtml(after);	
			System.out.println("before:" + before);
			System.out.println("after:" + after + "length:" + after.length());
			if(after.length() != 0){
				pData.put("content", after);
				dao.save("MicroCampusMapper.saveComment", pData);	
			}
			
			
			 		
		} catch (Exception e) {		
			
		}
		
	}
}
