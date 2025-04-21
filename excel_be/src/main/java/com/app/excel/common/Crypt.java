package com.app.excel.common;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Crypt {
	
	private static final String ALGO = "AES"; // Default uses ECB PKCS5Padding
	private static final String secretKey = "mustbe16byteskey";

	public static String encrypt(String Data) throws Exception {
		String encodedBase64Key = Crypt.encodeKey(secretKey);
		Key key = generateKey(encodedBase64Key);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}

	public static String decrypt(String strToDecrypt) {
	try {
		String encodedBase64Key = Crypt.encodeKey(secretKey);
		Key key = generateKey(encodedBase64Key);
		Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
		//System.out.println("Error while decrypting: " + e.toString());
	}
	return null;
	}

	private static Key generateKey(String secret) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
		Key key = new SecretKeySpec(decoded, ALGO);
		return key;
	}

	public static String decodeKey(String str) {
		byte[] decoded = Base64.getDecoder().decode(str.getBytes());
		return new String(decoded);
	}
	
	public static String encodeKey(String str) {
		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
		return new String(encoded);
	}
	
	/**public static void main(String a[]) throws Exception {
	//System.out.println("encrypt of str = " + Crypt.encrypt("123"));
	//System.out.println("encodeKey of str = " + Crypt.encodeKey("thisisd3op"));
		System.out.println("decrypt of str = " + Crypt.decrypt("77gfLIYbqN5+ZP8QlfC0vp53X8mFWz2TXbgC2KgNcas="));
	}**/
	

}