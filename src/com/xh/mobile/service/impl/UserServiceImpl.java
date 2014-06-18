package com.xh.mobile.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xh.mobile.dao.IUserDao;
import com.xh.mobile.pojo.User;
import com.xh.mobile.service.IUserService;

@Service(value = "userService")
public class UserServiceImpl implements IUserService{
	
	@Resource
	private IUserDao userDao;
	
	@Override
	public User queryUserByOpenid(String openid) {
		return userDao.queryUserByOpenid(openid);
	}

}
