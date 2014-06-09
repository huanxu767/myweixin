package com.xh.mobile.pojo;

import java.util.Map;

/**
 * 增、删、改操作结果
 * @author xuhuan
 *
 */
public class BaseResultBean {
	protected boolean success;
	protected String errorCode;
	protected String errorMsg;
	protected Map map;
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
