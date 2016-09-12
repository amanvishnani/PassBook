package com.darshan.client.passbook;

import android.widget.Toast;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DARSHAN on 11-09-2016.
 */
public class Encryption {
    String key;
    String encryptedString;

    public void generateKey(String keys) {

        this.key="1aa13f560k98uu6v";
    }


    public String encrypt(String data) {
        String encryptedText = "";
        try {
            String text = data;
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            System.out.println("encryptied " + String.valueOf(encrypted));
            encryptedText = encrypted.toString();
        } catch (Exception e) {
            return e + "";
        }


        return encryptedText;
    }
}
