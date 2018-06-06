package com.fh.service.weixinplus.regist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.dao.DaoSupport;
import com.fh.service.weixinplus.login.BingService;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.Utils;
import com.fh.util.model.StringDefault;

@Service("RegistService")
public class RegistService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	@Resource(name = "BingService")
	private BingService bingService;

	/**
	 * 获得补卡历史信息
	 * 
	 * @param openId
	 * @return pagedata补卡历史信息
	 * @throws IOException
	 */
	public PageData getRegistHistory(PageData pg) throws Exception {
		PageData res = new PageData();

		try {
			List<PageData> dblistlist = (List<PageData>) dao.findForList("CardReplacement.selectHistory", pg);
			if (dblistlist.size() == 0) {
				res.put("errorcode", errorMsg.Success(1001));
			} else {
				res.put("errorcode", errorMsg.Success(0));
				res.put("historyList", dblistlist);
			}

		} catch (Exception e) {
			// TODO: handle exception
			res.put("errorcode", errorMsg.Success(4001));
		}
		return res;
	}

	/**
	 * 提交补卡信息
	 * 
	 * @param pagedata
	 *            页面信息
	 * @return 是否成功
	 * @throws IOException
	 */
	public PageData submitCardInfo(PageData pg, MultipartHttpServletRequest request) throws Exception {
		PageData pageData = new PageData();
		try {
			// 先不处理
			int code = bingService.checkVerifyCode(pg);
			if (code != 0) {
				pageData.put(StringDefault.errorcode, errorMsg.Success(3002));
			} else {
				if (request != null) {
					MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
					List<MultipartFile> list = map.get("pictureList");
					String pictureUrl = "";
					for (MultipartFile c : list) {
						if (c.getSize() != 0) {
							pictureUrl = pictureUrl + Utils.saveFile(c) + ";";
						}
					}
					pageData.put("url", pictureUrl);
				}
				dao.save("CardReplacement.insertReplacement", pg);
				pageData.put(StringDefault.errorcode, errorMsg.Success(0));
			}

		} catch (Exception e) {
			// TODO: handle exception
			pageData.put(StringDefault.errorcode, errorMsg.Success(4001));
		}

		return pageData;
	}

}
