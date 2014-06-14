package com.xh.mobile.pojo;

import java.util.Date;

/**
 * 菜单
 * @author xuhuan
 *
 */
public class Menu {
	/** 编号*/
	private long id;
	/** 父编号*/
	private long parentId;
	/** 名称*/
	private String name;
	/** 描述*/
	private String description;
	/** 页面地址*/
	private String pageUrl;
	/** 图片地址*/
	private String imageUrl;
	/** 创建日期*/
	private Date createTime;
	/** 菜单类型 view or click */
	private String type;
	/**激活状态 0 未激活 1激活*/
	private int isActive;
	/** 事件KEY值，与自定义菜单接口中KEY值对应*/
	private String eventKey;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
