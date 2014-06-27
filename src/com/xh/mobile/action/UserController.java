package com.xh.mobile.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xh.mobile.service.IUserService;

@Controller
public class UserController extends BaseActionController {

	private final static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	
	@RequestMapping(value = "/mobile/checkUser.do")
	public void checkUser(HttpServletRequest request,HttpServletResponse response,String openid) {
		Map userMap = userService.checkUser(openid);
		outResult(response, userMap);
	}
	
	/**
	 * ajax登录
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/test/login.do")
	public ModelAndView ordertest(HttpServletRequest request,
			HttpServletResponse response,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		//日志测试 
		logger.info("name = "+name);
		/**
		String flag = userDao.queryUser(name);
		System.out.println(flag);
		map.put("flag", flag);
		**/
		/**
		 * 查询单个类
		User user = userDao.queryUserByEmail(name);
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		**/
		//查询list
		String openId="10";
//		List<User> users = userDao.queryUsersByOpenId(openId);
//		System.out.println(users.size());
		return new ModelAndView("/test/loginsuccess.jsp",map);
	}
}
