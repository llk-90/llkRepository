package com.fh.service.weixin.message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("messageListService")
public class MessageListService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 留言列表显示
	 */
	public List<PageData> messageList(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MessageListMapper.list", pd);
		List<PageData> listct = this.listcount(pd);
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
	public List<PageData> listcount(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MessageListMapper.listcount", pd);
	}

	/**
	 * 留言内容保存
	 */
	public void save(PageData pd) throws Exception {
		dao.findForObject("MessageListMapper.messageSave", pd);
	}

	/**
	 * 留言内容显示
	 */
	public List<PageData> contentList(PageData pd) throws Exception {
		String pic="";
		this.update(pd);
		List<PageData> list= (List<PageData>) dao.findForList("MessageListMapper.contentList", pd);
		for (int i = 0; i < list.size(); i++) {
			PageData pds = list.get(i);
			if(null!=pds){
				pic=getHeadPic(pds,"photo");
				pds.put("photo", pic);
			}
		 }
		return list;
	}

	/**
	 * 修改信息状态为已读
	 */
	public void update(PageData pd) throws Exception {
		dao.update("MessageListMapper.messageUpdate", pd);
	}
	/**
	 * 查询用户类型  
	 */
	public PageData getUserid(PageData pd) throws Exception {
		String pic="";
		PageData pdt=(PageData) dao.findForObject("MessageListMapper.getUserid",pd);
		if(null!=pdt){
			pic=getHeadPic(pdt,"head_photo");
			pdt.put("head_photo", pic);
		}
		return pdt;
	}
	public PageData getUseridte(PageData pd) throws Exception {
		String pic="";
		PageData pdt=(PageData) dao.findForObject("MessageListMapper.getUseridte",pd);
		if(null!=pdt){
			pic=getHeadPic(pdt,"head_photo");
			pdt.put("head_photo", pic);
		}
		return pdt;
	}
	
	
	
	/**
	 * 查询班主任id  
	 */
	public PageData getreceiveid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MessageListMapper.getreceiveid",pd);
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
	
	
	
	

}
