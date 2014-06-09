package com.xh.utils;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.xh.mobile.pojo.BaseResultBean;

/**
 * JSON工具类
 * @author xuhuan
 *
 */
public class JSONUtil {
	/**
	 * 转换object为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONString(BaseResultBean bean){
		Gson gson = new Gson();
		return gson.toJson(bean);
	}
	
	/**
	 * 将jsonArray转换为List<Bean>
	 * @param arr
	 * @param objClass
	 * @return
	 */
	public static List toBeanList(Object arr , Class objClass){
		return (List)JSONArray.toCollection( JSONArray.fromObject(arr),objClass);
	}
	
	/**
	 * 转换object为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONArrayString(Collection c){
		return JSONArray.fromObject(c).toString();
	}
	
	
}
