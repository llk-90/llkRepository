package com.fh.controller.weixin.ibaby;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.weixin.ibaby.ShopInfoService;

import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/getCommodityInfo")
public class ShopInfoController extends BaseController {
	
	
	@Resource(name = "shopInfoService")
	private ShopInfoService shopInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/commodityInfo")
	public Object commodityInfo() throws Exception {
		logBefore(logger, "获取商品信息");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		
		//商品名称.商品价格.商品图片
		List<PageData> commodityInfo = shopInfoService.list(pd);
		if(commodityInfo !=null && commodityInfo.size() >0){			
	        map.put("result", "success");
			map.put("commodity", commodityInfo);
		// 没有宝贝信息
		} else{
			map.put("result", "empty");
		}
		return map;
	}
 
	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

}
