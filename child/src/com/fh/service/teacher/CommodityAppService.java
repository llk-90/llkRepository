package com.fh.service.teacher;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;;


@Service("commodityAppService")
public class CommodityAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> commodityList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CommodityAppMapper.findlistPage", page);
	}
	
	/**
	 * 新增商品信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.update("CommodityAppMapper.add", pd);
	}
	
	/**
	 * 商品编号信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public String findmaxId(PageData pd) throws Exception {
		return (String) dao.findForObject("CommodityAppMapper.findMaxId", pd);
	}
	/**
	 * 根据商品id查询信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findBycommodityId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CommodityAppMapper.findBycommodityId", pd);
	}
	
	/**
	 * 更新商品信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public boolean editE(CommonsMultipartFile file, String name, HttpServletRequest request,
			PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", user.getUSER_ID());

		// 路径 文件名
		String filepaths = "";
		// 附件接收
		if (!file.isEmpty()) {
			// 文件后缀名
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			// 实际存储文件名
			String filename = name + fileType;// 取32位UUID作为文件名
			
			filepaths = "/uploadFiles/annoPic/" + filename;
			pd.put("ibaby_commodity_imgurl", "/files"+filepaths);
	
			String path = Tools.readTxtFile(Const.FILENAME) + filepaths;
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		Timestamp timestamp = new Timestamp(new Date().getTime());
		pd.put("ibaby_update_time", timestamp);
	
		dao.update("CommodityAppMapper.updateCommodity", pd);
		return true;
	}
}