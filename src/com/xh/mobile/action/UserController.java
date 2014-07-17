package com.xh.mobile.action;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
	
	@RequestMapping(value = "/mobile/uploadHeadImage.do")
	public void uploadHeadImage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		   // 转型为MultipartHttpRequest：     
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
        // 获得文件：     
        MultipartFile fileImage = multipartRequest.getFile("fileImage");  
        
		if(!fileImage.isEmpty()){  
            System.out.println(fileImage);  
            System.out.println(fileImage.getName());  
            System.out.println(fileImage.getContentType()); //  image/jpeg  image/png  image/gif  
            System.out.println(fileImage.getSize());  
            System.out.println(fileImage.getOriginalFilename());  
            fileImage.transferTo(new File("d:/"+fileImage.getOriginalFilename()));  
        }else{  
        	System.out.println("error");
        }  
		
//		System.out.println("uploadHeadImage");
//		//转型为MultipartHttpRequest(重点的所在)  
//        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
//        //  获得第1张图片（根据前台的name名称得到上传的文件）   
//
//        //从表单域中获取文件对象的详细,如：文件名称等等
//        String name = multipartRequest.getParameter("name");
//        System.out.println("name: " + name);
//     // 获得文件名(全路径)
//        String realFileName = fileImage.getOriginalFilename();
//        realFileName = encodeFilename(realFileName,request);
//        System.out.println("获得文件名：" + realFileName);
//
//        // 获取当前web服务器项目路径
//        String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "fileupload/";
//        
//        // 创建文件夹
//        File dirPath = new File(ctxPath);
//        if (!dirPath.exists()) {
//         dirPath.mkdir();
//        }
//        //创建文件
//        File uploadFile = new File(ctxPath + realFileName);
//        FileCopyUtils.copy(fileImage.getBytes(), uploadFile);
	}
	/** 
     * 设置下载文件中文件的名称
     * @param filename 
     * @param request 
     * @return 
     */   
 public static String encodeFilename(String filename,HttpServletRequest request) {
  /**
   * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
   * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
   * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
   * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
   */
  String agent = request.getHeader("USER-AGENT");
  try {
   if ((agent != null) && (-1 != agent.indexOf("MSIE"))) { // IE浏览器
    String newFileName = URLEncoder.encode(filename, "UTF-8");
    newFileName = StringUtils.replace(newFileName, "+", "%20");
    if (newFileName.length() > 150) {
     newFileName = new String(filename.getBytes("GB2312"),
       "ISO8859-1");
     newFileName = StringUtils.replace(newFileName, " ", "%20");
    }
    return newFileName;
   }
   if ((agent != null) && (-1 != agent.indexOf("Mozilla"))) // 火狐浏览器
    return MimeUtility.encodeText(filename, "UTF-8", "B");
   return filename;
  } catch (Exception ex) {
   return filename;
  }
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
	/** 
     * 通过传入页面读取到的文件，处理后保存到本地磁盘，并返回一个已经创建好的File 
     * @param imgFile 从页面中读取到的文件 
     * @param typeName  商品的分类名称 
     * @param brandName 商品的品牌名称 
     * @param fileTypes 允许的文件扩展名集合 
     * @return 
     */  
	
}
