package com.study.test.pojo;
/**
 * 高校迎新用户
 * @author xuhuan
 *
 */
public class User {
	
	/** 邮箱*/
	private String email;
	/** 密码*/
	private String password;
	/** 第三方返回的编号**/
	private String openId;
	/** 第三方**/
	private String thirdParty;
	/** 绑定时间**/
	private String bindTime;
	
	
	/**存放cookie值**/
	private String acCookie;
	/**存放激活时间**/
	private String ssoActivityTime;
	/**存放错误码  001:调用接口异常   002:正确返回用户信息  003:其他异常**/
	private String errorCode;
	/**用户在线方式：100：本地用户  200：通行证用户 **/
	private int type;
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the ssoActivityTime
	 */
	public String getSsoActivityTime() {
		return ssoActivityTime;
	}
	/**
	 * @param ssoActivityTime the ssoActivityTime to set
	 */
	public void setSsoActivityTime(String ssoActivityTime) {
		this.ssoActivityTime = ssoActivityTime;
	}
	/**
	 * @return the acCookie
	 */
	public String getAcCookie() {
		return acCookie;
	}
	/**
	 * @param acCookie the acCookie to set
	 */
	public void setAcCookie(String acCookie) {
		this.acCookie = acCookie;
	}
	public User() {
		
	}
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	public String getBindTime() {
		return bindTime;
	}
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
	
}
