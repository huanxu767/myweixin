package com.xh.mobile.pojo;

import java.util.Date;

/**
 * 代码表
 * @author xuhuan
 *
 */
public class Code {
	/** 编号*/
	private long id;
	/** 名称*/
	private String name;
	/** 内容*/
	private String content;
	/** 创建日期*/
	private Date createTime;
	/** 截止日期*/
	private Date lastUpdateTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
