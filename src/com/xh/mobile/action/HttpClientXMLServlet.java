package com.xh.mobile.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.xh.utils.XMLUtils;

public class HttpClientXMLServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getMessage(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getMessage(req, resp);
	}
	private void getMessage(HttpServletRequest request, HttpServletResponse response){
		String requestXML = "";
		String responseXML = "";
		//读XML报文
		try {
			requestXML=XMLUtils.readXMLString(request);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("requestXML:"+requestXML);
		//返回XML报文
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xml;charset=UTF-8"); 
		//ceshi begin
		// 模拟成功的返回报文
		StringBuffer successReturn = new StringBuffer();
		successReturn.append("<?xml version='1.0' encoding='UTF-8'?>");
		successReturn.append("<InterBOSS>");
		successReturn.append("<Response>");
		successReturn.append("<RspType>0</RspType>");
		successReturn.append("<RspCode>0000</RspCode>");
		successReturn.append("<RspDesc>成功</RspDesc>");
		successReturn.append("</Response>");
		successReturn.append("<OrigDomain>BOSS</OrigDomain>");
		successReturn.append("<SvcCont><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		successReturn.append("<CustPromotionVerifyRlt>");
		successReturn.append("<OprNumb>11111111111111111111</OprNumb>");
		successReturn.append("<ReceivedOprNumb>2</ReceivedOprNumb>");
		successReturn.append("<OrderCode>3213</OrderCode>");
		successReturn.append("<IsVerifySuccess>1</IsVerifySuccess>");
		successReturn.append("<VerifyComment>1111</VerifyComment>");
		successReturn.append("</CustPromotionVerifyRlt>]]>");
		successReturn.append("</SvcCont>");
		successReturn.append("</InterBOSS>");
		String resStr =successReturn.toString();
	    OutputStream out;
		try {
			out = response.getOutputStream();
			OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
		    outWriter.write(resStr);
		    outWriter.flush();
		    outWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
