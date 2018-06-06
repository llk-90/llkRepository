package com.fh.service.weixinplus.teacher;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.DigitalNumGenerate;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

@Service("teacherBingService")
public class TeacherBingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	/**
	 * 根据输页面上输入信息进行绑定
	 * 
	 * @param 页面信息
	 * @return 学生信息
	 * @throws Exception
	 * @throws IOException
	 */
	public PageData saveTeacherBingInfo(PageData pd) throws Exception {

		PageData pageData = new PageData();
		try {
			dao.save("TeacherBingMapper.insertTeacherBing", pd);
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));
		} catch (Exception e) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(4002));
		}
		return pageData;
	}

	public PageData saveQryWeiBing(PageData pd) throws Exception {

		PageData pageData = new PageData();
		try {
			PageData paSel = (PageData) dao.findForObject("TeacherBingMapper.selTeacherBing", pd);
			Integer idSel = Integer.parseInt(paSel.get("teacherId").toString());
			String test = DigitalNumGenerate.generateCode();
			String bStuid = "9697" + test;
			pd.put("ParentId", idSel);
			pd.put("StudentId", bStuid);
			dao.save("TeacherBingMapper.insertQryPaBing", pd);
			dao.save("TeacherBingMapper.insertWeiBing", pd);
			pageData.put(StringDefault.errorcode, errorMsg.Success(0));
		} catch (Exception e) {
			pageData.put(StringDefault.errorcode, errorMsg.Success(4002));
		}
		return pageData;
	}
}
