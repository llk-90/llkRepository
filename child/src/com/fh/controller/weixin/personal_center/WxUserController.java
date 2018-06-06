package com.fh.controller.weixin.personal_center;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixin.message.MessageListService;
import com.fh.service.weixin.personal_center.WxUserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping("/wxUser")
public class WxUserController extends BaseController {

	@Resource(name = "wxUserService")
	private WxUserService wxUserService;
	@Resource(name = "messageListService")
	private MessageListService messageListService;

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	public Object updateInfo() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		String s = pd.getString("user_id");
		String[] s1 = s.split("hkY5sn");
		pd.put("user_id", s1[0]);
		String newPwdVStr = new String(Base64.decodeBase64(s1[1].getBytes()), "UTF-8");
		newPwdVStr = newPwdVStr.substring(0,newPwdVStr.length()-6);
		newPwdVStr = new String(Base64.decodeBase64(newPwdVStr.getBytes()), "UTF-8");
		pd.put("newPwd", newPwdVStr);

		String oldPwdVStr =  new String(Base64.decodeBase64(s1[2].getBytes()), "UTF-8");
		oldPwdVStr = oldPwdVStr.substring(0,oldPwdVStr.length()-6);
		oldPwdVStr = new String(Base64.decodeBase64(oldPwdVStr.getBytes()), "UTF-8");
		pd.put("oldPwd", oldPwdVStr);

		PageData pWord = wxUserService.findPwd(pd);
		String oldPwd = new SimpleHash("SHA-1", pWord.getString("phone"), pd.getString("oldPwd")).toString();
		if (!oldPwd.equals(pWord.getString("password"))) {
			map.put("msg", "fail");
			return AppUtil.returnObject(new PageData(), map);
		}
		String newPwd = new SimpleHash("SHA-1", pWord.getString("phone"), pd.getString("newPwd")).toString();
		pd.put("password", newPwd);
		wxUserService.updatePwd(pd);
		map.put("msg", Const.SUCCESS);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 查询用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Info")
	@ResponseBody
	public Object find() throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = this.getPageData();
		if(user!=null){
			pd.put("USER_ID", user.getUSER_ID());
		}else{
			pd.put("USER_ID", "");
		}
		
		PageData Info = wxUserService.findInfo(pd);
		PageData ad_phone = wxUserService.findAd();
		PageData ant = wxUserService.findInfoCount(pd);
		String photo = messageListService.getHeadPic(Info, "head_photo");
		// 2016/9/1 yc 修改
		if (Info == null) {
			Info = new PageData();
		}
		Info.put("head_photo", photo);
		Info.put("ad_phone", ad_phone.get("phone"));
		Info.put("infoant", ant);
		return AppUtil.returnObject(new PageData(), Info);
	}

}
