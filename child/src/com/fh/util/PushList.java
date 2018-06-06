package com.fh.util;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushList{
	/**
	 * Android推送
	 * 
	 * @param listcid
	 *            Android设备唯一标识
	 * @param title
	 * @param content
	 * @param icon
	 * @param transmissionContent
	 * @throws Exception
	 */
	public static void pushinfo(List<String> listcid, String title, String content, String icon,
			String transmissionContent) throws Exception {
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IGtPush push = new IGtPush(Const.HOST, Const.APPKEY, Const.MASTERSECRET);
		// 通知透传模板
		NotificationTemplate template = notificationTemplateDemo(title, content, icon, transmissionContent);
		//设置消息网络图标
		template.setLogoUrl(icon);
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 配置推送目标
		List<Target> targets = new ArrayList<Target>();
		int t = listcid.size();
		for (int i = 0; i < t; i++) {
			Target target = new Target();
			target.setAppId(Const.APPID);
			target.setClientId(listcid.get(i));
			targets.add(target);
		}
		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);

		// System.out.println(ret.getResponse().toString());
	}

	/**
	 * Android推送模板
	 * 
	 * @param title
	 * @param content
	 * @param icon
	 * @param transmissionContent
	 * @return
	 */
	public static NotificationTemplate notificationTemplateDemo(String title, String content, String icon,
			String transmissionContent) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(Const.APPID);
		template.setAppkey(Const.APPKEY);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(content);
		// 配置通知栏图标
//		template.setLogo(icon);
		// 配置通知栏网络图标
//	   template.setLogoUrl(icon);
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent(transmissionContent);
		return template;
	}

	/**
	 * IOS推送模板
	 * 
	 * @param dtl
	 *            苹果设备唯一标识
	 * @param title
	 * @param content
	 * @throws Exception
	 */
	public static void apnpush(List<String> dtl, String title, String content, String transmissionContent)
			throws Exception {
		IGtPush push = new IGtPush(Const.HOST, Const.APPKEY, Const.MASTERSECRET);
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(Const.APPID);
		template.setAppkey(Const.APPKEY);
		template.setTransmissionContent(transmissionContent);
		template.setTransmissionType(1);
		APNPayload apnpayload = new APNPayload();
		apnpayload.setSound("default");
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		// 通知文本消息标题
		alertMsg.setTitle(title);
		// 通知文本消息字符串
		alertMsg.setBody(content);
		// 对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
		alertMsg.setTitleLocKey(title);
		// 指定执行按钮所使用的Localizable.strings
        //alertMsg.setActionLocKey("ActionLocKey");
		apnpayload.setAlertMsg(alertMsg);
		template.setAPNInfo(apnpayload);
		ListMessage message = new ListMessage();
		message.setData(template);
		String contentId = push.getAPNContentId(Const.APPID, message);
		// System.out.println(contentId);
		// List<String> dtl = new ArrayList<String>();
		// dtl.add(devicetoken);
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IPushResult ret = push.pushAPNMessageToList(Const.APPID, contentId, dtl);
		// 响应
        //System.out.println(ret.getResponse());
	}
}