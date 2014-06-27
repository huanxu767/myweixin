package com.xh.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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

	@Override
	public boolean addUser(String openid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map checkUser(String openid) {
		//验证是否存在该用户
		Map map = new HashMap();
		if(StringUtils.isEmpty(openid)){
			map.put("flag", false);
		}else{
			map.put("flag", true);
			User user = userDao.queryUserByOpenid(openid);
			if(user == null){
				map.put("isNewer", true);
				//若不存在则新增该用户
				userDao.addUser(openid);
				user = userDao.queryUserByOpenid(openid);
			}
			map.put("isNewer", false);
			map.put("user", user);
		}
		return map;
	}
	
}
