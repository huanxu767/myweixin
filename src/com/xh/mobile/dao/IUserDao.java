package com.xh.mobile.dao;

import java.util.List;
import java.util.Map;

import com.xh.mobile.pojo.User;

/**
 * 用户管理
 * 
 * @author xuhuan
 * 
 */
public interface IUserDao {
	/**
	 * 根据email查询用户
	 * 
	 * @param email
	 * @return
	 */
	public String queryUser(String email);

	public User queryUserByEmail(String email);

	public User queryUserByOpenid(String openid);

	public List<User> queryUsers();

	public int addUser(User user);

	public int addUser(String openid);

	public int updateUser(Map params);

	public List queryUserRecord(Long id);
	
	public Map queryUserById(String id);
}
