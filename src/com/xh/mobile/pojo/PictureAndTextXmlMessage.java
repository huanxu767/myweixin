package com.xh.mobile.pojo;

import java.util.List;

import org.dom4j.Element;

import com.xh.utils.BaseXmlObj;

/**
 * 推送图文信息报文
 * @author 许欢
 *
 */
public class PictureAndTextXmlMessage extends BaseXmlObj{

	
	/**
	 * <xml>
			<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[fromUser]]></FromUserName>
			<CreateTime>12345678</CreateTime>
			<MsgType><![CDATA[news]]></MsgType>
			<ArticleCount>2</ArticleCount>
			<Articles>
				<item>
					<Title><![CDATA[title1]]></Title> 
					<Description><![CDATA[description1]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
				<item>
					<Title><![CDATA[title]]></Title>
					<Description><![CDATA[description]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
			</Articles>
		</xml> 
	 */
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private List<Menu> articlesMap;
	
	public PictureAndTextXmlMessage(){
		init();
	}
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		super.setElement("ToUserName", toUserName);
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		super.setElement("FromUserName", fromUserName);
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		super.setElement("CreateTime", createTime);
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		super.setElement("MsgType", msgType);
	}
	public List<Menu> getArticlesMap() {
		return articlesMap;
	}

	public void setArticlesMap(List<Menu> articlesMap) {
		/**
		 * <item>
				<Title><![CDATA[title1]]></Title> 
				<Description><![CDATA[description1]]></Description>
				<PicUrl><![CDATA[picurl]]></PicUrl>
				<Url><![CDATA[url]]></Url>
			</item>
			<item>
				<Title><![CDATA[title2]]></Title> 
				<Description><![CDATA[description1]]></Description>
				<PicUrl><![CDATA[picurl]]></PicUrl>
				<Url><![CDATA[url]]></Url>
			</item>
		 */
		StringBuffer articlesBuffer = new StringBuffer();
		Element root = super.getDoc().getRootElement();
		Element articles = root.addElement("Articles");
		for (int i = 0; i < articlesMap.size(); i++) {
			articlesBuffer.setLength(0);
			Menu menu = articlesMap.get(i);
			Element item = articles.addElement("item");
			item.addElement("Title").setText(menu.getName()+"");
			item.addElement("Description").setText(menu.getDescription()+"");
			item.addElement("PicUrl").setText(menu.getImageUrl()+"");
			item.addElement("Url").setText(Constant.WEB_PATH+menu.getPageUrl()+"");
		}
		super.setElement("ArticleCount",articlesMap.size()+"");
	}
}
