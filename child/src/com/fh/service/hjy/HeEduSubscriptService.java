package com.fh.service.hjy;

import java.util.List;
import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.util.cmcc.OpenService;
import com.fh.util.cmcc.Response;
/**
 * @author 860114021
 * @data 20171019
 * 01
 */
@Service("heEduSubscriptService")
public class HeEduSubscriptService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;   
	
	@SuppressWarnings("unchecked")
	public PageData selectCity(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HeEduSubscriptMapper.selectCity2", pd);
	}
	  
	public PageData selectphonenum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HeEduSubscriptMapper.selectphonenum", pd);
	}
	
	public PageData selectfirmname(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HeEduSubscriptMapper.selectfirmname", pd);
	}
	
	public PageData selectUsername(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HeEduSubscriptMapper.selectUsername", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> Yewulist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HeEduSubscriptMapper.YeWulist", pd);
	}	
	//加载地区信息
	 
	public String findCity(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.findCity", pd);
	}
	//加载业务信息
	@SuppressWarnings("unchecked")
	public List<PageData> selectYewu(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HeEduSubscriptMapper.selectYewu", pd);
	}	
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(String cityId) throws Exception {
		return (List<PageData>) dao.findForList("HeEduSubscriptMapper.list", cityId);
	}	
	 
	public String findUidlist(String stu_id) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.selectUid", stu_id);
	}
 
	@SuppressWarnings("unchecked")
	public List<PageData> StuInfolist(String u_id) throws Exception {
		return (List<PageData>) dao.findForList("HeEduSubscriptMapper.selectStuInfo", u_id);
	}	
	 
	public String findCityId(String schoolId) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.selectCity", schoolId);
	}
	
	public String findUseridByOpenid(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.findUseridByOpenid", pd);
	}
	
	public String findStuid(String user_id) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.findStuid", user_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectStuInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HeEduSubscriptMapper.selectCodeInfo", pd);
	}	
	
	public String selectStuid(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.selectStuid", pd);
	}
	
	public String SelectOrderStaus(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.SelectOrderStaus", pd);
	}
	
	public String UpdateOrderStaus(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.UpdateOrderStaus", pd);
	}
	
 
	
	public String AddInfo(PageData pd) throws Exception {
		return (String) dao.findForObject("HeEduSubscriptMapper.addInfo", pd);
	}
	/**
	 * 业务开通验证码下发 
	 * @return 
	 */
	public String sendSMS(Object parentId ,String cityId,Object studentId,String packageId) throws Exception{
		String requestXML="<MSG_BODY>"
				+ "<Token></Token>"
				+ "<CityId>" +cityId+ "</CityId>"
				+ "<StudentId>"+studentId+"</StudentId>"
				+ "<ParentId>"+parentId+"</ParentId>"
				+ "<RecMobile></RecMobile>"
				+ "<PackageId>" +packageId+"</PackageId>"
				+ "<ChargeType>1</ChargeType>"
				+ "<ExpireDate></ExpireDate>"
				+ "<IsCharges></IsCharges>"
				+ "</MSG_BODY>";
	 
		String msgType = "SEND_CAPTCHA_SMS";
		Response response = OpenService.openService(requestXML, msgType);
	    String responseXML=response.getBody();
	    String repect=response.getDesc();
	    System.out.println(repect);
	    System.out.println(responseXML);
		Document doc = (Document) DocumentHelper.parseText(response.getBody());
		Element element = doc.getRootElement();
		List ele =element.content();
		if (ele!= null && !ele.equals("") && ele.size()!=0 ){
			String result = element.element("Result").getText();
			String desc = element.element("Desc").getText();
			System.out.println("请求返回结果:"+ result + ";" + desc);
			return result ;
		}else{
			return repect;
		}
	}
	
	//判断验证码是否正确
	public String VerifyCode(String code, String cityId, String stuId,String packageId) throws Exception{
		String requestXML="<MSG_BODY>"
				+ "<Captcha>" +code+ "</Captcha>"
				+ "<CityId>" +cityId+ "</CityId>"
				+ "<StudentId>" +stuId+ "</StudentId>"
				+ "<PackageId>" +packageId+ "</PackageId></MSG_BODY>";
		String msgType = "PUT_CAPTCHA_ORDER";
		Response response = OpenService.openService(requestXML, msgType);
	    String responseXML=response.getBody();
	    String repect=response.getDesc();
	    System.out.println(responseXML);
		Document doc = (Document) DocumentHelper.parseText(response.getBody());
		Element element = doc.getRootElement();
		List ele =element.content();
		if (ele!= null && !ele.equals("")&& ele.size()!=0 ){
			String result = element.element("Result").getText();
			String desc = element.element("Desc").getText();
			System.out.println("请求返回结果:"+ result + ";" + desc);
			return  result ;
		}else{
			return repect;
		}
	}

	
	
 
	
}
