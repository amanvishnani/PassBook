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
    {
        Encryption e=new Encryption();
        String cipherText=e.encryptData(etText.getText().toString());
        etText.setText(cipherText);
    }

    public void dData(View v)
    {

    }

}
