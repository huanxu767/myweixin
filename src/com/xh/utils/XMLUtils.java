package com.xh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
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
	
	/**
	 * 从request获得解析的MAP
	 * @param request
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map readXMLToMap(HttpServletRequest request) throws DocumentException, IOException{
		Map map = new HashMap();
		SAXReader reader = new SAXReader();
		Document document = reader.read(request.getInputStream());
		Element root=document.getRootElement(); 
		Iterator it=root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			map.put(element.getName(),element.getTextTrim());
		}
		return map;
	}
}
