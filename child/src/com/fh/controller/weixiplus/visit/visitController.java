package com.fh.controller.weixiplus.visit;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.controller.base.BaseController;
import com.fh.service.weixin.xft.XftRechargeService;
import com.fh.service.weixinplus.login.BingService;
import com.fh.service.weixinplus.regist.RegistService;
import com.fh.service.weixinplus.visit.ReplacementCardDetailService;
import com.fh.service.weixinplus.visit.visitService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

/**
 * 
 * @author roger
 *
 */
@RestController
@RequestMapping(value = "/visitRegister")
public class visitController extends BaseController {
	@Autowired
	private visitService service;
	@Resource(name = "RegistService")
	private RegistService registService;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	@Resource(name = "xftRechargeService")
	private XftRechargeService xftRechargeService;

	@Resource(name = "BingService")
	private BingService bingService;

	@Resource(name = "ReplacementCardDetailService")
	private ReplacementCardDetailService replacementCardDetailService;

	/**
	 * 拜访登记页面初始化
	 */
	@RequestMapping(value = "/getInfoList")
	public PageData visitInfoList(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.findInfoList(pd);
	}

	/**
	 * 申请拜访插入数据
	 */
	@RequestMapping(value = "/insertInfo")
	public PageData visitInfoAdd(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.addInfo(pd);
	}

	/**
	 * 获取老师待处理数据数据
	 */
	@RequestMapping(value = "/getHandleInfo")
	public PageData getHandleInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.getHandleInfo(pd);
	}

	/**
	 * 获取老师处理数据
	 */
	@RequestMapping(value = "/getNoHandleInfo")
	public PageData getNoHandleInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.getNoHandleInfo(pd);
	}

	/**
	 * 拜访回饋页面初始化
	 */
	@RequestMapping(value = "/recordInfo")
	public PageData recordInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.recordInfo(pd);
	}

	/**
	 * 页面点击判断状态
	 */
	@RequestMapping(value = "/judgeType")
	public PageData judgeType(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.judgeType(pd);
	}

	/**
	 * 同意拜访
	 */
	@RequestMapping(value = "/visitOk")
	public PageData visitOk(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.visitOk(pd);
	}

	/**
	 * 拜访拒绝
	 */
	@RequestMapping(value = "/visitRefuse")
	public PageData visitRefuse(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.visitRefuse(pd);
	}

	/*
	 * ***********请假的controller***************
	 */
	/**
	 * 申请请假插入数据
	 */
	@RequestMapping(value = "/leaveInfo")
	public PageData leaveInfoAdd(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.leaveInfoAdd(pd);
	}

	/**
	 * 请假回饋页面初始化
	 */
	@RequestMapping(value = "/leaveApplyInfo")
	public PageData leaveApplyInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.leaveApplyInfo(pd);
	}

	/**
	 * 获取老师待处理请假数据
	 */
	@RequestMapping(value = "/getNoHandleLeaveInfo")
	public PageData getNoHandleLeaveInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.getNoHandleLeaveInfo(pd);
	}

	/**
	 * 获取老师处理请假数据
	 */
	@RequestMapping(value = "/getHandleLeaveInfo")
	public PageData getHandleLeaveInfo(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.getHandleLeaveInfo(pd);
	}

	/**
	 * 请假拒绝
	 */
	@RequestMapping(value = "/leaveRefuse")
	public PageData leaveRefuse(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.leaveRefuse(pd);
	}

	/**
	 * 同意请假
	 */
	@RequestMapping(value = "/leaveOk")
	public PageData leaveOk(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.leaveOk(pd);
	}

	/*
	 **************** 作业
	 */
	/**
	 * 布置作业
	 */
	@RequestMapping(value = "/subHomework")
	public PageData subHomework(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.subHomework(pd);
	}

	/**
	 * 获取历史作业
	 */
	@RequestMapping(value = "/homeworkList")
	public PageData homeworkList(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.homeworkList(pd);
	}

	/**
	 * 家长获取历史作业
	 */
	@RequestMapping(value = "/askHomework")
	public PageData askHomework(String openId) throws Exception {
		PageData pd = this.getPageData();
		return service.askHomework(pd);
	}

	@RequestMapping(value = "/submit")
	@ResponseBody
	public Object submit(String openId, String SchoolID, String cardType, String className, String card_num,
			String contact_person, String phone_num, String address, String verifycode, String stuid, Integer money,String IcNo,
			MultipartHttpServletRequest multiRequest) throws Exception {
		PageData pg = new PageData();
		try {
			pg.put("openId", openId);
			pg.put("SchoolID", Integer.valueOf(SchoolID));
			pg.put("cardType", Integer.valueOf(cardType));
			pg.put("className", className);
			pg.put("card_num", Integer.valueOf(card_num));
			pg.put("contact_person", contact_person);
			pg.put("phone_num", phone_num);
			pg.put("address", address);
			pg.put("verifycode", verifycode);
			pg.put("stuId", Integer.parseInt(stuid));
			pg.put("money", money);
			pg.put("IcNo", IcNo);
			pg.put("type", 0);
			int code = bingService.checkVerifyCode(pg);
			PageData pageData = new PageData();
			PageData pdCheck = new PageData();
			pdCheck.put(StringDefault.errorcode, errorMsg.Success(0));
			if (code != 0) {
				pageData.put(StringDefault.errorcode, errorMsg.Success(3002));
			} else {
				pageData = replacementCardDetailService.saveReplacementRecord(pg, multiRequest);
				if (pdCheck.equals(pageData)) {
					return replacementCardDetailService.goToWeChatPay(pg);
				}
			}
			return pageData;
		} catch (NumberFormatException e) {
			PageData res = new PageData();
			res.put(StringDefault.errorcode, errorMsg.Success(4001));
			return res;
		} catch (Exception e) {
			PageData res = new PageData();
			res.put(StringDefault.errorcode, errorMsg.Success(0));
			return res;
		}

	}

}
