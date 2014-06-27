package com.xh.mobile.pojo;

import java.util.Date;

/**
 * 用户
 * @author xuhuan
 *
 */
public class User {
	/**编号**/
	private long id;
	/**微信编号**/
	private String openId;
	/**名字**/
	private String name;
	/**头像地址**/
	private String image_url;
	/**雀神号**/
	private String god_number;
	/**号码**/
	private long moblie;
	/**签名**/
	private String signature;
	/**创建时间**/
	private Date createTime;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMoblie() {
		return moblie;
	}
	public void setMoblie(long moblie) {
		this.moblie = moblie;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGod_number() {
		return god_number;
	}
	public void setGod_number(String godNumber) {
		god_number = godNumber;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String imageUrl) {
		image_url = imageUrl;
	}
	
}
