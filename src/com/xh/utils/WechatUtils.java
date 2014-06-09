package com.xh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * 微信工具类
 * @author xuhuan
 *
 */
public class WechatUtils {
	private static String APPID="wx6b14095999d2676d";
	private static String SECRET="efdae94708b193b4f83a0820f25141f5";
	private static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token";
	
	/**
	 * 获取ACCESS_TOKEN
	 * @return
	 */
	public static String getAccessToken() throws Exception{
		String url = ACCESS_TOKEN_URL+"?grant_type=client_credential&appid="+ APPID + "&secret=" + SECRET;
		return getBasicRemoteMessage(url);
	}
	/**
	 * 获取制定URL的数据--基础方法
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getBasicRemoteMessage(String url) throws Exception{
		String responseMessage = null ;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			Long len = entity.getContentLength();
			if (len != -1 && len < 2048) {
				responseMessage = EntityUtils.toString(entity);
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				responseMessage = sb.toString();
			}
		}
		return responseMessage;
	}
	public static void main(String[] args) {
		try {
			System.out.println(getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
