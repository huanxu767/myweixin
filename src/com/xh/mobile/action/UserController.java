package com.xh.mobile.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.xh.mobile.pojo.BaseResultBean;
import com.xh.mobile.pojo.User;
import com.xh.mobile.service.IUserService;

@Controller
public class UserController extends BaseActionController {

	private final static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * 验证用户
	 * @param request
	 * @param response
	 * @param openid
	 */
	@RequestMapping(value = "/mobile/checkUser.do")
	public void checkUser(HttpServletRequest request,HttpServletResponse response,String openid) {
		Map userMap = userService.checkUser(openid);
		outResult(response, userMap);
	}
	/**
	 * 上传头像
	 * @param request
	 * @param response
	 * @param openId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mobile/uploadHeadImage.do")
	public void uploadHeadImage(HttpServletRequest request,HttpServletResponse response,String openId){
		Map returnMap = new HashMap();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
        MultipartFile fileImage = multipartRequest.getFile("fileImage");  
        String path = request.getSession().getServletContext().getRealPath("/");
//        logger.info("path:"+path);
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
						String webPath = System.currentTimeMillis()+Math.round(Math.random() * 100)+exp;
						String orignalPath = path+"images"+File.separator+"uploadImages"+File.separator+webPath;
						String bigPath = path+"images"+File.separator+"uploadImages"+File.separator+"big"+File.separator+webPath;
						String smallPath = path+"images"+File.separator+"uploadImages"+File.separator+"small"+File.separator+webPath;
						fileImage.transferTo(new File(orignalPath));
						Thumbnails.of(orignalPath).size(200,200).toFile(smallPath); 
						Thumbnails.of(orignalPath).size(500,500).toFile(bigPath); 
						Map params = new HashMap();
						params.put("key", "image_url");
						params.put("value",webPath);
						params.put("openId", openId);
						userService.updateUser(params);
//						logger.info(totalPath);
						//上传完成写入数据库
						returnMap.put("totalPath", webPath);
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
	 * 更新用户
	 * @param request
	 * @param response
	 * @param openid
	 * @param type
	 * @param value
	 */
	@RequestMapping(value = "/mobile/updateUser.do")
	public void updateUser(HttpServletRequest request,HttpServletResponse response,
			String openid,int type,String value) {
		Map params = new HashMap();
		params.put("value",value.trim());
		params.put("openId", openid);
		switch (type) {
		case 1:
			//更新名称
			params.put("key", "name");
			break;
		case 2:
			params.put("key", "god_number");
			break;
		case 3:
			params.put("key", "moblie");
			break;
		case 4:
			params.put("key", "signature");
			break;
		default:
			break;
		}
		userService.updateUser(params);
//		outResult(response, userMap);
		
	}
	
	/**
	 * 查询所有用户
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/mobile/queryUsers.do")
	public void queryUsers(HttpServletRequest request,
			HttpServletResponse response) {
		BaseResultBean baseResult = new BaseResultBean();
		Map<String, Object> map = new HashMap<String, Object>();
		//查询list
		List<User> users = userService.queryUsers();
		map.put("users", users);
		baseResult.setSuccess(true);
		baseResult.setMap(map);
		outResult(response, baseResult);
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
	public static void main(String[] args) throws IOException {
		//生成缩略图
		Thumbnails.of("c:/1.jpg") 
		.size(200, 200) 
		.toFile("c:/2.jpg"); 
	}
}
