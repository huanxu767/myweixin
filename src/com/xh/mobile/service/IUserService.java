package com.xh.mobile.service;


import java.util.List;
import java.util.Map;

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
	/**
	 * 根据openid插入用户
	 * @param openid
	 * @return
	 */
	public boolean addUser(String openid);
	/**
	 * 根据openid验证用户，没有则新增
	 * @param openid
	 * @return
	 */
	public Map checkUser(String openid);
	/**
	 * 更新用户信息
	 * @param params
	 * @return
	 */
	public int updateUser(Map params);
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> queryUsers();
	/**
	 * 查询用户信息
	 * @param id
	 * @return
	 */
	public Map queryUserById(String id);
}
