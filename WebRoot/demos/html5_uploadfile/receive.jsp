<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="sun.misc.BASE64Decoder"%>
<%@page import="java.io.*"%>
<%@page import="java.net.URLDecoder"%>
<%! 
// 本文件：/receive.jsp 
// 图片存放路径 
String photoPath = "D:/"; 
File photoPathFile = new File(photoPath); 
// references: http://blog.csdn.net/remote_roamer/article/details/2979822 
private boolean saveImageToDisk(byte[] data,String imageName) throws IOException{ 
	int len = data.length; 
	// 
	// 写入到文件 
	FileOutputStream outputStream = new FileOutputStream(new File(photoPathFile,imageName)); 
	outputStream.write(data); 
	outputStream.flush(); 
	outputStream.close(); 
	// 
	return true; 
} 
private byte[] decode(String imageData) throws IOException{ 
BASE64Decoder decoder = new BASE64Decoder(); 
byte[] data = decoder.decodeBuffer(imageData); 
for(int i=0;i<data.length;++i) 
{ 
if(data[i]<0) 
{ 
//调整异常数据 
data[i]+=256; 
} 
} 
// 
return data; 
} 
%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<% 
//如果是IE，那么需要设置为text/html,否则会弹框下载 
//response.setContentType("text/html;charset=UTF-8"); 
response.setContentType("application/json;charset=UTF-8"); 
// 
String imageName = request.getParameter("imagename"); 
String imageData = request.getParameter("imagedata"); 
int success = 0; 
String message = ""; 
if(null == imageData || imageData.length() < 100){ 
// 数据太短，明显不合理 
message = "上传失败,数据太短或不存在"; 
} else { 
// 去除开头不合理的数据 
imageData = imageData.substring(30); 
imageData = URLDecoder.decode(imageData,"UTF-8"); 
//System.out.println(imageData); 
byte[] data = decode(imageData); 
int len = data.length; 
int len2 = imageData.length(); 
if(null == imageName || imageName.length() < 1){ 
imageName = System.currentTimeMillis()+".png"; 
} 
saveImageToDisk(data,imageName); 
// 
success = 1; 
message = "上传成功,参数长度:"+len2+"字符，解析文件大小:"+len+"字节"; 
} 
// 后台打印 
System.out.println("message="+message); 
%>
{ "message": "<%=message %>", "success":
<%=success %>
}
