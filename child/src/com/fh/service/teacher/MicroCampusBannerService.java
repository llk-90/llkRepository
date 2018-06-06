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
import com.fh.util.ErrorMsg;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("microCampusBannerService")
public class MicroCampusBannerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> bannerList(Page page) throws Exception {		 
		return (List<PageData>) dao.findForList("MicroCampusBannerMapper.findlistPage", page);
	}	
	
	/**
	 * 查询schoolId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String findSchoolId(String userId) throws Exception{
		return (String) dao.findForObject("MicroCampusBannerMapper.findSchoolId", userId);
	}
	
	/**
	 * 添加banner图
	 * @param pd
	 * @param img
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addBannerPicture(PageData pd,MultipartFile img) throws Exception{
		String path = null;	
		try {
			// 保存图片
			this.saveImage(img, path, pd);
			// 数据更新
			dao.save("MicroCampusBannerMapper.addmicrocampusbanner", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:../microCampusBanner/list.do", null);
	}
	
	/**
	 * 保存图片
	 * @param img
	 * @param path
	 * @param pd
	 */
	public void saveImage(MultipartFile img, String path, PageData pd) {
		if (!img.isEmpty()) {
			// 文件后缀名
			String fileType = img.getOriginalFilename().substring(img.getOriginalFilename().indexOf("."));
			// 实际存储文件名
			String filename = UuidUtil.get32UUID() + fileType;
			// filepaths = "/"+ Const.IMAGEURL + filename;
			// pd.put("pictureURL", "/files"+filepaths);
			path = "D:" + "\\weixin\\palmcare\\microCampuspicture\\" + filename;
			String url="http://www.guanai100.cn/palmcare/microCampuspicture/"+filename; 
			pd.put("imageUrl", url);
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(img.getInputStream(), destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除轮播图
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception{
		dao.delete("MicroCampusBannerMapper.deletemicrocampusinfo", pd);
	}
	
	/**
	 * 编辑迁移地址
	 * @param pd
	 * @throws Exception
	 */
	public void editMoveUrl(PageData pd) throws Exception{
		dao.update("MicroCampusBannerMapper.editMoveUrl", pd);
	}
	
}