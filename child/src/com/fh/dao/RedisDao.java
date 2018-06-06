package com.fh.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fh.util.PageData;


/** 
 * 说明： 第2数据源例子接口
 * 创建人：FH Q313596790
 * 创建时间：2016-05-2
 * @version
 */
public interface RedisDao {
	
	/**新增(存储String)
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addString(String key, String value);
	
	/**拼接String
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean appendString(String key, String value);
	
	/**通过key获取String
	 * @param key
	 * @return
	 */
	public String getString(String key);
	
	/**新增(存储Map)
	 * @param key
	 * @param map
	 * @return
	 */
	public <T> Object addMap(String key, Map<String, T> map);
	
	/**获取map
	 * @param key
	 * @return
	 */
	public Map<String, PageData> getMap(String key);
	
	/**新增(存储List)
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> Object addList(String key, List<T> list);
	
	/**获取List
	 * @param key
	 * @return
	 */
	public <T> List<T> getList(String key);
	
	/**新增(存储set)
	 * @param key
	 * @param set
	 */
	public <T> Object addSet(String key, Set<T> set);
	
	/**获取Set
	 * @param key
	 * @return
	 */
	public <T> Set<T> getSet(String key);
	
	/**新增(存储Zset)
	 * @param key
	 * @param set
	 */
	public <T> Object addZset(String key, Set<T> set);
	
	/**获取ZSet
	 * @param key
	 * @return
	 */
	public <T> Set<T> getZset(String key);
	
	/**删除
	 * @param key
	 */
	public boolean delete(String key); 
	
	/**删除多个 
	 * @param keys
	 */
	public void delete(List<String> keys);
	
	/**修改
	 * @param pd
	 * @return
	 */
	public boolean edit(String key, String value);
	
	/**更新
	 * @param pd
	 * @return
	 */
	public boolean update(String key, String value);
	
    /** 
     * 清空redis 所有数据 
     * @return 
     */ 
    public String flushDB();
    
    /** 
     * 查看redis里有多少数据 
     */
    public long dbSize();
    
    /**
	 * 
	 * @param str 要进行检索的关键字
	 * @param pattern 关键字后面的通配符
	 * @return 模糊检索结果
	 */
	public List<?> getTypicalData(String str,String pattern);
}
