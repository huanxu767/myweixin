package com.xh.mobile.pojo;

import com.xh.utils.BaseXmlObj;
/**
 * 推送文本信息报文
 * @author 许欢
 *
 */
public class TextXmlMessage extends BaseXmlObj{
	/**
	 * 
	  	<xml>
			<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[fromUser]]></FromUserName>
			<CreateTime>12345678</CreateTime>
			<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[你好]]></Content>
		</xml>
	 */
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String content;
	
	public TextXmlMessage() {
		init();
	}
	public void setToUserName(String toUserName) {
		super.setElement("ToUserName", toUserName);
	}

	public void setFromUserName(String fromUserName) {
		super.setElement("FromUserName", fromUserName);
	}

	public void setCreateTime(String createTime) {
		super.setElement("CreateTime", createTime);
	}

	public void setMsgType(String msgType) {
		super.setElement("MsgType", msgType);
	}

	public void setContent(String content) {
		super.setElement("Content", content);
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getContent() {
		return content;
	}
	public static void main(String[] args) {
		
	}
}
