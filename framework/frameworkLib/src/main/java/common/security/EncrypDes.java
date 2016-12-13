package common.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncrypDes {
	public Log logger = LogFactory.getLog(this.getClass());

	private Cipher c;
	private byte[] cipherByte;
	SecretKey myDesKey;

	public EncrypDes(String key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, NoSuchProviderException,
			InvalidKeyException, UnsupportedEncodingException {
		this(key, true);
	}

	public EncrypDes(String key, boolean md5) throws NoSuchAlgorithmException,
			NoSuchPaddingException, NoSuchProviderException,
			InvalidKeyException, UnsupportedEncodingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		c = Cipher.getInstance("DES");
		if (md5) {
			byte[] keyData = getEnKey(key);
			// System.out.println("MD5="+new String(keyData));
			myDesKey = new SecretKeySpec(keyData, "DES");
		} else {
			// byte[] keyData =key.getBytes();
			DESKeySpec dks = new DESKeySpec(key.getBytes("UTF-8"));
			// System.out.println("MD5="+new String(keyData));
			myDesKey = new SecretKeySpec(dks.getKey(), "DES");
		}

	}

	public String encrypt(String data) throws Exception {
		if(data==null) return null;
		byte[] bt = encrypt(data.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}

	/**
	 * 根据键值进行解密
	 */
	public String decrypt(String data) throws IOException, Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf);
		return new String(bt);
	}

	public byte[] encrypt(byte[] src) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, myDesKey);
		// byte[] src = str.getBytes();
		// 加密，结果保存进cipherByte
		cipherByte = c.doFinal(src);
		return cipherByte;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decrypt(byte[] buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, myDesKey);
		cipherByte = c.doFinal(buff);
		return cipherByte;
	}

	private byte[] md5(String strSrc) {
		byte[] returnByte = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnByte = md5.digest(strSrc.getBytes("GBK"));
		} catch (Exception e) {
			logger.error("对密钥加密失败", e);
		}
		return returnByte;
	}

	private byte[] getEnKey(String spKey) {
		byte[] desKey = null;
		try {
			byte[] desKey1 = md5(spKey);
			desKey = new byte[8];
			int i = 0;
			while (i < desKey1.length && i < 8) {
				desKey[i] = desKey1[i];
				i++;
			}
			if (i < 8) {
				desKey[i] = 0;
				i++;
			}
		} catch (Exception e) {
			logger.error("对密钥加密失败", e);
		}
		return desKey;
	}

	public static void main(String[] args) throws Exception {
		EncrypDes de1 = new EncrypDes("LY123456", false);
		String msg = "test";
		System.out.println("明文是:" + msg);
		String encontent = de1.encrypt(msg);
		System.out.println("加密后:" + encontent);
		String decontent = de1.decrypt(encontent);

		System.out.println("解密后:" + decontent);
	}

}
