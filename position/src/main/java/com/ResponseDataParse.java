package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class responseDataParse {
	
	/**
	 * 返回数据类型为xml格式
	 * 解析xml并转换成map
	 * @param xml
	 * @return
	 */
   public static Map<String,Object> parseXmlStr(String xml) {
	   Document doc = null;
	   Element childElement;
	   Iterator<Element> elementIter;
	   Map<String,Object> map = new HashMap<>();
	   try {
		   
		 doc = DocumentHelper.parseText(xml);
		 Element rootElement = doc.getRootElement(); // 获取根节点
		 Iterator<Element> childElements = rootElement.elementIterator();
		 //判断根节点下是否还有内容以及子节点，并遍历
		 while(childElements.hasNext()) {
			childElement= rootElement.element(childElements.next().getName());
			elementIter = childElement.elementIterator();
			//判断子节点中是否有其他子节点
			if(elementIter.hasNext()) {
				//while(elementIter.hasNext()) {
						 Element e = elementIter.next();
						 elementIter = e.elementIterator();
						 //判断子节点中是否含有子节点
						 if(elementIter.hasNext()) {
						 //遍历子节点中的子节点
						 while(elementIter.hasNext()) {
								 Element codeName = elementIter.next();
								 map.put(codeName.getName(), codeName.getStringValue());
							 }
						}else {
							//获取节点的内容与名称放入Map
							map.put(e.getName(), e.getStringValue());
						} 
					// }
				 }else {
					 map.put(childElement.getName(), childElement.getStringValue());
				 }
		 }
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	return map;
   }
   
   
   /**
	 *返回数据类型是json
	 *解析成map
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
   
   
}
