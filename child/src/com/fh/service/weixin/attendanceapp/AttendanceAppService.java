package com.fh.service.weixin.attendanceapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("attendanceAppService")
public class AttendanceAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> list(PageData pd) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat no0 = new SimpleDateFormat("yyyy-M-d");
		SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		/*if(pd.getString("date") == null){
			pd.put("date", sdf.format(date));
		}*/
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		/*String user_id = (String) dao.findForObject("AttendanceAppMapper.findUserId", pd);*/
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		
		
		List<PageData> listS = new ArrayList<PageData>();
		List<PageData> list = (List<PageData>) dao.findForList("AttendanceAppMapper.list", pd);
		Map<String, PageData> map = new HashMap<String, PageData>();
		for (int i = 0; i <list.size(); i++) {
			PageData currPD = list.get(i);
			String sid = currPD.get("ar_stu_id").toString();
			if (map.containsKey(sid)) {
				PageData pData = map.get(sid);
				if(currPD.getString("ar_type").equals("0")){
					pData.put("start_time",timef.format(currPD.get("ar_time")) );	
				}
				if(currPD.getString("ar_type").equals("1")){
					pData.put("end_time",timef.format(currPD.get("ar_time")) );	
				}
			} else {
				if(currPD.getString("ar_type").equals("0")){
					currPD.put("start_time",timef.format(currPD.get("ar_time")) );	
				}
				if(currPD.getString("ar_type").equals("1")){
					currPD.put("end_time",timef.format(currPD.get("ar_time")) );	
				}
				map.put(sid, currPD);
			}
		}
		for (Map.Entry<String, PageData> m : map.entrySet()) {
			PageData p = m.getValue();
			listS.add(p);
		}
		for(PageData p : listS){
			p.put("ar_date", no0.format(p.get("ar_date")));
		}
		return listS;
	}
	public List<PageData> listAll(PageData pd) throws Exception{
//		String user_id = (String) dao.findForObject("AttendanceAppMapper.findUserId", pd);
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		/*String user_id = (String) dao.findForObject("AttendanceAppMapper.findUserId", pd);*/
		String user_id = user.getUSER_ID();
		pd.put("user_id", user_id);
		List<PageData> list =(List<PageData>) dao.findForList("AttendanceAppMapper.listAll", pd);
		SimpleDateFormat no0 = new SimpleDateFormat("yyyy-M-d");
		for(PageData p :list){
			p.put("ar_date", no0.format(p.get("ar_date")));
		}
		return list;
	}
}
