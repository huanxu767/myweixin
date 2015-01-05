/*
 * Copyright 2007 XWTECH INC. All Rights reserved
 * XWTECH INC.
 * 创建日期: 2007/12/21
 * 创建人：  毛旭峰
 * 修改履历：
 * <ul>
 * <li>
 * </ul>
 */

package com.xh.mobile.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xh.mobile.pojo.BaseResultBean;
import com.xh.utils.JSONUtil;


/**
 * 
 * 项目通用的Controller基类，留作扩展
 * 
 * @author hansx
 * @date 2012-11-01
 * 
 */
public class BaseActionController extends MultiActionController {

	public static Logger log=Logger.getLogger(BaseActionController.class);
	

	protected void doResponse(HttpServletResponse response, String jsonStr) {
		// CLogger.debugLog(JsonResponseUtil.class.getName(), "start write",
		// jsonStr);
		try {
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outResult(HttpServletResponse response, JSONObject jsonObj) {
		// response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}

	public void outResult(HttpServletResponse response, JSONArray jsonObj) {
		// response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}

	public void outResult(HttpServletResponse response, BaseResultBean bean) {
		// response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = JSONUtil.toJSONString(bean);
		out.print(str);
		out.flush();
		out.close();
	}
	public void outResult(HttpServletResponse response, String jsonString) {
		// response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(jsonString);
		out.flush();
		out.close();
	}
	public void outResult(HttpServletResponse response, Map map) {
		response.setContentType("text/plain;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(JSONUtil.toJSONString(map));
		out.flush();
		out.close();
	}
}
