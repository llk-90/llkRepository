package com.fh.service.dataanalysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("coursewareAnalysisService")
public class CoursewareAnalysisService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列表
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listData(PageData pd) throws Exception {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		List<PageData> list = new ArrayList<PageData>();
		List<String> list_p = new ArrayList<String>();
		List<String> list_v = new ArrayList<String>();
		List<String> list_t = new ArrayList<String>();
		if (pd.getString("timeUnit").equals(Const.DAY)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("CoursewareAnalysisMapper.dayList", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				Date beginDate = sm.parse(start), endDate = sm.parse(end);
				List<Date> listDate = Tools.getDatesBetweenTwoDate(beginDate, endDate);
				// 进行遍历
				for (PageData pda : list) {
					if (pda.get("c_type") == Const.TYPE_PIC_CODE) {
						list_p.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_VIDEO_CODE) {
						list_v.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_TEXT_CODE) {
						list_t.add(pda.getString("dt"));
					}
				}
				for (Date dt : listDate) {
					String time = sm.format(dt);
					list = getList(list, list_p, list_v, list_t, time);
				}
				Collections.sort(list, new MapComparator());
			}
		} else if (pd.getString("timeUnit").equals(Const.WEEK)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("CoursewareAnalysisMapper.weekList", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				List<String> listDate= Tools.getWeekList(start, end);
				// 进行遍历
				for (PageData pda : list) {
					if (pda.get("c_type") == Const.TYPE_PIC_CODE) {
						list_p.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_VIDEO_CODE) {
						list_v.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_TEXT_CODE) {
						list_t.add(pda.getString("dt"));
					}
				}
				for (String dt : listDate) {
					String time = dt;
					list = getList(list, list_p, list_v, list_t, time);
				}
				Collections.sort(list, new MapComparator());
			}
		} else if (pd.getString("timeUnit").equals(Const.MONTH)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("CoursewareAnalysisMapper.monthList", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				List<String> listDate = Tools.getMonthBetween(start, end);
				// 进行遍历
				for (PageData pda : list) {
					if (pda.get("c_type") == Const.TYPE_PIC_CODE) {
						list_p.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_VIDEO_CODE) {
						list_v.add(pda.getString("dt"));
					} else if (pda.get("c_type") == Const.TYPE_TEXT_CODE) {
						list_t.add(pda.getString("dt"));
					}
				}
				for (String dt : listDate) {
					String time = dt;
					list = getList(list, list_p, list_v, list_t, time);
				}
				Collections.sort(list, new MapComparator());
			}
		}
		return list;
	}

	public List<PageData> getList(List<PageData> list, List<String> list_p, List<String> list_v, List<String> list_t,
			String time) {
		if (!list_p.contains(time)) {
			PageData pds = new PageData();
			pds.put("dt", time);
			pds.put("type_name", Const.TYPE_PIC_NAME);
			pds.put("c_type", Const.TYPE_PIC_CODE);
			pds.put("count", "0");
			list.add(pds);
		}
		if (!list_v.contains(time)) {
			PageData pds = new PageData();
			pds.put("dt", time);
			pds.put("type_name", Const.TYPE_VIDEO_NAME);
			pds.put("c_type", Const.TYPE_VIDEO_CODE);
			pds.put("count", "0");
			list.add(pds);
		}
		if (!list_t.contains(time)) {
			PageData pds = new PageData();
			pds.put("dt", time);
			pds.put("type_name", Const.TYPE_TEXT_NAME);
			pds.put("c_type", Const.TYPE_TEXT_CODE);
			pds.put("count", "0");
			list.add(pds);
		}
		return list;
	}

	static class MapComparator implements Comparator<PageData> {
		@Override
		public int compare(PageData o1, PageData o2) {
			// TODO Auto-generated method stub
			String b1 = o1.getString("dt");
			String b2 = o2.getString("dt");
			if (b1 != null) {
				return b1.compareTo(b2);
			}
			return 0;
		}

	}

}
