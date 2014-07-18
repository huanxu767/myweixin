package com.xh.mobile.action;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import sun.security.action.PutAllAction;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mobile/uploadHeadImage.do")
	public void uploadHeadImage(HttpServletRequest request,HttpServletResponse response,String openId){
		Map returnMap = new HashMap();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
        MultipartFile fileImage = multipartRequest.getFile("fileImage");  
        String path = request.getSession().getServletContext().getRealPath("/");
        logger.info("path:"+path);
        if(fileImage.getSize()>1024*1024*5){
        	//图片大于5M
        	returnMap.put("flag", false);
        	returnMap.put("msg", "图片大小不能超过5M");
        	returnMap.put("errorCode", 0001);
        	outResult(response, returnMap);
        	return ;
        }
		if(!fileImage.isEmpty()){  
//            System.out.println(fileImage);  
//            System.out.println(fileImage.getName());  
//            System.out.println(fileImage.getContentType()); //  image/jpeg  image/png  image/gif  
//            System.out.println(fileImage.getSize());  
//            System.out.println(fileImage.getOriginalFilename());
			String orginalFilename = fileImage.getOriginalFilename();
			if(!fileImage.getContentType().contains("image")){
				returnMap.put("flag", false);
	        	returnMap.put("msg", "必须上传图片文件");
	        	returnMap.put("errorCode", 0004);
			}else{
				  try {
					    String exp = orginalFilename.substring(orginalFilename.lastIndexOf("."));
						String totalPath = System.currentTimeMillis()+Math.round(Math.random() * 100)+exp;
						fileImage.transferTo(new File(path+"images"+File.separator+"uploadImages"+File.separator+totalPath));
						Map params = new HashMap();
						params.put("key", "image_url");
						params.put("value","images"+File.separator+"uploadImages"+File.separator+totalPath );
						params.put("openId", openId);
						userService.updateUser(params);
						logger.info(totalPath);
						//上传完成写入数据库
						returnMap.put("totalPath", totalPath);
						returnMap.put("flag", true);
					} catch (Exception e) {
						logger.error("uploadHeadImage:"+e);
			        	returnMap.put("flag", false);
			        	returnMap.put("msg", "图片上传出错，请重新再试");
			        	returnMap.put("errorCode", 0002);
					} 
			}
        }else{  
        	returnMap.put("flag", false);
        	returnMap.put("msg", "图片上传不能为空");
        	returnMap.put("errorCode", 0003);
        }  
		outResult(response, returnMap);
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
