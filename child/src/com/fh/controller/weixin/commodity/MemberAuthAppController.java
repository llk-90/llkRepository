package com.fh.controller.weixin.commodity;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.controller.base.BaseController;
import com.fh.service.member.MemberAuthService;
import com.fh.util.MapPlus;

/**
 * 
 * @author 860115007
 *
 */
@RestController
@RequestMapping("/memberAuthApp")
public class MemberAuthAppController extends BaseController {
	@Resource(name = "memberAuthService")
	private MemberAuthService memberAuthService;

	@RequestMapping("/list")
	public Object list() throws Exception {
		MapPlus result = new MapPlus();
		try {
			result.addParams("data", memberAuthService.authListApp(this.getPageData()));
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	@RequestMapping("/listNoFree")
	public Object listNoFree() throws Exception {
		MapPlus result = new MapPlus();
		try {
			result.addParams("data", memberAuthService.authListNoFreeApp(this.getPageData()));
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return result;
	}

}
