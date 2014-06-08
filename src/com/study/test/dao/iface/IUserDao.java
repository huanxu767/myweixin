package com.study.test.dao.iface;

import java.util.List;

import com.study.test.pojo.User;


/**
 * 用户管理
 * @author xuhuan
 *
 */
public interface IUserDao 
{
	/**
	 * 根据email查询用户
	 * @param email
	 * @return
	 */
	public String queryUser(String email);
	
	public User  queryUserByEmail(String email);
	
	public List<User> queryUsersByOpenId(String openId);
}
