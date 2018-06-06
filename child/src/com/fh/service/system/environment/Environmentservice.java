package com.fh.service.system.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Chart;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service("environmentservice")
public class Environmentservice {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RegionMapper.datalist", pd);
	}
	
	/**
	 * 页面初期化
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Chart> listData(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		List<Chart> equList = new ArrayList<Chart>();
		pd.put("farmId", user.getFARM_ID());
		if("".equals(pd.getString("zd_id")) || null == pd.getString("zd_id")){
			String id =  (String) dao.findForObject("EnvironmentMapper.findFirst", pd) ; //获取第一个垮id
			pd.put("zd_id", id);
		}
		if(null== pd.getString("unit") || "".equals(pd.getString("unit")) || "day".equals(pd.getString("unit"))){
			equList = (List<Chart>) dao.findForList("EnvironmentMapper.listDayDate", pd); //获取一天的数据
			if(equList == null || equList.size() == 0){
				return null;
			}
			String equ_threshold = equList.get(0).getEqu_threshold();
			String low_threshold = equList.get(0).getLow_threshold();
			String cycle = equList.get(0).getCycle();
			String equ_plan = equList.get(0).getEqu_plan();
			String equ_parameter = equList.get(0).getEqu_parameter();
			String area_kid = equList.get(0).getArea_kid();
			List<String> hav = new ArrayList<String>();
			for (Chart c:equList){
				hav.add(c.getCreate_date());
			}
			List<String> all = new ArrayList<String>();
			for(int i =0;i<=23;i++){
				all.add((i <10) ?  "0"+String.valueOf(i): String.valueOf(i) );
			}
			all.removeAll(hav);
			for(String a :all){
				Chart chart = new Chart();
				chart.setCreate_date(a);
				chart.setEqu_data("-");
				chart.setEqu_threshold(equ_threshold);
				chart.setLow_threshold(low_threshold);
				chart.setCycle(cycle);
				chart.setEqu_plan(equ_plan);
				chart.setArea_kid(area_kid);
				chart.setEqu_parameter(equ_parameter);
				equList.add(chart);
			}
	        Collections.sort(equList, new Comparator<Chart>(){  
	  
	            /*  
	             * int compare(Chart o1, Chart o2) 返回一个基本类型的整型，  
	             * 返回负数表示：o1 小于o2，  
	             * 返回0 表示：o1和o2相等，  
	             * 返回正数表示：o1大于o2。  
	             */  
	            public int compare(Chart o1, Chart o2) {  
	              
	                //按照时间进行排序
	                if(Integer.valueOf(o1.getCreate_date()) > Integer.valueOf(o2.getCreate_date())){  
	                    return 1;  
	                }  
	                if(o1.getCreate_date() == o2.getCreate_date()){  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        });   
		}else if("month".equals(pd.getString("unit"))){
			equList = (List<Chart>) dao.findForList("EnvironmentMapper.listMonthData", pd); 
		}else if("year".equals(pd.getString("unit"))){
			equList = (List<Chart>) dao.findForList("EnvironmentMapper.listYearData", pd);
		}
		return equList;
	}
	
	/*
	 * 区域
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> quyulist(PageData page) throws Exception {
		return (List<PageData>) dao.findForList("RegionMapper.listQuYu", page);
	}
	/*
	 *农场
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listfarm(Page page) throws Exception {
		return (List<PageData>) dao.findForList("RegionMapper.listfarm", page);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> column(PageData pd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> columnList = (List<PageData>) dao.findForList("RegionMapper.findColumnById", pd);
		map.put("columnList", columnList);
		return (HashMap<String, Object>) map;
	}

	public void saveNode(PageData pd) throws Exception {
		dao.save("RegionMapper.saveNode", pd);
	}

	public void saveKidNode(PageData pd) throws Exception {
		dao.save("RegionMapper.saveKidNode", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("RegionMapper.edit", pd);
	}

	/*
	 * 删除一级节点
	 */
	public void deleteparent(PageData pd) throws Exception {
		dao.delete("RegionMapper.deleteparent", pd);
	}
	/*
	 * 删除子节点
	 */
	public void delNode(PageData pd) throws Exception {
		dao.delete("RegionMapper.delete", pd);
	}
	
	/*
	 * 删除子节点
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("RegionMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 添加一级节点保存
	 */
	public void save(PageData pd) throws Exception {
		dao.save("RegionMapper.save", pd);
	}
	

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RegionMapper.findById", pd);
	}

	public String saveSetUp(PageData pd) throws Exception{
		String rs = "failed";
		PageData kqwd = new PageData();
		kqwd.put("type", '0');
		kqwd.put("max",pd.getString("max_kqwd"));
		kqwd.put("min",pd.getString("min_kqwd"));
		kqwd.put("area_kid", pd.getString("area_kid"));
		kqwd.put("cycle", pd.getString("cycle"));
		PageData kqsd = new PageData();
		kqsd.put("type", '1');
		kqsd.put("max",pd.getString("max_kqsd"));
		kqsd.put("min",pd.getString("min_kqsd"));
		kqsd.put("area_kid", pd.getString("area_kid"));
		kqsd.put("cycle", pd.getString("cycle"));
		PageData trsf = new PageData();
		trsf.put("type", '2');
		trsf.put("max",pd.getString("max_trsf"));
		trsf.put("min",pd.getString("min_trsf"));
		trsf.put("area_kid", pd.getString("area_kid"));
		trsf.put("cycle", pd.getString("cycle"));
		PageData gzd = new PageData();
		gzd.put("type", '3');
		gzd.put("max",pd.getString("max_gzd"));
		gzd.put("min",pd.getString("min_gzd"));
		gzd.put("area_kid", pd.getString("area_kid"));
		gzd.put("cycle", pd.getString("cycle"));
		PageData trwd = new PageData();
		trwd.put("type", '4');
		trwd.put("max",pd.getString("max_trwd"));
		trwd.put("min",pd.getString("min_trwd"));
		trwd.put("area_kid", pd.getString("area_kid"));
		trwd.put("cycle", pd.getString("cycle"));
		PageData co2nd = new PageData();
		co2nd.put("type", '5');
		co2nd.put("max",pd.getString("max_co2nd"));
		co2nd.put("min",pd.getString("min_co2nd"));
		co2nd.put("area_kid", pd.getString("area_kid"));
		co2nd.put("cycle", pd.getString("cycle"));
		dao.update("EnvironmentMapper.saveSetUp", kqwd);
		dao.update("EnvironmentMapper.saveSetUp", kqsd);
		dao.update("EnvironmentMapper.saveSetUp", trsf);
		dao.update("EnvironmentMapper.saveSetUp", gzd);
		dao.update("EnvironmentMapper.saveSetUp", trwd);
		dao.update("EnvironmentMapper.saveSetUp", co2nd);
		rs = "success";
		return rs;
	}
	
	public String newSetUp(PageData pd) throws Exception{
		String rs = "failed";
		PageData kqwd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_id = user.getUSER_ID();
		kqwd.put("type", '0');
		kqwd.put("max",pd.getString("max_kqwd"));
		kqwd.put("min",pd.getString("min_kqwd"));
		kqwd.put("area_kid", pd.getString("area_kid"));
		kqwd.put("cycle", pd.getString("cycle"));
		kqwd.put("user_id", user_id);
		PageData kqsd = new PageData();
		kqsd.put("type", '1');
		kqsd.put("max",pd.getString("max_kqsd"));
		kqsd.put("min",pd.getString("min_kqsd"));
		kqsd.put("area_kid", pd.getString("area_kid"));
		kqsd.put("cycle", pd.getString("cycle"));
		kqsd.put("user_id", user_id);
		PageData trsf = new PageData();
		trsf.put("type", '2');
		trsf.put("max",pd.getString("max_trsf"));
		trsf.put("min",pd.getString("min_trsf"));
		trsf.put("area_kid", pd.getString("area_kid"));
		trsf.put("cycle", pd.getString("cycle"));
		trsf.put("user_id", user_id);
		PageData gzd = new PageData();
		gzd.put("type", '3');
		gzd.put("max",pd.getString("max_gzd"));
		gzd.put("min",pd.getString("min_gzd"));
		gzd.put("area_kid", pd.getString("area_kid"));
		gzd.put("cycle", pd.getString("cycle"));
		gzd.put("user_id", user_id);
		PageData trwd = new PageData();
		trwd.put("type", '4');
		trwd.put("max",pd.getString("max_trwd"));
		trwd.put("min",pd.getString("min_trwd"));
		trwd.put("area_kid", pd.getString("area_kid"));
		trwd.put("cycle", pd.getString("cycle"));
		trwd.put("user_id", user_id);
		PageData co2nd = new PageData();
		co2nd.put("type", '5');
		co2nd.put("max",pd.getString("max_co2nd"));
		co2nd.put("min",pd.getString("min_co2nd"));
		co2nd.put("area_kid", pd.getString("area_kid"));
		co2nd.put("cycle", pd.getString("cycle"));
		co2nd.put("user_id", user_id);
		dao.save("EnvironmentMapper.newSetUp", kqwd);
		dao.save("EnvironmentMapper.newSetUp", kqsd);
		dao.save("EnvironmentMapper.newSetUp", trsf);
		dao.save("EnvironmentMapper.newSetUp", gzd);
		dao.save("EnvironmentMapper.newSetUp", trwd);
		dao.save("EnvironmentMapper.newSetUp", co2nd);
		rs = "success";
		return rs;
	} 
	
	public PageData getLimit(String id ,String type) throws Exception{
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("type", type);
		PageData rpd = (PageData) dao.findForObject("EnvironmentMapper.getLimit", pd);
		return rpd;
	}
	
	public String getName(String id) throws Exception{
		return (String) dao.findForObject("EnvironmentMapper.findName", id);
	}
	
	public String savePlan(PageData pd) throws Exception{
		int num = (int) dao.findForObject("EnvironmentMapper.checkPlan", pd);
		if(num != 1){
			return "error";
		}else{
			dao.update("EnvironmentMapper.savePlan", pd);
		}
		return "success";
	}

}
