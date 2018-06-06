package com.fh.service.dataanalysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.dataanalysis.CoursewareAnalysisService.MapComparator;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("newDataService")
public class NewDataService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public Map<String, Object> list(PageData pd) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sday = new SimpleDateFormat("MM-dd");
		List<PageData> list = new ArrayList<PageData>();
		int before = 0;
		List<String> listData = new ArrayList<String>();
		if("class".equals(pd.getString("type"))){
			pd.put("type", "4");
		}else if ("school".equals(pd.getString("type"))) {
			pd.put("type", "2");
		}
		
		before = (int) dao.findForObject("NewDataMapper.before", pd);
		if (pd.getString("timeUnit").equals(Const.DAY)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("NewDataMapper.daylist", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				Date beginDate = sm.parse(start), endDate = sm.parse(end);
				List<Date> listDate = Tools.getDatesBetweenTwoDate(beginDate, endDate);
				
				for (PageData pda : list) {
					listData.add(pda.getString("dt"));
				}
				
				for (Date dt : listDate) {
					String time = sday.format(dt);
					if (!listData.contains(time)) {
						PageData pds = new PageData();
						pds.put("dt", time);
						pds.put("count", "0");
						list.add(pds);
					}
				}
			}
			Collections.sort(list, new MapComparator());
		}else if (pd.getString("timeUnit").equals(Const.WEEK)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("NewDataMapper.weeklist", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				List<String> listDate= Tools.getWeekList(start, end);
				
				for (PageData pda : list) {
					listData.add(pda.getString("dt"));
				}
				
				for (String dt : listDate) {
					String time = dt;
					if (!listData.contains(time)) {
						PageData pds = new PageData();
						pds.put("dt", time);
						pds.put("count", "0");
						list.add(pds);
					}
				}
			}
			Collections.sort(list, new MapComparator());
		}else if (pd.getString("timeUnit").equals(Const.MONTH)) {
			if (pd.getString("time_start").isEmpty() && pd.getString("time_end").isEmpty()) {
				pd.put("time_start", Tools.getTimeStart());
				pd.put("time_end", Tools.getTime());
			}
			list = (List<PageData>) dao.findForList("NewDataMapper.monthlist", pd);
			if (!list.isEmpty()) {
				// 获取到日期集合
				String start = pd.getString("time_start"), end = pd.getString("time_end");
				List<String> listDate = Tools.getMonthBetween(start, end);
				
				for (PageData pda : list) {
					listData.add(pda.getString("dt"));
				}
				
				for (String dt : listDate) {
					String time = dt;
					if (!listData.contains(time)) {
						PageData pds = new PageData();
						pds.put("dt", time);
						pds.put("count", "0");
						list.add(pds);
					}
				}
			}
			Collections.sort(list, new MapComparator());
		}
		map.put("list", list);
		map.put("before", before);
		return map;
	}
}
