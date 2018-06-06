package com.fh.service.weixinplus.unbunding;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.model.StringDefault;

/**
 * This class is using to unbind the Teacher info.
 * 
 * @author 860118001
 * @date 2018年5月17日
 * 
 */
@Service("UnBindingTeacherService")
public class UnBindingTeacherService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	/**
	 * Teacher UnBinding method.
	 * 
	 * @author 860118001
	 * @date 2018年5月17日 上午11:29:53
	 *
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData unBingTeaInfo(PageData pd) throws Exception {
		PageData pageData = new PageData();
		try {
			if (null != dao.findForList("TeacherUnBindMapper.SelectInTeacherTableByOpenId", pd)) {
				// The info in qry_stuPar table was deleted.
				if (dao.delete("TeacherUnBindMapper.DeleteByTeacherOpenIdInQryTable", pd) != null) {
					// The info in teacher_user_weixin table was deleted.
					if (dao.delete("TeacherUnBindMapper.DeleteByTeacherOpenIdInTeacherTable", pd) != null) {
						pageData.put(StringDefault.errorcode, errorMsg.Success(0));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageData.put(StringDefault.errorcode, errorMsg.Success(4002));
		}
		return pageData;
	}
}