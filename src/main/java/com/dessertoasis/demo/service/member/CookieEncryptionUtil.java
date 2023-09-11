package com.dessertoasis.demo.service.member;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieEncryptionUtil {

    // 使用AES加密Cookie值
    public static void setEncryptedCookie(HttpServletResponse response, String cookieName, String cookieValue, SecretKey secretKey) throws Exception {
        // 創造AES加密器
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // 加密Cookie值
        byte[] encryptedBytes = cipher.doFinal(cookieValue.getBytes());

        // 將加密後的值轉換為Base64編碼
        String encryptedValue = Base64.getEncoder().encodeToString(encryptedBytes);

        // 創建Cookie並設置加密後的值
        Cookie cookie = new Cookie(cookieName, encryptedValue);
        response.addCookie(cookie);
    }

    // 使用AES解密Cookie值
    public static String getDecryptedCookieValue(Cookie cookie, SecretKey secretKey) throws Exception {
        if (cookie != null && cookie.getValue() != null) {
            // 解碼Base64值
            byte[] encryptedBytes = Base64.getDecoder().decode(cookie.getValue());

            // 創建AES解密器
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // 解密Cookie值
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // 將解密後轉換為字串
            return new String(decryptedBytes);
        }
        return null;
    }
}
