package com.xh.mobile.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xh.utils.XMLUtils;

@Controller
public class CoreController extends MultiActionController {

	private final static Logger logger = Logger.getLogger(CoreController.class);
	public static final String Token = "xuhuan_token_random_456212487956";

	/**
	 * ajax登录
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/mobile/core.do")
	public void core(HttpServletRequest request,HttpServletResponse response) {
//		weixinService(request,response);
		coreService(request, response);
	}

	public void coreService(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 日志测试
		String requestXML = "";
		// 读XML报文
		try {
			requestXML = XMLUtils.readXMLString(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("WeixinVerification-requestXML:" + requestXML);
		logger.info("WeixinVerification-requestXML:" + requestXML);
	}

	public String weixinService(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			if (echostr != null && !"".equals(echostr.trim())) {
				echostr = checkAuthentication(signature, timestamp, nonce,echostr);
				response.getWriter().write(echostr);
				response.getWriter().flush();
			}
		} catch (Exception e) {

		}
		return null;
	}

	private String checkAuthentication(String signature, String timestamp,
			String nonce, String echostr) {
		String result = "";
		String[] ArrTmp = { Token, timestamp, nonce };
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		String pwd = Encrypt(sb.toString());
		if (pwd.equals(signature)) {
			try {
				result = echostr;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		}
		return result;
	}

	/**
	 * 
	 * @param strSrc
	 * @return
	 */
	private String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	private String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
