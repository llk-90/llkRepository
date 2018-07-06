package com.example.entity;


public class ConfigureParam {

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
}
