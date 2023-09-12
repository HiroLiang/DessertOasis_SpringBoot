package com.dessertoasis.demo.model.member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenGenerator {
    public static String generateToken(String account, String email) {
        // account和username组合成字串
        String combinedString = account + email;

        try {
            // 建立SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            
            // 計算雜湊
            byte[] hashBytes = messageDigest.digest(combinedString.getBytes());
            
            // 使用Base64編碼轉換成字串
            String token = Base64.getEncoder().encodeToString(hashBytes);
            
            return token;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}