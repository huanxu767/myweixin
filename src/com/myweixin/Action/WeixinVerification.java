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
	// �Զ����TOken
	public static final String Token = "xuhuan_token_random_456212487956";

	/**
	 * ȷ����������΢�ŷ�����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		weixinService(request, response);
	}

	/**
	 * Function:΢�ŷ���
	 * 
	 * @author JLC
	 * @return
	 */
	public String weixinService(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// ΢�ż���ǩ��
			String signature = request.getParameter("signature");
			// ʱ���
			String timestamp = request.getParameter("timestamp");
			// �����
			String nonce = request.getParameter("nonce");
			// ����ַ���
			String echostr = request.getParameter("echostr");
			if (echostr != null && !"".equals(echostr.trim())) {
				echostr = checkAuthentication(signature, timestamp, nonce,
						echostr);
				// ��֤ͨ�������漴�ִ�
				response.getWriter().write(echostr);
				response.getWriter().flush();
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Function:΢����֤����
	 * 
	 * @param signature ΢�ż���ǩ��
	 * @param timestamp ʱ���
	 * @param nonce �����
	 * @param echostr ����ַ���
	 * @return
	 */
	private String checkAuthentication(String signature, String timestamp,
			String nonce, String echostr) {
		String result = "";
		// ����ȡ���Ĳ�����������
		String[] ArrTmp = { Token, timestamp, nonce };
		// ��΢���ṩ�ķ��������������ݽ�������
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// ���������ַ�������SHA-1����
		String pwd = Encrypt(sb.toString());
		if (pwd.equals(signature)) {
			try {
				System.out.println("΢��ƽ̨ǩ����Ϣ��֤�ɹ���");
				result = echostr;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("΢��ƽ̨ǩ����Ϣ��֤ʧ�ܣ�");
		}
		return result;
	}

	/**
	 * ��SHA-1�㷨�����ַ���������16���ƴ�
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
			System.out.println("����");
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