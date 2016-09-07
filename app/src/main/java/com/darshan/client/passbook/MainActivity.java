package com.darshan.client.passbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {

    EditText etText,etKey;
    Button btnEncrypt,btnDecrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewComponents();
    }

    private void initializeViewComponents() {
    etText= (EditText) findViewById(R.id.etText);
        etKey= (EditText) findViewById(R.id.etKey);
        btnDecrypt= (Button) findViewById(R.id.btnDecrypt);
        btnEncrypt= (Button) findViewById(R.id.btnEncrypt);
    }

    public void eData(View v)
    {try {
        String text=etText.getText().toString();
        String key=etKey.getText().toString();

        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");



        // encrypt the text

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] encrypted = cipher.doFinal(text.getBytes());
        System.out.println("encryptied "+String.valueOf(encrypted));
        etText.setText(" "+encrypted);

    }
    catch (Exception e) {

        Toast.makeText(getApplicationContext()," "+e,Toast.LENGTH_LONG).show();

    }


    }

    public void dData(View v)
    {
        String text=etText.getText().toString();
        String key=etKey.getText().toString();

        try {
            byte [] encrypted=text.getBytes();
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(encrypted));

            etText.setText(" "+decrypted);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext()," "+e,Toast.LENGTH_LONG).show();

        }
    }

}
