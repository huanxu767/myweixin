package com.xh.support;


import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author victor
 *
 */
public class Des {
	private static final String Algorithm = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish 
	
	//src为被加密的数据缓冲区（源） 
	public static byte[] encryptMode(byte[] keybyte, byte[] src) { 
		try { 
		//生成密钥 
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); 
			//加密 
			Cipher c1 = Cipher.getInstance(Algorithm); 
			c1.init(Cipher.ENCRYPT_MODE, deskey); 
			return c1.doFinal(src); 
		} 
			catch (java.security.NoSuchAlgorithmException e1) { 
			e1.printStackTrace(); 
		} 
			catch (javax.crypto.NoSuchPaddingException e2) { 
			e2.printStackTrace(); 
		} 
			catch (java.lang.Exception e3) { 
			e3.printStackTrace(); 
		} 
		return null; 
	} 

	//keybyte为加密密钥，长度为24字节 
	//src为加密后的缓冲区 
	public static byte[] decryptMode(byte[] keybyte, byte[] src) { 
		try { 
		//生成密钥 
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); 
			//解密 
			Cipher c1 = Cipher.getInstance(Algorithm); 
			c1.init(Cipher.DECRYPT_MODE, deskey); 
			return c1.doFinal(src); 
		} 
			catch (java.security.NoSuchAlgorithmException e1) { 
			e1.printStackTrace(); 
		} 
			catch (javax.crypto.NoSuchPaddingException e2) { 
			e2.printStackTrace(); 
		} 
			catch (java.lang.Exception e3) { 
			e3.printStackTrace(); 
		} 
		return null; 
	} 
	//转换成十六进制字符串 
	public static String byte2hex(byte[] b) { 
		String hs=""; 
		String stmp=""; 
		for (int n=0;n<b.length;n++) { 
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF)); 
			if (stmp.length()==1) hs=hs+"0"+stmp; 
			else hs=hs+stmp; 
			if (n<b.length-1) hs=hs+""; 
		} 
		return hs.toUpperCase(); 
	} 

	//16 进制 转 2 进制
	public static byte[] hex2byte(String hex) throws IllegalArgumentException {     
	    if (hex.length() % 2 != 0) {     
	        throw new IllegalArgumentException();     
	    }     
	    char[] arr = hex.toCharArray();     
	    byte[] b = new byte[hex.length() / 2];     
	    for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {     
	        String swap = "" + arr[i++] + arr[i];     
	        int byteint = Integer.parseInt(swap, 16) & 0xFF;     
	        b[j] = new Integer(byteint).byteValue();     
	    }     
	    return b;     
	} 
	
	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	//加密
	public static String Encrypt(String str, byte[] key){
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encrypt = encryptMode(key, str.getBytes());
		return byte2hex(encrypt);
	}
	
	//加密
	public static byte[] EncryptRetByte(byte[] src, byte[] key){
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encrypt = encryptMode(key, src);
		return encrypt;
	}

	//解密
	public static String Decrypt(String str, byte[] key){
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] decrypt = decryptMode(key, hex2byte(str)); 
		return new String(decrypt);
	}
	
	public static void main(String arg[]){
		
		String str = "c8hl9hai";
		String strKey = "0412026230720391";
		String s3 = Encrypt(str, hex2byte(strKey));
		String s4 = Decrypt(s3, hex2byte(strKey));
		System.out.println(s3);
		System.out.println(s4);
		
		
	}
}
