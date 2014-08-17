package com.xh.mobile.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xh.mobile.dao.IUserDao;
import com.xh.mobile.pojo.User;
import com.xh.mobile.service.IUserService;

@Service(value = "userService")
public class UserServiceImpl implements IUserService{
	
	@Resource
	private IUserDao userDao;
	
	public User queryUserByOpenid(String openid) {
		return userDao.queryUserByOpenid(openid);
	}

	public boolean addUser(String openid) {
		
		return false;
	}

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
			}else{
				Long id = user.getId();
				List recordList = userDao.queryUserRecord(id);
				Long loseTimes=0L ;
				Long winTimes=0L ;
				if(recordList!=null && !recordList.isEmpty() ){
					for (int i = 0; i < recordList.size(); i++) {
						Map recordMap = (Map) recordList.get(i);
						System.out.println(recordMap.get("isWin"));
						if("0".equals(recordMap.get("isWin")+"")){
							//输钱
							loseTimes=(Long)recordMap.get("counts");
							map.put("loseTimes",loseTimes);
							map.put("loseMoney",recordMap.get("money"));
						}else{
							winTimes=(Long)recordMap.get("counts");
							map.put("winTimes",winTimes);
							map.put("winMoney",recordMap.get("money"));
						}
					}
					map.put("totalTimes", loseTimes+winTimes);
					if(winTimes != 0){
						NumberFormat formatter = new DecimalFormat("0.00%");
						Double x=new Double(new Double(winTimes)/(loseTimes+winTimes));
						String xx = formatter.format(x);
						map.put("recordPer", xx);
					}else{
						map.put("recordPer", "0");
					}
					
				}
				map.put("isNewer", false);
			}
			map.put("user", user);
		}
		System.out.println(map);
		return map;

	}

	public int updateUser(Map params) {
		System.out.println(params);
		return userDao.updateUser(params);
	}

	@Override
	public List<User> queryUsers() {
		// TODO Auto-generated method stub
		return userDao.queryUsers();
	}
	public static void main(String[] args) {
		NumberFormat formatter = new DecimalFormat("0.00%");
		Double x=new Double(2.0/3);
		String xx = formatter.format(x);
		System.out.println(xx);
	}

	@Override
	public Map queryUserById(String id) {
		return userDao.queryUserById(id);
	}
	
}
