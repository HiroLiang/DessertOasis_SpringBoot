package com.dessertoasis.demo.service.member;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class TokenEncryptionUtil {

	public static String setEncryptedToken(String account,SecretKey secretKey) throws Exception {
		String accAddEmail = account;
		
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(accAddEmail.getBytes());

        String token = Base64.getEncoder().encodeToString(encryptedBytes);

        return token;
	}
	
	
	public static String decryptToken(String token,SecretKey secretKey) throws Exception {
		 byte[] tokenBytes = Base64.getDecoder().decode(token);

		 Cipher cipher = Cipher.getInstance("AES");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         byte[] decryptedBytes = cipher.doFinal(tokenBytes);

         String decryptedToken  = new String(decryptedBytes);
	
         return decryptedToken;
	}
	
	
	
}


