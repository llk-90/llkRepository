package com.fh.service.system.smsrecord;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;

@Service("SmsRecordService")
public class SmsRecordService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	 * 列表
	 */

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("SmsRecordMapper.datalistPage", page);
	}

	/*
	 * 通过id获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SmsRecordMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] arrayDATA_IDS) throws Exception {
		dao.delete("SmsRecordMapper.deleteAll", arrayDATA_IDS);
	}

	/*
	 * 短信记录添加
	 */
	public void insert_record(List<String> phoneList, String content) throws Exception {
		if (phoneList.size() > 0) {
			PageData pdatasms = new PageData();
			pdatasms.put("sms_content", content);
			// 信息实际发送条数
			int count = phoneList.size();
			pdatasms.put("sms_count", count);
			this.smsrecordsave(pdatasms);
			// 主表id
			int sms_id = this.sms_id();
			for (String phones : phoneList) {
				String pohone =phones;
				PageData plist = new PageData();
				plist.put("smsrecord_id", sms_id);
				plist.put("smsinfo_phone", pohone);
				// 子表信息添加
				this.smsrecordinfosave(plist);
			}

		}
	}

	/*
	 * 短信记录主表添加
	 */
	public void smsrecordsave(PageData pd) throws Exception {
		dao.save("SmsRecordMapper.smsrecordsave", pd);
	}

	/*
	 * 获取短信记录主表id
	 */
	public Integer sms_id() throws Exception {
		return (Integer) dao.findForObject("SmsRecordMapper.lastid", null);
	}

	/*
	 * 短信记录子表添加
	 */
	public void smsrecordinfosave(PageData pd) throws Exception {
		dao.save("SmsRecordMapper.smsrecordinfosave", pd);
	}

}
