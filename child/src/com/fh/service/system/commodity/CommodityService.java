package com.fh.service.system.commodity;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("commodityService")
public class CommodityService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public BigDecimal findPrice(PageData pData) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CommodityMapper.find", pData);
		return list != null && list.size() > 0 ? (BigDecimal) list.get(0).get("price") : null;
	}
	
	//保存订单数据
	public void addIbabyOrder(PageData pd) throws Exception {
		dao.save("CommodityMapper.addIbabyCommodityOrder", pd);
	}
	//更新订单数据
	@SuppressWarnings("unchecked")
	public void updateIbabyOrder(PageData pd) throws Exception {
		dao.update("CommodityMapper.updateIbabyorder", pd);
	}
	
	/**
	 * 下单
	 * 
	 * @param pData
	 * @throws Exception
	 */
	public void order(PageData pData) throws Exception {
		dao.save("CommodityMapper.order", pData);
	}

	@SuppressWarnings("unchecked")
	public boolean isResulted(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CommodityMapper.isResulted", pd);
		return list != null && list.size() > 0 ? true : false;
	}

	@SuppressWarnings({ "unchecked" })
	public void updateOrder(PageData pd) throws Exception {
		dao.update("CommodityMapper.updateOrder", pd);
		List<PageData> orders = (List<PageData>) dao.findForList("CommodityMapper.findOrder", pd);
		PageData order = orders != null && orders.size() > 0 ? orders.get(0) : null;
		if (order != null) {
			List<PageData> commoditys = (List<PageData>) dao.findForList("CommodityMapper.find", order);
			PageData commodity = commoditys != null && commoditys.size() > 0 ? commoditys.get(0) : null;
			if (commodity != null) {
				String user_id = order.getString("user_id");
				Integer commodity_id = (Integer) order.get("commodity_id");
				Integer c_time = (Integer) commodity.get("c_time");
				pd.clear();
				pd.put("user_id", user_id);
				pd.put("c_id", commodity_id);
				pd.put("c_time", c_time);
				dao.save("OrderMapper.offLinePay", pd);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public PageData findByCoursewareId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CommodityMapper.findByCoursewareId", pd);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public PageData findFileByCoursewareId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CommodityMapper.findFileByCoursewareId", pd);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
}
