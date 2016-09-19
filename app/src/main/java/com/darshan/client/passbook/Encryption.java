package com.darshan.client.passbook;

import android.widget.Toast;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DARSHAN on 11-09-2016.
 */
public class Encryption {
    String key;
    String encryptedString;

    public StringBuffer sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                //  System.out.println(hex);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
    public String encryptData(String base)
    {

        try{
            StringBuffer sb=sha256("Darshan");
            String key=sb.toString().substring(0,15);
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(base.getBytes());
            return String.valueOf(encrypted);


        }
        catch(Exception e) {
           return e+"";
        }
    }


}
