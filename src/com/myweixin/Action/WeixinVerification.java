package com.myweixin.Action;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeixinVerification extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	// 自定义的TOken
	public static final String Token = "xuhuan_token_random_456212487956";

	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		weixinService(request, response);
	}

	/**
	 * Function:微信服务
	 * 
	 * @author JLC
	 * @return
	 */
	public String weixinService(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			if (echostr != null && !"".equals(echostr.trim())) {
				echostr = checkAuthentication(signature, timestamp, nonce,
						echostr);
				// 验证通过返回随即字串
				response.getWriter().write(echostr);
				response.getWriter().flush();
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Function:微信验证方法
	 * 
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param echostr 随机字符串
	 * @return
	 */
	private String checkAuthentication(String signature, String timestamp,
			String nonce, String echostr) {
		String result = "";
		// 将获取到的参数放入数组
		String[] ArrTmp = { Token, timestamp, nonce };
		// 按微信提供的方法，对数据内容进行排序
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// 对排序后的字符串进行SHA-1加密
		String pwd = Encrypt(sb.toString());
		if (pwd.equals(signature)) {
			try {
				System.out.println("微信平台签名消息验证成功！");
				result = echostr;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("微信平台签名消息验证失败！");
		}
		return result;
	}

	/**
	 * 用SHA-1算法加密字符串并返回16进制串
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
			System.out.println("错误");
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