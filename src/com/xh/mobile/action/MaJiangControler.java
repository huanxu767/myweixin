package com.xh.mobile.action;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xh.mobile.dao.IUserDao;
import com.xh.mobile.pojo.BaseResultBean;
import com.xh.mobile.pojo.User;

@Controller
@RequestMapping("/user")
public class MaJiangControler extends BaseActionController {

	private final static Logger logger = Logger.getLogger(MaJiangControler.class);
	
	@Resource
	private IUserDao userDao;
	
	/**
	 * 查询所有用户
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/queryUsers.do")
	public void queryUsers(HttpServletRequest request,
			HttpServletResponse response) {
		BaseResultBean baseResult = new BaseResultBean();
		Map<String, Object> map = new HashMap<String, Object>();
		//查询list
		List<User> users = userDao.queryUsers();
		map.put("users", users);
		baseResult.setSuccess(true);
		baseResult.setMap(map);
		outResult(response, baseResult);
	}
	/**
	 * 新增用户
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/addUser.do")
	public void addUser(HttpServletRequest request,
			HttpServletResponse response,User user) {
		BaseResultBean baseResult = new BaseResultBean();
		//查询list
		user.setCreateTime(new Date());
//		user.setMark("个人签名");
//		user.setMoblie(new Long("18652090350"));
		user.setName("这很棒");
		user.setOpenId("4546ewq54e56qw4eqwe");
		user.setSignature("个人的得得得");
		int result = userDao.addUser(user);
		System.out.println(result);
		baseResult.setSuccess(true);
//		baseResult.setMap(map);
		outResult(response, baseResult);
	}
	
	/**
	 * 查询所有用户
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/test.do")
	public void test(HttpServletRequest request,
			HttpServletResponse response,String[] userName) {
		BaseResultBean baseResult = new BaseResultBean();
		Enumeration en =  request.getParameterNames();
		while (en.hasMoreElements()) {
			Object object = (Object) en.nextElement();
			System.out.println(object.toString()+" : "+request.getParameter(object.toString()));
		}
		
		baseResult.setErrorMsg("OK");
		outResult(response, baseResult);
	}
}
