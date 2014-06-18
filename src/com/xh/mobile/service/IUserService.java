package com.xh.mobile.service;


import com.xh.mobile.pojo.User;

/**
 * 微信用户管理接口
 * @author xuhuan
 *
 */
public interface IUserService {
	/**
	 * 根据openid查询用户信息
	 * @param openid
	 * @return
	 */
	public User queryUserByOpenid(String openid);
	
}
