package com.exam.resultprocess;

import java.security.*;

public class HashMD5 {

    public static String passwordHashing(final String msg) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(msg.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String value = Integer.toHexString(0xFF & aMessageDigest);
                while (value.length() < 2) {
                    value = "0" + value;
                }
                hexString.append(value);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
