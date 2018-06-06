package com.fh.controller.weixin.leave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixin.leave.LeaveAppService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/leaveApp")
public class LeaveAppController extends BaseController {
	
	String menuUrl = "leaveApp/list.do"; //菜单地址(权限用)
	
	@Resource(name="leaveAppService")
	private LeaveAppService leaveAppService;
    /**
     * 列表
     * @throws Exception 
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() throws Exception {
        logBefore(logger, "请假列表");
        PageData pd = this.getPageData();
        Map<String, Object> map = new HashMap<>();
        String user_id = "";
        if(pd.containsKey("user_id")){        	
        	user_id = pd.getString("user_id");
        	System.out.println("zhijiehuoqu======================"+user_id);
        }else{
    		Subject currentUser = SecurityUtils.getSubject();
    		Session session = currentUser.getSession();
    		User user = (User) session.getAttribute(Const.SESSION_USER);
    		user_id = user.getUSER_ID();
//        	user_id = leaveAppService.findUseridByOpenid(pd.getString("openid"));
        	System.out.println("chuanzhi======================"+user_id);
        }
        pd.put("user_id", user_id);
        //pd.put("user_id", "d10d9ca630c34c2289428d6ee114be2d");  
        List<PageData> result = leaveAppService.list(pd);
        String school = leaveAppService.findSchool(pd.getString("user_id"));
        map.put("list", result);
        map.put("school",school);
        return map;
    }
    
    @RequestMapping(value="/approval")
    @ResponseBody
    public Object approval(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	logBefore(logger, "审核开始");
    	PageData pd = this.getPageData();
    	Map<String, String> map = new HashMap<>();
    	String result = "";
    	leaveAppService.approval(pd);
    	logBefore(logger, "通知开始");
    	leaveAppService.sendTemp(request, response, pd);
    	result = "succ";
    	map.put("result", result);
    	logAfter(logger);
    	return map;
    }
    
    @RequestMapping(value="/listNotice")
    @ResponseBody
    public Object listNotice() throws Exception{
    	logBefore(logger, "请假通知列表");
        PageData pd = this.getPageData();
        Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
        Map<String, Object> map = new HashMap<>();
        //String user_id = leaveAppService.findUseridByOpenid(pd.getString("openid"));
        pd.put("user_id", user_id);  //正式替换为user_id
        //pd.put("user_id", "9854125ee749429ba6fa4a9c402a930d");
        List<PageData> result = leaveAppService.listNotice(pd);
        String school = leaveAppService.findSchoolByParent(pd.getString("user_id"));
        map.put("list", result);
        map.put("school",school);
        return map;
    }
}
