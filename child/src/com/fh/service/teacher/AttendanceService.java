package com.fh.service.teacher;

import java.util.ArrayList;
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
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;;


@Service("attendanceService")
public class AttendanceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public List<PageData> datalistPage(Page page) throws Exception{
		List<PageData> listS = new ArrayList<PageData>();
		List<PageData> list = (List<PageData>) dao.findForList("AttendanceMapper.datalistPage", page);
		Map<String, PageData> map = new HashMap<String, PageData>();
		for (int i = 0; i <list.size(); i++) {
			PageData currPD = list.get(i);
			String sid = currPD.get("skey").toString();
			if (map.containsKey(sid)) {
				PageData pd = map.get(sid);
				if(currPD.getString("ar_type").equals("0")){
					pd.put("start_time",currPD.get("ar_time").toString() );	
				}
				if(currPD.getString("ar_type").equals("1")){
					pd.put("end_time",currPD.get("ar_time").toString() );	
				}
				pd.put("state", "1");
			} else {
				if(currPD.getString("ar_type").equals("0")){
					currPD.put("start_time",currPD.get("ar_time").toString() );	
				}
				if(currPD.getString("ar_type").equals("1")){
					currPD.put("end_time",currPD.get("ar_time").toString() );	
				}
				map.put(sid, currPD);
			}
		}
		for (Map.Entry<String, PageData> m : map.entrySet()) {
			PageData pd = m.getValue();
			listS.add(pd);
		 }
		return listS;
	}
	
	public PageData findhead(PageData pd) throws Exception {
		PageData head = new PageData();
		int all = (int) dao.findForObject("AttendanceMapper.findAll", pd);
		int isSign = (int) dao.findForObject("AttendanceMapper.findIsSign", pd);
		int noSign = all - isSign;
		head.put("all", all);
		head.put("isSign", isSign);
		head.put("noSign", noSign);
		return head;
	}
	public String findZoneId() throws Exception{
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		return (String) dao.findForObject("AttendanceMapper.findZoneId", user_id);
	}
	public List<PageData> list(PageData pda) throws Exception{
		List<PageData> listS = new ArrayList<PageData>();
		List<PageData> list = (List<PageData>) dao.findForList("AttendanceMapper.daochu", pda);
		Map<String, PageData> map = new HashMap<String, PageData>();
		for (int i = 0; i <list.size(); i++) {
			PageData currPD = list.get(i);
			String sid = currPD.get("skey").toString();
			if (map.containsKey(sid)) {
				PageData pd = map.get(sid);
				if(currPD.getString("ar_type").equals("0")){
					pd.put("start_time",currPD.get("ar_time").toString() );	
				}
				if(currPD.getString("ar_type").equals("1")){
					pd.put("end_time",currPD.get("ar_time").toString() );	
				}
				pd.put("state", "1");
			} else {
				if(currPD.getString("ar_type").equals("0")){
					currPD.put("start_time",currPD.get("ar_time").toString() );	
				}
				if(currPD.getString("ar_type").equals("1")){
					currPD.put("end_time",currPD.get("ar_time").toString() );	
				}
				map.put(sid, currPD);
			}
		}
		for (Map.Entry<String, PageData> m : map.entrySet()) {
			PageData pd = m.getValue();
			listS.add(pd);
		 }
		return listS;
	}
}