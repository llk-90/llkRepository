package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebHTMLDecodeSpecialSymbol {
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_lessthan="<";
    private static final String regEx_morethan=">";
    private static final String regEx_acute="'";
    private static final String regEx_quot="\"";
    
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
        
        Pattern p_lessthan = Pattern.compile(regEx_lessthan, Pattern.CASE_INSENSITIVE);
        Matcher m_lessthan = p_lessthan.matcher(htmlStr);
        htmlStr = m_lessthan.replaceAll("");
        
        Pattern p_morethan = Pattern.compile(regEx_morethan, Pattern.CASE_INSENSITIVE);
        Matcher m_morethan = p_morethan.matcher(htmlStr);
        htmlStr = m_morethan.replaceAll(""); 
        
        Pattern p_acute = Pattern.compile(regEx_acute, Pattern.CASE_INSENSITIVE);
        Matcher m_acute = p_acute.matcher(htmlStr);
        htmlStr = m_acute.replaceAll(""); 
        
        Pattern p_quot = Pattern.compile(regEx_quot, Pattern.CASE_INSENSITIVE);
        Matcher m_quot = p_quot.matcher(htmlStr);
        htmlStr = m_quot.replaceAll(""); 
        
        return htmlStr.trim(); // 返回文本字符串
    }
}
