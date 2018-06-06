package com.fh.controller.weixin.leave;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.controller.weixin.TemplateData;
import com.fh.controller.weixin.WxTemplate;
import com.fh.controller.weixin.WxTest;
import com.fh.controller.weixin.WxUtil;
import com.fh.entity.system.User;
import com.fh.service.weixin.leave.LeaveApplicationService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping(value="/leaveappController")
public class LeaveApplicationController extends BaseController {
	
	@Resource(name="leaveappService")
	private LeaveApplicationService leaveappService;
	
	/**
	 * 请假申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/leaveApplication")
	@ResponseBody
	public Object leavaInsert(HttpServletRequest request) throws Exception{
		logBefore(logger, "请假申请");
		PageData pd = this.getPageData();
		Map<String, String> map = new HashMap<String,String>();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String openid = user.getOPENID();
		pd.put("userId", userId);
		pd.put("openid", openid);
		leaveappService.leavaInsert(pd);
		map.put("result", Const.SUCCESS);
		pd = leaveappService.findByOpenid(pd);	//根据openid读取学生信息
		PageData pd1 = this.getPageData();
		pd1.put("openid", openid);
		pd1 = leaveappService.findTeacherByOpenid(pd1);	//根据ID读取老师openID
		if(pd1!=null&&!Tools.isEmpty(pd1.getString("openTeaId"))){
			String openTeaID = pd1.getString("openTeaId");
			pd1.put("openTeaID", openTeaID);
			
			
			// 测试帐号新建测试模板。填写模板标题、模板内容
			// 模板标题：哈哈哈
			// 模板内容：我是{{aidi.DATA}}
			// 生成模板id：lTSkmf-__p7J5UfY_KH3EBLvfByOLZa2z5GXSUQGDQw	
			WxTemplate temp = new WxTemplate();
			// 点击消息转到的url
			/*temp.setUrl("http://www.baidu.com");*/
			// 家长的openid
			temp.setTouser(openTeaID);
			// 标题颜色
			temp.setTopcolor("#000000");
			// 模板id
			temp.setTemplate_id("OReg28haXX4u4hx8-Av-idUyd_ITcVtujC8TXxWC4EY");

			// 参数设置
			Map<String, TemplateData> m = new HashMap<String, TemplateData>();
			TemplateData zad = new TemplateData();
			zad.setColor("#000000");
			zad.setValue("您好，您有一个来自家长的请假申请,请在教师应用中及时处理。");			
			m.put("first", zad);
			//学生名字
			TemplateData lyf = new TemplateData();
			String name = pd.getString("name");
			lyf.setValue(name);
			m.put("childName", lyf);
			
			//请假理由
			TemplateData lr = new TemplateData();
			String reason = pd.getString("reason");
			lr.setValue(reason);
			m.put("score", lr);
			
			//请假时间
			TemplateData skc = new TemplateData();
			String stime = pd.getString("stime").toString();
			String etime = pd.getString("etime").toString();
			Integer day =Integer.parseInt(pd.get("day").toString());
			Integer state = Integer.parseInt(pd.get("state").toString());
			if (day == 0 && state == 1) {				
				skc.setValue(stime+"上午");
			}else if(day == 0 && state == -1){
				skc.setValue(stime+"下午");
			}else if(day == 0 && state == 0){
				skc.setValue(stime+"全天");
			}else {
			 String	day1 = day.toString();
			 Integer day2 =Integer.parseInt(day1);
			 day2+=1;
			 skc.setValue(stime+"到"+etime+"共"+day2+"天");
			}
			m.put("time", skc);
			temp.setData(m);
			// 开始发送
			WxTest wx = new WxTest();
			wx.sendTemp(WxUtil.wx_appid, WxUtil.wx_appsecret, temp,  request);
		}
		return map;
		
	}
	
	/**
	 * 已经请了假的右上角标红
	 */
	@RequestMapping(value= "/hadleave")
	@ResponseBody
	public Object hadleave() throws Exception{
		logBefore(logger, "请假申请");
		PageData pd = this.getPageData();
		//从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		pd.put("userId", userId);
		Map<String, Object> map = new HashMap<>();
		List<PageData> result = leaveappService.list(pd);		
		map.put("list", result);
		map.put("result", Const.SUCCESS);
		return map;
	
	}	
	
	/**
	 * 已经请了假的不能再次请假
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public Object check() throws Exception {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<>();
		//从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String openid = user.getOPENID();
		pd.put("openid", openid);
		List<PageData> getCheckList = leaveappService.getCheckList(pd);	//获取数据库中的所有请假时间
		String start_time = pd.getString("start_time");					//微信端传过来的请假开始时间
		String endtime = pd.getString("endtime");						//微信端传过来的请假结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date stime = sdf.parse(start_time);								//请假开始时间
		Date eime = sdf.parse(endtime);									//请假结束时间
		if (getCheckList.size()>0) {
			for(PageData checkList : getCheckList){
				String checkStime = checkList.getString("stime");
				String checkEtime = checkList.getString("etime");
				Date rtime = sdf.parse(checkStime);
				Date etime = sdf.parse(checkEtime);
				if (stime.equals(rtime) && eime.equals(etime)) {  //单日请假判断1
					map.put("flag", "fail");
					return map;
				}else if(stime.equals(rtime) && eime.equals(rtime)) {//连续日期（开始）进行单日请假判断
					map.put("flag", "fail");
					return map;
				}else if(stime.equals(etime) && eime.equals(etime)) {//连续日期（结束）进行单日请假判断
					map.put("flag", "fail");
					return map;
				}else if(stime.after(rtime) && stime.before(etime)){//连续日期（中间）进行单日请假判断
					map.put("flag", "fail");
					return map;
				}else if(!stime.equals(rtime) && eime.equals(rtime)){
					map.put("flag", "fail");
					return map;
				}else if(stime.equals(etime) && !eime.equals(etime)){
					map.put("flag", "fail");
					return map;
				}
				else if(!stime.equals(rtime) && eime.after(rtime) && eime.before(etime) ){
					map.put("flag", "fail");
					return map;
				}else if(!eime.equals(etime) && stime.after(rtime) && stime.before(etime) ){
					map.put("flag", "fail");
					return map;
				}
				else{
					map.put("flag", Const.SUCCESS);		
				}
			}
		}
		map.put("flag", Const.SUCCESS);
		return map;
	}
}
