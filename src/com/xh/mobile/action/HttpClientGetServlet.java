package com.xh.mobile.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpClientGetServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getMessage(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getMessage(req, resp);
	}
	public void getMessage(HttpServletRequest req, HttpServletResponse resp){
		resp.setContentType("text/html;charset=UTF-8");
		
		String name=req.getParameter("name");
		String love=req.getParameter("love");
		System.out.println("name:"+name+"  love:"+love);
		String retrunMessage="中文返回的message!";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			resp.getOutputStream().write(retrunMessage.getBytes("UTF-8"), 0,retrunMessage.getBytes().length);
			resp.getOutputStream().flush();
			resp.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
}
