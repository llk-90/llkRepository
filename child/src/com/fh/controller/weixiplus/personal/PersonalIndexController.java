package com.fh.controller.weixiplus.personal;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.service.weixinplus.Personal.PersonalIndexService;
import com.fh.service.weixinplus.weixiplusCommon.*;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.Utils;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value="/personal")
public class PersonalIndexController extends BaseController {

	@Autowired
	private PersonalIndexService service;
	
	@Resource(name="CheckValueService")
	private CheckValueService checkValueService;
	
	@Resource(name="errorMsg")
	private ErrorMsg errorMsg;
	
	/**
	 * 获得个人主页的信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPersonalInfo")
	public JSONObject getPersonalInfo(String openId) throws Exception{
		PageData pd=new PageData();
		JSONObject jsonObject=new JSONObject();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("openId", openId);
				jsonObject.element("errorcode", errorMsg.Success(0));
				return service.getPersonalInfo(pd);
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}	
		return jsonObject;
	}
	
	/**
	 * 获得个人姓名
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPersonalName")
	public JSONObject getPersonalName(String openId) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("openId", openId);
				jsonObject.element("errorcode", errorMsg.Success(0));
				String personalName=service.getPersonalName(pd);
				jsonObject.element("personalName", personalName);
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}	
		return jsonObject;
	}
	
	/**
	 * 修改个人姓名
	 * @param openId
	 * @param parentName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePersonalName")
	public JSONObject updatePersonalName(String openId,String parentName) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(parentName!=null){
					pd.put("openId", openId);
					pd.put("parentName", parentName);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.updatePersonalName(pd);
				}else{
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 获得个人生日
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPersonalBirthday")
	public JSONObject getPersonalBirthday(String openId) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("openId", openId);
				jsonObject.element("errorcode", errorMsg.Success(0));
				String birthday=service.getPersonalBirthday(pd);
				jsonObject.element("birthday", birthday);
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}	
		return jsonObject;
	}
	
	/**
	 * 修改个人生日
	 * @param openId
	 * @param birthday
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePersonalBirthday")
	public JSONObject updatePersonalBirthday(String openId,String birthday) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(birthday!=null){
					long date = Long.parseLong(birthday);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					birthday=sdf.format(date);
					pd.put("openId", openId);
					pd.put("birthday", birthday);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.updatePersonalBirthday(pd);
				}else{
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 获得个人性别
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPersonalSex")
	public PageData getPersonalSex(String openId) throws Exception{
		PageData pageData=new PageData();
		PageData pd=new PageData();
		if(openId==null){
			pageData.put("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				String parentId=service.getPersonalId(openId);
				pd.put("parentId", parentId);
				pageData.put("errorcode", errorMsg.Success(0));
				PageData personalSex=service.getPersonalSex(pd);
				pageData.put("personalSex", personalSex);
			}else{
				pageData.put("errorcode", errorMsg.Success(2001));
			}
		}	
		return pageData;
	}

	/**
	 * 更改个人性别
	 * @param openId
	 * @param sex
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePersonalSex")
	public JSONObject updatePersonalSex(String openId,String sex) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(sex!=null){
					String parentId=service.getPersonalId(openId);
					pd.put("parentId", parentId);
					pd.put("sex", sex);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.updatePersonalSex(pd);
				}else{
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	@RequestMapping(value = "/addPersonalIcon")
	public JSONObject addInfo(String openId,@RequestParam CommonsMultipartFile picture){
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();	
		try{
			if(openId==null){
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}else{
				if(checkValueService.OpenidCheck(openId)){
					MultipartFile file = picture;
					String img = Utils.saveFile(file);
					pd.put("openId", openId);
					pd.put("icon",img);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.addPersonalIcon(pd);
				}else{
					jsonObject.element("errorcode", errorMsg.Success(2001));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 更改个人头像
	 * @param openId
	 * @param picture
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePersonalIcon")
	public JSONObject updatePersonalIcon(String openId, @RequestParam CommonsMultipartFile picture) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				MultipartFile file = picture;
				String img = Utils.saveFile(file);
				pd.put("openId", openId);
				pd.put("icon",img );
				jsonObject.element("errorcode", errorMsg.Success(0));
				service.updatePersonalIcon(pd);
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 获得学生管理列表
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getStudentInfo")
	public JSONObject getStudentInfo(String openId) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("openId", openId);
				if(service.getStudentInfo(pd).size()==0){
					jsonObject.element("errorcode", errorMsg.Success(1001));
				}else{
					jsonObject.element("errorcode", errorMsg.Success(0));
					return service.getStudentInfo(pd);
				}
				
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 解除绑定学生信息
	 * @param openId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unbindStudent")
	public JSONObject unbindStudent(String openId,String userId) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("userId",userId);
				jsonObject.element("errorcode", errorMsg.Success(0));
				service.unbindStudent(pd);
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 添加用户地址
	 * @param openId
	 * @param cityId
	 * @param townId
	 * @param address
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addAddress")
	public JSONObject addAddress(String openId,String address) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(address==null){
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}else{
					pd.put("openId", openId);					
					pd.put("address", address);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.addAddress(pd);
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}	
		}
		return jsonObject;
	}
	
	/**
	 * 获得用户所有地址
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPersonalAddress")
	public JSONObject getPersonalAddress(String openId) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				pd.put("openId", openId);
				if(service.getStudentInfo(pd).size()==0){
					jsonObject.element("errorcode", errorMsg.Success(1001));
				}else{
					jsonObject.element("errorcode", errorMsg.Success(0));
					return service.getPersonalAddress(pd);
				}
				
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 修改用户地址
	 * @param openId
	 * @param id
	 * @param cityId
	 * @param townId
	 * @param address
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePersonalAddress")
	public JSONObject updatePersonalAddress(String openId,String id,String address) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(id==null||address==null){
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}else{
					pd.put("id", id);
					pd.put("address", address);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.updatePersonalAddress(pd);
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	/**
	 * 更新选中学生
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateSelectedStu")
	public PageData doSelectedStu() throws Exception {
		PageData pageData = this.getPageData();
		return service.doSelectedStu(pageData);
	}
	
	/**
	 * 删除个人地址
	 * @param openId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletePersonalAddress")
	public JSONObject deletePersonalAddress(String openId,String id) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData pd=new PageData();
		if(openId==null){
			jsonObject.element("errorcode", errorMsg.Success(2001));
		}else{
			if(checkValueService.OpenidCheck(openId)){
				if(id==null){
					jsonObject.element("errorcode", errorMsg.Success(4001));
				}else{
					pd.put("id", id);
					jsonObject.element("errorcode", errorMsg.Success(0));
					service.deletePersonalAddress(pd);
				}
			}else{
				jsonObject.element("errorcode", errorMsg.Success(2001));
			}
		}
		return jsonObject;
	}
	/**
	 * 获取用户和当前选中学生所有信息
	 * 
	 * @param openId
	 * @return 用户所有信息
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getPersonalInfoAll")
	public PageData getAtteInfo() throws Exception {
		PageData pageData = this.getPageData();
		return service.getPersonalInfoAll(pageData);
		
	}
}
