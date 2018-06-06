package com.fh.controller.weixin.jxht;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.controller.im.imServer;
import com.fh.service.jxht.EquipmentService2;
import com.fh.util.PageData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.sourceforge.pinyin4j.PinyinHelper;

@Controller
@RequestMapping(value = "/equipment2")
public class EquipmentController2 extends BaseController {
	@Resource(name="equipmentService2")
	private EquipmentService2 equipmentService2;
	 //找出该用户好友信息，通过openid
	@RequestMapping(value = "/selectClassStus")
	@ResponseBody
	public List<PageData> selectClassStus() throws Exception {
		PageData pd=this.getPageData();
		PageData tpd=equipmentService2.selectTeaInfo(pd);
		PageData spd=equipmentService2.selectClassStus2(pd);
		List<PageData> list= new ArrayList();
		if(spd!=null&&tpd==null){
	      //该用户为学生用户
		  int classId=(int) equipmentService2.selectClassStus2(pd).get("ClassId");
		  pd.put("classID", classId);
		  //通过该学生用户的班级id查找出该班级的老师
		  List<PageData> pdteach=equipmentService2.slectTinfoByclass(pd);
		  //查出同班同学
		   list= (List<PageData>) equipmentService2.selectClassStus(pd);
		  //把老师塞进返回列表
		if(pdteach.size()>0){
			for(int i=0;i<pdteach.size();i++){
				list.add(pdteach.get(i));
			} 
		 }
		}
		else if(spd==null&&tpd!=null)
		{
           //该用户为教师
		   //通过该用户所属班级查出该班级的学生列表
		  list= equipmentService2.selectStuByClass(tpd); 
		  //根据班级查出其他老师
		  List<PageData> pdteach=equipmentService2.slectTinfoByclass(tpd);
	      if(pdteach.size()>0){
			for(int i=0;i<pdteach.size();i++){
				//将该班级中的其他老师塞进list
					list.add(pdteach.get(i));
				 	 
				} 
		   }  
	 
		    
		}
	
	    for(int i=0;i<list.size();i++){
	    	String str=list.get(i).getString("UserName");
	    	if(str.equals("")){
	    		str="姓名为空";
	    	}
	    	char str2='a';
	    		for (int j = 0; j < str.length(); j++) {  
	    			str2 =(PinyinHelper.toHanyuPinyinStringArray(str.charAt(0))[0].toUpperCase()).charAt(0); 
	    			list.get(i).put("flag", str2);
	    			list.get(i).put("name", list.get(i).getString("UserName"));
	    		}  
	    }
	    
	    
	    return list;
	}
    //找出该用户信息，通过openid
	@RequestMapping(value = "/selectClassStus2")
	@ResponseBody
	public PageData selectClassStus2() throws Exception {
		PageData pd=this.getPageData();
		PageData tpd=equipmentService2.selectTeaInfo(pd);
		PageData spd=equipmentService2.selectClassStus2(pd);
		PageData outpd=new PageData();
		//如果该openid在学生表内检索不到数据则到教师表内进行检索
	 
		if(spd!=null&&tpd==null){
			outpd=spd;
		}else if(spd==null&&tpd!=null)
		{
			outpd=tpd;
		} 
	    return outpd;
	}
	
	@RequestMapping(value = "/startServer")
	@ResponseBody
	public void startServer() throws Exception {
		 new imServer(9999).run();
		 System.out.println("Server is start");
	}
 
}
