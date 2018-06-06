package com.fh.service.weixin.ibaby;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("shopInfoService")
public class ShopInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("GetShopInfoMapper.list", pd);
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal getIbabyPrice(PageData pData) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("GetShopInfoMapper.find", pData);
		return list != null && list.size() > 0 ? (BigDecimal) list.get(0).get("ibaby_commodity_price") : null;
	}
	
	@SuppressWarnings("unchecked")
	public String getIbabyComNam(PageData pData) throws Exception {
		String list = (String) dao.findForObject("GetShopInfoMapper.findComNam", pData);
		return list;
	}
}

