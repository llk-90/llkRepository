package com.fh.controller.system.configure;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.configure.ConfigureService;
import com.fh.util.PageData;

/**
 * 类名称：EnvironmentManageController 创建人：ZY 创建时间：2016年6月24日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/configure")
public class ConfigureController extends BaseController {

	public static class Configure {
		private String appId;
		private String mchId;
		private String notifyURL;
		private String prepayURL;
		private String apiKey;
		private String appSecret;

		public Configure() {
			super();
			this.appId = "wx09b3926c38a66b9e";
			this.mchId = "1371343902";
			this.notifyURL = "http://www.guanai100.cn/payment/wxpay_notify.webapp";
			this.prepayURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			this.apiKey = "320682198911174692lcc18258166863";
			this.appSecret = "11978f1098470ad6579e10713af2d074";
		}

		public Configure(String appId, String mchId, String notifyURL, String prepayURL, String apiKey) {
			super();
			this.appId = appId;
			this.mchId = mchId;
			this.notifyURL = notifyURL;
			this.prepayURL = prepayURL;
			this.apiKey = apiKey;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getMchId() {
			return mchId;
		}

		public void setMchId(String mchId) {
			this.mchId = mchId;
		}

		public String getNotifyURL() {
			return notifyURL;
		}

		public void setNotifyURL(String notifyURL) {
			this.notifyURL = notifyURL;
		}

		public String getPrepayURL() {
			return prepayURL;
		}

		public void setPrepayURL(String prepayURL) {
			this.prepayURL = prepayURL;
		}

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String getAppSecret() {
			return appSecret;
		}

		public void setAppSecret(String appSecret) {
			this.appSecret = appSecret;
		}
	}

	String menuUrl = "configure/list.do"; // 菜单地址(权限用)

	@Resource(name = "configureService")
	private ConfigureService configureService;

	/**
	 * 
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/list")
	public ModelAndView ztree() throws Exception {
		ModelAndView mv = this.getModelAndView();
		String file = "C:\\Users\\860115009\\Desktop\\come\\filename.txt";
		configureService.readTxtFile(file);
		;
		mv.setViewName("system/configure/systemConfigure");
		return mv;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/createFile")
	public void createFile() throws Exception {

		logBefore(logger, "新建写入txt文件");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String content = ("应用ID=" + pd.getString("applicationID") + "," + "商户号=" + pd.getString("merchants") + ","
				+ "回调通知url=" + pd.getString("informUrl") + "," + "统一下单url=" + pd.getString("orderUrl") + "," + "APIKey="
				+ pd.getString("apiKey") + "," + "域名=" + pd.getString("domainName"));
		configureService.createFile(content);
		;
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
	}
}
