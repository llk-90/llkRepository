package com.fh.service.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("messageService")
public class MessageService{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> messageList(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MessageMapper.list", page);
		List<PageData> listct = this.listcount(page);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				PageData pds = list.get(i);
				String pic=getHeadPic(pds,"photo");
				pds.put("photo", pic);
				if (!listct.isEmpty()) {
					for (int t = 0; t < listct.size(); t++) {
						PageData pda = listct.get(t);
						if (pds.getString("lm_send_user_id").equals(pda.getString("lm_send_user_id"))) {
							pds.put("ant", pda.get("msg_count"));
						}else{
							pds.put("ant", "0");
						}
					}
				} else {
					pds.put("ant", "0");
				}
			}
		}
		return list;
	}
	
	/**
	 * 留言列表数量
	 */
	public List<PageData> listcount(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MessageMapper.listcount", page);
	}
	
	/**
	 * 头像展示处理
	 */
	public String getHeadPic(PageData pd,String head_photo){
		String pic = "";
		if(null==pd||pd.isEmpty()){
			return pic;
		}
		if(pd.getString(head_photo) != null){
			pic = pd.getString(head_photo);
		}
		if(null!=pic&&!pic.isEmpty()){
			if(pic.indexOf("http")==-1){
				return "/files"+pic;
			}else{
				return pic;
			}	
		}else{
			return "";
		}
	}
	
	
	/*
	 * 回复页面留言信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByIdList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("MessageMapper.findlist", page);
	}
	
	/*
	 * 回复页面姓名列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByIdName(Page page) throws Exception{
		return (List<PageData>) dao.findForList("MessageMapper.namelistPage", page);
	}
	
	/*
	* 保存回复内容
	*/
	public void saveReply(PageData pd)throws Exception{
		dao.update("MessageMapper.save", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("MessageMapper.edit", pd);
	}
	
	
}
