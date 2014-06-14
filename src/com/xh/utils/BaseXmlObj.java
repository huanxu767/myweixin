package com.xh.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class BaseXmlObj {
	private Document doc = null;
	private String rootName = "xml";
	public void init(String rootName) {
		doc = DocumentHelper.createDocument().addElement(rootName).getDocument();
	}
	public void init() {
		doc = DocumentHelper.createDocument().addElement(rootName).getDocument();
	}

	public String getElement(String key) {
		return this.doc.getRootElement().elementText(key);
	}

	public void setElement(String key, String value) {
		this.doc.getRootElement().addElement(key).addCDATA(value);
	}
	public void setTextElement(String key, String value) {
		this.doc.getRootElement().addElement(key).addText(value);
	}
	public void setElement(String parent, String key, String value) {
		if (this.doc.getRootElement().element(parent) == null) {
			this.doc.getRootElement().addElement(parent);
		}
		this.doc.getRootElement().element(parent).addElement(key).addText(value);
	}
	public void setElement(String grandParent,String parent, String key, String value) {
		if (this.doc.getRootElement().element(grandParent) == null) {
			this.doc.getRootElement().addElement(grandParent);
		}
		if (this.doc.getRootElement().element(grandParent).element(parent) == null) {
			this.doc.getRootElement().element(grandParent).addElement(parent);
		}
		this.doc.getRootElement().element(grandParent).element(parent).addElement(key).addText(value);
	}
	public String getXML() throws IOException {
		StringWriter strWriter = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(strWriter, format);
		writer.write(this.doc);
		writer.close();
		return strWriter.toString();
	}

	public void print() throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(System.out, format);
		writer.write(this.doc);
		writer.close();
	}

	public int size() {
		return this.doc.getRootElement().nodeCount();
	}

	public Document getDoc() {
		return this.doc;
	}

	public static void main(String[] args) throws IOException {
		BaseXmlObj ro = new BaseXmlObj();
		ro.init();
		ro.setElement("b", "B");
		ro.setElement("c", "C");
		ro.setElement("a","a1","a21");
		ro.setElement("a","a1","a31");
		
		ro.setElement("a","a1","a22");
		ro.setElement("a","a1","a32");
		System.out.println(ro.getXML());
	}
}
