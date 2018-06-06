package com.fh.service.system.courseware;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.controller.system.courseware.CoursewarePublishController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.CoursewarePublish;
import com.fh.util.MapPlus;
import com.fh.util.PageData;

@Service("coursewarePublishService")
public class CoursewarePublishService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 课件列表查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findAuthList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CoursewarePublishMapper.findlistPage", page);
	}

	/*
	 * 权限查询
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> powersList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CoursewarePublishMapper.powersList", pd);
	}

	/*
	 * 文件查询
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> fileList(String[] id) throws Exception {
		return (List<PageData>) dao.findForList("CoursewarePublishMapper.fileList", id);
	}

	/*
	 * 根据id查询对象
	 */
	public PageData findObject(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CoursewarePublishMapper.findObject", pd);
	}

	/*
	 * 保存课件
	 */
	public void save(CoursewarePublish csw) throws Exception {
		PageData pd = new PageData();
		try {
			pd.put("c_name", csw.getC_name());
			pd.put("c_detail", csw.getC_detail());
			pd.put("c_type", csw.getC_type());
			pd.put("c_grade", csw.getC_grade());
			pd.put("c_subject", csw.getC_subject());
			pd.put("c_commodity_id", csw.getC_commodity_id());
			dao.save("CoursewarePublishMapper.save", pd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 保存课件文件
	 */
	public void filesave(PageData pd) throws Exception {
		dao.save("CoursewarePublishMapper.filesave", pd);
	}

	/*
	 * 编辑课件
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("CoursewarePublishMapper.edit", pd);
	}

	/*
	 * 删除课件
	 */
	public void delete(String c_id) throws Exception {
		String[] cids = { c_id };
		// 删除浏览数据表的数据
		this.deletecount(cids);
		dao.delete("CoursewarePublishMapper.delete", c_id);
	}

	/*
	 * 批量删除课件
	 */
	public void deleteAll(String[] cid) throws Exception {
		this.deletecount(cid);
		dao.delete("CoursewarePublishMapper.deleteAll", cid);
	}

	/*
	 * 删除课件浏览表数据
	 */
	public void deletecount(String[] cid) throws Exception {
		dao.delete("CoursewarePublishMapper.deletecount", cid);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pageData) throws Exception {
		List<PageData> result = (List<PageData>) dao.findForList("CoursewarePublishMapper.list", pageData);
		MapPlus subs = CoursewarePublishController.subjects;
		result.stream().forEach(it -> it.put("subjectName", subs.get(it.get("subject"))));
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findById(PageData pageData) throws Exception {
		List<PageData> result = (List<PageData>) dao.findForList("CoursewarePublishMapper.findById", pageData);
		MapPlus subs = CoursewarePublishController.subjects;
		result.stream().forEach(it -> it.put("subjectName", subs.get(it.get("subject"))));
		return result;
	}

	@SuppressWarnings("unchecked")
	public void clickCount(PageData pageData) throws Exception {
		List<PageData> todayDatas = (List<PageData>) dao.findForList("CoursewarePublishMapper.todayCount", pageData);
		PageData todayData = todayDatas != null && todayDatas.size() > 0 ? todayDatas.get(0) : null;
		if (todayData != null) {
			// 修改，count累加1
			Integer id = (Integer) todayData.get("id");
			if (id != null) {
				pageData.clear();
				pageData.put("id", id);
				dao.update("CoursewarePublishMapper.updateCount", pageData);
			}
		} else {
			// 新增,count=1
			pageData.put("count", 1);
			dao.save("CoursewarePublishMapper.insertBrowseCountRecord", pageData);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean findStuByUserId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CoursewarePublishMapper.findStuByUserId", pd);
		return list != null && list.size() > 0;
	}

}
