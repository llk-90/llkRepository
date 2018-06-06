package com.fh.service.weixinplus.Personal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.system.user.UserService;
import com.fh.service.weixinplus.weixiplusCommon.*;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSONObject;

/**
 * 
 * @author phf
 *
 */
@Service("personalIndexService")
public class PersonalIndexService {

	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="CheckValueService")
	private CheckValueService checkValueService;
	
	@Resource(name="errorMsg")
	private ErrorMsg errorMsg;
	
	
	/**
	 * 获得个人主页的个人信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getPersonalInfo(PageData pd) throws Exception{
		JSONObject jsonObject=new JSONObject();
		PageData personalInfo=(PageData) dao.findForObject("PersonalIndex.getPersonalInfo", pd);
		if(personalInfo!=null){
			jsonObject.element("errorcode", errorMsg.Success(0));
			jsonObject.element("personalInfo", personalInfo);
		}else{
			jsonObject.element("errorcode", errorMsg.Success(1001));
		}

		return jsonObject;
	}
	
	/**
	 * 获得个人姓名
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getPersonalName(PageData pd) throws Exception{
		return (String) dao.findForObject("PersonalIndex.getPersonalName", pd);
	}
	
	/**
	 * 修改个人姓名
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonalName(PageData pd) throws Exception{
		dao.update("PersonalIndex.updatePersonalName", pd);
	}
	
	/**
	 * 获得个人生日
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getPersonalBirthday(PageData pd) throws Exception{
		return (String) dao.findForObject("PersonalIndex.getPersonalBirthday", pd);
	}
	
	/**
	 * 修改个人生日
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonalBirthday(PageData pd) throws Exception{
		dao.update("PersonalIndex.updatePersonalBirthday", pd);
	}
	
	/**
	 * 获得个人Id
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public String getPersonalId(String openId) throws Exception{
		return (String) dao.findForObject("PersonalIndex.getPersonalId", openId);
	}
	
	/**
	 * 获得个人性别
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getPersonalSex(PageData pd) throws Exception{
		PageData pData=new PageData();
		String sex=(String) dao.findForObject("PersonalIndex.getPersonalSex", pd);
		pData.put("personalsex", sex);
		return pData;
	}
	
	/**
	 * 修改个人性别
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonalSex(PageData pd) throws Exception{
		dao.update("PersonalIndex.updatePersonalSex", pd);
	}
	
	/**
	 * 添加用户头像
	 * @param pd
	 * @throws Exception
	 */
	public void addPersonalIcon(PageData pd) throws Exception{
		dao.save("PersonalIndex.addPersonalIcon", pd);
	}
	
	/**
	 * 更改个人头像
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonalIcon(PageData pd) throws Exception{
		dao.update("PersonalIndex.updatePersonalIcon", pd);
	}
	
	/**
	 * 获得学生管理信息列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getStudentInfo(PageData pd) throws Exception{
		JSONObject jsonObject=new JSONObject();
		List<PageData> studentInfo=(List<PageData>) dao.findForList("WeixiplusCommon.selectStuUserid2", pd);
		if(studentInfo.size()!=0){
			jsonObject.element("errorcode", errorMsg.Success(0));
			for (int i = 0; i < studentInfo.size(); i++) {
				PageData pageData = studentInfo.get(i);
				pageData.put("IcNo", pageData.get("IcNo").toString());			
			}
			jsonObject.element("studentInfo", studentInfo);
		}else{
			jsonObject.element("errorcode", errorMsg.Success(1001));
		}
		return jsonObject;
	}
	/**
	 * 更新选中学生
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData doSelectedStu(PageData pd) throws Exception{
		
		PageData res = new PageData();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			List<PageData> userList =
					(List<PageData>)dao.findForList("PersonalIndex.selectSchoolIdByIcNo", pd);
			if (userList.size()==0) {
				res.put("errorcode", errorMsg.Success(4002));
			}
			else {
				PageData info = userList.get(0);
				session.setAttribute("schoolId", info.get("SchoolId"));
				dao.update("PersonalIndex.changeselectedstu", pd);
				res.put("errorcode", errorMsg.Success(0));
			}		
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e);
			res.put("errorcode", errorMsg.Success(4002));
		}
		 
		return res;
	}
	/**
	 * 解除绑定学生
	 * @param pd
	 * @throws Exception
	 */
	public void unbindStudent(PageData pd) throws Exception{
		dao.update("PersonalIndex.unbindStudent", pd);
	}
	
	/**
	 * 添加用户地址
	 * @param pd
	 * @throws Exception
	 */
	public void addAddress(PageData pd) throws Exception{
		dao.save("PersonalIndex.addAddress", pd);
	}
	
	/**
	 * 获得用户所有地址
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getPersonalAddress(PageData pd) throws Exception{
		JSONObject jsonObject=new JSONObject();
		List<PageData> addressInfo=(List<PageData>) dao.findForList("PersonalIndex.getPersonalAddress", pd);
		if(addressInfo.size()!=0){
			jsonObject.element("errorcode", errorMsg.Success(0));
			jsonObject.element("addressInfo", addressInfo);
		}else{
			jsonObject.element("errorcode", errorMsg.Success(1001));
		}
		return jsonObject;
	}
	
	/**
	 * 修改用户地址
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonalAddress(PageData pd) throws Exception{
		dao.update("PersonalIndex.updatePersonalAddress", pd);
	}
	
	/**
	 * 删除用户地址
	 * @param pd
	 * @throws Exception
	 */
	public void deletePersonalAddress(PageData pd) throws Exception{
		dao.delete("PersonalIndex.deletePersonalAddress", pd);
	}
	


	/**
	 * 查询用户家长和孩子的全部信息
	 */
	@SuppressWarnings("unchecked")
	public PageData getPersonalInfoAll(PageData pd) throws Exception{
		List<PageData> list = new ArrayList<>();
		PageData temppd = new PageData();
		list = (List<PageData>) dao.findForList("PersonalIndex.selectpersonalinfoall", pd);
		if(list.size() == 0){
			temppd.put(StringDefault.errorcode, errorMsg.Success(1002));
		}else{
			temppd = list.get(0);
			temppd.put(StringDefault.errorcode, errorMsg.Success(0));
		}
		
		//System.out.println(temppd);
		
		return temppd;
		
	}
}