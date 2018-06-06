package com.fh.service.teacher;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;

import net.sf.json.JSONObject;;

@Service("noticeManageService")
public class NoticeManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public String findSchoolId(String userId) throws Exception {
		return (String) dao.findForObject("NoticeManageMapper.findSchoolId", userId);
	}

	/**
	 * 通知列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findNoticeList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("NoticeManageMapper.findNoticelistPage", page);
	}

	/**
	 * 根据id找到该资讯
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByNoticeId(PageData pd) throws Exception {
		PageData pagedata = (PageData) dao.findForObject("NoticeManageMapper.findByNoticeId", pd);
		String content = pagedata.remove("content").toString();
		content = content.replaceAll("\r", "");
		content = content.replaceAll("\n", "");
		pagedata.put("content", content);
		return pagedata;
	}

	/**
	 * 根据id删除资讯
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteAll(String[] id) throws Exception {
		dao.delete("NoticeManageMapper.deleteById", id);
	}

	/**
	 * 添加新资讯
	 * 
	 * @param pd
	 * @param img
	 * @throws Exception
	 */
	public void newNotice(PageData pd, MultipartFile[] img) throws Exception {
		String path = null;
		try {
			// 保存图片
			this.saveImage(img, path, pd);
			String content = pd.remove("content").toString();
			content = content.replaceAll("\r", "");
			content = content.replaceAll("\n", "");
			pd.put("content", content);
			dao.save("NoticeManageMapper.newNotice", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 保存图片
	 * 
	 * @param img
	 * @param path
	 * @param pd
	 */
	public void saveImage(MultipartFile[] imgs, String path, PageData pd) {
		if(imgs != null && imgs.length>0){
			for(MultipartFile img : imgs){
				if (!img.isEmpty()) {
					// 文件后缀名
					String fileType = img.getOriginalFilename().substring(img.getOriginalFilename().indexOf("."));
					// 实际存储文件名
					String filename = UuidUtil.get32UUID() + fileType;
					// filepaths = "/"+ Const.IMAGEURL + filename;
					// pd.put("pictureURL", "/files"+filepaths);
					//path = "D:" + "\\weixin\\palmcare\\picture\\" + filename;
					path = "D:" + "\\weixin\\uploadfile\\uploadFiles\\uploadImgs\\" + filename;
					System.out.println(path);
					//String url = "http://www.guanai100.cn/palmcare/picture/" + filename;
					String url = "http://www.guanai100.cn/files/uploadFiles/uploadImgs/" + filename;
					if(pd.get("imageUrl")==null){
						pd.put("imageUrl", url);
					}else{
						pd.put("imageUrl", (String)pd.get("imageUrl") + ";" + url);
					}
					// 存放位置
					File destFile = new File(path);
					try {
						FileUtils.copyInputStreamToFile(img.getInputStream(), destFile);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	/**
	 * 编辑资讯
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editNotice(PageData pd) throws Exception {
		String content = pd.remove("content").toString();
		content = content.replaceAll("\r", "");
		content = content.replaceAll("\n", "");
		pd.put("content", content);
		dao.update("NoticeManageMapper.editNotice", pd);
	}
}