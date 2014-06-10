package com.xh.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * XML处理
 * @author xuhuan
 *
 */
public class XMLUtils {
	/**
	 * 从request获得XML格式的字符串
	 * @param request
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String readXMLString(HttpServletRequest request) throws DocumentException, IOException{
		SAXReader reader = new SAXReader();
		InputStream iStream = null ;
		Document document;
		try{
			iStream = request.getInputStream();
			document = reader.read(iStream);
		}finally{
			if(iStream !=null){
				iStream.close();
			}
		}
		return document.asXML();
	}
	/**
	 * 从request获得XML格式的document
	 * @param request
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document readXMLDocument(HttpServletRequest request) throws DocumentException, IOException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(request.getInputStream());
		return document;
	}
}
