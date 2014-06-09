package com.xh.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;

public class JSONResponseUtil {

	private static Logger logger = Logger.getLogger(JSONResponseUtil.class);

	private static JsonConfig jsonConfig;

	public static void print(HttpServletResponse response, Object object,
			String[] excludeProperty) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(
					JSONObject.fromObject(object,
							getJsonConfig(excludeProperty)).toString());
		} catch (IOException e) {
			logger.error("", e);
		} catch(Exception e){
			logger.error("", e);
		}
	}
	
	/**
	 * @param response
	 * @param object
	 * @param excludeProperty
	 * @param filter
	 */
	public static void print(HttpServletResponse response, Object object,
			String[] excludeProperty, PropertyFilter filter) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(
					JSONObject.fromObject(object,
							getNoProxyJsonConfig(excludeProperty, filter)).toString());
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	public static void print(HttpServletResponse response, Object object,
			boolean success) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("object", object);
			map.put("success", success);
			response.getWriter().print(
					JSONObject.fromObject(map, getJsonConfig(null)).toString());
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	public static void print(HttpServletResponse response, String[] excludeProperty, Object object,
			boolean success) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("object", object);
			map.put("success", success);
			response.getWriter().print(
					JSONObject.fromObject(map, getJsonConfig(excludeProperty)).toString());
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void print(HttpServletResponse response,
			Collection collection, String[] excludeProperty) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(
					JSONArray.fromObject(collection,
							getJsonConfig(excludeProperty)).toString());
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public static void print(HttpServletResponse response, Object object) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			if( object instanceof java.util.Collection ){
				response.getWriter().print(JSONArray.fromObject(object, getJsonConfig(null)).toString());
			}else{
				response.getWriter().print(JSONObject.fromObject(object, getJsonConfig(null)).toString());
			}
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public static void print(HttpServletResponse response, Boolean success) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print("{success : " + success + "}");
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	private static JsonConfig getJsonConfig(String[] excludeProperty) {
		if (excludeProperty != null && excludeProperty.length > 0) {
			JsonConfig jConfig = new JsonConfig();
			jConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
			jConfig.setExcludes(excludeProperty);
			return jConfig;
		}
		if (jsonConfig == null) {
			jsonConfig = new JsonConfig();
			
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
			String[] excludes = { "class" };
			jsonConfig.setExcludes(excludes);
			
		}
		return jsonConfig;
	}

	private static JsonConfig getNoProxyJsonConfig(String[] excludeProperty,
			PropertyFilter filter) {
		JsonConfig jConfig = new JsonConfig();
		jConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
		if (excludeProperty != null && excludeProperty.length > 0) {
			jConfig.setExcludes(excludeProperty);
		}
		jConfig.setJsonPropertyFilter(filter);
		return jConfig;
	}
}
