package com.fh.service.weixin.xft;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

import net.sf.json.JSONArray;

@Service("xftRechargeService")
public class XftRechargeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 充值
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("xftMapper.insertRecharge", pd);
	}

	/**
	 * 更新充值记录flg
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateTerminal(PageData pd)throws Exception{
		dao.update("xftMapper.updateTerminal", pd);
	}
	
	/**
	 * 添加消费表记录
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void saveConsume(PageData pd) throws Exception {
		pd.put("schoolid", Integer.parseInt((String)pd.get("schoolid")));
		dao.save("xftMapper.addXyinf", pd);
	}
	
	/**
	 * 添加操作表数据
	 * @param pd
	 * @throws Exception
	 */
	public void insertxyoper(PageData pd) throws Exception {
		pd.put("schoolid", Integer.parseInt((String)pd.get("schoolid")));
		dao.save("xftMapper.insertxyoper", pd);
	}
	
	/**
	 * 更新鑫源基础信息表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updatexycustomer(PageData pd)throws Exception{
		pd.put("schoolid", pd.get("schoolid"));
		dao.update("xftMapper.updatexycustomer", pd);
	}
	
	
	/**
	 * 获取充值记录
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String queryRechargeAll(PageData pd) throws Exception {
		List<PageData> p =(List<PageData>) dao.findForList("xftMapper.queryRechargeAll", pd);
		return JSONArray.fromObject(p).toString();
	}
	
	/**
	 * 获取鑫源充值账号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryXftCustomer(String hexic) throws Exception {
		return (PageData) dao.findForObject("xftMapper.queryXftCustomer", hexic);
	}
	
	/**
	 * 流水账（记录）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> runHistory(PageData pd) throws Exception{
		
		List<PageData> list= (List<PageData>) dao.findForList("xftMapper.selectAllxyinf", pd);
        for(PageData p : list){
        	//if(Double.parseDouble(String.valueOf(p.get("JE"))) != 0){
        		p.put("LeftJE", Double.parseDouble(String.valueOf(p.get("LeftJE")))/100);
  			  	p.put("JE",(Double.parseDouble(String.valueOf(p.get("JE")))/100));
        	//}
        }
		return  list;
	}

	
	/**
	 * 修改状态和时间
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("xftMapper.updateRecharge", pd);
	}
	
	/**
	 * 获取开卡学生基础数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String selUserDateAll(PageData pd) throws Exception {
		@SuppressWarnings("unchecked")
		List<PageData> p =(List<PageData>) dao.findForList("xftMapper.selUserDateAll", pd);
		return JSONArray.fromObject(p).toString();
	}
	
	/**
	 * 更新基开卡学生础数据flg
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updUserDataFlg(PageData pd)throws Exception{
		dao.update("xftMapper.updUserDataFlg", pd);
	}
	
}
