package com.common.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密工具
 * 
 * @author chenfc
 * 
 */
public class EncryptTool {
	private static final String Algorithm = "DESede";
	protected static final String DEFAULT_KEY = "fsti^d!B~";
	private String key;

	/**
	 * 加密函数
	 * 
	 * @param keybyte
	 *            byte[] 加密密钥，标准长度为24字节
	 * @param src
	 *            byte[] 被加密的数据
	 * @return byte[] 加密后的数据
	 * @exception Exception
	 */
	public byte[] encrypt(byte[] keybyte, byte[] src) throws Exception {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(initKey(keybyte), Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			throw e2;
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
			throw e3;
		}
	}

	/**
	 * 加密函数
	 * 
	 * @param key
	 *            String 加密密钥，标准长度为24字节
	 * @param src
	 *            String 被加密的数据
	 * @return String 加密后的数据
	 * @exception Exception
	 */
	public String encrypt(String key, String src) throws Exception {
		try {
			Base64 base64 = new Base64();
			byte[] encoded = encrypt(key.getBytes(), src.getBytes("UTF-8"));
			if (encoded != null)
				return base64.encode(encoded);
			else
				throw new Exception("加密过程发生异常");
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 解密函数
	 * 
	 * @param keybyte
	 *            byte[] 解密密钥，标准长度为24字节
	 * @param src
	 *            byte[] 被解密的数据
	 * @return byte[] 解密后的数据
	 * @exception Exception
	 */
	public byte[] decrypt(byte[] keybyte, byte[] src) throws Exception {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(initKey(keybyte), Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			throw e2;
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
			throw e3;
		}
	}

	/**
	 * 解密函数
	 * 
	 * @param key
	 *            String 解密密钥，标准长度为24字节
	 * @param src
	 *            String 被解密的数据
	 * @return String 解密后的数据
	 * @exception Exception
	 */
	public String decrypt(String key, String src) throws Exception {
		try {
			Base64 base64 = new Base64();
			byte[] srcBytes = decrypt(key.getBytes(), base64.decode(src));

			if (srcBytes != null)
				return new String(srcBytes, "UTF-8");
			else
				throw new Exception("解密过程发生异常");
		} catch (Exception e) {
			throw e;
		}

	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	// 初始化key，保证密钥的长度为24位
	private static byte[] initKey(byte[] key) {
		byte[] keyBytes = new byte[24];
		for (int i = 0, j = 0; i < 24; i++, j++) {
			if (j >= key.length)
				j = 0;
			keyBytes[i] = key[j];
		}
		return keyBytes;
	}

	/**
	 * 使用默认密码加密
	 * 
	 * @param src
	 * @return
	 */
	public String encrypt(String src) {
		try {
			return encrypt(getKey(), src);
		} catch (Exception e) {
			System.out.println("加密失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用默认密码解密
	 * 
	 * @param src
	 * @return
	 */
	public String decrypt(String src) {
		try {
			return decrypt(getKey(), src);
		} catch (Exception e) {
			System.out.println("解密失败");
			e.printStackTrace();
		}
		return null;
	}

	public String getKey() {
		if (key == null)
			key = DEFAULT_KEY;
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static void main(String[] args) {
		 //args=new String[]{"1","vasdop"};
		 args=new String[]{"2","E6a1sI69GQ8="};
		if ((null == args) || (args.length <= 1) || (args[0].equals("-help"))) {
			printHelp();
			return;
		}
		String result = "";
		EncryptTool tool = new EncryptTool();
		if (args.length > 2)
			tool.setKey(args[2]);
		if (args[0].equals("1")) {
			result = tool.encrypt(args[1]);
		} else {
			result = tool.decrypt(args[1]);
		}
		System.out.println(result);
	}

	private static void printHelp() {
		System.out.println("Usage: pwdtool <method> <string> [key]");
		System.out.println("<>must input,[] option");
		System.out.println("method: 1 encode 2 decode");
	}
}
